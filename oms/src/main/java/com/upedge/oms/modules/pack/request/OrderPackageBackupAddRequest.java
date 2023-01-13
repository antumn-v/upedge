package com.upedge.oms.modules.pack.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackageBackup;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrderPackageBackupAddRequest{

    /**
    * 
    */
    private Long packNo;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long storeId;
    /**
    * 
    */
    private String orgPath;
    /**
    * 
    */
    private Long orderId;
    /**
    * 
    */
    private Long pickId;
    /**
    * 真实追踪号
    */
    private String trackingCode;
    /**
    * 物流商单号
    */
    private String logisticsOrderNo;
    /**
    * 物流公司
    */
    private String trackingCompany;
    /**
    * 运输方式
    */
    private String trackingMethodName;
    /**
    * 运输方式代码
    */
    private String trackingMethodCode;
    /**
    * 平台ID
    */
    private String platId;
    /**
    * 0=未出库，1=已出库，-1=取消发货
    */
    private Integer packageState;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date sendTime;
    /**
    * 
    */
    private Date receiveTime;
    /**
    * 包裹备注
    */
    private String remark;
    /**
    * 
    */
    private Integer waveNo;
    /**
    * 是否已上传店铺
    */
    private Boolean isUploadStore;
    /**
    * 
    */
    private Date backupTime;

    public OrderPackageBackup toOrderPackageBackup(){
        OrderPackageBackup orderPackageBackup=new OrderPackageBackup();
        orderPackageBackup.setPackNo(packNo);
        orderPackageBackup.setCustomerId(customerId);
        orderPackageBackup.setStoreId(storeId);
        orderPackageBackup.setOrgPath(orgPath);
        orderPackageBackup.setOrderId(orderId);
        orderPackageBackup.setPickId(pickId);
        orderPackageBackup.setTrackingCode(trackingCode);
        orderPackageBackup.setLogisticsOrderNo(logisticsOrderNo);
        orderPackageBackup.setTrackingCompany(trackingCompany);
        orderPackageBackup.setTrackingMethodName(trackingMethodName);
        orderPackageBackup.setTrackingMethodCode(trackingMethodCode);
        orderPackageBackup.setPlatId(platId);
        orderPackageBackup.setPackageState(packageState);
        orderPackageBackup.setCreateTime(createTime);
        orderPackageBackup.setSendTime(sendTime);
        orderPackageBackup.setReceiveTime(receiveTime);
        orderPackageBackup.setRemark(remark);
        orderPackageBackup.setWaveNo(waveNo);
        orderPackageBackup.setIsUploadStore(isUploadStore);
        orderPackageBackup.setBackupTime(backupTime);
        return orderPackageBackup;
    }

}
