package com.upedge.oms.modules.order.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerReportRequest {

    private Date date = new Date();

    private List<Long> orgIds;

    private Integer days = 30;

    private Integer years;

    private String start;

    private String end;

    private Integer rangeType = 0;

    private Date startTime;

    private Date endTime;
}
