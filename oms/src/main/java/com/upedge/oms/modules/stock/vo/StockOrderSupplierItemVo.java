package com.upedge.oms.modules.stock.vo;

import com.upedge.common.model.oms.stock.StockOrderItemVo;
import lombok.Data;

import java.util.List;

@Data
public class StockOrderSupplierItemVo {

    private String supplierName;

    private List<StockOrderItemVo> items;
}
