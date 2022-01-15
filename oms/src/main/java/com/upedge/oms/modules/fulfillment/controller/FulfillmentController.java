package com.upedge.oms.modules.fulfillment.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.fulfillment.service.FulfillmentService;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/fulfillment")
@RestController
public class FulfillmentController {

    @Autowired
    FulfillmentService fulfillmentService;

    @Autowired
    OrderTrackingService orderTrackingService;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;

    @Autowired
    OrderService orderService;

    @PostMapping("/postTrack")
    public BaseResponse postTrack(){
        List<Long> ids = orderTrackingService.selectOrderIdByState(0);
        for (Long id : ids) {
            orderFulfillmentService.orderFulfillment(id);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @PostMapping("/{orderId}")
    public BaseResponse fulfillment(@PathVariable Long orderId){
        orderFulfillmentService.orderFulfillment(orderId);
        return BaseResponse.success();
    }


    @PostMapping("/updateTrack/{orderId}")
    public BaseResponse updateTrack(@PathVariable Long orderId){
        orderService.getTrackingFromSaihe(orderId);
        return BaseResponse.success();
    }

    /**
     * 更新物流信息
     */
    public BaseResponse updateTrack(OrderTracking orderTracking){
        fulfillmentService.updateOrderTracking(orderTracking);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

}
