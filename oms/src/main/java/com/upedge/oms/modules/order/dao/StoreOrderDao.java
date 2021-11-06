package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.store.StoreVo;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.request.StoreOrderSaleSelectRequest;
import com.upedge.oms.modules.order.request.UnrecognizedStoreOrderListRequest;
import com.upedge.oms.modules.order.vo.StoreOrderSaleVo;
import com.upedge.oms.modules.order.vo.StoreOrderVo;
import com.upedge.oms.modules.statistics.request.AppUserSortRequest;
import com.upedge.oms.modules.statistics.vo.AppUserSortVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StoreOrderDao{

    List<Long> selectIdsByCreateTime();

    List<StoreOrderVo> selectUnGeneratedStoreOrder(UnrecognizedStoreOrderListRequest request);

    Long countUnGeneratedStoreOrder(UnrecognizedStoreOrderListRequest request);

    List<StoreOrderSaleVo> selectCustomerStoreOrderSales(@Param("customerId") Long customerId,
                                                         @Param("param") StoreOrderSaleSelectRequest request);

    BigDecimal selectOrderIncomeByCustomer(@Param("customerId") Long customerId,
                                           @Param("orgIds") List<Long> orgIds);

    Long selectOrderCountByCustomer(@Param("customerId") Long customerId,
                                    @Param("orgIds") List<Long> orgIds);

    StoreOrder selectByStorePlatId(@Param("storeId") Long storeId,
                                   @Param("platOrderId") String platOrderId);

    StoreOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(StoreOrder record);

    int updateByPrimaryKey(StoreOrder record);

    int updateByPrimaryKeySelective(StoreOrder record);

    int insert(StoreOrder record);

    int insertSelective(StoreOrder record);

    int insertByBatch(List<StoreOrder> list);

    List<StoreOrder> select(Page<StoreOrder> record);

    long count(Page<StoreOrder> record);


    List<StoreOrderVo> disConnectList(Page<StoreOrder> record);
    long disConnectCount(Page<StoreOrder> record);

    void updateFulfillmentStatus(@Param("id") Long id,
                                 @Param("fulfillmentStatus") Integer fulfillmentStatus);

    List<StoreVo> selectOrderListByStoreId(@Param("storeId") Long storeId);

    List<AppUserSortVo> listAppUserSortPage(AppUserSortRequest request);

    Long listAppUserSortCount(AppUserSortRequest request);
}
