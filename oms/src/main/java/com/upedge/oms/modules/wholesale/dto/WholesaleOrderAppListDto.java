package com.upedge.oms.modules.wholesale.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.upedge.common.utils.DateUtils;
import com.upedge.oms.enums.WholesaleOrderTagEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class WholesaleOrderAppListDto {
    Long customerId;

    Long id;

    String tags;

    Integer payState;

    Integer shipState;

    Integer refundState;

    Integer freightReview;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date endTime;

    String productTitle;

    public WholesaleOrderAppListDto() {
        initDateRange();
        initOrderState();
    }

    public void initOrderState(){
        for (WholesaleOrderTagEnum tag: WholesaleOrderTagEnum.values()) {
            if(tag.name().equals(this.tags)){
                this.payState = tag.getPayState();
                this.shipState = tag.getShipState();
                this.refundState = tag.getRefundState();
                return;
            }
        }
    }

    public void initDateRange()  {
        if(null == this.beginTime || null == this.endTime){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String start = DateUtils.getDate("yyyy-MM-dd 00:00:00", -30, Calendar.DAY_OF_MONTH);
            String end = DateUtils.getDate("yyyy-MM-dd 23:59:59", +1, Calendar.DAY_OF_MONTH);
            try {
                this.beginTime = format.parse(start);
                this.endTime = format.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
