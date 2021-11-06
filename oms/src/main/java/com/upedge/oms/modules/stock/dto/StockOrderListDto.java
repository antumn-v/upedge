package com.upedge.oms.modules.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upedge.common.utils.DateUtils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
public class StockOrderListDto  {

    @JsonIgnore
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Long orderId;

    private Long paymentId;

    private Long customerId;

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

    {
        try {
            dateBegin = DateUtils.getDate("yyyy-MM-dd HH:mm:ss", -30, Calendar.DAY_OF_MONTH);

            dateEnd =  DateUtils.getDate("yyyy-MM-dd HH:mm:ss", +1, Calendar.DAY_OF_MONTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
}
