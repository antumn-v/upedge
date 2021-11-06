package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundItemAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundItemListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundItemUpdateRequest;
import com.upedge.oms.modules.wholesale.response.*;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundItemService;
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
@RequestMapping("/wholesaleRefundItem")
public class WholesaleRefundItemController {
    @Autowired
    private WholesaleRefundItemService wholesaleRefundItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesalerefunditem:info:id")
    public WholesaleRefundItemInfoResponse info(@PathVariable Integer id) {
        WholesaleRefundItem result = wholesaleRefundItemService.selectByPrimaryKey(id);
        WholesaleRefundItemInfoResponse res = new WholesaleRefundItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefunditem:list")
    public WholesaleRefundItemListResponse list(@RequestBody @Valid WholesaleRefundItemListRequest request) {
        List<WholesaleRefundItem> results = wholesaleRefundItemService.select(request);
        Long total = wholesaleRefundItemService.count(request);
        request.setTotal(total);
        WholesaleRefundItemListResponse res = new WholesaleRefundItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefunditem:add")
    public WholesaleRefundItemAddResponse add(@RequestBody @Valid WholesaleRefundItemAddRequest request) {
        WholesaleRefundItem entity=request.toWholesaleRefundItem();
        wholesaleRefundItemService.insertSelective(entity);
        WholesaleRefundItemAddResponse res = new WholesaleRefundItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefunditem:del:id")
    public WholesaleRefundItemDelResponse del(@PathVariable Integer id) {
        wholesaleRefundItemService.deleteByPrimaryKey(id);
        WholesaleRefundItemDelResponse res = new WholesaleRefundItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefunditem:update")
    public WholesaleRefundItemUpdateResponse update(@PathVariable Integer id, @RequestBody @Valid WholesaleRefundItemUpdateRequest request) {
        WholesaleRefundItem entity=request.toWholesaleRefundItem(id);
        wholesaleRefundItemService.updateByPrimaryKeySelective(entity);
        WholesaleRefundItemUpdateResponse res = new WholesaleRefundItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
