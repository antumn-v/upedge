package com.upedge.pms.modules.purchase.vo;

import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import lombok.Data;

import java.util.List;

@Data
public class PurchasePlanVo {

    String supplierName;

    List<PurchasePlan> purchasePlans;
}
