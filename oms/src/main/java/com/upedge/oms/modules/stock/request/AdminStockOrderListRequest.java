package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrder;
import lombok.Data;

@Data
public class AdminStockOrderListRequest  extends Page<StockOrder> {

    private String variantSku;

}
