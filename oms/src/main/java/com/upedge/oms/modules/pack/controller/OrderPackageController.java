package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.*;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.shipcompany.fpx.request.OrderPackageGetLabelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;
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
    OrderCommonService orderCommonService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("包裹信息")
    @PostMapping("/info")
    public BaseResponse packageInfo(@RequestBody OrderPackageInfoGetRequest request) {
        OrderPackageInfoVo orderPackageInfoVo = orderPackageService.packageInfo(request);
        return BaseResponse.success(orderPackageInfoVo);
    }

    @ApiOperation("包裹换单")
    @PostMapping("/replaceCode")
    public BaseResponse packageReplaceCode(@RequestBody PackageReplaceCodeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.packageReplaceLabel(request,session);
    }

    @ApiOperation("已出库包裹重新生成")
    @PostMapping("/reCreate")
    public BaseResponse reCreatePackage(@RequestBody PackageRecreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.reCreatePackage(request,session);
    }

    @ApiOperation("生成包裹")
    @PostMapping("/create/{id}")
    public BaseResponse orderCreatePackage(@PathVariable Long id) {
        return orderPackageService.createPackage(id);
    }

    @PostMapping("/createBatch")
    public BaseResponse createPackageBatch(@RequestBody List<Long> orderIds) {

        CountDownLatch latch = new CountDownLatch(orderIds.size());
        Random random = new Random();
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

    @ApiOperation("恢复搁置发货的订单")
    @PostMapping("/restore")
    public BaseResponse restoreRevokedPackage(@RequestBody List<Long> orderIds){
        Session session = UserUtil.getSession(redisTemplate);
        for (Long orderId : orderIds) {
            orderPackageService.restoreRevokedPackage(orderId,session);
        }
        return BaseResponse.success();
    }

    @ApiOperation("包裹列表")
    @PostMapping("/list")
    public BaseResponse packageList(@RequestBody OrderPackageListRequest request) {
        List<OrderPackage> packageList = orderPackageService.select(request);

        Long total = orderPackageService.count(request);

        request.setTotal(total);
        return BaseResponse.success(packageList, request);
    }

    @ApiOperation("包裹出库记录")
    @PostMapping("/exStockRecord")
    public BaseResponse packageExStockRecord(@RequestBody OrderPackageListRequest request){
        request.setOrderBy("send_time desc");
        OrderPackage orderPackage = request.getT();
        if (orderPackage == null){
            orderPackage = new OrderPackage();
        }
        orderPackage.setPackageState(1);
        request.setT(orderPackage);

        return packageList(request);
    }

    @ApiOperation("获取包裹面单")
    @PostMapping("/label")
    public BaseResponse getLabel(@RequestBody@Valid OrderPackageGetLabelRequest request) {
        Long packNo = request.getPackNo();
        String key = "lock:pack:print:label:" + packNo;
        boolean b = RedisUtil.lock(redisTemplate,key,0L,10 * 1000L);
        if (!b){
            return BaseResponse.failed("同一包裹面单10秒内不能重复打印");
        }
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
    @PostMapping("/exStock")
    public BaseResponse packExStock(@RequestBody@Valid PackageExStockRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        List<Long> packNos = request.getPackNos();
        if (ListUtils.isNotEmpty(packNos)){
            for (Long packNo : packNos) {
                request.setScanNo(packNo.toString());
                orderPackageService.packageExStock(request,session);
            }
            return BaseResponse.success();
        }
        return orderPackageService.packageExStock(request, session);
    }

    @ApiOperation("回传物流")
    @PostMapping("/uploadStore/{packNo}")
    public BaseResponse uploadStore(@PathVariable Long packNo) {
        OrderPackage orderPackage = orderPackageService.selectByPrimaryKey(packNo);
        orderFulfillmentService.orderFulfillment(orderPackage,true);
        return BaseResponse.success();
    }

    @ApiOperation("预上传物流单号到店铺")
    @PostMapping("/preUploadStore")
    public BaseResponse preUploadStore(@RequestBody@Valid PackagePreUploadStoreRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.preUploadStore(request,session);
    }

    @ApiOperation("包裹设置为处理中")
    @PostMapping("/returnToPending")
    public BaseResponse packReturnToPending(@RequestBody@Valid PackageReturnToPendingRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.packReturnToPending(request,session);
    }


    @PostMapping("/getTrackingCode")
    public BaseResponse packageGetTrackingCode(@RequestBody List<Long> packNos){
        for (Long packNo : packNos) {
            OrderPackage orderPackage = orderPackageService.selectByPrimaryKey(packNo);
            orderPackageService.packageRefreshTrackCode(orderPackage);
        }
        return BaseResponse.success();
    }


    @PostMapping("/saveLabelUrl")
    public BaseResponse saveLabelUrl() {
        orderPackageService.saveAllLabelUrl();
        return BaseResponse.success();
    }


    @PostMapping("/reUploadStore")
    public BaseResponse reUploadStore(@RequestBody List<Long> packNos){
        for (Long packNo : packNos) {
            OrderPackage orderPackage = orderPackageService.selectByPrimaryKey(packNo);
            orderPackage.setIsUploadStore(false);
            orderFulfillmentService.orderFulfillment(orderPackage,false);
        }
        return BaseResponse.success();
    }

    @ApiOperation("店小秘包裹导入")
    @PostMapping("/import/dxm")
    public BaseResponse packageImportByDxm(@RequestBody@Valid PackageInfoImportRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPackageService.packageImport(request,session);
    }

    @PostMapping("/test")
    public BaseResponse test(){
        Page<OrderPackage> page = new Page<>();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setPackageState(1);
        page.setT(orderPackage);
        page.setPageSize(-1);
        List<OrderPackage> packages = orderPackageService.select(page);
        for (OrderPackage aPackage : packages) {
            orderCommonService.addCustomerCommission(aPackage.getOrderId(),aPackage.getCustomerId());
        }
        return BaseResponse.success();
    }


}
