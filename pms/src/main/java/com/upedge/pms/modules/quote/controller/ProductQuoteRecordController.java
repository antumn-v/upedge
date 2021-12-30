package com.upedge.pms.modules.quote.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.pms.modules.quote.request.ProductQuoteRecordAddRequest;
import com.upedge.pms.modules.quote.request.ProductQuoteRecordListRequest;
import com.upedge.pms.modules.quote.request.ProductQuoteRecordUpdateRequest;
import com.upedge.pms.modules.quote.response.*;
import com.upedge.pms.modules.quote.service.ProductQuoteRecordService;
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
@RequestMapping("/productQuoteRecord")
public class ProductQuoteRecordController {
    @Autowired
    private ProductQuoteRecordService productQuoteRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "quote:productquoterecord:info:id")
    public ProductQuoteRecordInfoResponse info(@PathVariable Integer id) {
        ProductQuoteRecord result = productQuoteRecordService.selectByPrimaryKey(id);
        ProductQuoteRecordInfoResponse res = new ProductQuoteRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "quote:productquoterecord:list")
    public ProductQuoteRecordListResponse list(@RequestBody @Valid ProductQuoteRecordListRequest request) {
        List<ProductQuoteRecord> results = productQuoteRecordService.select(request);
        Long total = productQuoteRecordService.count(request);
        request.setTotal(total);
        ProductQuoteRecordListResponse res = new ProductQuoteRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "quote:productquoterecord:add")
    public ProductQuoteRecordAddResponse add(@RequestBody @Valid ProductQuoteRecordAddRequest request) {
        ProductQuoteRecord entity=request.toProductQuoteRecord();
        productQuoteRecordService.insertSelective(entity);
        ProductQuoteRecordAddResponse res = new ProductQuoteRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:productquoterecord:del:id")
    public ProductQuoteRecordDelResponse del(@PathVariable Integer id) {
        productQuoteRecordService.deleteByPrimaryKey(id);
        ProductQuoteRecordDelResponse res = new ProductQuoteRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:productquoterecord:update")
    public ProductQuoteRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid ProductQuoteRecordUpdateRequest request) {
        ProductQuoteRecord entity=request.toProductQuoteRecord(id);
        productQuoteRecordService.updateByPrimaryKeySelective(entity);
        ProductQuoteRecordUpdateResponse res = new ProductQuoteRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
