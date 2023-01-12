package com.upedge.oms.modules.pack.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PackageReplaceRecordAddRequest{

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

    public PackageReplaceRecord toPackageReplaceRecord(){
        PackageReplaceRecord packageReplaceRecord=new PackageReplaceRecord();
        packageReplaceRecord.setPackNo(packNo);
        packageReplaceRecord.setOrderId(orderId);
        packageReplaceRecord.setTrackingCode(trackingCode);
        packageReplaceRecord.setLogisticsOrderNo(logisticsOrderNo);
        packageReplaceRecord.setCreateTime(createTime);
        return packageReplaceRecord;
    }

}
