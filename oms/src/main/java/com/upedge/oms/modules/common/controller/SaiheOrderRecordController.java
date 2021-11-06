package com.upedge.oms.modules.common.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.request.SaiheOrderRecordAddRequest;
import com.upedge.oms.modules.common.request.SaiheOrderRecordListRequest;
import com.upedge.oms.modules.common.request.SaiheOrderRecordUpdateRequest;
import com.upedge.oms.modules.common.response.*;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/saiheOrderRecord")
public class SaiheOrderRecordController {
    @Autowired
    private SaiheOrderRecordService saiheOrderRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "common:saiheorderrecord:info:id")
    public SaiheOrderRecordInfoResponse info(@PathVariable Long id) {
        SaiheOrderRecord result = saiheOrderRecordService.selectByPrimaryKey(id);
        SaiheOrderRecordInfoResponse res = new SaiheOrderRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:list")
    public SaiheOrderRecordListResponse list(@RequestBody @Valid SaiheOrderRecordListRequest request) {
        List<SaiheOrderRecord> results = saiheOrderRecordService.select(request);
        Long total = saiheOrderRecordService.count(request);
        request.setTotal(total);
        SaiheOrderRecordListResponse res = new SaiheOrderRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:add")
    public SaiheOrderRecordAddResponse add(@RequestBody @Valid SaiheOrderRecordAddRequest request) {
        SaiheOrderRecord entity=request.toSaiheOrderRecord();
        saiheOrderRecordService.insertSelective(entity);
        SaiheOrderRecordAddResponse res = new SaiheOrderRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:del:id")
    public SaiheOrderRecordDelResponse del(@PathVariable Long id) {
        saiheOrderRecordService.deleteByPrimaryKey(id);
        SaiheOrderRecordDelResponse res = new SaiheOrderRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:update")
    public SaiheOrderRecordUpdateResponse update(@PathVariable Long id, @RequestBody @Valid SaiheOrderRecordUpdateRequest request) {
        SaiheOrderRecord entity=request.toSaiheOrderRecord(id);
        saiheOrderRecordService.updateByPrimaryKeySelective(entity);
        SaiheOrderRecordUpdateResponse res = new SaiheOrderRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
