package com.upedge.pms.modules.purchase.vo;

import com.alibaba.trade.param.AlibabaTradePromotionModel;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PurchaseOrderVo extends PurchaseOrder {

    private List<PurchaseOrderItem> purchaseItemVos = new ArrayList<>();

    private List<PurchaseOrderTracking> trackingList = new ArrayList<>();


    private List<AlibabaTradePromotionModel> cargoPromotionList;

    private String createFailedReason;


}
