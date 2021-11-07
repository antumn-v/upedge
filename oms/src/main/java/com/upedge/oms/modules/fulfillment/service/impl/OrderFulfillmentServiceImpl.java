package com.upedge.oms.modules.fulfillment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.fulfillment.dao.OrderTrackingWoocUserDao;
import com.upedge.oms.modules.fulfillment.dao.StoreOrderFulfillmentDao;
import com.upedge.oms.modules.fulfillment.entity.StoreOrderFulfillment;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.thirdparty.shopify.moudles.order.controller.ShopifyOrderApi;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyFulfillment;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyLineItem;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrder;
import com.upedge.thirdparty.shopify.moudles.shop.controller.ShopifyShopApi;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyLocation;
import com.upedge.thirdparty.shoplazza.moudles.order.api.ShoplazzaOrderApi;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaFulfilliment;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder.ShoplazzaLineItems;
import com.upedge.thirdparty.woocommerce.moudles.order.api.WoocommerceOrderApi;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderNote;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderShipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 海桐
 */
@Slf4j
@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    OrderTrackingDao orderTrackingDao;
    @Autowired
    UmsFeignClient umsFeignClient;
    @Autowired
    StoreOrderDao storeOrderDao;
    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;
    @Autowired
    StoreOrderItemDao storeOrderItemDao;
    @Autowired
    OrderTrackingWoocUserDao orderTrackingWoocUserDao;
    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    StoreOrderFulfillmentDao storeOrderFulfillmentDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderTrackingService orderTrackingService;

    //新增post
    private int fulfillment_post = 0;
    //修改已上传的post
    private int fulfillment_put = 1;

    /**
     * 订单物流回传
     * https://www.processon.com/diagraming/606599337d9c0829db678e1a
     *
     * @param id 订单ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void orderFulfillment(Long id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order == null){
            return;
        }
        OrderTracking orderTracking = orderTrackingService.queryOrderTrackingByOrderId(id, OrderType.NORMAL);
        if (null == orderTracking) {
            //无物流信息或物流信息已回传店铺
            return;
        }
        ShippingMethodRedis shippingMethodRedis = getShipMethodByTransportId(orderTracking.getTransportId());
        if (null == shippingMethodRedis){
            orderTracking.setState(0);
            orderTrackingDao.updateOrderTracking(orderTracking);
            return;
        }
        if (shippingMethodRedis.getTrackType() == 0){
            orderTracking.setTrackingCode(orderTracking.getTrackNumbers());
        }
        if (shippingMethodRedis.getTrackType() == 1){
            orderTracking.setTrackingCode(orderTracking.getLogisticsOrderNo());
        }
        if (null == orderTracking.getTrackingCode()){
            orderTracking.setState(4);
            orderTrackingDao.updateOrderTracking(orderTracking);
            return;
        }
        Long orderId = order.getId();
        StoreVo storeVo = getStoreVo(order.getStoreId());
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(orderId);
        try {
            storeOrderRelates.forEach(storeOrderRelate -> {
                List<StoreOrderItem> storeOrderItems = storeOrderItemDao.selectByStoreOrderAndOrder(orderId, storeOrderRelate.getStoreOrderId());
                String platOrderId = storeOrderItems.get(0).getPlatOrderId();
                Long storeOrderId = storeOrderRelate.getStoreOrderId();
                switch (storeVo.getStoreType()) {//0=shopify，1=woocommerce，2=shoplazza
                    case 0:
                        shopifyOrderFulfill(storeOrderItems, orderTracking, storeVo, platOrderId, storeOrderId);
                        break;
                    case 1:
                        woocommerceOrderFulfill(orderTracking, storeVo, platOrderId, storeOrderId);
                        break;
                    case 2:
                        shoplazzaUploadTrackingNumber(storeOrderItems, orderTracking, storeVo, platOrderId, storeOrderId);
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            orderTracking.setState(-1);
            orderTrackingDao.updateOrderTracking(orderTracking);
            return;
        }
        orderTracking.setState(1);
        orderTrackingDao.updateOrderTracking(orderTracking);
    }

    //shopify订单回传物流信息
    public boolean shopifyOrderFulfill(List<StoreOrderItem> storeOrderItems, OrderTracking orderTracking, StoreVo storeVo, String platOrderId, Long storeOrderId) {
        List<String> platOrderItemIds = new ArrayList<>();
        storeOrderItems.forEach(storeOrderItem -> {
            platOrderItemIds.add(storeOrderItem.getPlatOrderItemId());
        });
        JSONObject jsonObject = ShopifyOrderApi.getOrderDetailById(platOrderId, storeVo.getStoreUrl(), storeVo.getApiToken());
        if (jsonObject == null) {
            return false;
        }
        ShopifyOrder shopifyOrder = jsonObject.getJSONObject("order").toJavaObject(ShopifyOrder.class);
        //更新已回传物流的订单产品的物流信息
        List<ShopifyFulfillment> shopifyFulfillments = shopifyOrder.getFulfillments();
        if (ListUtils.isNotEmpty(shopifyFulfillments)) {
            shopifyUpdateFulfillment(shopifyFulfillments, platOrderItemIds, orderTracking, storeVo, platOrderId,storeOrderId);
        }

        if (ListUtils.isNotEmpty(platOrderItemIds)) {
            return uploadShopifyOrderFulfillment(platOrderItemIds, orderTracking, storeVo, platOrderId,storeOrderId);
        }
        return true;
    }

    //上传shopify订单物流信息
    boolean uploadShopifyOrderFulfillment(List<String> platOrderItemIds, OrderTracking orderTracking, StoreVo storeVo, String platOrderId, Long storeOrderId) {
        List<ShopifyLineItem> lineItems = new ArrayList<>();
        for (String platOrderItemId : platOrderItemIds) {
            ShopifyLineItem lineItem = new ShopifyLineItem();
            lineItem.setId(platOrderItemId);
            lineItems.add(lineItem);
        }
        //shopify订单回传物流需要添加location Id，location和订单产品的关系还需要测试
        String storeLocationKey = RedisKey.STRING_STORE_SHOPIFY_LOCATIONS + storeVo.getId();
        List<ShopifyLocation> locations = (List<ShopifyLocation>) redisTemplate.opsForValue().get(storeLocationKey);
        if (ListUtils.isEmpty(locations)) {
            locations = ShopifyShopApi.getShopifyLocations(storeVo.getStoreUrl(), storeVo.getApiToken());
            redisTemplate.opsForValue().set(storeLocationKey, locations);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tracking_number", orderTracking.getTrackingCode());
        map.put("tracking_company", orderTracking.getShippingMethodName());
        map.put("notify_customer", storeVo.isEmailPrompt());
        map.put("line_items", lineItems);
        for (ShopifyLocation location : locations) {
            map.put("location_id", location.getId());
            ShopifyFulfillment fulfillment = ShopifyOrderApi.orderFulfillment(platOrderId, storeVo.getStoreUrl(), storeVo.getApiToken(), map);
            saveStoreOrderFulfillment(fulfillment,storeVo,orderTracking,platOrderId,fulfillment_post,storeOrderId);
            if (null != fulfillment && fulfillment.getStatus().equals("success")) {
                return true;
            }
        }
        return false;
    }

    //更新shopify订单物流信息
    void shopifyUpdateFulfillment(List<ShopifyFulfillment> shopifyFulfillments, List<String> platOrderItemIds, OrderTracking orderTracking, StoreVo storeVo, String platOrderId, Long storeOrderId) {
        shopifyFulfillments.forEach(shopifyFulfillment -> {
            List<String> fulfillmentItemIds = new ArrayList<>();
            List<ShopifyLineItem> shopifyLineItems = shopifyFulfillment.getLine_items();
            shopifyLineItems.forEach(shopifyLineItem -> {
                if (platOrderItemIds.contains(shopifyLineItem.getId())) {
                    fulfillmentItemIds.add(shopifyLineItem.getId());
                }
            });
            if (ListUtils.isNotEmpty(fulfillmentItemIds)) {
                platOrderItemIds.removeAll(fulfillmentItemIds);
                Map<String, Object> fulfillment = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("tracking_number", orderTracking.getTrackingCode());
                map.put("tracking_company", orderTracking.getShippingMethodName());
                map.put("notify_customer", storeVo.isEmailPrompt());
                fulfillment.put("fulfillment", map);
                ShopifyFulfillment updateShopFulfillment = ShopifyOrderApi.orderFulfillmentUpdate(fulfillment, storeVo.getApiToken(), storeVo.getStoreUrl(), platOrderId, shopifyFulfillment.getId());
                saveStoreOrderFulfillment(fulfillment,storeVo,orderTracking,platOrderId,fulfillment_post,storeOrderId);

            }
        });
    }

    //woocommerce回传物流（note和插件）
    boolean woocommerceOrderFulfill(OrderTracking orderTracking, StoreVo storeVo, String platOrderId, Long storeOrderId) {
        String trackingCode = orderTracking.getTrackingCode();
        List<WoocommerceOrderNote> woocommerceOrderNotes = WoocommerceOrderApi.getOrderNotes(platOrderId, storeVo.getStoreUrl(), storeVo.getApiToken());
        if (ListUtils.isNotEmpty(woocommerceOrderNotes)) {
            for (WoocommerceOrderNote woocommerceOrderNote : woocommerceOrderNotes) {
                if (woocommerceOrderNote.getNote().contains(trackingCode)) {
                    woocommerceShipmentTracking(orderTracking, storeVo, platOrderId);
                    return true;
                }
            }
        }

        String note = "Shipping Method: " + orderTracking.getShippingMethodName() + " ; Tracking Number: " + orderTracking.getTrackingCode();
        WoocommerceOrderNote woocommerceOrderNote = new WoocommerceOrderNote();
        woocommerceOrderNote.setAuthor("SourcinBox");
        woocommerceOrderNote.setNote(note);
        woocommerceOrderNote.setCustomer_note(storeVo.isEmailPrompt());
        woocommerceOrderNote = WoocommerceOrderApi.postOrderNote(platOrderId, storeVo.getStoreUrl(), storeVo.getApiToken(), woocommerceOrderNote);
        saveStoreOrderFulfillment(woocommerceOrderNote,storeVo,orderTracking,platOrderId,fulfillment_post,storeOrderId);
        if (null == woocommerceOrderNote) {
            return false;
        }
        woocommerceShipmentTracking(orderTracking, storeVo, platOrderId);
        return true;
    }

    //回传woocommerce物流信息到插件，不一定每个店铺都会安装
    void woocommerceShipmentTracking(OrderTracking orderTracking, StoreVo storeVo, String platOrderId) {
        try {
            WoocommerceOrderShipment woocommerceOrderShipment = new WoocommerceOrderShipment();
            woocommerceOrderShipment.setTracking_link(storeVo.getTrackingUrl() + orderTracking.getTrackingCode());
            woocommerceOrderShipment.setTracking_number(orderTracking.getTrackingCode());
            woocommerceOrderShipment.setTracking_provider(orderTracking.getShippingMethodName());
            WoocommerceOrderApi.postOrderShipmentTracking(platOrderId, storeVo.getStoreUrl(), storeVo.getApiToken(), woocommerceOrderShipment);
        } catch (Exception e) {
            log.warn("store:{},platOrderId:{},Exception:{}", storeVo.getId(), platOrderId, e.getMessage());
        }
    }

    //店匠订单回传物流，同shopify先判断用不用修改已上传的物流信息
    boolean shoplazzaUploadTrackingNumber(List<StoreOrderItem> storeOrderItems, OrderTracking orderTracking, StoreVo storeVo, String platOrderId, Long storeOrderId) {
        try {
            List<String> platOrderItemIds = new ArrayList<>();
            storeOrderItems.forEach(storeOrderItem -> {
                platOrderItemIds.add(storeOrderItem.getPlatOrderItemId());
            });
            //修改
            List<ShoplazzaFulfilliment> fulfilliments = ShoplazzaOrderApi.getOrderFulfillment(storeVo.getStoreUrl(), storeVo.getApiToken(), platOrderId);
            if (ListUtils.isNotEmpty(fulfilliments)) {
                for (ShoplazzaFulfilliment fulfilliment : fulfilliments) {
                    List<String> fulfillmentItemIds = new ArrayList<>();
                    List<ShoplazzaLineItems> lineItems = fulfilliment.getLine_items();
                    lineItems.forEach(shoplazzaLineItems -> {
                        if (platOrderItemIds.contains(shoplazzaLineItems.getId())) {
                            fulfillmentItemIds.add(shoplazzaLineItems.getId());
                        }
                    });
                    if (ListUtils.isNotEmpty(fulfillmentItemIds)) {
                        platOrderItemIds.removeAll(fulfillmentItemIds);
                        ShoplazzaFulfilliment updateFulfullment = new ShoplazzaFulfilliment();
                        updateFulfullment.setTracking_company(orderTracking.getShippingMethodName());
                        updateFulfullment.setTracking_number(orderTracking.getTrackingCode());
                        updateFulfullment = ShoplazzaOrderApi.putOrderFulfillment(storeVo.getStoreUrl(), storeVo.getApiToken(), platOrderId, fulfilliment.getId(), updateFulfullment);
                        saveStoreOrderFulfillment(updateFulfullment,storeVo,orderTracking,platOrderId,fulfillment_put,storeOrderId);
                    }
                }
            }
            if (ListUtils.isEmpty(platOrderItemIds)) {
                return true;
            }
            //上传
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("line_item_ids", platOrderItemIds);
            jsonObject.put("tracking_number", orderTracking.getTrackingCode());
            jsonObject.put("tracking_company", orderTracking.getShippingMethodName());
            ShoplazzaFulfilliment fulfilliment = ShoplazzaOrderApi.postOrderFulfillment(storeVo.getStoreUrl(), storeVo.getApiToken(), platOrderId, jsonObject.toJSONString());
            saveStoreOrderFulfillment(fulfilliment,storeVo,orderTracking,platOrderId,fulfillment_post,storeOrderId);
            if (fulfilliment != null) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //保存物流回传记录
    void saveStoreOrderFulfillment(Object object, StoreVo storeVo, OrderTracking orderTracking, String platOrderId, Integer fulfillmentType, Long storeOrderId) {
        StoreOrderFulfillment storeOrderFulfillment = new StoreOrderFulfillment();
        storeOrderFulfillment.setCreateTime(new Date());
        storeOrderFulfillment.setStoreId(storeVo.getId());
        storeOrderFulfillment.setCustomerId(storeVo.getCustomerId());
        storeOrderFulfillment.setOrgId(storeVo.getOrgId());
        storeOrderFulfillment.setOrderId(orderTracking.getOrderId());
        storeOrderFulfillment.setPlatOrderId(platOrderId);
        storeOrderFulfillment.setFulfillmentType(fulfillmentType);
        storeOrderFulfillment.setStoreOrderId(storeOrderId);
        if (null != object) {
            storeOrderFulfillment.setFulfillmentResult(true);
            switch (storeVo.getStoreType()) {
                case 0:
                    ShopifyFulfillment shopifyFulfillment = (ShopifyFulfillment) object;
                    storeOrderFulfillment.setPlatFulfillmentStatus(shopifyFulfillment.getStatus());
                    storeOrderFulfillment.setPlatFulfillmentId(shopifyFulfillment.getId());
                    break;
                case 1:
                    WoocommerceOrderNote woocommerceOrderNote = (WoocommerceOrderNote) object;
                    storeOrderFulfillment.setPlatFulfillmentStatus("success");
                    storeOrderFulfillment.setPlatFulfillmentId(woocommerceOrderNote.getId());
                    break;
                case 2:
                    ShoplazzaFulfilliment shoplazzaFulfilliment = (ShoplazzaFulfilliment) object;
                    storeOrderFulfillment.setPlatFulfillmentId(shoplazzaFulfilliment.getId());
                    storeOrderFulfillment.setPlatFulfillmentStatus(shoplazzaFulfilliment.getStatus());
                    break;
                default:
                    break;
            }
        }else {
            storeOrderFulfillment.setFulfillmentResult(false);
        }
        storeOrderFulfillmentDao.insert(storeOrderFulfillment);
    }

    /**
     * 获取店铺信息
     *
     * @param storeId
     * @return
     */
    public StoreVo getStoreVo(Long storeId) {
        //先从缓存中取
        StoreVo storeVo = omsRedisService.getStoreVo(storeId);
        if (storeVo == null) {
            StoreSearchRequest storeSearchRequest = new StoreSearchRequest();
            storeSearchRequest.setId(storeId);
            BaseResponse baseResponse = umsFeignClient.storeSearch(storeSearchRequest);
            if (ResultCode.FAIL_CODE == baseResponse.getCode()) {
                return null;
            }
            storeVo = JSONObject.parseObject(JSON.toJSONString(baseResponse.getData())).toJavaObject(StoreVo.class);
            omsRedisService.setStoreVo(storeId, storeVo);
        }
        return storeVo;
    }


    public ShippingMethodRedis getShipMethodByTransportId(Integer transportId){
        List<Object> shippingMethodObjects = redisTemplate.opsForHash().values(RedisKey.SHIPPING_METHOD);
        for (Object shippingMethodObject : shippingMethodObjects) {
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) shippingMethodObject;
            if(shippingMethodRedis.getSaiheTransportId().equals(transportId)){
                return shippingMethodRedis;
            }
        }
        return null;
    }
}