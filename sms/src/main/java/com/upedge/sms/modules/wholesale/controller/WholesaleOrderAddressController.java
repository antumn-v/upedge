package com.upedge.sms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderAddressUpdateRequest;
import com.upedge.sms.modules.wholesale.response.WholesaleOrderAddressUpdateResponse;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "批发订单地址")
@RestController
@RequestMapping("/wholesaleOrderAddress")
public class WholesaleOrderAddressController {
    @Autowired
    private WholesaleOrderAddressService wholesaleOrderAddressService;


    @ApiOperation("修改批发订单地址")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderaddress:update")
    public WholesaleOrderAddressUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WholesaleOrderAddressUpdateRequest request) {
        WholesaleOrderAddress entity=request.toWholesaleOrderAddress(id);
        wholesaleOrderAddressService.updateByPrimaryKeySelective(entity);
        WholesaleOrderAddressUpdateResponse res = new WholesaleOrderAddressUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
