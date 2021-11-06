package com.upedge.pms.modules.quote.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.quote.request.QuoteApplyItemAddRequest;
import com.upedge.pms.modules.quote.request.QuoteApplyItemListRequest;
import com.upedge.pms.modules.quote.request.QuoteApplyItemUpdateRequest;

import com.upedge.pms.modules.quote.response.QuoteApplyItemAddResponse;
import com.upedge.pms.modules.quote.response.QuoteApplyItemDelResponse;
import com.upedge.pms.modules.quote.response.QuoteApplyItemInfoResponse;
import com.upedge.pms.modules.quote.response.QuoteApplyItemListResponse;
import com.upedge.pms.modules.quote.response.QuoteApplyItemUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/quoteApplyItem")
public class QuoteApplyItemController {
    @Autowired
    private QuoteApplyItemService quoteApplyItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "quote:quoteapplyitem:info:id")
    public QuoteApplyItemInfoResponse info(@PathVariable Long id) {
        QuoteApplyItem result = quoteApplyItemService.selectByPrimaryKey(id);
        QuoteApplyItemInfoResponse res = new QuoteApplyItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "quote:quoteapplyitem:list")
    public QuoteApplyItemListResponse list(@RequestBody @Valid QuoteApplyItemListRequest request) {
        List<QuoteApplyItem> results = quoteApplyItemService.select(request);
        Long total = quoteApplyItemService.count(request);
        request.setTotal(total);
        QuoteApplyItemListResponse res = new QuoteApplyItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "quote:quoteapplyitem:add")
    public QuoteApplyItemAddResponse add(@RequestBody @Valid QuoteApplyItemAddRequest request) {
        QuoteApplyItem entity=request.toQuoteApplyItem();
        quoteApplyItemService.insertSelective(entity);
        QuoteApplyItemAddResponse res = new QuoteApplyItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:quoteapplyitem:del:id")
    public QuoteApplyItemDelResponse del(@PathVariable Long id) {
        quoteApplyItemService.deleteByPrimaryKey(id);
        QuoteApplyItemDelResponse res = new QuoteApplyItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:quoteapplyitem:update")
    public QuoteApplyItemUpdateResponse update(@PathVariable Long id,@RequestBody @Valid QuoteApplyItemUpdateRequest request) {
        QuoteApplyItem entity=request.toQuoteApplyItem(id);
        quoteApplyItemService.updateByPrimaryKeySelective(entity);
        QuoteApplyItemUpdateResponse res = new QuoteApplyItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
