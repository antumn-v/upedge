package com.upedge.common.model.old.oms;

import lombok.Data;
import lombok.ToString;

/**
 * old 分组查询订单的不同类型数量

 * select app_user_id  , app_order_state ,admin_order_state,count(1)  countNum
 * from  `app_upedge_order`
 * group by app_order_state,admin_order_state,app_user_id
 * order by app_user_id asc
 *
 * -- where app_user_id = '1315830372183113728'



     select user_id  appUserId, pay_status  appOrderState,ship_status adminOrderState,count(1)  countNum
     from  `wholesale_order`
     group by pay_status,ship_status,user_id
     order by appUserId asc;
 *
 *
 */
@Data
@ToString
public class OldCompareOrderNumberVo {

    private Long appUserId;

    private Integer appOrderState;

    private Integer adminOrderState;

    private Integer countNum;
}
