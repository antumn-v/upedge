package com.upedge.common.model.statistics.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatisticsDto {

    Date beginTime;

    Date endTime;

    Integer orderType;

    String managerCode;

    Long customerId;

}
