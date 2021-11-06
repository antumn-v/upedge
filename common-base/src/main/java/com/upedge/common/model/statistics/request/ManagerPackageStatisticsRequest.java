package com.upedge.common.model.statistics.request;

import lombok.Data;

import java.util.Date;

@Data
public class ManagerPackageStatisticsRequest {

    Date beginTime;

    Date endTime;

    Integer withDay;

    String managerCode;
}
