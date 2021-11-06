package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.wholesale.entity.WholesaleTracking;
import com.upedge.oms.modules.wholesale.request.WholesaleTrackingAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleTrackingListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleTrackingUpdateRequest;
import com.upedge.oms.modules.wholesale.response.*;
import com.upedge.oms.modules.wholesale.service.WholesaleTrackingService;
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
@RequestMapping("/wholesaleTracking")
public class WholesaleTrackingController {
    @Autowired
    private WholesaleTrackingService wholesaleTrackingService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaletracking:info:id")
    public WholesaleTrackingInfoResponse info(@PathVariable Long id) {
        WholesaleTracking result = wholesaleTrackingService.selectByPrimaryKey(id);
        WholesaleTrackingInfoResponse res = new WholesaleTrackingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaletracking:list")
    public WholesaleTrackingListResponse list(@RequestBody @Valid WholesaleTrackingListRequest request) {
        List<WholesaleTracking> results = wholesaleTrackingService.select(request);
        Long total = wholesaleTrackingService.count(request);
        request.setTotal(total);
        WholesaleTrackingListResponse res = new WholesaleTrackingListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaletracking:add")
    public WholesaleTrackingAddResponse add(@RequestBody @Valid WholesaleTrackingAddRequest request) {
        WholesaleTracking entity=request.toWholesaleTracking();
        wholesaleTrackingService.insertSelective(entity);
        WholesaleTrackingAddResponse res = new WholesaleTrackingAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaletracking:del:id")
    public WholesaleTrackingDelResponse del(@PathVariable Long id) {
        wholesaleTrackingService.deleteByPrimaryKey(id);
        WholesaleTrackingDelResponse res = new WholesaleTrackingDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaletracking:update")
    public WholesaleTrackingUpdateResponse update(@PathVariable Long id, @RequestBody @Valid WholesaleTrackingUpdateRequest request) {
        WholesaleTracking entity=request.toWholesaleTracking(id);
        wholesaleTrackingService.updateByPrimaryKeySelective(entity);
        WholesaleTrackingUpdateResponse res = new WholesaleTrackingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
