package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddressAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddressListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddressUpdateRequest;
import com.upedge.oms.modules.wholesale.response.*;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/wholesaleOrderAddress")
public class WholesaleOrderAddressController {
    @Autowired
    private WholesaleOrderAddressService wholesaleOrderAddressService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaleorderaddress:info:id")
    public WholesaleOrderAddressInfoResponse info(@PathVariable Long id) {
        WholesaleOrderAddress result = wholesaleOrderAddressService.selectByPrimaryKey(id);
        WholesaleOrderAddressInfoResponse res = new WholesaleOrderAddressInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderaddress:list")
    public WholesaleOrderAddressListResponse list(@RequestBody @Valid WholesaleOrderAddressListRequest request) {
        List<WholesaleOrderAddress> results = wholesaleOrderAddressService.select(request);
        Long total = wholesaleOrderAddressService.count(request);
        request.setTotal(total);
        WholesaleOrderAddressListResponse res = new WholesaleOrderAddressListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderaddress:add")
    public WholesaleOrderAddressAddResponse add(@RequestBody @Valid WholesaleOrderAddressAddRequest request) {
        WholesaleOrderAddress entity=request.toWholesaleOrderAddress();
        wholesaleOrderAddressService.insertSelective(entity);
        WholesaleOrderAddressAddResponse res = new WholesaleOrderAddressAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderaddress:del:id")
    public WholesaleOrderAddressDelResponse del(@PathVariable Long id) {
        wholesaleOrderAddressService.deleteByPrimaryKey(id);
        WholesaleOrderAddressDelResponse res = new WholesaleOrderAddressDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderaddress:update")
    public WholesaleOrderAddressUpdateResponse update(@PathVariable Long id, @RequestBody @Valid WholesaleOrderAddressUpdateRequest request) {
        WholesaleOrderAddress entity=request.toWholesaleOrderAddress(id);
        wholesaleOrderAddressService.updateByPrimaryKeySelective(entity);
        WholesaleOrderAddressUpdateResponse res = new WholesaleOrderAddressUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
