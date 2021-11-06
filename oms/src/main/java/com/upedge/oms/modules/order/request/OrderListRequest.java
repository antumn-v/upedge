package com.upedge.oms.modules.order.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.Order;
import lombok.Data;

/**
 * @author author
 */
@Data
public class OrderListRequest extends Page<Order> {

    private Integer bagFlag;

}
