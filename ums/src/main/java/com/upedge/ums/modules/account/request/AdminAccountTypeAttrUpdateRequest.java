package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;
import lombok.Data;

/**
 * @author author
 */
@Data
public class AdminAccountTypeAttrUpdateRequest{

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

    public AdminAccountTypeAttr toAdminAccountTypeAttr(Long id){
        AdminAccountTypeAttr adminAccountTypeAttr=new AdminAccountTypeAttr();
        adminAccountTypeAttr.setId(id);
        adminAccountTypeAttr.setAccountType(accountType);
        adminAccountTypeAttr.setAttrName(attrName);
        adminAccountTypeAttr.setAttrKey(attrKey);
        return adminAccountTypeAttr;
    }

}
