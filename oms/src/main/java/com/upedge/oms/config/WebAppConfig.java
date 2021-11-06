package com.upedge.oms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDirectory = "file:/root/files/image/";
        String excelDirectory = "file:/root/files/excel/";
        String imageMapping = "/oms/image/**";
        String excelMapping = "/oms/excel/**";
        registry.addResourceHandler(imageMapping).addResourceLocations(imageDirectory);
        registry.addResourceHandler(excelMapping).addResourceLocations(excelDirectory);
        System.out.println("========WebAppConfig==========");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
