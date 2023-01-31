package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.common.model.oms.stock.StockOrderItemVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StockOrderItemDao {

    int updateInboundQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    StockOrderItem selectByOrderIdAndVariant(@Param("orderId") Long orderId, @Param("variantId") Long variantId);

    int updateRefundQuantityById(@Param("id") Long id, @Param("refundQuantity") Integer refundQuantity);

    int updateRefundQuantityReduceById(@Param("id") Long id, @Param("refundQuantity") Integer refundQuantity);

    int updatePurchaseInfo(@Param("variantId") Long variantId, @Param("purchaseSku") String purchaseSku, @Param("supplierName") String supplierName);

    List<StockOrderItem> selectByOrderId(Long orderId);

    List<StockOrderItemVo> selectItemVoByOrderIds(@Param("ids") List<Long> ids);

    int updatePriceByVariantId(@Param("variantId") Long variantId,
                               @Param("price") BigDecimal price);

    List<StockOrderItem> countVariantQuantityByOrderPaymentId(Long paymentId);

    List<StockOrderItem> countVariantQuantityByOrderId(Long orderId);

    StockOrderItem selectByPrimaryKey(StockOrderItem record);

    int deleteByPrimaryKey(StockOrderItem record);

    int updateByPrimaryKey(StockOrderItem record);

    int updateByPrimaryKeySelective(StockOrderItem record);

    int insert(StockOrderItem record);

    int insertSelective(StockOrderItem record);

    int insertByBatch(List<StockOrderItem> list);

    List<StockOrderItem> select(Page<StockOrderItem> record);

    long count(Page<StockOrderItem> record);

    List<StockOrderItem> listOrderItemByOrderId(Long orderId);

    StockOrderItem queryStockOrderItem(@Param("id") Long id, @Param("orderId") Long orderId);
}
