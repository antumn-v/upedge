package com.upedge.zuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by guoxing on 2020/11/9.
 */
@Configuration
public  class WebAppConfig implements WebMvcConfigurer {


    @Value("${files.image.local}")
    String imageLocal;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDirectory = "file:" + imageLocal;
        String imageMapping = "/image/**";
        registry.addResourceHandler(imageMapping).addResourceLocations(imageDirectory);
        System.out.println("========WebAppConfig==========");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
