package com.upedge.pms.modules.purchase.vo;

import com.alibaba.trade.param.AlibabaTradePromotionModel;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrderVo {

    private Long id;
    /**
     *
     */
    private String purchaseId;
    /**
     *
     */
    private BigDecimal productAmount;
    /**
     *
     */
    private BigDecimal shipPrice;
    /**
     *
     */
    private BigDecimal amount;
    /**
     *
     */
    private BigDecimal discountAmount;
    /**
     *
     */
    private String supplierName;
    /**
     *
     */
    private String purchaseStatus;
    /**
     *
     */
    private Integer purchaseState;
    /**
     * 0=1688采购
     */
    private Integer purchaseType;
    /**
     *
     */
    private Long operatorId;
    /**
     *
     */
    private String warehouseCode;
    /**
     * 留言
     */
    private String remark;
    /**
     * 物流单号
     */
    private String trackingCode;
    /**
     * 发货时间
     */
    private Date deliveredTime;
    /**
     * 收货时间
     */
    private Date receiveTime;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    private List<PurchaseOrderItem> purchaseItemVos = new ArrayList<>();

    private List<PurchaseOrderTracking> trackingList = new ArrayList<>();


    private List<AlibabaTradePromotionModel> cargoPromotionList;


}
