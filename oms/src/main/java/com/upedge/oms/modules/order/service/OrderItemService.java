package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.oms.order.OrderStockClearRequest;
import com.upedge.common.model.order.dto.OrderItemPurchaseAdviceDto;
import com.upedge.common.model.order.vo.OrderItemPurchaseAdviceVo;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.product.RelateDetailVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.vo.AirwallexVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface OrderItemService {

    BaseResponse updateDeclarePrice(OrderItemDeclarePriceUpdateRequest request,Session session);

    BaseResponse updateVariant(OrderItemUpdateVariantRequest request,Session session);

    int updateQuantityById(Long id,  Integer quantity);

    List<OrderItem> selectUnPaidItemByStoreOrderId(Long storeOrderId);

    int updateLockedQuantityClear(OrderStockClearRequest request);

    List<Long> selectOutStockOrderIdsByVariantId(Long variantId);

    List<Long> selectItemAdminVariantIds(OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto);

    List<OrderItem> selectByOrderIds(List<Long> orderIds);

    int batchUpdatePickedQuantity(List<OrderItem> orderItems);

    Integer selectCountQuantityByOrderId(Long orderId);

    List<Long> selectOrderIdsByStoreOrderItemIds(List<Long> storeOrderItemIds);

    int updateQuantityByStoreOrderItemId(Long storeOrderItemId, Integer quantity);

    List<OrderItem> selectByOrderId(Long orderId);

    int updateImageNameByStoreVariantId(OrderItemUpdateImageNameRequest request);

    BaseResponse orderItemApplyQuote(OrderItemQuoteRequest request, Session session);

    void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo);

    void updatePaidOrderItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) throws CustomerException;

    @Transactional
    void updateSplitVariantItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) throws CustomerException;

    List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId);

    boolean addOrderItemFromStoreOrder(Long orderId, Long storeOrderId, RelateDetailVo relateDetailVo);

    void updateAdminVariantByVariantId(VariantDetail variantDetail, String tag);

    int updateQuantity(OrderItemUpdateQuantityRequest request, Long id);

    int updateAdminVariantByStoreVariantId(Long storeVariantId,
                                           RelateVariantVo relateVariantVo);

    int saveItemStockRecord(Long paymentId, Long customerId);

    OrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderItem record);

    int updateByPrimaryKeySelective(OrderItem record);

    int insert(OrderItem record);

    int insertByBatch(List<OrderItem> record);

    int insertSelective(OrderItem record);

    List<OrderItem> select(Page<OrderItem> record);

    long count(Page<OrderItem> record);

    /**
     * 导出
     *
     * @param airwallexRequest
     * @return
     */
    List<AirwallexVo> airwallex(AirwallexRequest airwallexRequest);


    int updateCustomOrderItemPrice(Long variantId, BigDecimal cnyPrice, BigDecimal usdPrice);

    List<VariantPreSaleQuantity> selectVariantPreSaleQuantity(List<Long> variantIds);

    OrderItemPurchaseAdviceVo selectUnStockOrderItems(OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto);
}

