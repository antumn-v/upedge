package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class AdminCreditUpdateRequest{
    @NotNull
    private Long id;

}
