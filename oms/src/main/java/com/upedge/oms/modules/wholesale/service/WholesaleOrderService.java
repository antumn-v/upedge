package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.request.ApplyReshipOrderRequest;
import com.upedge.oms.modules.order.request.UpdatePendingOrderRequest;
import com.upedge.oms.modules.order.request.WholesaleOrderAppListRequest;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest.WholesaleExcelData;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderShipUpdateRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleUpdateTrackingCodeRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderListResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderUpdateResponse;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderExport;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderItemVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author author
 */
public interface WholesaleOrderService{

    BaseResponse updateTrackingCode(WholesaleUpdateTrackingCodeRequest request);

    BaseResponse updateShip(WholesaleOrderShipUpdateRequest request,Session session);

    Long orderInitToAreaId(WholesaleOrder order);

    List<WholesaleOrderExport> selectOrderTrackingByDate(Long customerId,
                                                         Date start,
                                                         Date end);

    List<ShipDetail> orderShipMethods(Long id, Long toAreaId);

    List<WholesaleOrderAppVo> orderAppList(WholesaleOrderAppListRequest request, Session session);

    Map<String, Long> orderAppCount(WholesaleOrderAppListRequest request, Session session);

    WholesaleOrder selectByPrimaryKey(Long id);

    List<WholesaleOrderItemVo> selectOrderItems(Long id);

    List<WholesaleExcelData> excelDataCheck(ExcelCreateWholesaleRequest request, Session session);

    void createOrdersByExcel(Map<String, WholesaleExcelData> orderAddress, Map<String, Map<String, Integer>> orderSkuList, Long customerId);

    boolean orderUpdateAddress(Long id, AddressVo addressVo);

    BaseResponse orderUpdateShipMethod(Long id, ShipDetail shipDetail);

    int updatePayStateById(Long id, Integer state);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKeySelective(WholesaleOrder record);

    int insert(WholesaleOrder record);

    int insertSelective(WholesaleOrder record);

    List<WholesaleOrder> select(Page<WholesaleOrder> record);

    long count(Page<WholesaleOrder> record);

    WholesaleOrderListResponse adminList(WholesaleOrderListRequest request, Session session);

    WholesaleOrderListResponse historyList(WholesaleOrderListRequest request, Session session);

    BaseResponse orderDetails(Long id);

    BaseResponse applyWholesaleReshipOrder(ApplyReshipOrderRequest request, Session session)throws CustomerException;

    WholesaleOrderUpdateResponse confirmPendingOrder(UpdatePendingOrderRequest request, Session session);

    WholesaleOrderUpdateResponse cancelPendingOrder(UpdatePendingOrderRequest request, Session session);

    WholesaleOrderListResponse pendingList(WholesaleOrderListRequest request, Session session);

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
    boolean getTrackingFromSaihe(Long id) throws CustomerException;

    /**
     * 根绝payMentId 查询订单
     * @param paymentId
     * @return
     */
    List<WholesaleOrder> selectWholesaleOrderByPaymentId(Long paymentId);

    /**
     * 获取客户经理批发订单销售额 根据 set<managerCode> select
     */
    BaseResponse getWholeSaleOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo);

    /**
     * 获取某月客户经理批发订单下单客户数  根据 set<managerCode> select
     * @param allOrderAmountVo
     * @return
     */
    BaseResponse getWholeSaleOrderCount(AllOrderAmountVo allOrderAmountVo);

    int updateOrderShipMethod(Long id);

    List<WholesaleOrder> selectPage(Page<WholesaleOrder> page);

    void delOrderShipInfoByProductId(Long productId);

    void delOrderShipInfoByVariantId(Long variantId);

    void matchingShipInfoByProductId(List<WholesaleOrderItem> list);

    void matchingShipInfoByVariantId(List<WholesaleOrderItem> list);

    ShipDetail orderInitShipDetail(Long orderId);

    List<PaymentDetail> selectUploadSaiheAndUms(int wholesale);

    PaymentDetail selectUploadSaiheAndUmsOne(Long paymentId);
}

