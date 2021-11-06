package com.upedge.ums.modules.manager.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerCustomerOrderSortRequest extends ManagerOrderSortRequest{

    @NotNull
    String managerCode;

    @NotNull
    Integer orderType;
}
