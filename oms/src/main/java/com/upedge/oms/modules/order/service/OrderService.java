package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.mq.ChangeManagerVo;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.ManagerActualVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.dto.OrderAnalysisDto;
import com.upedge.oms.modules.order.dto.OrderListDto;
import com.upedge.oms.modules.order.entity.*;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderListResponse;
import com.upedge.oms.modules.order.response.OrderUpdateResponse;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author author
 */
public interface OrderService{
    /**
     * 修改订单包裹信息
     * @param id
     * @param packageState
     * @param packNo
     * @return
     */
    int updateOrderPackInfo(Long id, Integer packageState,Long packNo);

    int updateShipState(Long id,Integer shipState);

    int updateOrderPickState( List<Long> orderIds, Integer state,Long waveNo);

    void updatePickType(Long id, Integer pickType);

    int updateStockState(Long id,Integer stockState);

    BaseResponse updateOrderShippingWarehouse(Long shipMethodId);

    BaseResponse orderAddItem(OrderAddItemRequest request,Session session);

    List<Long> selectUploadSaiheFailedIds();

    List<Long> selectUnPaidIdsByShipRule( OrderShipRuleVo shipRuleVo,  Long areaId);

    BaseResponse orderCancelUploadSaihe(Long orderId);

    int cancelOrderByIds(@Param("ids") List<Long> ids);

    void initShipByShipUnitId(Long shipUnitId);

    int updateOrderVatAmountByAreaId(List<Long> areaIds,
                                     BigDecimal vatAmount);

    BaseResponse createReshipOrder(CreateReshipOrderRequest request, Session session);

    void initQuoteState(Long id);

    int initVatAmountByCustomerId(Long customerId);

    void updateSaiheOrderCode(String id,
                              String saiheOrderCode);

    int initOrderProductAmount(List<Long> orderIds);

    void initOrderVatAmountByAreaId(Long areaId,Long customerId);

    //撤销补发订单
    BaseResponse revokeReshipOrder(Long orderId,Session session);


    /**
     * 初始化订单运输方式
     * @param orderId
     */
    ShipDetail orderInitShipDetail(Long orderId);

    ShipDetail orderInitShipDetail(Long orderId,String warehouseCode);

    void orderUpdateToAreaId(Long orderId, String country);

    /**
     * 更新订单运输单元信息
     * @param orderId
     * @param shippingUnitId
     */
    void updateOrderShipUnit(Long orderId, Long shippingUnitId);

    AppOrderVo appOrderDetail(Long id);

    /**
     * 订单产品
     * @param orderId
     * @return
     */
    List<AppOrderItemVo> selectAppOrderItemByOrderId(Long orderId);

    /**
     * app客户订单列表
     * @param request
     * @return
     */
    List<AppOrderVo> selectAppOrderList(AppOrderListRequest request);

    /**
     * 订单数量
     * @param request
     * @return
     */
    Long selectAppOrderCount(AppOrderListRequest request);

    OrderAttr getOrderAttrByName(Long id, String attrName);

    OrderAddress getOrderAddress(Long id);


    List<UnPaidOrderCountVo> countCustomerUnPaidOrder(Session session);

    /**
     * 修改订单运输信息
     * @param id
     * @param shipDetail
     * @return vat
     */
    ShipDetail updateShipDetail(Long id,ShipDetail shipDetail);

    int updateOrderAttr(OrderAttr orderAttr);

    List<OrderShipMethodVo> orderShipList(Long id);

    OrderShipRuleDetail matchShipRule(Long id);

    Order selectByPrimaryKey(Long id);

    /**
     * 创建订单
     * @param storeOrderId
     * @return
     */
    Order createOrderByStoreOrder(Long storeOrderId);

    int updateByPrimaryKey(Order record);

    int updateByPrimaryKeySelective(Order record);

    int insert(Order record);

    int insertSelective(Order record);

    void deleteOrderByIds(List<Long> ids) throws CustomerException;

    List<Order> select(Page<Order> record);

    long count(Page<Order> record);

    OrderListResponse orderList(OrderListRequest request, Session session);

    BaseResponse orderDetails(Long id);

    OrderListResponse manageList(AppOrderListRequest request, Session session);

    OrderListResponse pendingList(OrderListQueryRequest request, Session session);

    OrderUpdateResponse confirmPendingOrder(UpdatePendingOrderRequest request, Session session);

    OrderUpdateResponse cancelPendingOrder(UpdatePendingOrderRequest request, Session session);

    BaseResponse applyReshipOrder(ApplyReshipOrderRequest request, Session session)throws CustomerException;

    Map<Long, List<StoreOrderRelate>> listOrderNumber(Set<Long> orderIds);

    /**
     * 导入订单到赛盒
     * @param id
     * @return
     */
    boolean importOrderToSaihe(Long id);

    /**
     * 获取赛盒物流信息
     * @param id
     * @return
     */
    boolean getTrackingFromSaihe(Long id) ;

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 潘达信息 分页列表
     * @param record
     * @return
     */
    List<PandaOrderListVo> upedgeOrderPage(Page<OrderListDto> record);
    Long upedgeOrderCount(Page<OrderListDto> record);

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 数量 和 地区分布）
     * @param query
     * @return
     */
    HashMap quantityAndArea(OrderAnalysisDto query);

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 按月统计下单数和下单金额）
     * @param request
     * @return
     */
    Map quantityAndAmount(OrderAnalysisDto request) throws ParseException;

    List<Order> getOrderList(OrderListDto dto);

    ManagerActualVo getManagerActual(ManagerActualRequest request);

    List<Order> selectOrderByPaymentId(Long paymentId);

    /**
     * 获取普通订单月销售额
     * @param allOrderAmountVo
     * Set<String> managerCodeSet
     * String startDay
     * String endDay;
     * @return
     */
    BaseResponse getNormalOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo);

    /**
     * 获取普通订单下单客户数量 根据 set<managerCode> select
     * @param allOrderAmountVo
     * @return
     */
    BaseResponse getNormalOrderCount(AllOrderAmountVo allOrderAmountVo);

    /**
     * 客户订单统计
     * @param customerId
     * @return
     */
    BaseResponse getCustomerOrderStatistical(Long customerId);

    int updateOrderShipMethod(Long id);

    /**
     * 通过枚举修改各表的managerCode  不是修改一张order表  请勿随意调用
     * @param changeManagerVo
     * @param tableName
     */
    void updateManagerCode(ChangeManagerVo changeManagerVo, String tableName);

    List<Order> selectPage(Page<Order> page);

    void delOrderShipInfoByProductId(Long productId);

    void delOrderShipInfoByVariantId(Long variantId);

    void orderInitShipDetailByIds(List<Long> ids);


    List<ShipDetail> orderLocalWarehouseShipMethods(Long orderId, Long areaId);

    List<ShipDetail> orderOverseaWarehouseShipMethods(Long orderId, Long areaId);

    OrderShipRuleDetail matchShipRule(Long orderId, OrderShipRuleVo orderShipRuleVo);

    BaseResponse importExcelOrder(OrderExcelImportRequest request,Session session);

    BaseResponse orderCustomCreate(OrderCustomCreateRequest request,Session session);

    ApiOrderInfo revokeSaiheOrder(String saiheOrderCode) throws CustomerException;

    void addNewStoreOrderItem(StoreOrder storeOrder, List<StoreOrderItem> storeOrderItems);
}

