package com.upedge.common.model.order.request;

import lombok.Data;

@Data
public class ManagerActualRequest {

    private String startTime;

    private String endTime;

    private Long managerCode;
}
