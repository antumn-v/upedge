package com.upedge.common.model.order.vo;

import com.upedge.common.model.pms.vo.PurchaseAdviceItemVo;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemPurchaseAdviceVo {

   private List<PurchaseAdviceItemVo> purchaseAdviceItemVos;

   private Long total;
}
