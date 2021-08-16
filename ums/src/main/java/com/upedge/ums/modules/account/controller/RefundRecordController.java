package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.RefundRecord;
import com.upedge.ums.modules.account.service.RefundRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.RefundRecordAddRequest;
import com.upedge.ums.modules.account.request.RefundRecordListRequest;
import com.upedge.ums.modules.account.request.RefundRecordUpdateRequest;

import com.upedge.ums.modules.account.response.RefundRecordAddResponse;
import com.upedge.ums.modules.account.response.RefundRecordDelResponse;
import com.upedge.ums.modules.account.response.RefundRecordInfoResponse;
import com.upedge.ums.modules.account.response.RefundRecordListResponse;
import com.upedge.ums.modules.account.response.RefundRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 退款记录   refund_recharge_record   退款充值记录
 *
 * @author gx
 */
@RestController
@RequestMapping("/refundRecord")
public class RefundRecordController {
    @Autowired
    private RefundRecordService refundRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:refundrecord:info:id")
    public RefundRecordInfoResponse info(@PathVariable Integer id) {
        RefundRecord result = refundRecordService.selectByPrimaryKey(id);
        RefundRecordInfoResponse res = new RefundRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:refundrecord:list")
    public RefundRecordListResponse list(@RequestBody @Valid RefundRecordListRequest request) {
        List<RefundRecord> results = refundRecordService.select(request);
        Long total = refundRecordService.count(request);
        request.setTotal(total);
        RefundRecordListResponse res = new RefundRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:refundrecord:add")
    public RefundRecordAddResponse add(@RequestBody @Valid RefundRecordAddRequest request) {
        RefundRecord entity=request.toRefundRecord();
        refundRecordService.insertSelective(entity);
        RefundRecordAddResponse res = new RefundRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:refundrecord:del:id")
    public RefundRecordDelResponse del(@PathVariable Integer id) {
        refundRecordService.deleteByPrimaryKey(id);
        RefundRecordDelResponse res = new RefundRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:refundrecord:update")
    public RefundRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid RefundRecordUpdateRequest request) {
        RefundRecord entity=request.toRefundRecord(id);
        refundRecordService.updateByPrimaryKeySelective(entity);
        RefundRecordUpdateResponse res = new RefundRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
