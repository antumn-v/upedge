package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.request.StoreDataListRequest;
import com.upedge.oms.modules.order.request.StoreOrderListRequest;
import com.upedge.oms.modules.order.request.UnrecognizedStoreOrderListRequest;
import com.upedge.oms.modules.order.response.StoreOrderListResponse;
import com.upedge.oms.modules.statistics.request.AppUserSortRequest;
import com.upedge.oms.modules.statistics.vo.AppUserSortVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StoreOrderService{


    void getSingleOrder(Long storeId,String platOrderId);

    List<Long> selectIdsByCreateTime();

    BaseResponse unrecognizedStoreOrderList(UnrecognizedStoreOrderListRequest request, Session session);

    /**
     * 订单保存更新
     * @param request
     * @return
     */
    StoreOrder shopifyOrderUpdate(StoreApiRequest request);

    StoreOrder woocommerceOrderUpdate(StoreApiRequest request);

    StoreOrder shoplazzaOrderUpdate(StoreApiRequest request);

    StoreOrder selectByPrimaryKey(Long id);

    Long customerStoreOrderCount(Session session);

    BigDecimal customerStoreOrderIncome(Session session);

    /**
     * 补全store_order_item订单产品信息
     * @param storeOrderId
     */
    void completeStoreOrderItemDetail(Long storeOrderId);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreOrder record);

    int updateByPrimaryKeySelective(StoreOrder record);

    int insert(StoreOrder record);

    int insertSelective(StoreOrder record);

    List<StoreOrder> select(Page<StoreOrder> record);

    long count(Page<StoreOrder> record);

    StoreOrderListResponse disConnectList(StoreOrderListRequest request);

    StoreOrderListResponse dataList(StoreDataListRequest request);

    StoreOrderListResponse exportList(StoreDataListRequest request);

    /**
     * 根据店铺id查询 店铺下所有订单
     * @param storeId
     * @return
     */
    List<StoreVo> selectOrderListByStoreId(Long storeId);

    List<AppUserSortVo> listAppUserSortPage(AppUserSortRequest request);

    Long listAppUserSortCount(AppUserSortRequest request);
}

