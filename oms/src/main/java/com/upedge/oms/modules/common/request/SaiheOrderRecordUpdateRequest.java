package com.upedge.oms.modules.common.request;

import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class SaiheOrderRecordUpdateRequest{

    /**
     * 客户自定义单号
     */
    private String clientOrderCode;
    /**
     * 
     */
    private Integer orderType;
    /**
     * 赛盒订单号
     */
    private String saiheOrderCode;
    /**
     * 
     */
    private Long customerId;

    /**
     * 
     */
    private String managerCode;
    /**
     * 
     */
    private String orderSourceId;
    /**
     * 
     */
    private String orderSourceName;
    /**
     * app运输方式ID
     */
    private Long shipMethodId;
    /**
     * 
     */
    private Integer transportId;
    /**
     * 
     */
    private Date importTime;
    /**
     * 0=失败，1=成功
     */
    private Integer state;

    public SaiheOrderRecord toSaiheOrderRecord(Long id){
        SaiheOrderRecord saiheOrderRecord=new SaiheOrderRecord();
        saiheOrderRecord.setId(id);
        saiheOrderRecord.setClientOrderCode(clientOrderCode);
        saiheOrderRecord.setOrderType(orderType);
        saiheOrderRecord.setSaiheOrderCode(saiheOrderCode);
        saiheOrderRecord.setCustomerId(customerId);
        saiheOrderRecord.setManagerCode(managerCode);
        saiheOrderRecord.setOrderSourceId(orderSourceId);
        saiheOrderRecord.setOrderSourceName(orderSourceName);
        saiheOrderRecord.setShipMethodId(shipMethodId);
        saiheOrderRecord.setTransportId(transportId);
        saiheOrderRecord.setImportTime(importTime);
        saiheOrderRecord.setState(state);
        return saiheOrderRecord;
    }

}
