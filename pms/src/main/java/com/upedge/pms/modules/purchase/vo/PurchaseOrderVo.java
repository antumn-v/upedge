package com.upedge.pms.modules.purchase.vo;

import com.alibaba.trade.param.AlibabaTradePromotionModel;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrderVo {

    private Long id;

    private String purchaseId;

    private BigDecimal productAmount;
    /**
     *
     */
    private BigDecimal shipPrice;
    /**
     *
     */
    private BigDecimal amount;

    private String supplierName;

    private Long buyerId;
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
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    private List<PurchaseOrderItem> purchaseItemVos;


    private List<AlibabaTradePromotionModel> cargoPromotionList;


}
