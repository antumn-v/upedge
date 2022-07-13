package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordAddRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordListRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordUpdateRequest;
import com.upedge.pms.modules.purchase.response.*;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
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
@RequestMapping("/variantWarehouseStockRecord")
public class VariantWarehouseStockRecordController {
    @Autowired
    private VariantWarehouseStockRecordService variantWarehouseStockRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:variantwarehousestockrecord:info:id")
    public VariantWarehouseStockRecordInfoResponse info(@PathVariable Integer id) {
        VariantWarehouseStockRecord result = variantWarehouseStockRecordService.selectByPrimaryKey(id);
        VariantWarehouseStockRecordInfoResponse res = new VariantWarehouseStockRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestockrecord:list")
    public VariantWarehouseStockRecordListResponse list(@RequestBody @Valid VariantWarehouseStockRecordListRequest request) {
        List<VariantWarehouseStockRecord> results = variantWarehouseStockRecordService.select(request);
        Long total = variantWarehouseStockRecordService.count(request);
        request.setTotal(total);
        VariantWarehouseStockRecordListResponse res = new VariantWarehouseStockRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestockrecord:add")
    public VariantWarehouseStockRecordAddResponse add(@RequestBody @Valid VariantWarehouseStockRecordAddRequest request) {
        VariantWarehouseStockRecord entity=request.toVariantWarehouseStockRecord();
        variantWarehouseStockRecordService.insertSelective(entity);
        VariantWarehouseStockRecordAddResponse res = new VariantWarehouseStockRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestockrecord:del:id")
    public VariantWarehouseStockRecordDelResponse del(@PathVariable Integer id) {
        variantWarehouseStockRecordService.deleteByPrimaryKey(id);
        VariantWarehouseStockRecordDelResponse res = new VariantWarehouseStockRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestockrecord:update")
    public VariantWarehouseStockRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid VariantWarehouseStockRecordUpdateRequest request) {
        VariantWarehouseStockRecord entity=request.toVariantWarehouseStockRecord(id);
        variantWarehouseStockRecordService.updateByPrimaryKeySelective(entity);
        VariantWarehouseStockRecordUpdateResponse res = new VariantWarehouseStockRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
