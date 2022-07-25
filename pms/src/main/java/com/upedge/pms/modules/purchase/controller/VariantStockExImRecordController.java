package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordAddRequest;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordListRequest;
import com.upedge.pms.modules.purchase.response.VariantStockExImRecordAddResponse;
import com.upedge.pms.modules.purchase.response.VariantStockExImRecordDelResponse;
import com.upedge.pms.modules.purchase.response.VariantStockExImRecordInfoResponse;
import com.upedge.pms.modules.purchase.response.VariantStockExImRecordListResponse;
import com.upedge.pms.modules.purchase.service.VariantStockExImRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 变体库存出入库记录
 *
 * @author gx
 */
@RestController
@RequestMapping("/variantStockExImRecord")
public class VariantStockExImRecordController {
    @Autowired
    private VariantStockExImRecordService variantStockExImRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:variantstockeximrecord:info:id")
    public VariantStockExImRecordInfoResponse info(@PathVariable Long id) {
        VariantStockExImRecord result = variantStockExImRecordService.selectByPrimaryKey(id);
        VariantStockExImRecordInfoResponse res = new VariantStockExImRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantstockeximrecord:list")
    public VariantStockExImRecordListResponse list(@RequestBody @Valid VariantStockExImRecordListRequest request) {
        List<VariantStockExImRecord> results = variantStockExImRecordService.select(request);
        Long total = variantStockExImRecordService.count(request);
        request.setTotal(total);
        VariantStockExImRecordListResponse res = new VariantStockExImRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantstockeximrecord:add")
    public VariantStockExImRecordAddResponse add(@RequestBody @Valid VariantStockExImRecordAddRequest request) {
        VariantStockExImRecord entity=request.toVariantStockExImRecord();
        variantStockExImRecordService.insertSelective(entity);
        VariantStockExImRecordAddResponse res = new VariantStockExImRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantstockeximrecord:del:id")
    public VariantStockExImRecordDelResponse del(@PathVariable Long id) {
        variantStockExImRecordService.deleteByPrimaryKey(id);
        VariantStockExImRecordDelResponse res = new VariantStockExImRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }




}
