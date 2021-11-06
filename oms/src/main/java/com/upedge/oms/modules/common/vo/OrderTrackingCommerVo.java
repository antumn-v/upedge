package com.upedge.oms.modules.common.vo;

import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;


/**
 * 处理回传物流不同vo问题  将相同字段封装到同一个Vo中
 */
@Data
public class OrderTrackingCommerVo {

    private String saiheOrderCode;
    private Long shipMethodId;

    private BigDecimal shipPrice;
    /**
     * 0=未发货。1=已发货。
     */
    private Integer shipState;
    /**
     * -1=已取消。0=未支付。1=已支付。2=退款申请中。3=部分退款。4=全部退款  5=支付中
     */
    private Integer payState = 0;
    private Long id;

    public  OrderTrackingCommerVo NormalOrderTrackingCommerVo(Order order,OrderTrackingCommerVo orderTrackingCommerVo){

        BeanUtils.copyProperties(order,orderTrackingCommerVo);
        return orderTrackingCommerVo;
    }


    public  OrderTrackingCommerVo wholesaleOrderTrackingCommerVo(WholesaleOrder order,OrderTrackingCommerVo orderTrackingCommerVo){
        BeanUtils.copyProperties(order,orderTrackingCommerVo);
        return orderTrackingCommerVo;
    }
}
