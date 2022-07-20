package com.upedge.pms.modules.purchase.vo;

import com.alibaba.trade.param.AlibabaTradePromotionModel;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderVo {

    private String id;

    private double productAmount;

    private double freight;

    private double amount;

    private String supplierName;

    private Long buyerId;

    private List<PurchaseItemVo> purchaseItemVos;


    private List<AlibabaTradePromotionModel> cargoPromotionList;


}
