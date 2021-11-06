package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CustomerStockRecordUpdateRequest{

    /**
     * 用户ID
     */
    private Long customerId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 变体ID
     */
    private Long variantId;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 订单id
     */
    private Long relateId;
    /**
     * 交易类型  增加=0，抵扣=1，退款=2
     */
    private Integer type;
    /**
     * 0=普通订单，1=批发订单
     */
    private Integer orderType;
    /**
     * 数量变动
     */
    private Integer quantity;
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

    public CustomerStockRecord toCustomerStockRecord(Long id){
        CustomerStockRecord customerStockRecord=new CustomerStockRecord();
        customerStockRecord.setId(id);
        customerStockRecord.setCustomerId(customerId);
        customerStockRecord.setProductId(productId);
        customerStockRecord.setVariantId(variantId);
        customerStockRecord.setWarehouseId(warehouseId);
        customerStockRecord.setRelateId(relateId);
        customerStockRecord.setType(type);
        customerStockRecord.setOrderType(orderType);
        customerStockRecord.setQuantity(quantity);
        customerStockRecord.setCreateTime(createTime);
        customerStockRecord.setUpdateTime(updateTime);
        customerStockRecord.setVariantImage(variantImage);
        return customerStockRecord;
    }

}
