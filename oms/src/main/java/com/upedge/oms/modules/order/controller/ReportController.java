package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.request.OrderCogsSelectRequest;
import com.upedge.oms.modules.order.request.OrderInsightSelectRequest;
import com.upedge.oms.modules.order.request.StoreOrderSaleSelectRequest;
import com.upedge.oms.modules.order.service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("report sales")
    @PostMapping("/sales")
    public BaseResponse customerStoreOrderSales(@RequestBody StoreOrderSaleSelectRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return reportService.storeOrderSales(session,request);
    }

    @ApiOperation("report cogs")
    @PostMapping("/cogs")
    public BaseResponse customerOrderCogs(@RequestBody OrderCogsSelectRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return reportService.customerOrderCogs(session,request);
    }

    @ApiOperation("report insights")
    @PostMapping("/insights")
    public BaseResponse customerOrderInsights(@RequestBody OrderInsightSelectRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return reportService.customerOrderInsights(session,request);
    }
}
