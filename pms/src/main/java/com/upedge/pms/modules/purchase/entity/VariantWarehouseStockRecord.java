package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.util.Date;
@Data
public class VariantWarehouseStockRecord {

    //0：发货减少，修改锁定库存，1=增加，入库，2：锁定，减少安全库存，增加锁定库存，3：手动修改安全库存，4：修改采购中数量，5：手动出库，6：手动入库
    public static Integer SHIP_REDUCE = 0;
    public static Integer PURCHASE_ADD = 1;
    public static Integer STOCK_LOCK = 2;
    public static Integer CUSTOM_UPDATE = 3;
    public static Integer PURCHASE_UPDATE = 4;
    public static Integer CUSTOM_EX = 5;
    public static Integer CUSTOM_IM = 6;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long variantId;
    /**
     *
     */
    private String warehouseCode;
    /**
     *
     */
    private Integer changeStock;
    /**
     *
     */
    private Integer processType;
    /**
     *
     */
    private Integer originalStock;
    /**
     *
     */
    private Integer nowStock;
    /**
     *
     */
    private Long relateId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String shelfNum;

    private Long operatorId;

    private Integer processStatus;

    public VariantWarehouseStockRecord() {
    }

    public VariantWarehouseStockRecord(Long variantId, String warehouseCode, Integer changeStock, Integer processType, Integer originalStock, Integer nowStock, Long relateId, Date createTime, String shelfNum,Long operatorId,Integer processStatus) {
        this.variantId = variantId;
        this.warehouseCode = warehouseCode;
        this.changeStock = changeStock;
        this.processType = processType;
        this.originalStock = originalStock;
        this.nowStock = nowStock;
        this.relateId = relateId;
        this.createTime = createTime;
        this.shelfNum = shelfNum;
        this.operatorId = operatorId;
        this.processStatus = processStatus;
    }
}
