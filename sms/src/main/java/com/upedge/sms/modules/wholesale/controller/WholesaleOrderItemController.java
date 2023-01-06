package com.upedge.sms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderItemUpdateDischargeQuantityRequest;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "批发订单产品")
@RestController
@RequestMapping("/wholesaleOrderItem")
public class WholesaleOrderItemController {
    @Autowired
    private WholesaleOrderItemService wholesaleOrderItemService;


    @ApiOperation("手动修改使用库存的数量")
    @PostMapping("/updateDischargeQuantity")
    public BaseResponse updateDischargeQuantity(@RequestBody  WholesaleOrderItemUpdateDischargeQuantityRequest request){
        return wholesaleOrderItemService.customUpdateDischargeQuantity(request);
    }


}
