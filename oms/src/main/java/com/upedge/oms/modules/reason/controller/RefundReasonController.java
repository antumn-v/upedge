package com.upedge.oms.modules.reason.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.reason.entity.RefundReason;
import com.upedge.oms.modules.reason.request.RefundReasonAddRequest;
import com.upedge.oms.modules.reason.request.RefundReasonListRequest;
import com.upedge.oms.modules.reason.request.RefundReasonUpdateRequest;
import com.upedge.oms.modules.reason.response.RefundReasonAddResponse;
import com.upedge.oms.modules.reason.response.RefundReasonInfoResponse;
import com.upedge.oms.modules.reason.response.RefundReasonListResponse;
import com.upedge.oms.modules.reason.response.RefundReasonUpdateResponse;
import com.upedge.oms.modules.reason.service.RefundReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/refundReason")
public class RefundReasonController {
    @Autowired
    private RefundReasonService refundReasonService;

    @RequestMapping(value="/admin/all", method=RequestMethod.POST)
    public RefundReasonListResponse all() {
        return refundReasonService.all();
    }

    @RequestMapping(value="/admin/info/{id}", method=RequestMethod.GET)
    public RefundReasonInfoResponse info(@PathVariable Long id) {
        RefundReason result = refundReasonService.selectByPrimaryKey(id);
        RefundReasonInfoResponse res = new RefundReasonInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public RefundReasonListResponse list(@RequestBody @Valid RefundReasonListRequest request) {
        List<RefundReason> results = refundReasonService.select(request);
        Long total = refundReasonService.count(request);
        request.setTotal(total);
        RefundReasonListResponse res = new RefundReasonListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/admin/add", method=RequestMethod.POST)
    public RefundReasonAddResponse add(@RequestBody @Valid RefundReasonAddRequest request) {
        RefundReason entity=request.toRefundReason();
        refundReasonService.insertSelective(entity);
        RefundReasonAddResponse res = new RefundReasonAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/admin/update/{id}", method=RequestMethod.POST)
    public RefundReasonUpdateResponse update(@PathVariable Long id, @RequestBody @Valid RefundReasonUpdateRequest request) {
        RefundReason entity=request.toRefundReason(id);
        refundReasonService.updateByPrimaryKeySelective(entity);
        RefundReasonUpdateResponse res = new RefundReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/admin/enable/{id}", method=RequestMethod.POST)
    public RefundReasonUpdateResponse enable(@PathVariable Long id) {
        RefundReason entity=new RefundReason();
        entity.setId(id);
        entity.setState(1);
        refundReasonService.updateByPrimaryKeySelective(entity);
        RefundReasonUpdateResponse res = new RefundReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/admin/disable/{id}", method=RequestMethod.POST)
    public RefundReasonUpdateResponse disable(@PathVariable Long id) {
        RefundReason entity=new RefundReason();
        entity.setId(id);
        entity.setState(0);
        refundReasonService.updateByPrimaryKeySelective(entity);
        RefundReasonUpdateResponse res = new RefundReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }
}
