package com.upedge.oms.modules.statistics.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upedge.common.utils.DateUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class OrderPayCountRequest {

    Long customerId;

    List<Long> orgIds;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date endTime;

    public void initDateRange() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
        String end = DateUtils.getDate("yyyy-MM-dd", 0, Calendar.DAY_OF_MONTH);
        try {
            if (null != this.beginTime) {
                start = format.format(this.beginTime);
            }
            if (null != this.endTime) {
                end = format.format(this.endTime);
            }
            this.beginTime = format.parse(start);
            this.endTime = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
