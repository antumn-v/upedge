package com.upedge.oms.modules.pack.request;

import lombok.Data;

@Data
public class TrackingAnalysisRequest {

    String startDay;

    String endDay;

    Integer orderSourceId;

    Long customerId;

    Integer durationType;

    Integer saiheTransport;
}
