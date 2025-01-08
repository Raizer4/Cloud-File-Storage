package com.project.minio;

import com.project.exceptions.SearchQueryException;
import com.project.mapper.FileMapper;
import com.project.minio.dto.DirRequest;
import com.project.minio.dto.FileRenameRequest;
import com.project.minio.dto.FileRequest;
import com.project.minio.dto.FileResponse;
import com.project.user.UserRepository;
import com.project.util.CountFilesSize;
import com.project.util.converter.FileResponseConverter;
import com.project.util.converter.FileSizeConverter;
import com.project.util.strings.StringFileUtils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.minio.messages.Item;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioRepositoryFacade minioRepository;
    private final UserRepository userRepository;
    private final FileMapper fileMapper;

    public void uploadFile(FileRequest fileRequest, Integer userId){
        minioRepository.uploadFile(fileRequest.getPath(), fileRequest.getFiles(), userId);
        BigInteger sizeOfFiles = CountFilesSize.countFileSize(Arrays.asList(fileRequest.getFiles()));
        userRepository.increaseUserCapacity(sizeOfFiles,userId);
    }

    public void uploadEmptyDir(DirRequest dirRequest, Integer userId) {
         minioRepository.uploadEmptyDir(dirRequest.getDirName(),dirRequest.getPath(), userId);
    }

    public BigDecimal getUserCapacity(Integer userId) {
        return FileSizeConverter.convertBytesToMB(userRepository.findUserCapacity(userId).orElse(BigInteger.ZERO));
    }

    public List<FileResponse> getUserFiles(String path, Integer userId) {
        List<Item> objectsFromStorage = minioRepository.getFilesFromPath(path, userId);
        List<FileResponse> filesResponse = fileMapper.mapListItemsFromStorageToListFileResponse(objectsFromStorage);
        FileResponseConverter.convertFieldsFileResponse(filesResponse);
        return filesResponse;
    }

    public List<FileResponse> searchFiles(String filePrefix, Integer userId) {
        if (StringUtils.isBlank(filePrefix))
            throw new SearchQueryException("You search query for file is wrong");
        List<Item> userFilesByPrefix = minioRepository.getFilesRecursiveFromUserDirectory(userId);
        List<FileResponse> filesResponse = fileMapper.mapListItemsFromStorageToListFileResponse(userFilesByPrefix);
        FileResponseConverter.convertFieldsFileResponse(filesResponse);
        return filesResponse.stream()
                .filter(f -> f.getObjectName().toLowerCase().contains(filePrefix.toLowerCase()))
                .toList();
    }

    public InputStream downloadFile(String filePath, Integer userId) {
        return minioRepository.downloadFile(filePath, userId);
    }

    public String removeFile(String filePath, Boolean isDir, Integer userId) {
        List<Item> files = minioRepository.getFilesRecursiveFromPath(filePath, userId);
        minioRepository.removeFile(filePath, isDir, userId);
        BigInteger sizeOfFile = CountFilesSize.countItemSize(files);
        userRepository.decreaseUserCapacity(sizeOfFile, userId);
        return StringFileUtils.getPathToObjectDirectory(filePath, isDir);
    }

    public String updateFileName(FileRenameRequest fileRenameRequest, Integer userId) {
        String oldFileName = fileRenameRequest.getOldFileName();
        String oldFilePath = fileRenameRequest.getOldFilePath();
        String newFileName = fileRenameRequest.getNewFileName();

        String pathToFile = StringFileUtils.getPathToObjectDirectory(oldFilePath,fileRenameRequest.getIsFileDirectory());

        if (areNamesSame(oldFileName, newFileName, fileRenameRequest.getIsFileDirectory())) {
            return pathToFile;
        }

        if (!fileRenameRequest.getIsFileDirectory()){
            String newFilePath = pathToFile.concat(newFileName).concat(StringFileUtils.getFileType(oldFileName));
            minioRepository.copyFileWithNewName(newFilePath, oldFilePath, userId);
            minioRepository.removeFile(oldFilePath, fileRenameRequest.getIsFileDirectory(), userId);
        } else {
            String escapedFileName = Pattern.quote(oldFileName);
            String newPathDir = oldFilePath.replaceFirst("[" + escapedFileName + "]+\\/$", newFileName).concat("/");
            minioRepository.copyDirWithNewName(oldFilePath, newPathDir, userId);
            minioRepository.removeFile(oldFilePath, fileRenameRequest.getIsFileDirectory(), userId);
        }

        return pathToFile;
    }

    private boolean areNamesSame(String oldFileName, String newFileName, Boolean isDir) {
        if (isDir) {
            return oldFileName.equals(newFileName);
        } else {
            String fileType = StringFileUtils.getFileType(oldFileName);
            return oldFileName.equals(newFileName.concat(fileType));
        }
    }

}
