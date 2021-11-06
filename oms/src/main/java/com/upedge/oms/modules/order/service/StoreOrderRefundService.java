package com.upedge.oms.modules.order.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.store.StoreVo;
import com.upedge.oms.modules.order.entity.StoreOrderRefund;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrderRefund;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceRefundOrder;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderRefundService{

    List<StoreOrderRefund> getStoreOrderRefund(Long storeOrderId);

    StoreOrderRefund saveShopifyStoreOrderRefundDetail(ShopifyOrderRefund shopifyOrderRefund, StoreVo storeVo);

    StoreOrderRefund saveWoocommerceRefundOrder(WoocommerceRefundOrder refundOrder, StoreVo storeVo, String platOrderId);

    StoreOrderRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreOrderRefund record);

    int updateByPrimaryKeySelective(StoreOrderRefund record);

    int insert(StoreOrderRefund record);

    int insertSelective(StoreOrderRefund record);

    List<StoreOrderRefund> select(Page<StoreOrderRefund> record);

    long count(Page<StoreOrderRefund> record);
}

