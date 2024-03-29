package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.pms.dto.StockPurchaseOrderItemReceiveDto;
import com.upedge.common.model.pms.dto.VariantPurchaseInfoDto;
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
import java.util.List;

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

    @PostMapping("/updatePurchaseInfo")
    public int updatePurchaseInfo(@RequestBody VariantPurchaseInfoDto variantPurchaseInfoDto){
        return stockOrderItemService.updatePurchaseInfo(variantPurchaseInfoDto.getVariantId(), variantPurchaseInfoDto.getPurchaseSku(), variantPurchaseInfoDto.getSupplierName());
    }

    @PostMapping("/updateInboundQuantity")
    public BaseResponse updateInboundQuantity(@RequestBody List<StockPurchaseOrderItemReceiveDto> stockPurchaseOrderItemReceiveDtos){
        for (StockPurchaseOrderItemReceiveDto purchaseOrderItemReceiveDto : stockPurchaseOrderItemReceiveDtos) {
            stockOrderItemService.updateInboundQuantity(purchaseOrderItemReceiveDto);
        }
        return BaseResponse.success();
    }

}
