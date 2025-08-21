package com.kt.backendapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 설정 - CORS 정책 관리
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                    "http://localhost:3000",     // 개발환경 (React/Vue/Angular)
                    "http://127.0.0.1:3000",     // 대체 로컬호스트
                    "https://your-domain.com"    // 운영환경 (추후 실제 도메인으로 변경)
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders(
                    "Content-Type", 
                    "Authorization", 
                    "X-Requested-With", 
                    "Accept", 
                    "Origin",
                    "Access-Control-Request-Method",
                    "Access-Control-Request-Headers"
                )
                .exposedHeaders(
                    "X-Total-Count", 
                    "X-Page-Number", 
                    "X-Page-Size",
                    "Access-Control-Allow-Origin",
                    "Access-Control-Allow-Credentials"
                )
                .allowCredentials(true)
                .maxAge(3600); // 1시간 동안 preflight 요청 캐시
    }
}
