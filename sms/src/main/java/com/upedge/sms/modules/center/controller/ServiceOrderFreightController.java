package com.upedge.sms.modules.center.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.center.service.ServiceOrderFreightService;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderUpdateFreightRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/serviceOrderFreight")
public class ServiceOrderFreightController {
    @Autowired
    private ServiceOrderFreightService serviceOrderFreightService;


    @Autowired
    ServiceOrderService serviceOrderService;



    @ApiOperation("修改订单运费")
    @PostMapping("/update")
    public BaseResponse updateFreight(@RequestBody@Valid OverseaWarehouseServiceOrderUpdateFreightRequest request){
        ServiceOrder serviceOrder = serviceOrderService.selectByPrimaryKey(request.getOrderId());
        if (null == serviceOrder
                || serviceOrder.getPayState() != 0){
            return BaseResponse.failed("订单不存在或订单已支付");
        }
        List<ServiceOrderFreight> orderFreights = request.getOrderFreights();;
        for (ServiceOrderFreight orderFreight : orderFreights) {
            serviceOrderFreightService.updateByPrimaryKeySelective(orderFreight);
        }
        return BaseResponse.success();
    }



}
