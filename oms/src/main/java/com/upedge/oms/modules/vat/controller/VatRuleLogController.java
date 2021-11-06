package com.upedge.oms.modules.vat.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.vat.entity.VatRuleLog;
import com.upedge.oms.modules.vat.request.VatRuleLogListRequest;
import com.upedge.oms.modules.vat.response.VatRuleLogListResponse;
import com.upedge.oms.modules.vat.service.VatRuleLogService;
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
@RequestMapping("/vatRuleLog")
public class VatRuleLogController {
    @Autowired
    private VatRuleLogService vatRuleLogService;

    /**
     * 规则配置日志列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public VatRuleLogListResponse list(@RequestBody @Valid VatRuleLogListRequest request) {
        List<VatRuleLog> results = vatRuleLogService.select(request);
        Long total = vatRuleLogService.count(request);
        request.setTotal(total);
        VatRuleLogListResponse res = new VatRuleLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

}
