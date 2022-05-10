package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.request.CustomerVipAddRebateRequest;
import com.upedge.ums.modules.user.service.CustomerVipRebateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerVipRebateRecord")
public class CustomerVipRebateRecordController {
    @Autowired
    private CustomerVipRebateRecordService customerVipRebateRecordService;


    @RequestMapping(value="/add", method=RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid CustomerVipAddRebateRequest request) {
        customerVipRebateRecordService.addCustomerVipRebate(request.getCustomerId(), request.getOrderId());
        return BaseResponse.success();
    }



}
