package com.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить доступ ко всем эндпоинтам
                .allowedOrigins("*") // Разрешить все источники. Можно указать конкретные, например, "http://example.com"
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Укажите методы, которые хотите разрешить
                .allowedHeaders("*") // Разрешить все заголовки. Для определенных заголовков укажите их явно
                .exposedHeaders("Authorization", "Content-Type") // Укажите заголовки, которые должны быть видимы клиенту
                .allowCredentials(true); // Разрешить отправку куки
    }
}