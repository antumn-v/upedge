package com.upedge.ums.modules.account.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;
import lombok.Data;

/**
 * @author author
 */
@Data
public class AdminAccountTypeAttrAddRequest{

    /**
    * 账户类型
    */
    private Integer accountType;
    /**
    * 
    */
    private String attrName;
    /**
    * 
    */
    private String attrKey;

    public AdminAccountTypeAttr toAdminAccountTypeAttr(){
        AdminAccountTypeAttr adminAccountTypeAttr=new AdminAccountTypeAttr();
        adminAccountTypeAttr.setId(IdGenerate.nextId());
        adminAccountTypeAttr.setAccountType(accountType);
        adminAccountTypeAttr.setAttrName(attrName);
        adminAccountTypeAttr.setAttrKey(attrKey);
        return adminAccountTypeAttr;
    }

}
