package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.StoreOrderDao;
import com.upedge.oms.modules.order.dao.StoreOrderRefundDao;
import com.upedge.oms.modules.order.dao.StoreOrderRefundItemDao;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.entity.StoreOrderRefund;
import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;
import com.upedge.oms.modules.order.service.StoreOrderRefundService;
import com.upedge.thirdparty.shopify.moudles.order.controller.ShopifyOrderApi;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrderRefund;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrderRefundItem;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyTransaction;
import com.upedge.thirdparty.woocommerce.moudles.order.api.WoocommerceOrderApi;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderItem;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceRefundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class StoreOrderRefundServiceImpl implements StoreOrderRefundService {


    @Autowired
    private StoreOrderDao storeOrderDao;

    @Autowired
    private StoreOrderRefundDao storeOrderRefundDao;

    @Autowired
    StoreOrderRefundItemDao storeOrderRefundItemDao;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<StoreOrderRefund> getStoreOrderRefund(Long storeOrderId) {

        StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderId);

        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + storeOrder.getStoreId());
        if (null == storeVo) {
            StoreSearchRequest storeSearchRequest = new StoreSearchRequest();
            storeSearchRequest.setId(storeOrder.getStoreId());
            storeVo = JSONObject.parseObject(JSON.toJSONString(umsFeignClient.storeSearch(storeSearchRequest).getData())).toJavaObject(StoreVo.class);
        }
        JSONArray jsonArray = new JSONArray();
        List<StoreOrderRefund> storeOrderRefunds = new ArrayList<>();
        switch (storeVo.getStoreType()) {
            case 0:
                JSONObject jsonObject = ShopifyOrderApi.getOrderRefundList(storeOrder.getPlatOrderId(), storeVo.getStoreName(), storeVo.getApiToken());
                if (null == jsonObject) {
                    break;
                }

                jsonArray = jsonObject.getJSONArray("refunds");

                List<ShopifyOrderRefund> refundList = jsonArray.toJavaList(ShopifyOrderRefund.class);

                for (ShopifyOrderRefund refund : refundList) {
                    StoreOrderRefund storeOrderRefund = saveShopifyStoreOrderRefundDetail(refund, storeVo);
                    if(null != storeOrderRefund){
                        storeOrderRefunds.add(storeOrderRefund);
                    }
                }
                break;
            case 1:
                jsonArray = WoocommerceOrderApi.getOrderRefund(storeVo.getApiToken(), storeVo.getStoreUrl(), storeOrder.getPlatOrderId());

                if (ListUtils.isEmpty(jsonArray)) {
                    break;
                }

                List<WoocommerceRefundOrder> refundOrders = jsonArray.toJavaList(WoocommerceRefundOrder.class);

                for (WoocommerceRefundOrder refundOrder : refundOrders) {
                    StoreOrderRefund storeOrderRefund = saveWoocommerceRefundOrder(refundOrder, storeVo, storeOrder.getPlatOrderId());
                    if(null != storeOrderRefund){
                        storeOrderRefunds.add(storeOrderRefund);
                    }
                }
                break;
        }
        return storeOrderRefunds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreOrderRefund saveShopifyStoreOrderRefundDetail(ShopifyOrderRefund shopifyOrderRefund, StoreVo storeVo) {

        StoreOrder storeOrder = storeOrderDao.selectByStorePlatId(storeVo.getId(), shopifyOrderRefund.getOrder_id());
        if (null == storeOrder) {
            return null;
        }
        BigDecimal cnyRate = (BigDecimal) redisTemplate.opsForHash().get(RedisKey.HASH_CURRENCY_RATE, "cnyRate");
        StoreOrderRefund storeOrderRefund = new StoreOrderRefund();
        Date date = new Date();
        BigDecimal usdRate = storeVo.getUsdRate();
        Long storeOrderRefundId = IdGenerate.nextId();
        ShopifyTransaction transaction = shopifyOrderRefund.getTransactions().get(0);
        storeOrderRefund.setId(storeOrderRefundId);
        storeOrderRefund.setPlatOrderId(shopifyOrderRefund.getOrder_id());
        storeOrderRefund.setRefundAmount(transaction.getAmount());
        storeOrderRefund.setUsdRate(usdRate);
        storeOrderRefund.setCnyRate(cnyRate);
        storeOrderRefund.setCreateTime(shopifyOrderRefund.getCreated_at());
        storeOrderRefund.setPlatOrderRefundId(shopifyOrderRefund.getId());
        storeOrderRefund.setUsdAmount(transaction.getAmount().multiply(usdRate));
        storeOrderRefund.setStoreOrderId(storeOrder.getId());
        storeOrderRefund.setCreateTime(date);
        List<ShopifyOrderRefundItem> lineItems = shopifyOrderRefund.getRefund_line_items();
        if (ListUtils.isEmpty(lineItems)) {
            storeOrderRefund.setOtherFee(transaction.getAmount());
        } else {
            List<StoreOrderRefundItem> refundItems = new ArrayList<>();
            BigDecimal productAmount = BigDecimal.ZERO;
            for (ShopifyOrderRefundItem item : lineItems) {
                StoreOrderRefundItem refundItem = new StoreOrderRefundItem();
                productAmount = productAmount.add(item.getSubtotal());
                refundItem.setRefundAmount(item.getSubtotal());
                refundItem.setRefundQuantity(item.getQuantity());
                refundItem.setPlatOrderItemId(item.getLine_item_id());
                refundItem.setStoreOrderRefundId(storeOrderRefundId);
                refundItems.add(refundItem);
            }
            storeOrderRefund.setOtherFee(transaction.getAmount().subtract(productAmount));
            storeOrderRefundItemDao.insertByBatch(refundItems);
            storeOrderRefundItemDao.updateStoreOrderItemId(storeOrderRefundId, storeOrder.getId());
        }
        storeOrderRefundDao.insert(storeOrderRefund);
        return storeOrderRefund;

    }

    @Transactional
    @Override
    public StoreOrderRefund saveWoocommerceRefundOrder(WoocommerceRefundOrder refundOrder, StoreVo storeVo, String platOrderId) {
        StoreOrder storeOrder = storeOrderDao.selectByStorePlatId(storeVo.getId(), platOrderId);
        if (null == storeOrder) {
            return null;
        }
        StoreOrderRefund storeOrderRefund = new StoreOrderRefund();
        BigDecimal usdRate = storeVo.getUsdRate();
        Long storeOrderRefundId = IdGenerate.nextId();
        BigDecimal refundAmount = refundOrder.getAmount();
        storeOrderRefund.setRefundAmount(refundAmount);
        storeOrderRefund.setStoreOrderId(storeOrder.getId());
        storeOrderRefund.setPlatOrderRefundId(refundOrder.getId());
        storeOrderRefund.setUsdAmount(usdRate.multiply(refundAmount));
        storeOrderRefund.setCreateTime(refundOrder.getDate_created());
        storeOrderRefund.setId(storeOrderRefundId);
        storeOrderRefund.setPlatOrderId(platOrderId);
        storeOrderRefund.setUsdRate(usdRate);

        List<WoocommerceOrderItem> orderItems = refundOrder.getLine_items();
        if (ListUtils.isEmpty(orderItems)) {
            storeOrderRefund.setOtherFee(refundAmount);
        } else {
            List<StoreOrderRefundItem> items = new ArrayList<>();
            BigDecimal productAmount = BigDecimal.ZERO;
            for (WoocommerceOrderItem refundItem : orderItems) {
                productAmount = productAmount.add(refundItem.getSubtotal());
                StoreOrderRefundItem item = new StoreOrderRefundItem();
                item.setPlatOrderItemId(refundItem.getId());
                item.setStoreOrderRefundId(storeOrderRefundId);
                item.setRefundQuantity(refundItem.getQuantity());
                item.setRefundQuantity(refundItem.getQuantity());
                items.add(item);
            }
            storeOrderRefund.setOtherFee(refundAmount.subtract(productAmount));
            storeOrderRefundItemDao.insertByBatch(items);
            storeOrderRefundItemDao.updateStoreOrderItemId(storeOrderRefundId, storeOrder.getId());
        }
        storeOrderRefundDao.insert(storeOrderRefund);
        return storeOrderRefund;
    }

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreOrderRefund record = new StoreOrderRefund();
        record.setId(id);
        return storeOrderRefundDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreOrderRefund record) {
        return storeOrderRefundDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreOrderRefund record) {
        return storeOrderRefundDao.insert(record);
    }

    /**
     *
     */
    public StoreOrderRefund selectByPrimaryKey(Long id) {
        StoreOrderRefund record = new StoreOrderRefund();
        record.setId(id);
        return storeOrderRefundDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(StoreOrderRefund record) {
        return storeOrderRefundDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(StoreOrderRefund record) {
        return storeOrderRefundDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<StoreOrderRefund> select(Page<StoreOrderRefund> record) {
        record.initFromNum();
        return storeOrderRefundDao.select(record);
    }

    /**
     *
     */
    public long count(Page<StoreOrderRefund> record) {
        return storeOrderRefundDao.count(record);
    }

}