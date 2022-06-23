package com.upedge.oms.modules.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class InvoiceListDto {

    Long customerId;
    Integer orderType = null;
    @JsonFormat(pattern ="yyyy-MM-dd")
    String beginTime;
    @JsonFormat(pattern ="yyyy-MM-dd")
    String endTime;
    public Long paymentId = null;
    public Long orderId = null;
    public String platOrderName = null;

//    public InvoiceListDto() {
//        initDateRange();
//    }
//
//    public void initDateRange() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
//        String end = DateUtils.getDate("yyyy-MM-dd", +1, Calendar.DAY_OF_MONTH);
//        try {
//            if (null != this.beginTime) {
//                start = format.format(this.beginTime);
//            }
//            if (null != this.endTime) {
//                end = format.format(this.endTime);
//            }
//            this.beginTime = format.parse(start);
//            this.endTime = format.parse(end);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
