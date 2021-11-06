package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CustomerProductStockUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Long productId;
    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private Integer stock;
    /**
     * 
     */
    private Long warehouseId;
    /**
     * 被锁定的库存
     */
    private Integer lockStock;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private String variantImage;

    public CustomerProductStock toCustomerProductStock(Long id){
        CustomerProductStock customerProductStock=new CustomerProductStock();
        customerProductStock.setId(id);
        customerProductStock.setCustomerId(customerId);
        customerProductStock.setProductId(productId);
        customerProductStock.setVariantId(variantId);
        customerProductStock.setStock(stock);
        customerProductStock.setWarehouseId(warehouseId);
        customerProductStock.setLockStock(lockStock);
        customerProductStock.setCreateTime(createTime);
        customerProductStock.setUpdateTime(updateTime);
        customerProductStock.setVariantImage(variantImage);
        return customerProductStock;
    }

}
