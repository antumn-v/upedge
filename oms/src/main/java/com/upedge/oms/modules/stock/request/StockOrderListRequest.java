package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.enums.StockOrderTagEnum;
import com.upedge.oms.modules.stock.dto.StockOrderListDto;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class StockOrderListRequest extends Page<StockOrderListDto> {

    private String orderBy = "id desc";

    String tag = StockOrderTagEnum.To_Order.name();
}
