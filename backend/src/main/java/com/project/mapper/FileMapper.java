package com.project.mapper;


import com.project.minio.dto.FileResponse;
import io.minio.messages.Item;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "filePath", expression = "java(item.objectName())")
    @Mapping(target = "isDirectory", expression = "java(item.isDir())")
    FileResponse mapItemToResponse(Item item);

    @BeforeMapping
    default void convertFileSizeToString(Item item, @MappingTarget FileResponse fileResponse) {
        fileResponse.setSize(String.valueOf(item.size()));
    }

    @BeforeMapping
    default void convertDateToPattern(Item item, @MappingTarget FileResponse fileResponse) {
        if (!item.isDir()) {
            ZonedDateTime lastModified = item.lastModified();
            lastModified = lastModified.withZoneSameInstant(ZoneId.of("Europe/Kiev"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm");
            String lastModifiedFormatted = lastModified.format(formatter);
            fileResponse.setLastModified(lastModifiedFormatted);
        }
    }

    List<FileResponse> mapListItemsFromStorageToListFileResponse(List<Item> files);

}
