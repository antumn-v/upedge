package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class ApplyStockOrderRefundRequest {

    @NotNull
    private Long orderId;
    @NotBlank
    private String reason;

    @Size(min = 1)
    List<StockOrderRefundItem> refundItemList;

    public StockOrderRefund toStockOrderRefund(StockOrder stockOrder, String managerCode) {
        StockOrderRefund stockOrderRefund=new StockOrderRefund();
        stockOrderRefund.setStockOrderId(orderId);
        //退款状态，申请中=0，通过=1，驳回=2
        stockOrderRefund.setState(0);
        stockOrderRefund.setCreateTime(new Date());
        stockOrderRefund.setUpdateTime(new Date());
        stockOrderRefund.setSource(1);
        stockOrderRefund.setCustomerId(stockOrder.getCustomerId());
        //设置后台操作人
        stockOrderRefund.setManagerCode(managerCode);
        stockOrderRefund.setWarehouseId(stockOrder.getWarehouseId().intValue());
        return stockOrderRefund;
    }
}