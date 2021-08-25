package com.upedge.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Configuration
public  class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDirectory = "file:/root/files/image/";

        String umsImageMapping = "/ums/image/**";
        String pmsImageMapping = "/pms/image/**";
        String omsImageMapping = "/oms/image/**";
        String cmsImageMapping = "/cms/image/**";

        String staticMapping="/root/files/**";
        String localDirectory = "file:"+"/root/files/";
        registry.addResourceHandler(umsImageMapping).addResourceLocations(imageDirectory);
        registry.addResourceHandler(pmsImageMapping).addResourceLocations(imageDirectory);
        registry.addResourceHandler(omsImageMapping).addResourceLocations(imageDirectory);
        registry.addResourceHandler(cmsImageMapping).addResourceLocations(imageDirectory);
        registry.addResourceHandler(staticMapping).addResourceLocations(localDirectory);

        String omsExcelMapping = "/oms/image/**";
        String excelDirectory = "file:/root/files/image/";
        registry.addResourceHandler(omsExcelMapping).addResourceLocations(excelDirectory);
        System.out.println("========WebAppConfig==========");
         WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
