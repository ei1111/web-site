package com.web.site.global.common.config.web;

import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 현재 프로젝트 경로
        String uploadDir = Paths.get("").toAbsolutePath() + "/upload/";

        // 요청한 /images/** 경로를 실제 폴더 경로 /Users/your-username/upload/로 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir); // MacOS에서 파일 경로
    }
}
