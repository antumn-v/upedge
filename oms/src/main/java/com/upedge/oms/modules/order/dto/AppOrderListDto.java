package com.upedge.oms.modules.order.dto;

import com.upedge.oms.enums.OrderTagEnum;
import lombok.Data;

import java.util.List;

@Data
public class AppOrderListDto {

    Long customerId;

    String tags;

    Integer payState;

    Integer packState;

    Integer shipState;

    Integer refundState;

    Integer quoteState;

    Integer orderType;

    String platOrderName;

    Long orderId;

    List<Long> orderIds;

    Long paymentId;

    List<Long> paymentIds;

    Integer financialStatus;

    Integer fulfillmentStatus;

    Long orgId;

    Long storeId;

    Long toAreaId;

    List<Long> orgIds;

    List<String> platOrderNames;

    String orderNameBegin;

    String orderNameEnd;

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

    String barcode;

    Integer saiheState;

    Integer pickType;

    Long shipMethodId;

    Integer stockState;

    Integer waveNo;

    private Integer errorType;

    private String shipCompany;

    List<Long> shipMethodIds;

    private List<String> tagList;

    private Boolean isPrintLabel;

    private Integer pickState;

    private Integer trackingCodeType;

    private List<String> trackingCodes;

    private String sendBeginTime;

    private String sendEndTime;

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
                this.packState = tag.getPackState();
                this.stockState = tag.getStockState();
                this.pickState = tag.getPickState();
                this.isPrintLabel = tag.getPrintLabel();
                this.trackingCodeType = tag.getTrackingCodeType();
                if (tag.name().equals("RESHIPPED")) {
                    this.orderType = 1;
                } else {
                    this.orderType = null;
                }
                this.errorType = tag.getErrorType();
                return;
            }
        }
    }

//    public void initDateRange() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
//        String end = DateUtils.getDate("yyyy-MM-dd", +1, Calendar.DAY_OF_MONTH);
//
//        if (null == this.beginTime) {
//            this.beginTime = start;
//        }
//        if (null == this.endTime) {
//            this.endTime = end;
//        }
//    }

}
