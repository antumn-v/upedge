package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.OrderPackRevokeRequest;
import com.upedge.oms.modules.pack.request.OrderPackageInfoGetRequest;
import com.upedge.oms.modules.pack.request.OrderPackageListRequest;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.shipcompany.fpx.request.OrderPackageGetLabelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "订单包裹")
@RestController
@RequestMapping("/orderPackage")
public class OrderPackageController {

    @Autowired
    OrderPackageService orderPackageService;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("包裹信息")
    @PostMapping("/info")
    public BaseResponse packageInfo(@RequestBody OrderPackageInfoGetRequest request){
        OrderPackageInfoVo orderPackageInfoVo = orderPackageService.packageInfo(request);
        return BaseResponse.success(orderPackageInfoVo);
    }

    @ApiOperation("生成包裹")
    @PostMapping("/create/{id}")
    public BaseResponse orderCreatePackage(@PathVariable Long id) {
        return orderPackageService.createPackage(id);
    }

    @ApiOperation("撤销包裹")
    @PostMapping("/revoke")
    public BaseResponse revokePackage(@RequestBody OrderPackRevokeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.orderRevokePackage(request,session);
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
    @PostMapping("/label")
    public BaseResponse getLabel(@RequestBody OrderPackageGetLabelRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.printPackLabel(request,session);
    }

    @ApiOperation("面单打印记录")
    @GetMapping("/labelLog/{packNo}")
    public BaseResponse labelPrintLog(@PathVariable Long packNo){
        List<OrderLabelPrintLog> orderLabelPrintLogs = orderPackageService.packLabelPrintLog(packNo);
        return BaseResponse.success(orderLabelPrintLogs);
    }

    @ApiOperation("包裹出库")
    @PostMapping("/exStock/{packNo}")
    public BaseResponse packExStock(@PathVariable Long packNo){
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return orderPackageService.packageExStock(packNo,session);
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("回传物流")
    @PostMapping("/uploadStore/{packNo}")
    public BaseResponse uploadStore(@PathVariable Long packNo){
        OrderPackage orderPackage = orderPackageService.selectByPrimaryKey(packNo);
        orderFulfillmentService.orderFulfillment(orderPackage);
        return BaseResponse.success();
    }


}
