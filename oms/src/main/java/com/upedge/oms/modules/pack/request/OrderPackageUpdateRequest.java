package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.OrderPackage;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class OrderPackageUpdateRequest{

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
    private Long packageNo;
    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private Integer waveNo;
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
     * 0=待发货，1=已发货
     */
    private Integer pageageState;
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

    public OrderPackage toOrderPackage(Long id){
        OrderPackage orderPackage=new OrderPackage();
        orderPackage.setId(id);
        orderPackage.setCustomerId(customerId);
        orderPackage.setStoreId(storeId);
        orderPackage.setOrgPath(orgPath);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setWaveNo(waveNo);
        orderPackage.setTrackingCode(trackingCode);
        orderPackage.setLogisticsOrderNo(logisticsOrderNo);
        orderPackage.setTrackingCompany(trackingCompany);
        orderPackage.setTrackingMethodName(trackingMethodName);
        orderPackage.setTrackingMethodCode(trackingMethodCode);
        orderPackage.setPlatId(platId);
        orderPackage.setPackageState(pageageState);
        orderPackage.setCreateTime(createTime);
        orderPackage.setSendTime(sendTime);
        orderPackage.setReceiveTime(receiveTime);
        return orderPackage;
    }

}
