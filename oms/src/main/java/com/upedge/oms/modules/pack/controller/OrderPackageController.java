package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderPackage")
public class OrderPackageController {

    @Autowired
    OrderPackageService orderPackageService;

    @PostMapping("/create/{id}")
    public BaseResponse orderCreatePackage(@PathVariable Long id){
        return orderPackageService.createFpxPackage(id);
    }
}
