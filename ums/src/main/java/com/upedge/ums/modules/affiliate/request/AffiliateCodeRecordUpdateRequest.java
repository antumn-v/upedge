package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class AffiliateCodeRecordUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Date createTime;

    public AffiliateCodeRecord toAffiliateCodeRecord(String affiliateCode){
        AffiliateCodeRecord affiliateCodeRecord=new AffiliateCodeRecord();
        affiliateCodeRecord.setAffiliateCode(affiliateCode);
        affiliateCodeRecord.setCustomerId(customerId);
        affiliateCodeRecord.setCreateTime(createTime);
        return affiliateCodeRecord;
    }

}
