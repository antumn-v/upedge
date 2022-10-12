package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
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
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

@Api(tags = "订单包裹")
@RestController
@RequestMapping("/orderPackage")
public class OrderPackageController {

    @Autowired
    OrderPackageService orderPackageService;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("包裹信息")
    @PostMapping("/info")
    public BaseResponse packageInfo(@RequestBody OrderPackageInfoGetRequest request) {
        OrderPackageInfoVo orderPackageInfoVo = orderPackageService.packageInfo(request);
        return BaseResponse.success(orderPackageInfoVo);
    }

    @ApiOperation("生成包裹")
    @PostMapping("/create/{id}")
    public BaseResponse orderCreatePackage(@PathVariable Long id) {
        return orderPackageService.createPackage(id);
    }

    @PostMapping("/createBatch")
    public BaseResponse createPackageBatch(@RequestBody List<Long> orderIds) {

        CountDownLatch latch = new CountDownLatch(orderIds.size());

        for (Long orderId : orderIds) {
            threadPoolExecutor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try {
                        orderPackageService.createPackage(orderId);
                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return BaseResponse.success();
    }

    @ApiOperation("搁置发货")
    @PostMapping("/revoke")
    public BaseResponse revokePackage(@RequestBody OrderPackRevokeRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.orderRevokePackage(request, session);
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
        return orderPackageService.printPackLabel(request, session);
    }

    @ApiOperation("面单打印记录")
    @GetMapping("/labelLog/{packNo}")
    public BaseResponse labelPrintLog(@PathVariable Long packNo) {
        List<OrderLabelPrintLog> orderLabelPrintLogs = orderPackageService.packLabelPrintLog(packNo);
        return BaseResponse.success(orderLabelPrintLogs);
    }

    @ApiOperation("包裹出库")
    @PostMapping("/exStock/{scanNo}")
    public BaseResponse packExStock(@PathVariable String scanNo) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.packageExStock(scanNo, session);
    }

    @ApiOperation("回传物流")
    @PostMapping("/uploadStore/{packNo}")
    public BaseResponse uploadStore(@PathVariable Long packNo) {
        OrderPackage orderPackage = orderPackageService.selectByPrimaryKey(packNo);
        orderFulfillmentService.orderFulfillment(orderPackage);
        return BaseResponse.success();
    }


    @PostMapping("/saveLabelUrl")
    public BaseResponse saveLabelUrl() {
        orderPackageService.saveAllLabelUrl();
        return BaseResponse.success();
    }


}
