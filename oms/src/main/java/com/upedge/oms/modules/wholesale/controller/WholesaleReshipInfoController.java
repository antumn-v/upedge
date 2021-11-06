package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;
import com.upedge.oms.modules.wholesale.request.WholesaleReshipInfoAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleReshipInfoListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleReshipInfoUpdateRequest;
import com.upedge.oms.modules.wholesale.response.*;
import com.upedge.oms.modules.wholesale.service.WholesaleReshipInfoService;
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
@RequestMapping("/wholesaleReshipInfo")
public class WholesaleReshipInfoController {
    @Autowired
    private WholesaleReshipInfoService wholesaleReshipInfoService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesalereshipinfo:info:id")
    public WholesaleReshipInfoInfoResponse info(@PathVariable Long id) {
        WholesaleReshipInfo result = wholesaleReshipInfoService.selectByPrimaryKey(id);
        WholesaleReshipInfoInfoResponse res = new WholesaleReshipInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalereshipinfo:list")
    public WholesaleReshipInfoListResponse list(@RequestBody @Valid WholesaleReshipInfoListRequest request) {
        List<WholesaleReshipInfo> results = wholesaleReshipInfoService.select(request);
        Long total = wholesaleReshipInfoService.count(request);
        request.setTotal(total);
        WholesaleReshipInfoListResponse res = new WholesaleReshipInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalereshipinfo:add")
    public WholesaleReshipInfoAddResponse add(@RequestBody @Valid WholesaleReshipInfoAddRequest request) {
        WholesaleReshipInfo entity=request.toWholesaleReshipInfo();
        wholesaleReshipInfoService.insertSelective(entity);
        WholesaleReshipInfoAddResponse res = new WholesaleReshipInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalereshipinfo:del:id")
    public WholesaleReshipInfoDelResponse del(@PathVariable Long id) {
        wholesaleReshipInfoService.deleteByPrimaryKey(id);
        WholesaleReshipInfoDelResponse res = new WholesaleReshipInfoDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalereshipinfo:update")
    public WholesaleReshipInfoUpdateResponse update(@PathVariable Long id, @RequestBody @Valid WholesaleReshipInfoUpdateRequest request) {
        WholesaleReshipInfo entity=request.toWholesaleReshipInfo(id);
        wholesaleReshipInfoService.updateByPrimaryKeySelective(entity);
        WholesaleReshipInfoUpdateResponse res = new WholesaleReshipInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
