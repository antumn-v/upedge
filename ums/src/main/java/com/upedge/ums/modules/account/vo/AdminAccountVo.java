package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminAccountVo {

    /**
     *
     */
    private Long id;
    /**
     * 账号类型 1:Payoneer  2:PayPal  3:AirWallex 4:Transferwise
     5:银行账号
     */
    private Integer accountType;
    /**
     * 账号
     */
    private String accountNumber;
    /**
     * 账号名
     */
    private String accountName;
    /**
     * 0:对公 1:对私
     */
    private Integer accountFlag;
    /**
     * 0:禁用 1:启用
     */
    private Integer state;

    private List<AdminAccountAttrVo> attrs;

}
