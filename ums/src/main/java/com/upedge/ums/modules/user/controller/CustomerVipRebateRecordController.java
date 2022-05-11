package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.request.CustomerVipAddRebateRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import com.upedge.ums.modules.user.request.CustomerVipRebateRecordListRequest;
import com.upedge.ums.modules.user.response.CustomerVipRebateRecordListResponse;
import com.upedge.ums.modules.user.service.CustomerVipRebateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "vip佣金记录")
@RestController
@RequestMapping("/customerVipRebateRecord")
public class CustomerVipRebateRecordController {
    @Autowired
    private CustomerVipRebateRecordService customerVipRebateRecordService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("vip佣金记录")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprebaterecord:list")
    public CustomerVipRebateRecordListResponse list(@RequestBody @Valid CustomerVipRebateRecordListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getApplicationId().equals(Constant.APP_APPLICATION_ID)){
            if (request.getT() == null){
                request.setT(new CustomerVipRebateRecord());
            }
            request.getT().setCustomerId(session.getCustomerId());
        }
        List<CustomerVipRebateRecord> results = customerVipRebateRecordService.select(request);
        Long total = customerVipRebateRecordService.count(request);
        request.setTotal(total);
        CustomerVipRebateRecordListResponse res = new CustomerVipRebateRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid CustomerVipAddRebateRequest request) {
        customerVipRebateRecordService.addCustomerVipRebate(request.getCustomerId(), request.getOrderId());
        return BaseResponse.success();
    }






}
