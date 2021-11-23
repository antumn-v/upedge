package com.upedge.oms.modules.stock.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import com.upedge.oms.modules.stock.request.StockOrderRefundAddRequest;
import com.upedge.oms.modules.stock.request.StockOrderRefundListRequest;
import com.upedge.oms.modules.stock.request.StockOrderRefundUpdateRequest;
import com.upedge.oms.modules.stock.response.*;
import com.upedge.oms.modules.stock.service.StockOrderRefundService;
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
//@RequestMapping("/stockOrderRefund")
public class StockOrderRefundController {
    @Autowired
    private StockOrderRefundService stockOrderRefundService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "stock:stockorderrefund:info:id")
    public StockOrderRefundInfoResponse info(@PathVariable Long id) {
        StockOrderRefund result = stockOrderRefundService.selectByPrimaryKey(id);
        StockOrderRefundInfoResponse res = new StockOrderRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefund:list")
    public StockOrderRefundListResponse list(@RequestBody @Valid StockOrderRefundListRequest request) {
        List<StockOrderRefund> results = stockOrderRefundService.select(request);
        Long total = stockOrderRefundService.count(request);
        request.setTotal(total);
        StockOrderRefundListResponse res = new StockOrderRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefund:add")
    public StockOrderRefundAddResponse add(@RequestBody @Valid StockOrderRefundAddRequest request) {
        StockOrderRefund entity=request.toStockOrderRefund();
        stockOrderRefundService.insertSelective(entity);
        StockOrderRefundAddResponse res = new StockOrderRefundAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefund:del:id")
    public StockOrderRefundDelResponse del(@PathVariable Long id) {
        stockOrderRefundService.deleteByPrimaryKey(id);
        StockOrderRefundDelResponse res = new StockOrderRefundDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:stockorderrefund:update")
    public StockOrderRefundUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StockOrderRefundUpdateRequest request) {
        StockOrderRefund entity=request.toStockOrderRefund(id);
        stockOrderRefundService.updateByPrimaryKeySelective(entity);
        StockOrderRefundUpdateResponse res = new StockOrderRefundUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
