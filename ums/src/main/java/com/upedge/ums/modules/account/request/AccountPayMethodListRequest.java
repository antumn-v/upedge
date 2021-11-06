package com.upedge.ums.modules.account.request;

import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class AccountPayMethodListRequest  {

    private Integer status;
    
    private String bankNum;

    private String routeType;

    private String routeName;
}
