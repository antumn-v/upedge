package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
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
public interface OrderItemDao {

    void initDischargeQuantityByPaymentId(Long paymentId);

    List<OrderStoreVariantIdsVo> selectOrderStoreVariantIdsByStoreVariantIds(@Param("storeVariantIds") List<Long> storeVariantIds);

    int increaseQuantityById(@Param("id") Long id, @Param("quantity") Integer quantity);

    int updateQuantityByStoreOrderItemId(@Param("storeOrderItemId") Long storeOrderItemId, @Param("quantity") Integer quantity);

    int updateImageNameByStoreVariantId(OrderItemUpdateImageNameRequest request);

    List<Long> selectOrderIdsByStoreOrderItemIds(@Param("storeOrderItemIds") List<Long> storeOrderItemIds);

    OrderItem selectByOrderIdAndStoreVariantId(@Param("orderId") Long orderId, @Param("storeVariantId") Long storeVariantId);

    List<Long> selectStoreVariantIdsByOrderIds(@Param("orderIds") List<Long> orderIds);

    List<OrderItem> selectItemByOrderId(Long orderId);

    List<Long> selectUnQuoteItemOrderIdByOrderIds(@Param("orderIds") List<Long> orderIds);

    List<Long> selectOrderIdsByOrderIdsAndQuoteState(@Param("orderIds") List<Long> orderIds, @Param("quoteState") Integer quoteState);

    List<Long> selectUnpaidOrderIdByStoreVariantId(Long storeVariantId);

    OrderItem selectByStoreVariantIdAndQuoteState(@Param("storeVariantId") Long storeVariantId, @Param("quoteState") Integer quoteState);

    int updateQuoteStateByIds(@Param("ids") List<Long> ids,
                              @Param("quoteState") Integer quoteState);

    int updateOrderAsQuotingByStoreVariantIds(@Param("storeVariantIds") List<Long> storeVariantIds);

    List<OrderItem> selectItemByPaymentId(Long paymentId);

    List<CustomerStockRecord> selectStockRecordByPaymentId(@Param("paymentId") Long paymentId,
                                                           @Param("customerId") Long customerId);

    void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo);

    void cancelItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo);

    Long selectStoreOrderIdById(Long id);

    Integer selectCountQuantityByOrderId(Long orderId);

    List<OrderItem> selectAppOrderItemByOrderIds(@Param("orderIds") List<Long> orderIds);

    List<AppOrderItemVo> selectAppOrderItemByOrderId(Long orderId);

    List<VariantDetail> selectAdminVariantDetailByOrder(Long orderId);

    List<VariantDetail> selectAdminVariantDetailByOrderIds(@Param("orderIds") List<Long> orderIds);

    OrderItem selectByPrimaryKey(Long id);

    List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId);

    void updateUsdPriceByAdminVariantId(@Param("adminVariantId") Long adminVariantId,
                                        @Param("usdPrice") BigDecimal usdPrice);

    int updateOrderIdByOrderItemMap(@Param("orderItemMap") Map<Long, List<Long>> orderItemMap);

    int updateAdminVariantDetailByVariantId(@Param("name") String name,
                                            @Param("value") Object value,
                                            @Param("adminVariantId") Long adminVariantId);

    int updateShippingIdByAdminProductId(@Param("shippingId") Long shippingId,
                                         @Param("adminProductId") Long adminProductId);

    int updateAdminVariantByStoreVariantId(@Param("storeVariantId") Long storeVariantId,
                                           @Param("r") RelateVariantVo r);

    int updateVolumeByVariantId(VariantDetail variantDetail);

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

    List<Long> selectOrderItemListByProduct(@Param("productId") Long productId);

    List<Long> selectOrderItemListByVariantId(@Param("variantId") Long variantId);

    int updateCustomOrderItemPrice(@Param("variantId") Long variantId, @Param("cnyPrice") BigDecimal cnyPrice, @Param("usdPrice") BigDecimal usdPrice);


    List<VariantPreSaleQuantity> selectVariantPreSaleQuantity(@Param("variantIds") List<Long> variantIds);
}
