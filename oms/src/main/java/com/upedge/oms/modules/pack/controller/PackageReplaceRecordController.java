package com.upedge.oms.modules.pack.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import com.upedge.oms.modules.pack.request.PackageReplaceRecordAddRequest;
import com.upedge.oms.modules.pack.request.PackageReplaceRecordListRequest;
import com.upedge.oms.modules.pack.request.PackageReplaceRecordUpdateRequest;
import com.upedge.oms.modules.pack.response.*;
import com.upedge.oms.modules.pack.service.PackageReplaceRecordService;
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
@RequestMapping("/packageReplaceRecord")
public class PackageReplaceRecordController {
    @Autowired
    private PackageReplaceRecordService packageReplaceRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pack:packagereplacerecord:info:id")
    public PackageReplaceRecordInfoResponse info(@PathVariable Integer id) {
        PackageReplaceRecord result = packageReplaceRecordService.selectByPrimaryKey(id);
        PackageReplaceRecordInfoResponse res = new PackageReplaceRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:packagereplacerecord:list")
    public PackageReplaceRecordListResponse list(@RequestBody @Valid PackageReplaceRecordListRequest request) {
        List<PackageReplaceRecord> results = packageReplaceRecordService.select(request);
        Long total = packageReplaceRecordService.count(request);
        request.setTotal(total);
        PackageReplaceRecordListResponse res = new PackageReplaceRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pack:packagereplacerecord:add")
    public PackageReplaceRecordAddResponse add(@RequestBody @Valid PackageReplaceRecordAddRequest request) {
        PackageReplaceRecord entity=request.toPackageReplaceRecord();
        packageReplaceRecordService.insertSelective(entity);
        PackageReplaceRecordAddResponse res = new PackageReplaceRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:packagereplacerecord:del:id")
    public PackageReplaceRecordDelResponse del(@PathVariable Integer id) {
        packageReplaceRecordService.deleteByPrimaryKey(id);
        PackageReplaceRecordDelResponse res = new PackageReplaceRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:packagereplacerecord:update")
    public PackageReplaceRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid PackageReplaceRecordUpdateRequest request) {
        PackageReplaceRecord entity=request.toPackageReplaceRecord(id);
        packageReplaceRecordService.updateByPrimaryKeySelective(entity);
        PackageReplaceRecordUpdateResponse res = new PackageReplaceRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
