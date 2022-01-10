package com.upedge.zuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Configuration
public  class WebAppConfig implements WebMvcConfigurer {


    @Value("${imageDirectory}")
    String imageDirectory;
    @Value("${imageMapping}")
    String imageMapping;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(imageMapping).addResourceLocations("file:" + imageDirectory);

//        String omsExcelMapping = "/excel/**";
//        String excelDirectory = "file:/root/files/excel/";
//        registry.addResourceHandler(omsExcelMapping).addResourceLocations(excelDirectory);
        System.out.println("========WebAppConfig==========");
         WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
