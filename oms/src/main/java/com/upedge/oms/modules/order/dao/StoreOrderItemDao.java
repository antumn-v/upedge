package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.StoreProductDailySales;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.request.StoreDataListRequest;
import com.upedge.oms.modules.order.vo.StoreOrderVariantData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StoreOrderItemDao{

    List<StoreProductDailySales> selectStoreProductSales();

    BigDecimal selectItemAmountByOrderId(Long orderId);

    StoreOrderItem selectByStoreVariantId(@Param("storeOrderId") Long storeOrderId,
                                          @Param("storeVariantId") Long storeVariantId);

    List<Long> selectStoreOrderIdByStoreVariantIdAndState(@Param("storeVariantId") Long storeVariantId,
                                                          @Param("state") Integer state);

    int updateStateByIds(@Param("ids") List<Long> ids,
                         @Param("state") Integer state);

    List<StoreOrderItem> selectByStoreOrderId(Long storeOrderId);

    List<StoreOrderItem> selectByStoreOrderAndOrder(@Param("orderId") Long orderId, @Param("storeOrderId") Long storeOrderId);

    List<StoreOrderItem> selectIncompleteItems(Long storeOrderId);

    List<String> selectPlatItemIdByStoreOrderId(Long storeOrderId);

    StoreOrderItem selectByPrimaryKey(StoreOrderItem record);

    int completeItemDetail(@Param("storeOrderId") Long storeOrderId,
                           @Param("variantVos") List<StoreProductVariantVo> variantVos);

    int updateRemoveState(@Param("storeOrderId") Long storeOrderId,
                          @Param("itemIds") List<String> itemIds);

    int updateStateAfterRemoveOrder(@Param("orderIds") List<Long> orderIds);

    int deleteByPrimaryKey(StoreOrderItem record);

    int updateByPrimaryKey(StoreOrderItem record);

    int updateByPrimaryKeySelective(StoreOrderItem record);

    int insert(StoreOrderItem record);

    int insertSelective(StoreOrderItem record);

    int insertByBatch(List<StoreOrderItem> list);

    List<StoreOrderItem> select(Page<StoreOrderItem> record);

    long count(Page<StoreOrderItem> record);

    List<StoreOrderVariantData> dataList(StoreDataListRequest request);
    Long dataCount(StoreDataListRequest request);
}
