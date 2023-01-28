package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.request.CustomerProductStockCustomUpdateRequest;
import com.upedge.oms.modules.stock.vo.CustomerWarehouseVariantStockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface CustomerProductStockDao {



    List<CustomerProductStock> selectByCustomerIds(@Param("customerIds") List<Long> customerIds);

    int updateByWholesaleOrderItem(@Param("variantId") Long variantId, @Param("customerId") Long customerId, @Param("warehouseCode") String warehouseCode, @Param("dischargeQuantity") Integer dischargeQuantity, @Param("originalDischargeQuantity") Integer originalDischargeQuantity);

    List<CustomerProductStock> selectCustomerStockByVariantIds(@Param("customerId") Long customerId, @Param("variantIds") List<Long> variantIds);

    List<String> selectCustomerStockWarehouses(Long customerId);

    List<CustomerProductStock> selectAllCustomerStocks();

    List<CustomerWarehouseVariantStockVo> selectCustomerWarehouseVariantStock(Long customerId);

    long selectVariantStockByCustomer(@Param("customerId") Long customerId,
                                      @Param("variantId") Long variantId,
                                      @Param("warehouseCode") String warehouseCode);

    int increaseVariantStock(@Param("stocks") List<CustomerProductStock> stocks);

    List<Long> selectWarehouseVariantIdsByCustomer(@Param("customerId") Long customerId,
                                                   @Param("warehouseCode") String warehouseCode);

    CustomerProductStock selectByPrimaryKey(CustomerProductStock record);

    /**
     * 支付订单时锁定已使用的库存
     *
     * @param customerId
     * @param items
     * @return
     */
    boolean increaseCustomerLockStock(@Param("customerId") Long customerId,
                                      @Param("items") List<ItemDischargeQuantityVo> items);

    /**
     * 从锁定的库存中减少库存（支付订单已完成）
     *
     * @param customerId
     * @param items
     * @return
     */
    int reduceFromLockStock(@Param("customerId") Long customerId,
                            @Param("items") List<ItemDischargeQuantityVo> items);

    /**
     * 从锁定的库存中恢复库存（支付订单失败）
     *
     * @param customerId
     * @param items
     * @return
     */
    int increaseFromLockStock(@Param("customerId") Long customerId,
                              @Param("items") List<ItemDischargeQuantityVo> items);

    int reduceCustomerStockAfterDeduct(@Param("customerId") Long customerId,
                                       @Param("items") List<ItemDischargeQuantityVo> items);

    int deleteByPrimaryKey(CustomerProductStock record);

    int updateByPrimaryKey(CustomerProductStock record);

    int updateByPrimaryKeySelective(CustomerProductStock record);

    int insert(CustomerProductStock record);

    int insertSelective(CustomerProductStock record);

    int insertByBatch(List<CustomerProductStock> list);

    List<CustomerProductStock> select(Page<CustomerProductStock> record);

    long count(Page<CustomerProductStock> record);

    /**
     * 查询客户产品库存
     */
    CustomerProductStock queryStockForCustomerProduct(
            @Param("customerId") Long customerId,
            @Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode);

    Integer subStockForRefund(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<CustomerProductStockNumVo> getCustomerStockNum();

    CustomerProductStock selectStockByVariantAndCustomerId(@Param("variantId") Long variantId,
                                                           @Param("customerId") Long customerId,
                                                           @Param("warehouseCode") String warehouseCode);

    int customUpdateCustomerProductStock(CustomerProductStockCustomUpdateRequest request);
}
