package com.tiembanhhoangtube.config;


import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverters customConverters() {
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        converter.setCharset(StandardCharsets.UTF_8);
        return new HttpMessageConverters(converter);
    }
}

