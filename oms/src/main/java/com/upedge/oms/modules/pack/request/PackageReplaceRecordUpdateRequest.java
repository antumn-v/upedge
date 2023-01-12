package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class PackageReplaceRecordUpdateRequest{

    /**
     * 换单包裹ID
     */
    private Long packNo;
    /**
     * 
     */
    private Long orderId;
    /**
     * 原真实追踪号
     */
    private String trackingCode;
    /**
     * 原物流商单号
     */
    private String logisticsOrderNo;
    /**
     * 
     */
    private Date createTime;

    public PackageReplaceRecord toPackageReplaceRecord(Integer id){
        PackageReplaceRecord packageReplaceRecord=new PackageReplaceRecord();
        packageReplaceRecord.setId(id);
        packageReplaceRecord.setPackNo(packNo);
        packageReplaceRecord.setOrderId(orderId);
        packageReplaceRecord.setTrackingCode(trackingCode);
        packageReplaceRecord.setLogisticsOrderNo(logisticsOrderNo);
        packageReplaceRecord.setCreateTime(createTime);
        return packageReplaceRecord;
    }

}
