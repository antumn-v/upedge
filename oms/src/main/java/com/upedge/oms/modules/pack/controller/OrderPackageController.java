package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.OrderPackageInfoGetRequest;
import com.upedge.oms.modules.pack.request.OrderPackageListRequest;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.fpx.api.FpxOrderApi;
import com.upedge.thirdparty.fpx.request.OrderPackageGetLabelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "订单包裹")
@RestController
@RequestMapping("/orderPackage")
public class OrderPackageController {

    @Autowired
    OrderPackageService orderPackageService;

    @ApiOperation("包裹信息")
    @PostMapping("/info")
    public BaseResponse packageInfo(@RequestBody OrderPackageInfoGetRequest request){
        OrderPackageInfoVo orderPackageInfoVo = orderPackageService.packageInfo(request.getPackageNo());
        return BaseResponse.success(orderPackageInfoVo);
    }

    @ApiOperation("生成包裹")
    @PostMapping("/create/{id}")
    public BaseResponse orderCreatePackage(@PathVariable Long id) {
        return orderPackageService.createPackage(id);
    }

    @ApiOperation("包裹列表")
    @PostMapping("/list")
    public BaseResponse packageList(@RequestBody OrderPackageListRequest request) {
        List<OrderPackage> packageList = orderPackageService.select(request);

        Long total = orderPackageService.count(request);

        request.setTotal(total);
        return BaseResponse.success(packageList, total);
    }

    @ApiOperation("获取包裹面单")
    @GetMapping("/label")
    public BaseResponse getLabel(@RequestBody OrderPackageGetLabelRequest request) {
        String labelUrl = "";
        try {
            if (StringUtils.isNotBlank(request.getPlatId())) {
                labelUrl = FpxOrderApi.getSinglePackageLabel(request.getPlatId());
            } else if (ListUtils.isNotEmpty(request.getPlatIds())) {
                labelUrl = FpxOrderApi.getMultiPackageLabel(request.getPlatIds(), "F3");
            }
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
        Map<String,String> map = new HashMap<>();
        map.put("labelUrl",labelUrl);
        return BaseResponse.success(map);
    }

}
