package com.upedge.cms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDirectory = "file:/root/files/image/";
        String imageMapping = "/cms/image/**";
        registry.addResourceHandler(imageMapping).addResourceLocations(imageDirectory);
        System.out.println("========WebAppConfig==========");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
