package com.upedge.oms.modules.order.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.vo.OrderQueryVo;
import lombok.Data;

/**
 * @author author
 */
@Data
public class OrderListQueryRequest extends Page<OrderQueryVo> {

    private Integer bagFlag;

}
