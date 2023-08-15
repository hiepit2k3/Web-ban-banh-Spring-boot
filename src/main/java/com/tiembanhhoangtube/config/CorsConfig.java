package com.tiembanhhoangtube.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500","http://127.0.0.1:5500","https://accounts.google.com/*")
                .allowedMethods("*")
                .allowedHeaders("*");
//                .allowCredentials(true);
    }
}

