package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RechargeRequestAttrUpdateRequest{

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

    public RechargeRequestAttr toRechargeRequestAttr(Integer id){
        RechargeRequestAttr rechargeRequestAttr=new RechargeRequestAttr();
        rechargeRequestAttr.setId(id);
        rechargeRequestAttr.setRechargeRequestId(rechargeRequestId);
        rechargeRequestAttr.setAttrName(attrName);
        rechargeRequestAttr.setAttrValue(attrValue);
        return rechargeRequestAttr;
    }

}
