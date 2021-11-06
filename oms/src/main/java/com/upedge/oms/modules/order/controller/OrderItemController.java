package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.oms.modules.order.request.AirwallexRequest;
import com.upedge.oms.modules.order.request.OrderItemUpdateQuantityRequest;
import com.upedge.oms.modules.order.service.OrderItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/order/item")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;


    @PostMapping("/{id}/update/quantity")
    public BaseResponse orderItemUpdateQuantity(@PathVariable Long id, @RequestBody @Valid OrderItemUpdateQuantityRequest request){
        int i = orderItemService.updateQuantity(request,id);
        if(i == 1){
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    @ApiOperation("airwallex导出")
    @PostMapping("/airwallex")
    public BaseResponse airwallex(@RequestBody AirwallexRequest airwallexRequest) throws CustomerException {
        if (airwallexRequest == null || airwallexRequest.getBeginTime() == null || airwallexRequest.getEndTime() == null){
            throw new CustomerException(CustomerExceptionEnum.TIME_CANNOT_BE_EMPTY);
        }
        return  BaseResponse.success(orderItemService.airwallex(airwallexRequest));
    }
}
