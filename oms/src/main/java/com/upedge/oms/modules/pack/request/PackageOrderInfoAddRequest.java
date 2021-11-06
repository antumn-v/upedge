package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.PackageOrderInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageOrderInfoAddRequest{

    /**
    * 
    */
    private String orderCode;
    /**
    * 
    */
    private String clientOrderCode;
    /**
    * 
    */
    private Date payTime;
    /**
    * 
    */
    private String firstName;
    /**
    * 
    */
    private String lastName;
    /**
    * 
    */
    private Integer orderSourceId;
    /**
    * 
    */
    private String orderSourceName;
    /**
    * 
    */
    private Integer orderSourceType;
    /**
    * 
    */
    private Date shipDate;
    /**
    * 支付到发货的处理时长
    */
    private Long handleTime;
    /**
    * 
    */
    private Long customerId;
    /**
    * 从包裹生成到发货的处理时长
    */
    private Long packageShipDuration;
    /**
    * 从支付到包裹生成的处理时长
    */
    private Long payPackageDuration;

    public PackageOrderInfo toPackageOrderInfo(){
        PackageOrderInfo packageOrderInfo=new PackageOrderInfo();
        packageOrderInfo.setOrderCode(orderCode);
        packageOrderInfo.setClientOrderCode(clientOrderCode);
        packageOrderInfo.setPayTime(payTime);
        packageOrderInfo.setFirstName(firstName);
        packageOrderInfo.setLastName(lastName);
        packageOrderInfo.setOrderSourceId(orderSourceId);
        packageOrderInfo.setOrderSourceName(orderSourceName);
        packageOrderInfo.setOrderSourceType(orderSourceType);
        packageOrderInfo.setShipDate(shipDate);
        packageOrderInfo.setHandleTime(handleTime);
        packageOrderInfo.setCustomerId(customerId);
        packageOrderInfo.setPackageShipDuration(packageShipDuration);
        packageOrderInfo.setPayPackageDuration(payPackageDuration);
        return packageOrderInfo;
    }

}
