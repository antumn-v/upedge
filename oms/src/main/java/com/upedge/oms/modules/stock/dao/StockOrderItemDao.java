package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StockOrderItemDao{

    int updatePriceByVariantId(@Param("variantId") Long variantId,
                               @Param("price") BigDecimal price);

    List<StockOrderItem> countVariantQuantityByOrderPaymentId(Long paymentId);

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
