package com.upedge.oms.modules.order.dto;

import com.upedge.common.utils.DateUtils;
import com.upedge.oms.enums.OrderTagEnum;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Data
public class AppOrderListDto {

    Long customerId;

    String tags = "ALL";

    Integer payState;

    Integer shipState;

    Integer refundState;

    Integer quoteState;

    Integer orderType;

    String platOrderName;

    String orderId;

    Long paymentId;

    Integer financialStatus;

    Integer fulfillmentStatus;

    Long orgId;

    Long storeId;

    Long toAreaId;

    List<Long> orgIds;

    List<String> platOrderNames;

    String beginTime;

    String endTime;

    /**
     * 物流单号，单独查询
     */
    String shipNumber;

    /**
     * 收件人，关联查询
     */
    String customer;

    String productTitle;

    String sku;

    public AppOrderListDto() {
//        initDateRange();
        initOrderState();
    }

    public void initOrderState() {
        for (OrderTagEnum tag : OrderTagEnum.values()) {
            if (tag.name().equals(this.tags)) {
                this.payState = tag.getPayState();
                this.shipState = tag.getShipState();
                this.refundState = tag.getRefundState();
                this.quoteState = tag.getQuoteState();
                if (tag.name().equals("RESHIPPED")) {
                    this.orderType = 1;
                } else {
                    this.orderType = null;
                }
                return;
            }
        }
    }

    public void initDateRange() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
        String end = DateUtils.getDate("yyyy-MM-dd", +1, Calendar.DAY_OF_MONTH);

        if (null == this.beginTime) {
            this.beginTime = start;
        }
        if (null == this.endTime) {
            this.endTime = end;
        }


    }

}
