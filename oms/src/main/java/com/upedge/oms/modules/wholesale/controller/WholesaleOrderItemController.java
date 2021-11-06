package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderItemAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderItemListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderItemUpdateRequest;
import com.upedge.oms.modules.wholesale.response.*;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
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
@RequestMapping("/wholesaleOrderItem")
public class WholesaleOrderItemController {
    @Autowired
    private WholesaleOrderItemService wholesaleOrderItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaleorderitem:info:id")
    public WholesaleOrderItemInfoResponse info(@PathVariable Long id) {
        WholesaleOrderItem result = wholesaleOrderItemService.selectByPrimaryKey(id);
        WholesaleOrderItemInfoResponse res = new WholesaleOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderitem:list")
    public WholesaleOrderItemListResponse list(@RequestBody @Valid WholesaleOrderItemListRequest request) {
        List<WholesaleOrderItem> results = wholesaleOrderItemService.select(request);
        Long total = wholesaleOrderItemService.count(request);
        request.setTotal(total);
        WholesaleOrderItemListResponse res = new WholesaleOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderitem:add")
    public WholesaleOrderItemAddResponse add(@RequestBody @Valid WholesaleOrderItemAddRequest request) {
        WholesaleOrderItem entity=request.toWholesaleOrderItem();
        wholesaleOrderItemService.insertSelective(entity);
        WholesaleOrderItemAddResponse res = new WholesaleOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderitem:del:id")
    public WholesaleOrderItemDelResponse del(@PathVariable Long id) {
        wholesaleOrderItemService.deleteByPrimaryKey(id);
        WholesaleOrderItemDelResponse res = new WholesaleOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorderitem:update")
    public WholesaleOrderItemUpdateResponse update(@PathVariable Long id, @RequestBody @Valid WholesaleOrderItemUpdateRequest request) {
        WholesaleOrderItem entity=request.toWholesaleOrderItem(id);
        wholesaleOrderItemService.updateByPrimaryKeySelective(entity);
        WholesaleOrderItemUpdateResponse res = new WholesaleOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
