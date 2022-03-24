package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.RelateDetailVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.AirwallexRequest;
import com.upedge.oms.modules.order.request.OrderItemQuoteRequest;
import com.upedge.oms.modules.order.request.OrderItemUpdateQuantityRequest;
import com.upedge.oms.modules.order.vo.AirwallexVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author author
 */
public interface OrderItemService{

    List<Long> selectOrderIdsByStoreOrderItemIds(List<Long> storeOrderItemIds);

    int updateQuantityByStoreOrderItemId(Long storeOrderItemId, Integer quantity);

    List<OrderItem> selectByOrderId(Long orderId);

    int updateImageNameByStoreVariantId(OrderItemUpdateImageNameRequest request);

    BaseResponse orderItemApplyQuote(OrderItemQuoteRequest request, Session session);

    void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo);

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

    int insertSelective(OrderItem record);

    List<OrderItem> select(Page<OrderItem> record);

    long count(Page<OrderItem> record);

    /**
     * 导出
     * @param airwallexRequest
     * @return
     */
    List<AirwallexVo> airwallex(AirwallexRequest airwallexRequest);
}

