package com.upedge.zuul.config;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaqi on 2020/10/9.
 */
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    private final RouteLocator routeLocator;

    public DocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<Route> routes = routeLocator.getRoutes();
//        routes.forEach(route -> {
//            resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), "1.0"));
//        });
//        return resources;
//    }

    @Override
        public List<SwaggerResource> get() {
            List resources = new ArrayList<>();
            resources.add(swaggerResource("用户模块ums", "/ums/v2/api-docs", "1.0"));
            resources.add(swaggerResource("产品模块pms", "/pms/v2/api-docs", "1.0"));
            resources.add(swaggerResource("运输模块tms", "/tms/v2/api-docs", "1.0"));
            resources.add(swaggerResource("订单模块oms", "/oms/v2/api-docs", "1.0"));
            resources.add(swaggerResource("内容模块cms", "/cms/v2/api-docs", "1.0"));
            return resources;
        }




    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}