package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AdminAccount;
import com.upedge.ums.modules.account.entity.AdminAccountAttr;
import lombok.Data;

import java.util.List;

/**
 * @author author
 */
@Data
public class AdminAccountAddRequest{

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

    private List<AdminAccountAttr> attrs;

    public AdminAccount toAdminAccount(){
        AdminAccount adminAccount=new AdminAccount();
        adminAccount.setAccountType(accountType);
        adminAccount.setAccountNumber(accountNumber);
        adminAccount.setAccountName(accountName);
        adminAccount.setAccountFlag(accountFlag);
        adminAccount.setState(1);
        return adminAccount;
    }

}
