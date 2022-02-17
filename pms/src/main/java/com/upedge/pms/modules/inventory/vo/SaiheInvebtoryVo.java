package com.upedge.pms.modules.inventory.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户库存分析列表
 */
@Data
public class SaiheInvebtoryVo {

    /**
     * variant_sku
     */
    private String variantSku;
    /**
     * 可用库存数量
     */
    private Integer goodNum;
    /**
     * 锁定库存数量
     */
    private Integer lockNum;
    /**
     * 仓库id
     */
    private Integer warehouseCode;
    /**
     * 库存更新时间
     */
    private Date updateTime;
    private String updateTimeStr;

    /**
     * 活跃天数(库龄)
     */
    private Integer activeDays;
    /**
     * 活跃时间
     */
    private Date activeTime;
    private String activeTimeStr;
    /**
     * 库位
     */
    private String positionCode;
    /**
     * 采购中
     */
    private Integer processingNum;
    /**
     * 历史入库
     */
    private Integer historyInNum;
    /**
     * 历史出库
     */
    private Integer historyOutNum;

    private Integer upedgeNum;
    private BigDecimal upedgeSkuAmount;
    private BigDecimal variantPrice;

    private String productId;
    private String variantId;

    private Integer start;
    private Integer size;

    private Integer option1;
    private Integer option2;
    private Integer option3;

}
