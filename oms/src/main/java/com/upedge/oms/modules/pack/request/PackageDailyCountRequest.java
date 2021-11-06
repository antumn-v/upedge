package com.upedge.oms.modules.pack.request;

import lombok.Data;

@Data
public class PackageDailyCountRequest {

    String dateMonth;

    String beginTime;

    String endTime;

    String managerCode;

    Long customerId;

    Integer orderSourceId;
}
