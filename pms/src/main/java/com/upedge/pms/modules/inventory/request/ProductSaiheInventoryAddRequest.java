package com.upedge.pms.modules.inventory.request;

import com.upedge.pms.modules.inventory.entity.ProductSaiheInventory;
import lombok.Data;

import java.util.Date;

/**
 * @author Ï¦Îí
 */
@Data
public class ProductSaiheInventoryAddRequest{

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
    /**
    * 活跃天数(库龄)
    */
    private Integer activeDays;
    /**
    * 活跃时间
    */
    private Date activeTime;
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
    /**
    * 
    */
    private Integer customerStock;

    public ProductSaiheInventory toProductSaiheInventory(){
        ProductSaiheInventory productSaiheInventory=new ProductSaiheInventory();
        productSaiheInventory.setGoodNum(goodNum);
        productSaiheInventory.setLockNum(lockNum);
        productSaiheInventory.setWarehouseCode(warehouseCode);
        productSaiheInventory.setUpdateTime(updateTime);
        productSaiheInventory.setActiveDays(activeDays);
        productSaiheInventory.setActiveTime(activeTime);
        productSaiheInventory.setPositionCode(positionCode);
        productSaiheInventory.setProcessingNum(processingNum);
        productSaiheInventory.setHistoryInNum(historyInNum);
        productSaiheInventory.setHistoryOutNum(historyOutNum);
        productSaiheInventory.setCustomerStock(customerStock);
        return productSaiheInventory;
    }

}
