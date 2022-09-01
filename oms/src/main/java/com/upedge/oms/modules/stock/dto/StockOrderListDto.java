package com.upedge.oms.modules.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class StockOrderListDto  {

    @JsonIgnore
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long orderId;

    private Long paymentId;

    private Long customerId;


    private Integer shipReview;
    /**
     * 支付状态,待支付=0，已支付=1，取消订单=-1
     */
    private Integer payState;
    /**
     * 退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
     */
    private Integer refundState;


    /**
     * create_time,pay_time
     */
    private String dateType = "create_time";

    private String dateBegin;

    private String dateEnd;

    /**
     *
     */
    private String productTitle;
    /**
     *
     */
    private String variantName;
    /**
     *
     */
    private String variantSku;

    private String warehouseCode;
}
