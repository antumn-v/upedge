package com.upedge.ums.modules.address.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.ums.modules.address.service.CustomerAddressService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.address.request.CustomerAddressAddRequest;
import com.upedge.ums.modules.address.request.CustomerAddressListRequest;
import com.upedge.ums.modules.address.request.CustomerAddressUpdateRequest;

import com.upedge.ums.modules.address.response.CustomerAddressAddResponse;
import com.upedge.ums.modules.address.response.CustomerAddressDelResponse;
import com.upedge.ums.modules.address.response.CustomerAddressInfoResponse;
import com.upedge.ums.modules.address.response.CustomerAddressListResponse;
import com.upedge.ums.modules.address.response.CustomerAddressUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户地址")
@RestController
@RequestMapping("/customerAddress")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("地址详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "address:customeraddress:info:id")
    public CustomerAddressInfoResponse info(@PathVariable Long id) {
        CustomerAddress result = customerAddressService.selectByPrimaryKey(id);
        CustomerAddressInfoResponse res = new CustomerAddressInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("客户地址列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:list")
    public CustomerAddressListResponse list(@RequestBody @Valid CustomerAddressListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (request.getT() == null){
            request.setT(new CustomerAddress());
        }
        request.getT().setCustomerId(session.getCustomerId());
        List<CustomerAddress> results = customerAddressService.select(request);
        Long total = customerAddressService.count(request);
        request.setTotal(total);
        CustomerAddressListResponse res = new CustomerAddressListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("添加地址")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:add")
    public CustomerAddressAddResponse add(@RequestBody @Valid CustomerAddressAddRequest request) {
        CustomerAddress entity=request.toCustomerAddress();
        int i = customerAddressService.insertSelective(entity);
        CustomerAddressAddResponse res = null;
        if (i == 1){
            res = new CustomerAddressAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        }else {
            res = new CustomerAddressAddResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL,entity,request);
        }
        return res;
    }

    @ApiOperation("删除地址")
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:del:id")
    public CustomerAddressDelResponse del(@PathVariable Long id) {
        customerAddressService.deleteByPrimaryKey(id);
        CustomerAddressDelResponse res = new CustomerAddressDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("修改地址")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:update")
    public CustomerAddressUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerAddressUpdateRequest request) {
        CustomerAddress entity=request.toCustomerAddress(id);
        customerAddressService.updateByPrimaryKeySelective(entity);
        CustomerAddressUpdateResponse res = new CustomerAddressUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
