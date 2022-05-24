package com.upedge.ums.modules.manager.request;

import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerManagerUpdateRequest{

    /**
     * 用户经理code
     */
    private String managerCode;
    /**
     * 
     */
    private Date createTime;



}
