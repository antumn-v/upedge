package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.enums.StockOrderTagEnum;
import com.upedge.oms.modules.stock.dto.StockOrderListDto;

/**
 * @author gx
 */
public class StockOrderListRequest extends Page<StockOrderListDto> {
    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 20;

    private String orderBy = "id desc";

    String tag = StockOrderTagEnum.To_Order.name();
}
