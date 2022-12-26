package com.upedge.oms.modules.pack.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackageImportLog;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrderPackageImportLogAddRequest{

    /**
    * 
    */
    private String storeName;
    /**
    * 
    */
    private String platOrderName;
    /**
    * 
    */
    private String trackingCode;
    /**
    * 
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
    * 
    */
    private Boolean state;
    /**
    * 
    */
    private Date importTime;
    /**
    * 
    */
    private String failReason;
    /**
    * 0=其他  1=店小秘
    */
    private Integer importSource;

    public OrderPackageImportLog toOrderPackageImportLog(){
        OrderPackageImportLog orderPackageImportLog=new OrderPackageImportLog();
        orderPackageImportLog.setStoreName(storeName);
        orderPackageImportLog.setPlatOrderName(platOrderName);
        orderPackageImportLog.setTrackingCode(trackingCode);
        orderPackageImportLog.setLogisticsOrderNo(logisticsOrderNo);
        orderPackageImportLog.setTrackingCompany(trackingCompany);
        orderPackageImportLog.setTrackingMethodName(trackingMethodName);
        orderPackageImportLog.setState(state);
        orderPackageImportLog.setImportTime(importTime);
        orderPackageImportLog.setFailReason(failReason);
        orderPackageImportLog.setImportSource(importSource);
        return orderPackageImportLog;
    }

}
