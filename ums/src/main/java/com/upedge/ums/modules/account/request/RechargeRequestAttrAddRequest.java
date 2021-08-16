package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RechargeRequestAttrAddRequest{

    /**
    * 
    */
    private Long rechargeRequestId;
    /**
    * 
    */
    private String attrName;
    /**
    * 
    */
    private String attrValue;

    public RechargeRequestAttr toRechargeRequestAttr(){
        RechargeRequestAttr rechargeRequestAttr=new RechargeRequestAttr();
        rechargeRequestAttr.setRechargeRequestId(rechargeRequestId);
        rechargeRequestAttr.setAttrName(attrName);
        rechargeRequestAttr.setAttrValue(attrValue);
        return rechargeRequestAttr;
    }

}
