package com.upedge.oms.modules.stock.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;
import com.upedge.oms.modules.stock.request.StockOrderRefundItemAddRequest;
import com.upedge.oms.modules.stock.request.StockOrderRefundItemListRequest;
import com.upedge.oms.modules.stock.request.StockOrderRefundItemUpdateRequest;
import com.upedge.oms.modules.stock.response.*;
import com.upedge.oms.modules.stock.service.StockOrderRefundItemService;
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
@RequestMapping("/stockOrderRefundItem")
public class StockOrderRefundItemController {
    @Autowired
    private StockOrderRefundItemService stockOrderRefundItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "stock:stockorderrefunditem:info:id")
    public StockOrderRefundItemInfoResponse info(@PathVariable Long id) {
        StockOrderRefundItem result = stockOrderRefundItemService.selectByPrimaryKey(id);
        StockOrderRefundItemInfoResponse res = new StockOrderRefundItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefunditem:list")
    public StockOrderRefundItemListResponse list(@RequestBody @Valid StockOrderRefundItemListRequest request) {
        List<StockOrderRefundItem> results = stockOrderRefundItemService.select(request);
        Long total = stockOrderRefundItemService.count(request);
        request.setTotal(total);
        StockOrderRefundItemListResponse res = new StockOrderRefundItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefunditem:add")
    public StockOrderRefundItemAddResponse add(@RequestBody @Valid StockOrderRefundItemAddRequest request) {
        StockOrderRefundItem entity=request.toStockOrderRefundItem();
        stockOrderRefundItemService.insertSelective(entity);
        StockOrderRefundItemAddResponse res = new StockOrderRefundItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefunditem:del:id")
    public StockOrderRefundItemDelResponse del(@PathVariable Long id) {
        stockOrderRefundItemService.deleteByPrimaryKey(id);
        StockOrderRefundItemDelResponse res = new StockOrderRefundItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefunditem:update")
    public StockOrderRefundItemUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StockOrderRefundItemUpdateRequest request) {
        StockOrderRefundItem entity=request.toStockOrderRefundItem(id);
        stockOrderRefundItemService.updateByPrimaryKeySelective(entity);
        StockOrderRefundItemUpdateResponse res = new StockOrderRefundItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
