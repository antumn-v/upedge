package com.upedge.oms.modules.wholesale.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.oms.modules.wholesale.entity.WholesaleShipReviewRecord;
import com.upedge.oms.modules.wholesale.service.WholesaleShipReviewRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.oms.modules.wholesale.request.WholesaleShipReviewRecordAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleShipReviewRecordListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleShipReviewRecordUpdateRequest;

import com.upedge.oms.modules.wholesale.response.WholesaleShipReviewRecordAddResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleShipReviewRecordDelResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleShipReviewRecordInfoResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleShipReviewRecordListResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleShipReviewRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/wholesaleShipReviewRecord")
public class WholesaleShipReviewRecordController {
    @Autowired
    private WholesaleShipReviewRecordService wholesaleShipReviewRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaleshipreviewrecord:info:id")
    public WholesaleShipReviewRecordInfoResponse info(@PathVariable Long id) {
        WholesaleShipReviewRecord result = wholesaleShipReviewRecordService.selectByPrimaryKey(id);
        WholesaleShipReviewRecordInfoResponse res = new WholesaleShipReviewRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleshipreviewrecord:list")
    public WholesaleShipReviewRecordListResponse list(@RequestBody @Valid WholesaleShipReviewRecordListRequest request) {
        List<WholesaleShipReviewRecord> results = wholesaleShipReviewRecordService.select(request);
        Long total = wholesaleShipReviewRecordService.count(request);
        request.setTotal(total);
        WholesaleShipReviewRecordListResponse res = new WholesaleShipReviewRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleshipreviewrecord:add")
    public WholesaleShipReviewRecordAddResponse add(@RequestBody @Valid WholesaleShipReviewRecordAddRequest request) {
        WholesaleShipReviewRecord entity=request.toWholesaleShipReviewRecord();
        wholesaleShipReviewRecordService.insertSelective(entity);
        WholesaleShipReviewRecordAddResponse res = new WholesaleShipReviewRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleshipreviewrecord:del:id")
    public WholesaleShipReviewRecordDelResponse del(@PathVariable Long id) {
        wholesaleShipReviewRecordService.deleteByPrimaryKey(id);
        WholesaleShipReviewRecordDelResponse res = new WholesaleShipReviewRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleshipreviewrecord:update")
    public WholesaleShipReviewRecordUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WholesaleShipReviewRecordUpdateRequest request) {
        WholesaleShipReviewRecord entity=request.toWholesaleShipReviewRecord(id);
        wholesaleShipReviewRecordService.updateByPrimaryKeySelective(entity);
        WholesaleShipReviewRecordUpdateResponse res = new WholesaleShipReviewRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
