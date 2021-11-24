package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderItemUpdatePurchaseNoRequest;
import com.upedge.oms.modules.stock.service.StockOrderItemService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
/**
 * 
 *
 * @author author
 */
@Api(tags = "备库订单产品")
@RestController
@RequestMapping("/stockOrderItem")
public class StockOrderItemController {
    @Autowired
    private StockOrderItemService stockOrderItemService;

    @Autowired
    StockOrderService stockOrderService;

    @ApiOperation("修改采购单号")
    @PostMapping("/updatePurchaseNo")
    public BaseResponse updateItemPurchaseNo(@RequestBody @Valid StockOrderItemUpdatePurchaseNoRequest request){
        return stockOrderService.updateOrderItemPurchaseNo(request);
    }

}
