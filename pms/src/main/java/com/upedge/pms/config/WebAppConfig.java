package com.upedge.pms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Value("${files.image.local}")
    String imageLocal;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDirectory = "file:" + imageLocal;
        String umsImageMapping = "/image/**";
        registry.addResourceHandler(umsImageMapping).addResourceLocations(imageDirectory);
        System.out.println("========WebAppConfig==========");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
