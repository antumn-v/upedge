package com.upedge.common.model.old.oms;

import lombok.Data;
import lombok.ToString;

/**
 * oms 订单不同类型分组数量统计
 *
     select customer_id, pay_state,refund_state,ship_state,count(1) countNum
     from  `order`
     group by pay_state,refund_state,ship_state,customer_id
     order by customer_id asc

     -- where customer_id = '1315830372183113728'


     select customer_id, pay_state,refund_state,ship_state,count(1)
     from  `wholesale_order`
     group by pay_state,refund_state,ship_state,customer_id;
     order by customer_id asc
 */
@Data
@ToString
public class OmsCompareOrderNumberVo {

    private Long customerId;

    private Integer payState;

    private Integer refundState;

    private Integer shipState;

    private Integer countNum;
}
