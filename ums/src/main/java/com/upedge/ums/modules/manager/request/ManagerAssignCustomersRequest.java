package com.upedge.ums.modules.manager.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ManagerAssignCustomersRequest {

    @NotNull
    String managerCode;

    @Size(min = 1)
    List<Long> customerIds;
}
