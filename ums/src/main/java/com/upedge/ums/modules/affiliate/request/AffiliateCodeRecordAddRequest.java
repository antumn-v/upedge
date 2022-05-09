package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class AffiliateCodeRecordAddRequest{


    String token;

    public AffiliateCodeRecord toAffiliateCodeRecord(Long customerId){
        AffiliateCodeRecord affiliateCodeRecord=new AffiliateCodeRecord();
        affiliateCodeRecord.setAffiliateCode(token);
        affiliateCodeRecord.setCustomerId(customerId);
        affiliateCodeRecord.setCreateTime(new Date());
        return affiliateCodeRecord;
    }

}
