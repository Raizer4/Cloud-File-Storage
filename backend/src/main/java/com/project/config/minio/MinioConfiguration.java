package com.project.config.minio;

import org.springframework.beans.factory.annotation.Value;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.host}")
    private String minioHost;

    @Value("${minio.port}")
    private Integer minioPort;

    @Value("${minio.login}")
    private String minioLogin;

    @Value("${minio.password}")
    private String minioPassword;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioHost, minioPort, false)
                .credentials(minioLogin, minioPassword)
                .build();

    }



}
