package com.upedge.oms.modules.pack.vo;

import lombok.Data;

@Data
public class PackageCountVo {

    /**
     * 包裹总数
     */
    Integer packageCount;
    /**
     * 包裹订单数
     */
    Integer packageOrderCount;
    /**
     * 订单拆分成多个包裹订单数
     */
    Integer orderSplitManyPackageCount;
    /**
     * 包裹出现两个相同订单数
     */
    Integer packageHaveSameOrderCount;
    /**
     * 潘达订单对应多个赛盒订单数
     */
    Integer orderToManySaiheOrderCount;
}
