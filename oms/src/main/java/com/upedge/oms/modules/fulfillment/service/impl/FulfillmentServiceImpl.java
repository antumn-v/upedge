package com.upedge.oms.modules.fulfillment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.oms.modules.fulfillment.dao.OrderTrackingWoocUserDao;
import com.upedge.oms.modules.fulfillment.entity.OrderTrackingWoocUser;
import com.upedge.oms.modules.fulfillment.service.FulfillmentService;
import com.upedge.oms.modules.fulfillment.service.GetResponse;
import com.upedge.oms.modules.fulfillment.service.PostRequest;
import com.upedge.oms.modules.fulfillment.service.PutRequest;
import com.upedge.oms.modules.fulfillment.vo.*;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.dao.OrderTrackingDao;
import com.upedge.oms.modules.order.dao.StoreOrderDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.order.vo.OrderItemVo;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.thirdparty.shopify.config.Shopify;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyFulfillment;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyLineItem;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrder;
import com.upedge.thirdparty.shoplazza.moudles.order.api.ShoplazzaOrderApi;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaFulfilliment;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder.*;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FulfillmentServiceImpl implements FulfillmentService {

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
    OrderTrackingWoocUserDao orderTrackingWoocUserDao;
    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    private OrderTrackingService orderTrackingService;
    /**
     * 回传物流单号到店铺
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public boolean postTrackNumberToStore(Long id) {
        //回传物流单号到店铺0 00 06 ? * *
        //合并 拆分 正常 补发订单
        //wooc店铺 shopify店铺
        Order order=orderDao.selectByPrimaryKey(id);
        List<OrderItemVo> itemList=orderItemDao.listOrderItem(id);
        OrderTracking orderTracking=orderTrackingService.queryOrderTrackingByOrderId(id, OrderType.NORMAL);
        //订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
        if(order.getOrderType()==0){
            //正常订单 都在同一个店铺同一个订单
            normalOrderTracking(itemList,orderTracking);
        }
        if(order.getOrderType()==1){
            //补发订单 可能跨店铺跨订单
            againOrderTracking(itemList,orderTracking);
        }
        if(order.getOrderType()==2){
            //拆分订单
            otherOrderTracking(itemList,orderTracking,true);
        }
        if(order.getOrderType()==3){
            //合并订单 可能跨店铺跨订单
            otherOrderTracking(itemList,orderTracking,false);
        }
        //更新orderTracking state物流回传状态  -1失败 0:待回传 1待更新 2回传成功 3更新成功
        orderTrackingDao.updateOrderTracking(orderTracking);
        return false;
    }

    /**
     * 正常订单回传物流单号
     * @param itemList
     * @param orderTracking
     * @return
     */
    public void normalOrderTracking(List<OrderItemVo> itemList, OrderTracking orderTracking){
        OrderItemVo orderItemVo=itemList.get(0);
        Long storeId=orderItemVo.getStoreId();
        String platOrderId=orderItemVo.getPlatOrderId();

        //获取店铺信息
        StoreVo storeVo=getStoreVo(storeId);

        //回传物流单号
        //0:shopify,1:woocommerce
        //Woocommerce回传物流
        if (storeVo.getStoreType() == 1) {
            woocommerceTrackingNumber(orderItemVo,storeVo,orderTracking);
        }
        //shopify回传物流
        if (storeVo.getStoreType() == 0) {
            //先post一次
            postFulfillment(itemList,orderTracking, storeVo,platOrderId);

            String str = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/"+Shopify.version+"/orders/" + platOrderId + ".json", storeVo.getApiToken());
            if (!StringUtils.isBlank(str)) {
                EntityOrder entityOrder = JSON.parseObject(str, EntityOrder.class);
                ShopifyOrder orders = entityOrder.getOrder();
                List<ShopifyFulfillment> fulfillments = orders.getFulfillments();
                if (fulfillments != null && fulfillments.size() > 0) {
                    for (ShopifyFulfillment fulfillment : fulfillments) {
                        if (fulfillment.getTracking_number() == null) {
                            //更新Fulfillment
                            updateFulfillment(storeVo,fulfillment,orderTracking,platOrderId);
                        } else {
                            if (fulfillment != null && fulfillment.getStatus() != null && fulfillment.getStatus().equals("pending")) {
                                String resul = PostRequest.sendPost("https://" + storeVo.getStoreUrl()+ ".myshopify.com/admin/api/"+Shopify.version+"/orders/"+platOrderId+"/fulfillments/" + fulfillment.getId() + "/complete.json",storeVo.getApiToken(), null);
                                if (!resul.equals("error")) {
                                    //设置回传状态修改成功
                                    orderTracking.setState(2);
                                }
                            } else {
                                //设置回传状态修改成功
                                orderTracking.setState(1);
                            }
                        }
                    }
                } else {
                    postFulfillment(itemList,orderTracking,storeVo,platOrderId);
                }
            }
        }

        if(storeVo.getStoreType() == 2){
            shoplazzaUploadTrackingNumber(itemList,orderTracking,storeVo,platOrderId);
        }
    }


    /**
     * 补发订单更新运输单号
     */
    public void againOrderTracking(List<OrderItemVo> itemList, OrderTracking orderTracking){
        try {
            Map<String, Long> storeVoMap=new HashMap<>();
            Map<String,OrderItemVo> storeOrderMap=new HashMap<>();
            itemList.forEach(orderItemVo -> {
                //平台订单->店铺id
                storeVoMap.put(orderItemVo.getPlatOrderId(),orderItemVo.getStoreId());
                //平台订单->(店铺订单id,店铺订单Number)
                storeOrderMap.put(orderItemVo.getPlatOrderId(),orderItemVo);
            });
            for(Map.Entry<String, Long> entry:storeVoMap.entrySet()) {
                Long storeId=entry.getValue();
                String platOrderId=entry.getKey();
                //获取店铺信息
                StoreVo storeVo=getStoreVo(storeId);
                if(storeVo==null){
                    continue;
                }
                //Woocommerce回传物流
                if (storeVo.getStoreType() == 1) {
                    OrderItemVo itemVo=storeOrderMap.get(platOrderId);
                    woocommerceTrackingNumber(itemVo,storeVo,orderTracking);
                }
                //shopify回传物流
                if (storeVo.getStoreType() == 0) {
                    String str = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + ".json", storeVo.getApiToken());
                    if (!StringUtils.isBlank(str)) {
                        EntityOrder entityOrder = JSON.parseObject(str, EntityOrder.class);
                        ShopifyOrder orders = entityOrder.getOrder();
                        List<ShopifyFulfillment> fulfillments = orders.getFulfillments();
                        if (fulfillments != null && fulfillments.size() > 0) {
                            for (ShopifyFulfillment fulfillment : fulfillments) {
                                updateFulfillment(storeVo, fulfillment, orderTracking,platOrderId);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //合并订单回传物流  合并、拆分Split
    //拆分订单回传物流
    public void otherOrderTracking(List<OrderItemVo> itemList, OrderTracking orderTracking, boolean splitFlag){
        //查询订单变体
        //查询变体的店铺 根据店铺上传该变体的物流 变体数量
        //捆绑变体和复制变体 original_item_id
        Map<String,StoreVo> storeVoMap=new HashMap<>();
        Set<String> woocSet=new HashSet();
        for(OrderItemVo itemVo:itemList){
            Long storeId=itemVo.getStoreId();
            //获取店铺信息
            StoreVo storeVo=getStoreVo(storeId);
            if(storeVo==null){
                continue;
            }
            //获取该变体店铺类型 shopify/wooc
            //回传物流单号
            //Woocommerce回传物流
            if (storeVo.getStoreType() == 1&&!woocSet.contains(itemVo.getPlatOrderId())) {
                //物流单号一个Woocommerce店铺appOrder只回传一次
                woocommerceTrackingNumber(itemVo,storeVo,orderTracking);
                woocSet.add(itemVo.getPlatOrderId());
            }

            //根据顶单项对应的店铺，去获取设置的链接
            //shopify回传物流
            if (storeVo.getStoreType() == 0) {
                //shopify订单
                storeVoMap.put(itemVo.getPlatOrderId(),storeVo);
                //获取locations idLocation
                List<Long> locationIds = getLocations(storeVo);
                //获取邮件设置
                boolean emailPrompt = storeVo.isEmailPrompt();

                ShopifyFulfillment fulfillment = new ShopifyFulfillment();
                if (!StringUtils.isBlank(storeVo.getTrackingUrl(orderTracking.getTrackingCode()))) {
                    List<String> l = new ArrayList<>();
                    l.add(storeVo.getTrackingUrl(orderTracking.getTrackingCode()));
                    fulfillment.setTracking_urls(l);
                }
                fulfillment.setTracking_number(orderTracking.getTrackingCode());
                fulfillment.setTracking_company(orderTracking.getShippingMethodName());
                fulfillment.setNotify_customer(emailPrompt);
                //获取有效的变体
                List<ShopifyLineItem> lineItemList =new ArrayList<>();
                ShopifyLineItem lineItemQ=new ShopifyLineItem();
                lineItemQ.setId(itemVo.getPlatOrderItemId());
                lineItemList.add(lineItemQ);
                fulfillment.setLine_items(lineItemList);
                EntityObject entityObject = new EntityObject();
                entityObject.setFulfillment(fulfillment);
                String result = "error";
                for (Long locationId : locationIds) {
                    fulfillment.setLocation_id(locationId);
                    result = PostRequest.sendPost("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + itemVo.getPlatOrderId() + "/fulfillments.json", storeVo.getApiToken(), JSONObject.toJSON(entityObject));
                    if (!result.equals("error")) {
                        System.out.println("====================fulfillment=====================");
                        break;
                    }
                }
            }


            if(storeVo.getStoreType() == 3){
                List<OrderItemVo> orderItemVos = new ArrayList<>();
                orderItemVos.add(itemVo);

                shoplazzaUploadTrackingNumber(orderItemVos,orderTracking,storeVo,itemVo.getPlatOrderId());
            }
        }

        /**
         * 查看shopify订单回传是否成功
         */
        for(Map.Entry<String,StoreVo> entry:storeVoMap.entrySet()) {
            StoreVo storeVo=entry.getValue();
            String platOrderId=entry.getKey();
            if(splitFlag){
                //拆分订单，如果之前已经有Fulfillment，新的单号更新追加
                updateSplitTrack(itemList,platOrderId,storeVo,orderTracking);
            }

            String strRes = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + ".json", storeVo.getApiToken());
            if (!StringUtils.isBlank(strRes)) {
                EntityOrder entityOrder = JSON.parseObject(strRes, EntityOrder.class);
                if (entityOrder != null) {
                    ShopifyOrder orders = entityOrder.getOrder();
                    String fulfillmentStatus = orders.getFulfillment_status();
                    //fulfilled
                    String fulfillmentsRes = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + "/fulfillments.json", storeVo.getApiToken());
                    EntityObject entityObject = JSON.parseObject(fulfillmentsRes, EntityObject.class);
                    List<ShopifyFulfillment> fulfillments = entityObject.getFulfillments();
                    if (fulfillments != null && fulfillments.size() > 0) {
                        for (ShopifyFulfillment fulfillment : fulfillments) {
                            String trackNumber = fulfillment.getTracking_number();
                            log.info(trackNumber + " " + orderTracking.getTrackingCode());
                            if (!StringUtils.isBlank(trackNumber) && trackNumber.contains(orderTracking.getTrackingCode())) {
                                System.out.println("成功:" + orders.getId());
                                orderTracking.setState(1);
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 拆分订单，如果之前已经有Fulfillment，新的单号更新追加
     * @param platOrderId
     * @param storeVo
     */
    public void updateSplitTrack(List<OrderItemVo> itemList, String platOrderId, StoreVo storeVo, OrderTracking orderTracking){
        String str = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + ".json", storeVo.getApiToken());
        if (!StringUtils.isBlank(str)) {
            EntityOrder entityOrder = JSON.parseObject(str, EntityOrder.class);
            ShopifyOrder orders = entityOrder.getOrder();
            List<ShopifyFulfillment> fulfillments = orders.getFulfillments();
            if (fulfillments != null && fulfillments.size() > 0) {
                for (ShopifyFulfillment fulfillment : fulfillments) {
                    System.out.println(fulfillment.toString());
                    System.out.println(fulfillment.getTracking_number());
                    Set<String> lineItemVariantIdSet = new HashSet<>();
                    System.out.println("getLine_items:" + fulfillment.getLine_items());
                    for (ShopifyLineItem lineItem : fulfillment.getLine_items()) {
                        log.debug("lineItem:" + lineItem.toString());
                        if (lineItem.getVariant_id() != null) {
                            lineItemVariantIdSet.add(String.valueOf(lineItem.getVariant_id()));
                        }
                    }
                    log.debug("lineItemVariantIdSet:" + lineItemVariantIdSet);
                    if (lineItemVariantIdSet.size() == 0) {
                        continue;
                    }
                    Set<String> stringSet = new HashSet<>(fulfillment.getTracking_numbers());
                    //追踪号不为空，且不包含拆分订单的追踪号
                    if (!StringUtils.isBlank(fulfillment.getTracking_number()) &&
                            !fulfillment.getTracking_number().contains(orderTracking.getTrackingCode())
                            && !stringSet.contains(orderTracking.getTrackingCode())) {
                        String fulfillmentId = String.valueOf(fulfillment.getId());
                        String shipMethodName = orderTracking.getShippingMethodName();
                        boolean updateFlag = false;
                        for (OrderItemVo orderItemVo : itemList) {
                            if (lineItemVariantIdSet.contains(orderItemVo.getPlatOrderItemId())) {
                                updateFlag = true;
                            }
                        }
                        System.out.println("updateFlag:" + updateFlag);
                        if (updateFlag && !StringUtils.isBlank(shipMethodName)) {
                            fulfillment.setNotify_customer(storeVo.isEmailPrompt());
                            //fulfillment.setTracking_number(trackNumber);
                            List<String> tracking_numbers = fulfillment.getTracking_numbers();
                            List<String> tracking_urls = fulfillment.getTracking_urls();
                            Set<String> setNumbers = new HashSet<>(tracking_numbers);
                            tracking_numbers.add(orderTracking.getTrackingCode());
                            List<String> trackingList = new ArrayList<>();
                            for (String s : setNumbers) {
                                trackingList.add(s);
                            }
                            Set<String> setUrls = new HashSet<>(tracking_urls);
                            tracking_urls.add(storeVo.getTrackingUrl(orderTracking.getTrackingCode()));
                            List<String> urlList = new ArrayList<>();
                            for(String u:setUrls) {
                                urlList.add(u);
                            }
                            fulfillment.setTracking_numbers(trackingList);
                            fulfillment.setTracking_urls(urlList);
                            String json4 = JSONObject.toJSONString(fulfillment);
                            System.out.println(json4);
                            EntityObject entityObject = new EntityObject();
                            entityObject.setFulfillment(fulfillment);
                            String s4 = PutRequest.httpUrlConnectionPut("https://" + storeVo.getStoreUrl()+ ".myshopify.com/admin/api/" + Shopify.version +  "orders/" + platOrderId + "/fulfillments/" + fulfillmentId + ".json", storeVo.getApiToken(), JSONObject.toJSON(entityObject));
                            System.out.println(s4);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Locations
     * @param storeVo
     * @return
     */
    public List<Long> getLocations(StoreVo storeVo){
        List<Long> locationIds = new ArrayList<>();
        String ss = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/"+Shopify.version+ "/locations.json",storeVo.getApiToken());
        if(!ss.equals("error")) {
            EntityLocations entityLocations = JSON.parseObject(ss, EntityLocations.class);
            List<Locations> locations = entityLocations.getLocations();
            for (Locations l : locations) {
                locationIds.add(l.getId());
            }
        }
        return locationIds;
    }

    /**
     * 获取店铺信息
     * @param storeId
     * @return
     */
    public StoreVo getStoreVo(Long storeId){
        //先从缓存中取
        StoreVo storeVo=omsRedisService.getStoreVo(storeId);
        if(storeVo==null) {
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

    /**
     * woocommerce回传物流 单个回传
     * @param storeVo
     * @param orderTracking
     */
    public void woocommerceTrackingNumber(OrderItemVo orderItemVo,StoreVo storeVo,OrderTracking orderTracking){
        try {
            String methodName = orderTracking.getShippingMethodName();
            String trackingCode = orderTracking.getTrackingCode();

            String platOrderName=orderItemVo.getPlatOrderName();
            Long storeOrderId=orderItemVo.getStoreOrderId();
            String orderName = platOrderName.substring(1);

            //查询
            String s1 = GetResponse.sendGet("https://"+storeVo.getStoreUrl()+"/index.php/wp-json/wc/v3/orders/" + orderName + "/notes",storeVo.getApiToken());
            if(!StringUtils.isBlank(s1)){
                List<WooCommerceNote> wooCommerceNotes = JSON.parseArray(s1,WooCommerceNote.class);

                if(wooCommerceNotes!=null&&wooCommerceNotes.size()>0) {
                    boolean flag=false;
                    for (WooCommerceNote wooCommerceNote : wooCommerceNotes) {
                        if (!StringUtils.isBlank(wooCommerceNote.getNote()) && wooCommerceNote.getNote().contains(trackingCode)) {
                            flag=true;
                        }
                    }
                    //未没有回传
                    if(!flag){
                        WoocommerceTrackingNote woocommerceTrackingNote = new WoocommerceTrackingNote();
                        woocommerceTrackingNote.setAuthor("SourcinBox");
                        woocommerceTrackingNote.setNote("Shipping Method: " + methodName + " ; Tracking Number: " + trackingCode);
                        String result = PostRequest.sendPost("https://" + storeVo.getStoreUrl() + "/index.php/wp-json/wc/v3/orders/" + orderName + "/notes",
                                storeVo.getApiToken(), JSONObject.toJSON(woocommerceTrackingNote));
                        WoocommerceTrackingRes res = JSON.parseObject(result, WoocommerceTrackingRes.class);

                        WoocommerceOrder woocommerceOrder = new WoocommerceOrder();
                        woocommerceOrder.setStatus("completed");
                        String s = PostRequest.sendPost("https://" + storeVo.getStoreUrl() + "/index.php/wp-json/wc/v3/orders/" + orderName,
                                storeVo.getApiToken(), JSONObject.toJSON(woocommerceOrder));

                        woocommerceOrder = JSON.parseObject(s, WoocommerceOrder.class);
                        if (!StringUtils.isBlank(res.getNote()) && woocommerceOrder != null
                                && woocommerceOrder.getStatus().equals("completed")) {
                            orderTracking.setState(1);
                            //完结店铺订单 0=未发货 1=部分发货 2=全部发货
                            storeOrderDao.updateFulfillmentStatus(storeOrderId, 2);
                        }
                    }

                }
            }
            //追踪插件
            advancedShipmentTracking(storeVo,orderTracking,storeOrderId,orderName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * wooc插件回传物流信息
     */
    @Transactional(readOnly = false)
    public void advancedShipmentTracking(StoreVo storeVo, OrderTracking orderTracking, Long storeOrderId, String orderName){
        try {
            OrderTrackingWoocUser existUser=orderTrackingWoocUserDao.selectByPrimaryKey(storeVo.getCustomerId());
            //没有安装插件
            if(existUser==null){
                return;
            }
            //回传物流单号
            String methodName = orderTracking.getShippingMethodName();
            Set<Object> methodSet=omsRedisService.getWoocShipMethod(storeVo.getCustomerId());
            if(methodSet!=null&&methodSet.contains(methodName)){
                //该运输方式插件未定义
                return;
            }
            String trackingCode = orderTracking.getTrackingCode();
            WoocommerceASTracking woocommerceASTracking = new WoocommerceASTracking();
            woocommerceASTracking.setTracking_provider(methodName);
            woocommerceASTracking.setTracking_number(trackingCode);
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            woocommerceASTracking.setDate_shipped(format.format(new Date()));
            woocommerceASTracking.setStatus_shipped(1);

            String res=GetResponse.sendGet("https://" + storeVo.getStoreUrl() + "/index.php/wp-json/wc/v1/orders/"+orderName+"/shipment-trackings",
                    storeVo.getApiToken());

            boolean flag=true;
            if(!StringUtils.isBlank(res)){
                List<WoocommerceASTracking> trackings=JSON.parseArray(res,WoocommerceASTracking.class);
                for(WoocommerceASTracking tracking:trackings){
                    //已经回传过了
                    if(!StringUtils.isBlank(tracking.getTracking_number())
                            &&tracking.getTracking_number().equals(trackingCode)){
                        flag=false;
                        //0=未发货 1=部分发货 2=全部发货
                        storeOrderDao.updateFulfillmentStatus(storeOrderId,2);
                        break;
                    }
                }
                if(flag) {
                    String result = PostRequest.sendPost("https://" + storeVo.getStoreUrl() + "/index.php/wp-json/wc/v1/orders/" + orderName + "/shipment-trackings",
                            storeVo.getApiToken(), JSONObject.toJSON(woocommerceASTracking));
                    log.info("woocommerce-advanced-shipment-tracking:{}", result);
                    if (!StringUtils.isBlank(result) && !result.equals("error")) {
                        WoocommerceASTracking asTracking = JSON.parseObject(result, WoocommerceASTracking.class);
                        if(asTracking!=null){
                            //回传插件未定义，之后暂时不回传
                            if(StringUtils.isBlank(asTracking.getTracking_provider())) {
                                //加入缓存
                                omsRedisService.setWoocShipMethod(storeVo.getCustomerId(),methodName);
                            }
                            //0=未发货 1=部分发货 2=全部发货
                            storeOrderDao.updateFulfillmentStatus(storeOrderId,2);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * shopify回传物流信息
     * @param itemList
     * @param orderTracking
     * @param storeVo
     * @param platOrderId
     */
    public void postFulfillment(List<OrderItemVo> itemList, OrderTracking orderTracking, StoreVo storeVo, String platOrderId){

        /**
         * 获取店铺Locations
         */
        List<Long> locationIds =getLocations(storeVo);

        ShopifyFulfillment fulfillment = new ShopifyFulfillment();
        List<String> l = new ArrayList<>();
        l.add(storeVo.getTrackingUrl());
        fulfillment.setTracking_urls(l);
        fulfillment.setTracking_number(orderTracking.getTrackingCode());
        fulfillment.setTracking_company(orderTracking.getShippingMethodName());
        //获取邮件设置
        fulfillment.setNotify_customer(storeVo.isEmailPrompt());

        //获取店铺订单items
        List<ShopifyLineItem> lineItemList = itemList.stream().map(i->{
            ShopifyLineItem lineItemsBean=new ShopifyLineItem();
            lineItemsBean.setId(i.getPlatOrderItemId());
            return lineItemsBean;
        }).collect(Collectors.toList());

        fulfillment.setLine_items(lineItemList);
        EntityObject entityObject = new EntityObject();
        entityObject.setFulfillment(fulfillment);
        String result = "error";

        for (Long locationId : locationIds) {
            fulfillment.setLocation_id(locationId);
            result = PostRequest.sendPost("https://" + storeVo.getStoreUrl() +".myshopify.com/admin/api/"+Shopify.version+"/orders/" + platOrderId + "/fulfillments.json",storeVo.getApiToken(), JSONObject.toJSON(entityObject));
            if (!result.equals("error")) {
                System.out.println("====================fulfillment=====================");
                EntityFulfillment fulfillmentRes = JSON.parseObject(result, EntityFulfillment.class);
                System.out.println(fulfillmentRes);

                ShopifyFulfillment f = fulfillmentRes.getFulfillment();
                if (f != null && f.getStatus() != null && f.getStatus().equals("pending")) {
                    String resul = PostRequest.sendPost("https://" + storeVo.getStoreUrl() +".myshopify.com/admin/api/"+Shopify.version+"/orders/" + platOrderId + "/fulfillments/" + f.getId() + "/complete.json", storeVo.getApiToken(), null);
                    if (!resul.equals("error")) {
                        //设置回传状态成功
                        orderTracking.setState(1);
                    }
                } else {
                    //设置回传状态成功
                    orderTracking.setState(1);
                }
                break;
            }
        }
    }

    /**
     * 更新Fulfillment
     * @param storeVo
     * @param fulfillment
     * @param orderTracking
     */
    public void updateFulfillment(StoreVo storeVo, ShopifyFulfillment fulfillment, OrderTracking orderTracking, String platOrderId){
        if (!StringUtils.isBlank(storeVo.getTrackingUrl())) {
            List<String> l = new ArrayList<>();
            l.add(storeVo.getTrackingUrl());
            fulfillment.setTracking_urls(l);
        }
        fulfillment.setTracking_company(orderTracking.getShippingMethodName());
        fulfillment.setTracking_number(orderTracking.getTrackingCode());
        //获取邮件设置
        fulfillment.setNotify_customer(storeVo.isEmailPrompt());
        EntityObject entityObject = new EntityObject();
        entityObject.setFulfillment(fulfillment);
        String s = PutRequest.httpUrlConnectionPut("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" +platOrderId+ "/fulfillments/" + fulfillment.getId() + ".json", storeVo.getApiToken(), JSONObject.toJSON(entityObject));
        if (!s.equals("error")) {
            EntityObject entity = JSON.parseObject(s, EntityObject.class);
            ShopifyFulfillment f = entity.getFulfillment();
            if (f != null && f.getStatus() != null && f.getStatus().equals("pending")) {
                String resul = PostRequest.sendPost("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + "/fulfillments/" + f.getId() + "/complete.json", storeVo.getApiToken(), null);
                if (!resul.equals("error")) {
                    //设置回传状态修改成功
                    orderTracking.setState(2);
                }
            } else {
                //设置回传状态修改成功
                orderTracking.setState(2);
            }

        }
    }

    /**
     * 更新追踪号（shopify替换 原始追踪号->新的追踪号）（woocommerce追加新的追踪号）
     */
    @Override
    public boolean updateOrderTracking(OrderTracking orderTracking){
        try {
            if(StringUtils.isBlank(orderTracking.getShippingMethodName())
               ||StringUtils.isBlank(orderTracking.getTrackingCode())){
                return false;
            }
            if(orderTracking.getOrderId()==null){
                return false;
            }
            Long id =orderTracking.getOrderId();
            Order order = orderDao.selectByPrimaryKey(id);
            OrderTracking old = orderTrackingService.queryOrderTrackingByOrderId(id,OrderType.NORMAL);
            String originalCode = old.getTrackingCode();
            String newCode = orderTracking.getTrackingCode();
            List<OrderItemVo> itemList=orderItemDao.listOrderItem(id);

            Map<String, Long> storeVoMap=new HashMap<>();
            Map<String,OrderItemVo> storeOrderMap=new HashMap<>();
            itemList.forEach(orderItemVo -> {
                //平台订单->店铺id
                storeVoMap.put(orderItemVo.getPlatOrderId(),orderItemVo.getStoreId());
                //平台订单->(店铺订单id,店铺订单Number)
                storeOrderMap.put(orderItemVo.getPlatOrderId(),orderItemVo);
            });
            for(Map.Entry<String, Long> entry:storeVoMap.entrySet()) {
                Long storeId=entry.getValue();
                String platOrderId=entry.getKey();
                //获取店铺信息
                StoreVo storeVo=getStoreVo(storeId);
                if(storeVo==null){
                    continue;
                }
                //Woocommerce回传物流
                if (storeVo.getStoreType() == 1) {
                    OrderItemVo itemVo=storeOrderMap.get(platOrderId);
                    woocommerceTrackingNumber(itemVo,storeVo,orderTracking);
                    orderTracking.setId(old.getId());
                    orderTracking.setOrderId(id);
                    orderTracking.setUpdateTime(new Date());
                    orderTracking.setTrackingCode(newCode);
                    //更新
                    orderTracking.setState(2);
                    orderTrackingDao.updateOrderTracking(orderTracking);
                    return true;
                }
                //shopify回传物流
                if (storeVo.getStoreType() == 0) {
                    String str = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version +"/orders/" + platOrderId + ".json", storeVo.getApiToken());
                    if (!StringUtils.isBlank(str)) {
                        EntityOrder entityOrder = JSON.parseObject(str, EntityOrder.class);
                        ShopifyOrder orders = entityOrder.getOrder();
                        List<ShopifyFulfillment> fulfillments = orders.getFulfillments();
                        if (fulfillments != null && fulfillments.size() > 0) {
                            for (ShopifyFulfillment fulfillment : fulfillments) {
                                System.out.println(fulfillment);
                                System.out.println(fulfillment.getTracking_number());
                                if (!StringUtils.isBlank(fulfillment.getTracking_number())) {
                                    String fulfillmentId = String.valueOf(fulfillment.getId());
                                    List<String> trackNumberList=new ArrayList<>();
                                    List<String> trackUrlList=new ArrayList<>();
                                    Set<String> trackNumberSet=new HashSet<>(fulfillment.getTracking_numbers());
                                    Set<String> trackUrlSet=new HashSet<>(fulfillment.getTracking_urls());
                                    boolean updateFlag=false;
                                    for(String s:trackNumberSet){
                                        if(s.contains(originalCode)){
                                            trackNumberList.add(newCode);
                                            updateFlag=true;
                                        }else {
                                            trackNumberList.add(s);
                                        }
                                    }
                                    for(String u:trackUrlSet){
                                        if(u.contains(originalCode)){
                                            trackNumberList.add(u.replace(originalCode,newCode));
                                            updateFlag=true;
                                        }else {
                                            trackNumberList.add(u);
                                        }
                                    }
                                    if (originalCode.equals(fulfillment.getTracking_number())) {
                                        updateFlag=true;
                                    }
                                    //更新
                                    if(updateFlag) {
                                        fulfillment.setTracking_url(storeVo.getTrackingUrl(newCode));
                                        fulfillment.setTracking_company(orderTracking.getShippingMethodName());
                                        fulfillment.setTracking_number(orderTracking.getTrackingCode());
                                        fulfillment.setTracking_numbers(trackNumberList);
                                        fulfillment.setTracking_urls(trackUrlList);
                                        //获取邮件设置
                                        fulfillment.setNotify_customer(storeVo.isEmailPrompt());
                                        EntityObject entityObject = new EntityObject();
                                        entityObject.setFulfillment(fulfillment);
                                        String s4 = PutRequest.httpUrlConnectionPut("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + "/fulfillments/" + fulfillmentId + ".json", storeVo.getApiToken(), JSON.toJSON(entityObject));
                                        System.out.println(s4);
                                    }
                                }
                            }
                        }
                    }

                    //是否修改成功
                    String strRes = GetResponse.sendGet("https://" + storeVo.getStoreUrl() + ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + ".json", storeVo.getApiToken());
                    if (!StringUtils.isBlank(strRes)) {
                        EntityOrder entityOrder = JSON.parseObject(str, EntityOrder.class);
                        if (entityOrder != null) {
                            ShopifyOrder orders = entityOrder.getOrder();
                            String fulfillmentStatus = orders.getFulfillment_status();
                            System.out.println(fulfillmentStatus);
                            String fulfillmentsRes = GetResponse.sendGet("https://" + storeVo.getStoreUrl()+ ".myshopify.com/admin/api/" + Shopify.version + "/orders/" + platOrderId + "/fulfillments.json",storeVo.getApiToken());
                            EntityObject entityObject = JSON.parseObject(fulfillmentsRes, EntityObject.class);
                            List<ShopifyFulfillment> fulfillments = entityObject.getFulfillments();
                            if (fulfillments != null && fulfillments.size() > 0) {
                                for (ShopifyFulfillment fulfillment : fulfillments) {
                                    String trackNumber = fulfillment.getTracking_number();
                                    Set<String> trackNumberSet=new HashSet<>(fulfillment.getTracking_numbers());
                                    Set<String> trackUrlSet=new HashSet<>(fulfillment.getTracking_urls());
                                    boolean successFlag=false;
                                    for(String s:trackNumberSet){
                                        if(s.contains(newCode)){
                                            successFlag=true;
                                        }
                                    }
                                    for(String u:trackUrlSet){
                                        if(u.contains(newCode)){
                                            successFlag=true;
                                        }
                                    }
                                    if (!StringUtils.isBlank(trackNumber) && trackNumber.equals(newCode)) {
                                        successFlag=true;
                                    }
                                    if(successFlag) {
                                        System.out.println("成功:" + orders.getId());
                                        orderTracking.setId(old.getId());
                                        orderTracking.setOrderId(id);
                                        orderTracking.setUpdateTime(new Date());
                                        orderTracking.setTrackingCode(newCode);
                                        //更新
                                        orderTracking.setState(2);
                                        orderTrackingDao.updateOrderTracking(orderTracking);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public void shoplazzaUploadTrackingNumber(List<OrderItemVo> itemList, OrderTracking orderTracking, StoreVo storeVo, String platOrderId) {
        try {
            JSONObject jsonObject = new JSONObject();

            List<String> shoplazzaItemIds = new ArrayList<>();
            itemList.forEach(orderItemVo -> {
                shoplazzaItemIds.add(orderItemVo.getPlatOrderItemId());
            });
            jsonObject.put("line_item_ids", shoplazzaItemIds);
            jsonObject.put("tracking_number", orderTracking.getTrackingCode());
            jsonObject.put("tracking_company", orderTracking.getShippingMethodName());
            ShoplazzaFulfilliment fulfilliment = ShoplazzaOrderApi.postOrderFulfillment(storeVo.getStoreUrl(),storeVo.getApiToken(),platOrderId,jsonObject.toJSONString());
            if(fulfilliment != null){
                orderTracking.setState(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void shoplazzaUpdateTrackingNumber(List<OrderItemVo> itemList, OrderTracking orderTracking, StoreVo storeVo, String platOrderId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tracking_number", orderTracking.getTrackingCode());
            jsonObject.put("tracking_company", orderTracking.getShippingMethodName());
            ShoplazzaFulfilliment fulfilliment = ShoplazzaOrderApi.postOrderFulfillment(storeVo.getStoreUrl(),storeVo.getApiToken(),platOrderId,jsonObject.toJSONString());
            if(null != fulfilliment){
                orderTracking.setState(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
