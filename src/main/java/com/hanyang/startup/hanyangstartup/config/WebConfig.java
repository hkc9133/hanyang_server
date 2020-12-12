package com.hanyang.startup.hanyangstartup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://127.0.0.1:3000")
                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("http://127.0.0.1:8080")
                .allowedOrigins("http://210.103.188.119")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Headers", "Authorization"," x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .maxAge(3600)
                .allowCredentials(true);
    }
}

