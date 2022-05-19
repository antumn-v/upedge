package com.upedge.pms.modules.product.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;
import com.upedge.pms.modules.product.request.StoreCustomVariantRecordAddRequest;
import com.upedge.pms.modules.product.request.StoreCustomVariantRecordUpdateRequest;
import com.upedge.pms.modules.product.response.StoreCustomVariantRecordAddResponse;
import com.upedge.pms.modules.product.response.StoreCustomVariantRecordDelResponse;
import com.upedge.pms.modules.product.response.StoreCustomVariantRecordUpdateResponse;
import com.upedge.pms.modules.product.service.StoreCustomVariantRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/storeCustomVariantRecord")
public class StoreCustomVariantRecordController {
    @Autowired
    private StoreCustomVariantRecordService storeCustomVariantRecordService;




    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:storecustomvariantrecord:add")
    public StoreCustomVariantRecordAddResponse add(@RequestBody @Valid StoreCustomVariantRecordAddRequest request) {
        StoreCustomVariantRecord entity=request.toStoreCustomVariantRecord();
        storeCustomVariantRecordService.insertSelective(entity);
        StoreCustomVariantRecordAddResponse res = new StoreCustomVariantRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:storecustomvariantrecord:del:id")
    public StoreCustomVariantRecordDelResponse del(@PathVariable Long id) {
        storeCustomVariantRecordService.deleteByPrimaryKey(id);
        StoreCustomVariantRecordDelResponse res = new StoreCustomVariantRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:storecustomvariantrecord:update")
    public StoreCustomVariantRecordUpdateResponse update(@PathVariable Long id,@RequestBody @Valid StoreCustomVariantRecordUpdateRequest request) {
        StoreCustomVariantRecord entity=request.toStoreCustomVariantRecord(id);
        storeCustomVariantRecordService.updateByPrimaryKeySelective(entity);
        StoreCustomVariantRecordUpdateResponse res = new StoreCustomVariantRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
