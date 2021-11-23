package com.upedge.oms.modules.stock.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.oms.modules.stock.request.StockOrderItemAddRequest;
import com.upedge.oms.modules.stock.request.StockOrderItemListRequest;
import com.upedge.oms.modules.stock.request.StockOrderItemUpdateRequest;
import com.upedge.oms.modules.stock.response.*;
import com.upedge.oms.modules.stock.service.StockOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/stockOrderItem")
public class StockOrderItemController {
    @Autowired
    private StockOrderItemService stockOrderItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "stock:stockorderitem:info:id")
    public StockOrderItemInfoResponse info(@PathVariable Long id) {
        StockOrderItem result = stockOrderItemService.selectByPrimaryKey(id);
        StockOrderItemInfoResponse res = new StockOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderitem:list")
    public StockOrderItemListResponse list(@RequestBody @Valid StockOrderItemListRequest request) {
        List<StockOrderItem> results = stockOrderItemService.select(request);
        Long total = stockOrderItemService.count(request);
        request.setTotal(total);
        StockOrderItemListResponse res = new StockOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderitem:add")
    public StockOrderItemAddResponse add(@RequestBody @Valid StockOrderItemAddRequest request) {
        StockOrderItem entity=request.toStockOrderItem();
        stockOrderItemService.insertSelective(entity);
        StockOrderItemAddResponse res = new StockOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderitem:del:id")
    public StockOrderItemDelResponse del(@PathVariable Long id) {
        stockOrderItemService.deleteByPrimaryKey(id);
        StockOrderItemDelResponse res = new StockOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderitem:update")
    public StockOrderItemUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StockOrderItemUpdateRequest request) {
        StockOrderItem entity=request.toStockOrderItem(id);
        stockOrderItemService.updateByPrimaryKeySelective(entity);
        StockOrderItemUpdateResponse res = new StockOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
