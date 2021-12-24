package com.upedge.oms.modules.common.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.request.SaiheOrderRecordListRequest;
import com.upedge.oms.modules.common.response.SaiheOrderRecordListResponse;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("订单上传赛盒记录")
@RestController
@RequestMapping("/saiheOrderRecord")
public class SaiheOrderRecordController {
    @Autowired
    private SaiheOrderRecordService saiheOrderRecordService;

    @ApiOperation("列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:list")
    public SaiheOrderRecordListResponse list(@RequestBody @Valid SaiheOrderRecordListRequest request) {
        List<SaiheOrderRecord> results = saiheOrderRecordService.select(request);
        Long total = saiheOrderRecordService.count(request);
        request.setTotal(total);
        SaiheOrderRecordListResponse res = new SaiheOrderRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



}
