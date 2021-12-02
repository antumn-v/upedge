package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.AirwallexRequest;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author author
 */
public interface OrderItemDao{

    int updateQuoteStateByIds(@Param("ids") List<Long> ids,
                              @Param("quoteState") Integer quoteState);

    List<OrderItem> selectItemByPaymentId(Long paymentId);

    List<CustomerStockRecord> selectStockRecordByPaymentId(@Param("paymentId") Long paymentId,
                                                           @Param("customerId") Long customerId);

    void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo);

    Long selectStoreOrderIdById(Long id);

    Integer selectCountQuantityByOrderId(Long orderId);

    List<AppStoreOrderVo> selectAppOrderItemByOrderIds(@Param("orders") List<AppOrderVo> orders);

    List<AppOrderItemVo> selectAppOrderItemByOrderId(Long orderId);

    List<VariantDetail> selectAdminVariantDetailByOrder(Long orderId);

    List<VariantDetail> selectAdminVariantDetailByOrderIds(@Param("orderIds") List<Long> orderIds);

    OrderItem selectByPrimaryKey(Long id);

    List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId);

    void  updateUsdPriceByAdminVariantId(@Param("adminVariantId") Long adminVariantId,
                                         @Param("usdPrice") BigDecimal usdPrice);

    int updateOrderIdByOrderItemMap(@Param("orderItemMap") Map<Long, List<Long>> orderItemMap);

    int updateAdminVariantDetailByVariantId(@Param("name") String name,
                                            @Param("value") Object value,
                                            @Param("adminVariantId") Long adminVariantId);

    int updateShippingIdByAdminProductId(@Param("shippingId") Long shippingId,
                                         @Param("adminProductId") Long adminProductId);

    int updateAdminVariantByStoreVariantId(@Param("storeVariantId") Long storeVariantId,
                                           @Param("r") RelateVariantVo r);

    int updateOrderIdByOrderIds(@Param("orderIds") List<Long> orderIds,
                                @Param("orderId") Long orderId);

    int updateDischargeQuantityByMap(@Param("map") Map<Long, Integer> map);

    int deleteByPrimaryKey(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int updateByPrimaryKeySelective(OrderItem record);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    int insertByBatch(List<OrderItem> list);

    List<OrderItem> select(Page<OrderItem> record);

    long count(Page<OrderItem> record);

    List<OrderItemVo> listOrderItem(Long orderId);

    OrderItem queryOrderItemByIdAndOrderId(@Param("id") Long id, @Param("orderId") Long orderId);

    List<SaiheOrderItem> listSaiheOrderItemByOrderId(Long orderId);

    List<AirwallexVo> airwallex(@Param("t") AirwallexRequest airwallexRequest);

    List<OrderItem> selectOrderItemListByProduct(@Param("productId") Long productId);

    List<OrderItem> selectOrderItemListByVariantId(@Param("variantId") Long variantId);
}
