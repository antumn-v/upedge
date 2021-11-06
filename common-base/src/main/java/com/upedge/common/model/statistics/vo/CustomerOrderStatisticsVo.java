package com.upedge.common.model.statistics.vo;

import lombok.Data;

@Data
public class CustomerOrderStatisticsVo extends OrderStatisticsVo {

    Long customerId;

    String managerCode;


}
