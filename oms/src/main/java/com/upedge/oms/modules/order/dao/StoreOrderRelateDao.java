package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author author
 */
public interface StoreOrderRelateDao {

    List<StoreOrderRelate> selectByStockOrderInfo(@Param("storeName") String storeName, @Param("platOrderName") String platOrderName);

    int deleteByOrderId(@Param("orderIds") List<Long> orderIds);

    int updateStoreStatusByStoreOrderId(StoreOrder storeOrder);

    List<StoreOrderRelate> selectByOrderIds(@Param("ids") List<Long> ids);

    List<StoreOrderRelate> selectUnPaidByStoreOrderId(@Param("storeOrderIds") List<Long> storeOrderIds);

    List<StoreOrderRelate> selectByOrderId(Long orderId);

    List<StoreOrderRelate> selectByStoreOrderId(Long storeOrderId);

    StoreOrderRelate selectByPrimaryKey(StoreOrderRelate record);

    int updateCustomerNameByOrderId(@Param("orderId") Long orderId,
                                    @Param("customerName") String customerName);

    int updateOrderId(@Param("orderIds") List<Long> orderIds,
                      @Param("orderId") Long orderId);

    int deleteByOrderAndStoreOrder(@Param("orderId") Long orderId,
                                   @Param("storeOrderId") Long storeOrderId);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(StoreOrderRelate record);

    int updateByPrimaryKeySelective(StoreOrderRelate record);

    int insert(StoreOrderRelate record);

    int insertSelective(StoreOrderRelate record);

    int insertByBatch(List<StoreOrderRelate> list);

    List<StoreOrderRelate> select(Page<StoreOrderRelate> record);

    long count(Page<StoreOrderRelate> record);

    List<StoreOrderRelate> listStoreOrderRelateByOrderIds(@Param("orderIds") Set<Long> orderIds);

    List<StoreOrderRelate> selectRepeatOrder();
}
