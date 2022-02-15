package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.*;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.mq.ChangeManagerVo;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.CustomerOrderStatisticalVo;
import com.upedge.common.model.order.vo.ManagerActualVo;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductVariantTo;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.tms.ArearedisVo;
import com.upedge.common.model.tms.ShippingUnitVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateTools;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.enums.OrderAttrEnum;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.dto.OrderAnalysisDto;
import com.upedge.oms.modules.order.dto.PandaOrderListDto;
import com.upedge.oms.modules.order.entity.*;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderListResponse;
import com.upedge.oms.modules.order.response.OrderUpdateResponse;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.rules.dto.ShipRuleConditionDto;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.service.OrderShipRuleService;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.vat.service.VatRuleService;
import com.upedge.thirdparty.fpx.api.FpxCommonApi;
import com.upedge.thirdparty.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.fpx.dto.ShipPriceCalculator;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    OrderAddressDao orderAddressDao;

    @Autowired
    StoreOrderDao storeOrderDao;

    @Autowired
    StoreOrderItemDao storeOrderItemDao;

    @Autowired
    StoreOrderAddressDao storeOrderAddressDao;

    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;

    @Autowired
    OrderAttrDao orderAttrDao;

    @Autowired
    CustomerProductStockDao customerProductStockDao;

    @Autowired
    OrderShipRuleService orderShipRuleService;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    TmsFeignClient tmsFeignClient;
    @Autowired
    UmsFeignClient umsFeignClient;
    @Autowired
    private OrderReshipInfoDao orderReshipInfoDao;
    @Autowired
    private OrderTrackingDao orderTrackingDao;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    private OrderTrackingService orderTrackingService;

    /**
     * 批发订单和普通订单共用service
     */
    @Autowired
    OrderCommonService orderCommonService;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;


    /**
     * 冗余订单物流单元信息
     */
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;


    /**
     *
     */
    @Transactional
    public int insert(Order record) {
        return orderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Order record) {
        return orderDao.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrderByIds(List<Long> ids) {
        storeOrderItemDao.updateStateAfterRemoveOrder(ids);
        orderDao.deleteByIds(ids);
        storeOrderRelateDao.deleteByOrderId(ids);
    }


    @Override
    public AppOrderVo appOrderDetail(Long id) {
        AppOrderVo appOrderVo = orderDao.selectAppOrderById(id);
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(id);
        appOrderVo.setOrderCustomerName(storeOrderRelates.get(0).getOrderCustomerName());
        List<AppOrderItemVo> itemVos = orderItemDao.selectAppOrderItemByOrderId(id);
        appOrderVo.setStoreOrderVos(new ArrayList<>());
        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            AppStoreOrderVo appStoreOrderVo = new AppStoreOrderVo();
            BeanUtils.copyProperties(storeOrderRelate, appStoreOrderVo);
            appStoreOrderVo.setItemVos(new ArrayList<>());
            itemVos.forEach(appOrderItemVo -> {
                if (appOrderItemVo.getStoreOrderId().equals(appStoreOrderVo.getStoreOrderId())) {
                    appStoreOrderVo.getItemVos().add(appOrderItemVo);
                }
            });
            appOrderVo.getStoreOrderVos().add(appStoreOrderVo);
        }
        if (appOrderVo.getShipState() == 1){
            OrderTracking orderTracking = orderTrackingService.queryOrderTrackingByOrderId(id, OrderType.NORMAL);
            if (orderTracking != null){
                appOrderVo.setTrackingCode(orderTracking.getTrackingCode());
            }
        }
        OrderAddress orderAddress = orderAddressDao.selectByOrderId(id);
        appOrderVo.setOrderAddress(orderAddress);
        if (null != appOrderVo.getShipMethodId()) {
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, appOrderVo.getShipMethodId().toString());
            if (null != shippingMethodRedis) {
                appOrderVo.setShipMethodName(shippingMethodRedis.getName());
            }
        }
        completeOrderStoreUrl(appOrderVo);
        return appOrderVo;
    }

    @Override
    public List<AppOrderItemVo> selectAppOrderItemByOrderId(Long orderId) {
        return orderItemDao.selectAppOrderItemByOrderId(orderId);
    }

    @Override
    public List<AppOrderVo> selectAppOrderList(AppOrderListRequest request) {
        request.initFromNum();
        List<AppOrderVo> appOrderVos = orderDao.selectAppOrderList(request);
        List<AppStoreOrderVo> storeOrderVos = new ArrayList<>();
        if (ListUtils.isEmpty(appOrderVos)) {
            appOrderVos = new ArrayList<>();
        } else {
            storeOrderVos = orderItemDao.selectAppOrderItemByOrderIds(appOrderVos);
        }
        List<Long> shippedOrderIds = new ArrayList<>();
        for (AppOrderVo orderVo : appOrderVos) {
            if (1 == orderVo.getPayState()) {
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, orderVo.getShipMethodId().toString());
                if (null != shippingMethodRedis) {
                    orderVo.setShipMethodName(shippingMethodRedis.getName());
                }
            }
            if (1 == orderVo.getShipState()){
                shippedOrderIds.add(orderVo.getId());
            }
            orderVo.setStoreOrderVos(new ArrayList<>());
            for (AppStoreOrderVo storeOrderVo : storeOrderVos) {
                if (orderVo.getId().equals(storeOrderVo.getOrderId())) {
                    orderVo.getStoreOrderVos().add(storeOrderVo);
                    orderVo.setOrderCustomerName(storeOrderVo.getOrderCustomerName());
                }
            }
            ArearedisVo arearedisVo = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA,String.valueOf(orderVo.getToAreaId()));
            if (null != arearedisVo){
                orderVo.setCountry(arearedisVo.getEnName());
            }
            completeOrderStoreUrl(orderVo);
        }
        completeOrderTrackingCode(appOrderVos);
        return appOrderVos;
    }

    void completeOrderTrackingCode(List<AppOrderVo> appOrderVos){
        Map<Long,AppOrderVo> shippedOrderMap = new HashMap<>();
        List<Long> shippedOrderIds = new ArrayList<>();
        for (AppOrderVo appOrderVo : appOrderVos) {
            if (appOrderVo.getShipState() == 1){
                shippedOrderIds.add(appOrderVo.getId());
                shippedOrderMap.put(appOrderVo.getId(),appOrderVo);
            }
        }
        if (ListUtils.isNotEmpty(shippedOrderIds)){
            List<OrderTracking> orderTrackings = orderTrackingService.listOrderTrackingByOrderIds(shippedOrderIds);
            if (ListUtils.isNotEmpty(orderTrackings)){
                for (OrderTracking orderTracking : orderTrackings) {
                    AppOrderVo appOrderVo = shippedOrderMap.get(orderTracking.getOrderId());
                    appOrderVo.setTrackingCode(orderTracking.getTrackingCode());
                }
            }
        }
    }

    void completeOrderStoreUrl(AppOrderVo orderVo) {
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + orderVo.getStoreId());
        if (null != storeVo) {
            orderVo.setStoreUrl(storeVo.getStoreUrl());
            orderVo.setStoreName(storeVo.getStoreName());
        }

    }

    @Override
    public Long selectAppOrderCount(AppOrderListRequest request) {
        Long total = orderDao.selectAppOrderCount(request);
        if (null == total) {
            total = 0L;
        }
        return total;
    }

    @Override
    public OrderAttr getOrderAttrByName(Long id, String attrName) {
        return orderAttrDao.selectByOrderIdAndName(id, attrName);
    }

    @Override
    public OrderAddress getOrderAddress(Long id) {
        return orderAddressDao.queryOrderAddressByOrderId(id);
    }

    @Override
    public List<UnPaidOrderCountVo> countCustomerUnPaidOrder(Session session) {
        List<Long> orgIds = null;
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            orgIds = session.getOrgIds();
        }
        return orderDao.countCustomerUnPaidOrder(session.getCustomerId(), orgIds);
    }

    @Transactional
    @Override
    public ShipDetail updateShipDetail(Long id, ShipDetail shipDetail) {

        List<ShipDetail> shipDetails = orderShipList(id);
        for (ShipDetail detail : shipDetails) {
            if (detail.getMethodId().equals(shipDetail.getMethodId())) {
                return updateShipDetailById(id, detail);
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateOrderAttr(OrderAttr orderAttr) {
        orderAttrDao.deleteByOrderIdAndName(orderAttr.getOrderId(), orderAttr.getAttrName());
        orderAttr.setCreateTime(new Date());
        return orderAttrDao.insert(orderAttr);
    }


    @Override
    public List<ShipDetail> orderShipList(Long id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order.getToAreaId() == null) {
            OrderAddress orderAddress = orderAddressDao.selectByOrderId(id);
            if (StringUtils.isNotBlank(orderAddress.getCountry())) {
                Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry());
                order.setToAreaId(toAreaId);
                orderDao.updateToAreaIdById(id, toAreaId);
            } else {
                return new ArrayList<>();
            }
        }
        List<ShipDetail> shipDetails = orderShipMethods(order.getId(), order.getToAreaId());
        if (ListUtils.isEmpty(shipDetails)) {
            shipDetails = new ArrayList<>();
        }
        return shipDetails;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderShipRuleDetail matchShipRule(Long id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order.getPayState() != 0 && order.getOrderType() != 0) {
            return null;
        }

        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(id);
        if (storeOrderRelates.size() > 1) {
            return null;
        }
        StoreOrderRelate storeOrderRelate = storeOrderRelates.get(0);
        StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderRelate.getStoreOrderId());
        ShipRuleConditionDto shipRuleConditionDto = new ShipRuleConditionDto();
        shipRuleConditionDto.setAmount(storeOrder.getTotalPrice());
        shipRuleConditionDto.setFreight(storeOrder.getFreight());
        shipRuleConditionDto.setAreaId(order.getToAreaId());
        shipRuleConditionDto.setCustomerId(order.getCustomerId());
        List<OrderShipRule> rules = orderShipRuleService.selectShipRulesByCondition(shipRuleConditionDto);
        if (ListUtils.isEmpty(rules)) {
            return null;
        }

        List<ShipDetail> shipDetails = orderShipMethods(order.getId(), order.getToAreaId());
        if (ListUtils.isNotEmpty(shipDetails)) {
            for (OrderShipRule rule : rules) {
                for (ShipDetail ship : shipDetails) {
                    if (rule.getShippingMethodId().equals(ship.getMethodId())) {
                        order.setShipPrice(ship.getPrice());
                        order.setTotalWeight(ship.getWeight());
                        order.setShipMethodId(ship.getMethodId());

                        ship = updateShipDetailById(id, ship);

                        OrderAttr orderAttr = new OrderAttr();
                        orderAttr.setOrderId(id);
                        orderAttr.setAttrName(OrderAttrEnum.SHIP_RULE_ID.name());
                        orderAttr.setAttrValue(String.valueOf(rule.getId()));
                        orderAttr.setCreateTime(new Date());
                        orderAttrDao.deleteByOrderIdAndName(id, OrderAttrEnum.SHIP_RULE_ID.name());
                        orderAttrDao.insert(orderAttr);

                        OrderShipRuleDetail detail = new OrderShipRuleDetail();
                        detail.setOrderId(id);
                        detail.setShipRuleId(rule.getId());
                        detail.setShipRuleName(rule.getTitle());
                        detail.setShipDetail(ship);
                        return detail;
                    }
                }
            }
        }
        return null;
    }

    List<ShipDetail> orderShipMethods(Long orderId, Long areaId) {
        try {
            Page<OrderItem> page = new Page<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            page.setT(orderItem);
            List<OrderItem> items = orderItemDao.select(page);
            BigDecimal weight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            List<String> strings = new ArrayList<>();
            for (OrderItem item : items) {
                if (null == item.getAdminVariantWeight()
                        || null == item.getAdminVariantVolume()
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantWeight()) == 0
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantVolume()) == 0) {
                    return null;
                }
                weight = weight.add(item.getAdminVariantWeight().multiply(new BigDecimal(item.getQuantity())));
                volume = volume.add(item.getAdminVariantVolume().multiply(new BigDecimal(item.getQuantity())));
                strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + item.getShippingId());
            }
            if (ListUtils.isEmpty(strings)) {
                return null;
            }
            Set<Object> shipMethodIds = redisTemplate.opsForSet().union(strings);
            if (ListUtils.isNotEmpty(shipMethodIds)) {
                Set<Long> methodIds = new HashSet<>();
                shipMethodIds.forEach(shipMethodId -> {
                    methodIds.add((Long) shipMethodId);
                });
                ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
                searchRequest.setToAreaId(areaId);
                searchRequest.setWeight(weight);
                searchRequest.setVolumeWeight(volume);
                searchRequest.setMethodIds(methodIds);
                ShipMethodSearchResponse searchResponse = tmsFeignClient.shipSearch(searchRequest);
                if (searchResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(searchResponse.getData())).toJavaList(ShipDetail.class);
                    return shipDetails;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    List<ShipDetail> orderShipMethods(Long orderId, Long areaId,String warehouseCode) {
        try {

            Page<OrderItem> page = new Page<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            page.setT(orderItem);
            List<OrderItem> items = orderItemDao.select(page);
            BigDecimal weight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            List<String> strings = new ArrayList<>();
            for (OrderItem item : items) {
                if (null == item.getAdminVariantWeight()
                        || null == item.getAdminVariantVolume()
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantWeight()) == 0
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantVolume()) == 0) {
                    return null;
                }
                weight = weight.add(item.getAdminVariantWeight().multiply(new BigDecimal(item.getQuantity())));
                volume = volume.add(item.getAdminVariantVolume().multiply(new BigDecimal(item.getQuantity())));
                strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + item.getShippingId());
            }
            if (ListUtils.isEmpty(strings)) {
                return null;
            }
            Set<Object> shipMethodIds = redisTemplate.opsForSet().union(strings);
            if (shipMethodIds == null
            || shipMethodIds.size() == 0){
                return null;
            }

            Map<String,ShippingMethodRedis> codeShipMethodMap = new HashMap<>();
            List<String> methodCodes = new ArrayList<>();
            for (Object shipMethodId : shipMethodIds) {
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,shipMethodId);
                if (shippingMethodRedis.getWarehouseCode().equals(warehouseCode)){
                    methodCodes.add(shippingMethodRedis.getMethodCode());
                }
            }
            if (ListUtils.isEmpty(methodCodes)){
                return null;
            }

            ArearedisVo arearedisVo = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA,String.valueOf(areaId));
            ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
            destinationDTO.setCountry(arearedisVo.getAreaCode());

            ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
            priceCalculator.setHeight("1");
            priceCalculator.setLength("1");
            priceCalculator.setWidth("1");
            priceCalculator.setWeight(weight.toPlainString());
            priceCalculator.setService_code("FB4");
            priceCalculator.setProduct_codes(methodCodes);
            priceCalculator.setWarehouse_code(warehouseCode);
            priceCalculator.setBilling_time(System.currentTimeMillis());
            priceCalculator.setDestination(destinationDTO);

            List<PriceCalculatorDTO> priceCalculatorDTOS = FpxCommonApi.priceCalculator(priceCalculator);
            if (ListUtils.isNotEmpty(priceCalculatorDTOS)){
                List<ShipDetail> shipDetails = new ArrayList<>();
                for (PriceCalculatorDTO priceCalculatorDTO : priceCalculatorDTOS) {
                    List<PriceCalculatorDTO.FeesDTO> feesDTOS = priceCalculatorDTO.getFees();
                    BigDecimal price = BigDecimal.ZERO;
                    for (PriceCalculatorDTO.FeesDTO feesDTO : feesDTOS) {
                        price = price.add(feesDTO.getAmount());
                    }
                    for (PriceCalculatorDTO.FeesDTO feesDTO : feesDTOS) {
                        ShippingMethodRedis shippingMethodRedis = codeShipMethodMap.get(priceCalculatorDTO);
                        ShipDetail shipDetail = new ShipDetail(shippingMethodRedis);
                        shipDetail.setWeight(weight);
                        shipDetail.setDays(priceCalculatorDTO.getTimely());
                        shipDetail.setPrice(feesDTO.getAmount().divide(new BigDecimal("6.3"),2,BigDecimal.ROUND_UP));
                        shipDetails.add(shipDetail);
                    }
                }
                return shipDetails;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     */
    @Override
    public Order selectByPrimaryKey(Long id) {
        return orderDao.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrderByStoreOrder(Long storeOrderId) {
        String key = RedisKey.STRING_STORE_ORDER_CREATE + storeOrderId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 10);
        if (!flag) {
            return null;
        }
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByStoreOrderId(storeOrderId);
        if (ListUtils.isNotEmpty(storeOrderRelates)) {
            RedisUtil.unLock(redisTemplate, key);
            return null;
        }
        StoreOrderItem storeOrderItem = new StoreOrderItem();
        storeOrderItem.setStoreOrderId(storeOrderId);
        storeOrderItem.setState(0);
        storeOrderItem.setIsRemoved(false);

        Page<StoreOrderItem> page = new Page<>();
        page.setT(storeOrderItem);
        page.setPageSize(null);
        page.setCondition("store_variant_id is not null");

        List<StoreOrderItem> storeOrderItems = storeOrderItemDao.selectByStoreOrderId(storeOrderId);

        if (ListUtils.isEmpty(storeOrderItems)) {
            RedisUtil.unLock(redisTemplate, key);
            return null;
        }

        List<Long> storeVariantIds = new ArrayList<>();
        storeOrderItems.forEach(item -> {
            storeVariantIds.add(item.getStoreVariantId());
        });
        //查询产品报价
        CustomerProductQuoteSearchRequest customerProductQuoteSearchRequest = new CustomerProductQuoteSearchRequest();
        customerProductQuoteSearchRequest.setStoreVariantIds(storeVariantIds);
        List<CustomerProductQuoteVo> customerProductQuoteVos = pmsFeignClient.searchCustomerProductQuote(customerProductQuoteSearchRequest);

        Map<Long, CustomerProductQuoteVo> map = new HashMap<>();
        if (ListUtils.isNotEmpty(customerProductQuoteVos)) {
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                map.put(customerProductQuoteVo.getStoreVariantId(), customerProductQuoteVo);
                storeVariantIds.remove(customerProductQuoteVo.getStoreVariantId());
            }
        }
        Long orderId = IdGenerate.nextId();

        StoreOrderAddress storeOrderAddress = storeOrderAddressDao.selectByStoreOrderId(storeOrderId);
        OrderAddress address = new OrderAddress();
        BeanUtils.copyProperties(storeOrderAddress, address);
        address.setOrderId(orderId);

        Date date = new Date();
        StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderId);
        Order order = new Order();
        order.setId(orderId);
        order.setOrgId(storeOrder.getOrgId());
        order.setOrgPath(storeOrder.getOrgPath());
        order.setCustomerId(storeOrder.getCustomerId());
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setStoreId(storeOrder.getStoreId());
        order.setOrderType(0);
        order.initOrder();
        if (address.getCountry() != null) {
            order.setToAreaId((Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, address.getCountry()));
        }
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, storeOrder.getCustomerId().toString());
        if (StringUtils.isBlank(managerCode)) {
            managerCode = "system";
        }
        order.setManagerCode(managerCode);
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal cnyProductAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;

        List<OrderItem> items = new ArrayList<>();
        List<Long> storeOrderItemIds = new ArrayList<>();

        Collection<String> strings = new ArrayList<>();
        Integer quoteState = 0;
        for (StoreOrderItem item : storeOrderItems) {
            OrderItem orderItem = null;
            BigDecimal itemQuantity = new BigDecimal(item.getQuantity());
            if (map.containsKey(item.getStoreVariantId())) {
                CustomerProductQuoteVo customerProductQuoteVo = map.get(item.getStoreVariantId());
                //报价中
                if (customerProductQuoteVo.getQuoteType() == 5) {
                    orderItem = new OrderItem();
                    orderItem.setQuoteState(5);
                    //产品报价失败
                } else if (customerProductQuoteVo.getQuoteState() == 0) {
                    orderItem = new OrderItem();
                    orderItem.setQuoteState(4);
                } else {//报价成功
                    orderItem = new OrderItem(customerProductQuoteVo);
                    orderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                    quoteState++;
                    try {
                        cnyProductAmount = cnyProductAmount.add(orderItem.getCnyPrice().multiply(itemQuantity));
                    } catch (Exception e) {
                        continue;
                    }
                    productAmount = productAmount.add(orderItem.getUsdPrice().multiply(itemQuantity));
                    totalWeight = totalWeight.add(customerProductQuoteVo.getWeight().multiply(itemQuantity));
                    volume = volume.add(customerProductQuoteVo.getVolume().multiply(itemQuantity));
                }
            } else {
                orderItem = new OrderItem();
                orderItem.setQuoteState(0);
            }
            BeanUtils.copyProperties(item, orderItem);
            orderItem.setOrderId(orderId);
            orderItem.setStoreOrderItemId(item.getId());
            orderItem.setDischargeQuantity(0);
            orderItem.setItemType(0);
            orderItem.setId(IdGenerate.nextId());
            strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + orderItem.getShippingId());
            items.add(orderItem);
        }
        if (quoteState > 0 && quoteState == items.size()) {
            order.setQuoteState(3);
        } else if (quoteState == 0) {
            order.setQuoteState(0);
        } else {
            order.setQuoteState(2);
        }
        order.setCnyProductAmount(cnyProductAmount);
        order.setProductAmount(productAmount);
        order.setTotalWeight(totalWeight);


        orderDao.insert(order);
        orderItemDao.insertByBatch(items);
        orderAddressDao.insert(address);
        if (ListUtils.isNotEmpty(storeOrderItemIds)) {
            storeOrderItemDao.updateStateByIds(storeOrderItemIds, 1);
        }
        StoreOrderRelate storeOrderRelate = new StoreOrderRelate(storeOrder);
        storeOrderRelate.setOrderId(orderId);
        storeOrderRelate.setOrderCreateTime(date);
        storeOrderRelate.setStoreName(storeOrder.getStoreName());
        if (null != address) {
            storeOrderRelate.setOrderCustomerName(address.getFirstName() + " " + address.getLastName());
        }
        storeOrderRelateDao.insert(storeOrderRelate);
        RedisUtil.unLock(redisTemplate, key);
        orderInitShipDetail(orderId);
        return order;
    }


    @Override
    public int cancelOrderByIds(List<Long> ids) {
        if (ListUtils.isEmpty(ids)){
            return 0;
        }
        return orderDao.cancelOrderByIds(ids);
    }

    @Override
    public int initShipByShipUnitId(Long shipUnitId) {

        if (shipUnitId != null) {
            return orderDao.initShipByShipUnitId(shipUnitId);
        }
        return 0;
    }

    @Transactional
    @Override
    public BaseResponse createReshipOrder(Long id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order == null) {
            return BaseResponse.failed("Order does not exist");
        }
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        Long reshipOrderId = IdGenerate.nextId();
        Order reshipOrder = new Order();
        BeanUtils.copyProperties(order, reshipOrder);
        reshipOrder.initOrder();
        reshipOrder.setQuoteState(order.getQuoteState());
        reshipOrder.setId(reshipOrderId);

        List<Long> storeVariantIds = new ArrayList<>();
        orderItems.forEach(item -> {
            storeVariantIds.add(item.getStoreVariantId());
        });
        //查询产品报价
        CustomerProductQuoteSearchRequest customerProductQuoteSearchRequest = new CustomerProductQuoteSearchRequest();
        customerProductQuoteSearchRequest.setStoreVariantIds(storeVariantIds);
        List<CustomerProductQuoteVo> customerProductQuoteVos = pmsFeignClient.searchCustomerProductQuote(customerProductQuoteSearchRequest);

        Map<Long, CustomerProductQuoteVo> map = new HashMap<>();
        if (ListUtils.isNotEmpty(customerProductQuoteVos)) {
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                map.put(customerProductQuoteVo.getStoreVariantId(), customerProductQuoteVo);
                storeVariantIds.remove(customerProductQuoteVo.getStoreVariantId());
            }
        }

        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal cnyProductAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        Integer quoteState = 0;
        List<OrderItem> reshipOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItem reshipOrderItem = new OrderItem();
            BeanUtils.copyProperties(orderItem, reshipOrderItem);
            reshipOrderItem.setDischargeQuantity(0);
            reshipOrderItem.setOrderId(reshipOrderId);
            reshipOrderItem.setId(IdGenerate.nextId());
            BigDecimal itemQuantity = new BigDecimal(orderItem.getQuantity());
            CustomerProductQuoteVo customerProductQuoteVo = map.get(orderItem.getStoreVariantId());
            if (null == customerProductQuoteVo) {
                reshipOrderItem.setQuoteState(0);
                reshipOrderItems.add(reshipOrderItem);
                continue;
            }
            if (customerProductQuoteVo.getQuoteType() == 5) {
                reshipOrderItem.setQuoteState(5);
            } else {
                reshipOrderItem.initItemQuoteDetail(customerProductQuoteVo);
                reshipOrderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                quoteState++;
                try {
                    cnyProductAmount = cnyProductAmount.add(orderItem.getCnyPrice().multiply(itemQuantity));
                } catch (Exception e) {
                    continue;
                }
                productAmount = productAmount.add(orderItem.getUsdPrice().multiply(itemQuantity));
                totalWeight = totalWeight.add(customerProductQuoteVo.getWeight().multiply(itemQuantity));
                volume = volume.add(customerProductQuoteVo.getVolume().multiply(itemQuantity));
            }

            reshipOrderItems.add(reshipOrderItem);
        }
        if (quoteState > 0 && quoteState == reshipOrderItems.size()) {
            order.setQuoteState(3);
        } else if (quoteState == 0) {
            order.setQuoteState(0);
        } else {
            order.setQuoteState(2);
        }
        reshipOrder.setCnyProductAmount(cnyProductAmount);
        reshipOrder.setProductAmount(productAmount);
        reshipOrder.setTotalWeight(totalWeight);


        orderDao.insert(reshipOrder);
        orderItemDao.insertByBatch(reshipOrderItems);

        OrderAddress orderAddress = orderAddressDao.selectByOrderId(id);
        orderAddress.setOrderId(reshipOrderId);
        orderAddress.setId(IdGenerate.nextId());
        orderAddressDao.insert(orderAddress);

        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(id);
        StoreOrderRelate storeOrderRelate = storeOrderRelates.get(0);
        storeOrderRelate.setOrderId(reshipOrderId);
        storeOrderRelate.setOrderCreateTime(new Date());
        storeOrderRelate.setId(null);
        storeOrderRelateDao.insert(storeOrderRelate);
        return BaseResponse.success();
    }

    @Override
    public void initQuoteState(Long id) {
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        Integer quoteProducts = 0;
        Integer quoteState = 0;
        if (ListUtils.isNotEmpty(orderItems)) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getQuoteState() == 5) {
                    quoteState = OrderConstant.QUOTE_STATE_QUOTING;
                    break;
                }
                if (orderItem.getQuoteState() != 1
                        && orderItem.getQuoteState() != 6) {
                    quoteProducts++;
                }
            }
        }
        if (quoteState != OrderConstant.QUOTE_STATE_QUOTING) {
            if (quoteProducts == 0) {
                quoteState = OrderConstant.QUOTE_STATE_QUOTED;
            } else if (quoteProducts == orderItems.size()) {
                quoteState = OrderConstant.QUOTE_STATE_UNQUOTED;
            } else {
                quoteState = OrderConstant.QUOTE_STATE_PART_UNQUOTED;
            }
        }
        Order order = new Order();
        order.setId(id);
        order.setQuoteState(quoteState);
        order.setUpdateTime(new Date());
        updateByPrimaryKeySelective(order);
    }

    @Override
    public int initVatAmountByCustomerId(Long customerId) {
        return orderDao.initVatAmountByCustomerId(customerId);
    }

    @Override
    public SaiheOrder querySaiheOrder(Long id) {
        return orderDao.querySaiheOrder(id);
    }

    @Override
    public void updateSaiheOrderCode(String id, String saiheOrderCode) {
        orderDao.updateSaiheOrderCode(id, saiheOrderCode);
    }

    @Override
    public boolean initOrderProductAmount(Long orderId) {
        return false;
    }

    public ShipDetail updateShipDetailById(Long id, ShipDetail shipDetail) {
        orderAttrDao.deleteByOrderIdAndName(id, OrderAttrEnum.SHIP_RULE_ID.name());
        Order order = orderDao.selectByPrimaryKey(id);
        // 删除原来的unit  并插入新的冗余
        orderShippingUnitService.delByOrderId(id, OrderType.NORMAL);
        BaseResponse response = tmsFeignClient.unitInfo(shipDetail.getShippingUtilId());
        if (response.getCode() == ResultCode.SUCCESS_CODE && response.getData() != null) {
            ShippingUnitVo shippingUnit = JSON.parseObject(JSON.toJSONString(response.getData()), ShippingUnitVo.class);
            OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
            BeanUtils.copyProperties(shippingUnit, orderShippingUnit);
            orderShippingUnit.setOrderId(id);
            orderShippingUnit.setOrderType(OrderType.NORMAL);
            orderShippingUnit.setId(IdGenerate.nextId());
            orderShippingUnit.setShipUnitId(shippingUnit.getId());
            orderShippingUnit.setCreateTime(new Date());
            orderShippingUnitService.insert(orderShippingUnit);
        }
        //vat
        BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), order.getShipPrice(), order.getToAreaId(), order.getCustomerId());
        shipDetail.setVatAmount(vatAmount);
        orderDao.updateShipDetailById(shipDetail, id);
        shipDetail.setPrice(shipDetail.getPrice().add(shipDetail.getServiceFee()));
        return shipDetail;

    }

    @Transactional
    @Override
    public ShipDetail orderInitShipDetail(Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (null == order
                || order.getPayState() != 0
                || order.getOrderType() == 1) {
            return null;
        }
        if (order.getToAreaId() == null) {
            OrderAddress orderAddress = orderAddressDao.selectByOrderId(orderId);
            if (StringUtils.isNotBlank(orderAddress.getCountry())) {
                Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry());
                order.setToAreaId(toAreaId);
                orderDao.updateToAreaIdById(orderId, toAreaId);
            } else {
                return null;
            }
        }
        OrderShipRuleDetail shipRuleDetail = matchShipRule(orderId);
        if (null == shipRuleDetail) {
            List<ShipDetail> shipDetails = orderShipMethods(order.getId(), order.getToAreaId());
            if (ListUtils.isNotEmpty(shipDetails)) {
                ShipDetail shipDetail = shipDetails.get(0);
                shipDetail = updateShipDetailById(orderId, shipDetail);
                return shipDetail;
            }
        } else {
            return shipRuleDetail.getShipDetail();
        }
        return null;
    }

    @Override
    public void orderUpdateToAreaId(Long orderId, String country) {
        Long areaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, country);
        orderDao.updateToAreaIdById(orderId, areaId);
        orderInitShipDetail(orderId);
    }

    @Override
    @Transactional
    public void updateOrderShipUnit(Long orderId, Long shippingUnitId) {
        if (shippingUnitId != null) {
            // 删除原来的unit  并插入新的冗余
            orderShippingUnitService.delByOrderId(orderId, OrderType.NORMAL);
            BaseResponse shippingUnitResponse = tmsFeignClient.unitInfo(shippingUnitId);
            if (shippingUnitResponse.getCode() == ResultCode.SUCCESS_CODE && shippingUnitResponse.getData() != null) {
                ShippingUnitVo shippingUnit = JSON.parseObject(JSON.toJSONString(shippingUnitResponse.getData()), ShippingUnitVo.class);
                OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
                BeanUtils.copyProperties(shippingUnit, orderShippingUnit);
                orderShippingUnit.setOrderId(orderId);
                orderShippingUnit.setCreateTime(new Date());
                orderShippingUnit.setOrderType(OrderType.NORMAL);
                orderShippingUnit.setId(IdGenerate.nextId());
                orderShippingUnit.setShipUnitId(shippingUnit.getId());
                orderShippingUnitService.insert(orderShippingUnit);
            }
        }
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(Order record) {
        return orderDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(Order record) {
        return orderDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<Order> select(Page<Order> record) {
        record.initFromNum();
        return orderDao.select(record);
    }

    /**
     *
     */
    public long count(Page<Order> record) {
        return orderDao.count(record);
    }

    /**
     * admin订单导出 赛盒待导入，待发货订单列表
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public OrderListResponse orderList(OrderListRequest request, Session session) {
        if (request.getT() == null) {
            request.setT(new Order());
        }
        Order o = request.getT();
        o.setPayState(1);
        o.setRefundState(0);
        //  正常 = 0,待审查 = 1,作废 = 2
        o.setOrderStatus(0);
        /*        o.setShipState(0);*/

        List<Order> results = this.select(request);
        Long total = this.count(request);
        request.setTotal(total);

        List<OrderVo> orderListVos = new ArrayList<>();
        try {
            orderListVos = loadOrderInfo(results);
        } catch (Exception e) {
            e.printStackTrace();
            return new OrderListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, orderListVos, request);
    }

    /**
     * 查询订单运输、目的地信息、补发信息、发货信息
     *
     * @param results
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<OrderVo> loadOrderInfo(List<Order> results) throws Exception {
        List<OrderVo> orderListVos = new ArrayList<>();
        if (results.size() == 0) {
            return orderListVos;
        }
        List<Long> areaIds = new ArrayList<>();
        List<Long> shipIds = new ArrayList<>();
        List<Long> reshipIds = new ArrayList<>();
        List<Long> trackOrderIds = new ArrayList<>();
        Set<Long> orderIds = new HashSet<>();
        results.forEach(order -> {
            OrderVo orderListVo = new OrderVo();
            BeanUtils.copyProperties(order, orderListVo);
            orderListVos.add(orderListVo);
            areaIds.add(order.getToAreaId());
            shipIds.add(order.getShipMethodId());
            orderIds.add(order.getId());
            if (order.getOrderType() == 1) {
                reshipIds.add(order.getId());
            }
            if (order.getShipState() == 1) {
                trackOrderIds.add(order.getId());
            }
        });


        //目的地信息
        CompletableFuture<Void> areaFuture = CompletableFuture.runAsync(() -> {
            orderListVos.forEach(orderVo -> {
                ArearedisVo a = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA, String.valueOf(orderVo.getToAreaId()));
                if (a != null) {
                    orderVo.setToAreaName(a.getName());
                }
            });
        }, threadPoolExecutor);

        //运输方式信息
        CompletableFuture<Void> shipFuture = CompletableFuture.runAsync(() -> {
            orderListVos.forEach(orderVo -> {
                ShippingMethodRedis s = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, String.valueOf(orderVo.getShipMethodId()));
                if (s != null) {
                    orderVo.setShippingMethodName(s.getName());
                    orderVo.setSaiheTransportId(s.getSaiheTransportId());
                    orderVo.setSaiheTransportName(s.getSaiheTransportName());
                }
            });
        }, threadPoolExecutor);


        //发货信息
        CompletableFuture<Void> trackFuture = CompletableFuture.runAsync(() -> {
            Map<Long, OrderTracking> trackMap = new HashMap<>();
            if (trackOrderIds.size() > 0) {
                List<OrderTracking> orderTrackingList = orderTrackingDao.listOrderTrackingByOrderIds(trackOrderIds);
                orderTrackingList.forEach(orderTracking -> {
                    trackMap.put(orderTracking.getOrderId(), orderTracking);
                });
                orderListVos.forEach(orderVo -> {
                    OrderTracking o = trackMap.get(orderVo.getId());
                    if (orderVo.getShipState() == 1 && o != null) {
                        orderVo.setShipMethodName(o.getShippingMethodName());
                        orderVo.setTrackingCode(o.getTrackingCode());
                        orderVo.setTrackState(o.getState());
                    }
                });
            }
        });

        //店铺订单Number
        CompletableFuture<Void> storeFuture = CompletableFuture.runAsync(() -> {
            Map<Long, List<StoreOrderRelate>> listOrderRelateMap = this.listOrderNumber(orderIds);
            orderListVos.forEach(orderVo -> {
                List<StoreOrderRelate> list = listOrderRelateMap.get(orderVo.getId());
                if (list != null) {
                    StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
                    StringJoiner fulfillmentStatus = new StringJoiner(",", "[", "]");
                    for (StoreOrderRelate relate : list) {
                        orderVo.setFinancialStatus(relate.getFinancialStatus());
                        stringJoiner.add(relate.getPlatOrderName());
                        if (relate.getFulfillmentStatus() == null) {
                            continue;
                        }
                        switch (relate.getFulfillmentStatus()) {
                            case 0:
                                fulfillmentStatus.add("no_fulfilled");
                                break;
                            case 1:
                                fulfillmentStatus.add("partially_fulfilled");
                                break;
                            case 2:
                                fulfillmentStatus.add("fulfilled");
                                break;
                            default:
                                break;
                        }
                    }

                    orderVo.setOrderNumber(stringJoiner.toString());
                    orderVo.setFulfillmentStatus(fulfillmentStatus.toString());
                }
            });
        });


        CompletableFuture.allOf(areaFuture, shipFuture, trackFuture, storeFuture).get();

        return orderListVos;
    }

    /**
     * 店铺订单Number
     *
     * @param orderIds
     * @return
     */
    public Map<Long, List<StoreOrderRelate>> listOrderNumber(Set<Long> orderIds) {
        List<StoreOrderRelate> storeOrderRelateList = storeOrderRelateDao.listStoreOrderRelateByOrderIds(orderIds);
        Map<Long, List<StoreOrderRelate>> listMap = new HashMap<>();
        storeOrderRelateList.forEach(storeOrderRelate -> {
            List<StoreOrderRelate> l = listMap.get(storeOrderRelate.getOrderId());
            if (l == null) {
                l = new ArrayList<>();
                listMap.put(storeOrderRelate.getOrderId(), l);
            }
            l.add(storeOrderRelate);
        });
        return listMap;
    }

    /**
     * admin普通订单详情页
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse orderDetails(Long id) {

        OrderDetailsVo orderDetailsVo = new OrderDetailsVo();
        //查询订单信息
        CompletableFuture<Void> orderFuture = CompletableFuture.runAsync(() -> {
            Order order = orderDao.selectByPrimaryKey(id);
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            if (order.getShipState() == 1) {
                OrderTracking orderTracking = orderTrackingService.queryOrderTrackingByOrderId(order.getId(), OrderType.NORMAL);
                if (orderTracking != null) {
                    orderVo.setShipMethodName(orderTracking.getShippingMethodName());
                    orderVo.setTrackingCode(orderTracking.getTrackingCode());
                }
            }
            orderDetailsVo.setOrder(orderVo);
        }, threadPoolExecutor);

        List<Long> variantIds = new ArrayList<>();
        Map<Long, ProductVariantTo> variantDetailMap = new HashMap<>();
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        List<OrderStoreVo> orderStoreVos = new ArrayList<>();
        Set<String> storeOrderIds = new HashSet<>();
        //订单项列表
        CompletableFuture<Void> orderItemsFuture = CompletableFuture.runAsync(() -> {
            List<OrderItemVo> orderItems = orderItemDao.listOrderItem(id);
            orderItems.forEach(orderItem -> {
                variantIds.add(orderItem.getAdminVariantId());
                orderItemVoList.add(orderItem);
                Set<Long> setStoreId = new HashSet<>();
                if (!storeOrderIds.contains(orderItem.getPlatOrderId())) {
                    OrderStoreVo orderStoreVo = new OrderStoreVo();
                    orderStoreVo.setStoreId(orderItem.getStoreId());
                    orderStoreVo.setFinancialStatus(orderItem.getFinancialStatus());
                    orderStoreVo.setFulfillmentStatus(orderItem.getFulfillmentStatus());
                    orderStoreVo.setPlatOrderId(orderItem.getPlatOrderId());
                    orderStoreVo.setPlatOrderName(orderItem.getPlatOrderName());
                    //店铺信息
                    if (!setStoreId.contains(orderItem.getStoreId())) {
                        BaseResponse response = umsFeignClient.storeInfo(orderItem.getStoreId());
                        LinkedHashMap map = (LinkedHashMap) response.getData();
                        if (map != null) {
                            orderStoreVo.setStoreName(String.valueOf(map.get("storeName")));
                        }
                    }
                    orderStoreVo.setCreateTime(orderItem.getCreateTime());
                    orderStoreVo.setUpdateTime(orderItem.getUpdateTime());
                    orderStoreVos.add(orderStoreVo);
                    storeOrderIds.add(orderItem.getPlatOrderId());
                }
            });
            orderDetailsVo.setOrderStores(orderStoreVos);
        }, threadPoolExecutor).thenRunAsync(() -> {
            //产品信息
            ListVariantsRequest request = new ListVariantsRequest();
            request.setVariantIds(variantIds);
            BaseResponse response = pmsFeignClient.listVariantByIds(request);
            List<LinkedHashMap> variantDetailList = (List<LinkedHashMap>) response.getData();
            variantDetailList.forEach(v -> {
                ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(v), ProductVariantTo.class);
                variantDetailMap.put(variantDetail.getId(), variantDetail);
            });
            orderItemVoList.forEach(orderItemVo -> {
                ProductVariantTo variantDetail = variantDetailMap.get(orderItemVo.getAdminVariantId());
                orderItemVo.setAdminVariantSku(variantDetail.getVariantSku());
                orderItemVo.setAdminVariantImg(variantDetail.getVariantImage());
                orderItemVo.setAdminVariantName(variantDetail.getCnName());
            });
            orderDetailsVo.setOrderItems(orderItemVoList);
        });

        //地址信息
        CompletableFuture<Void> orderAddressFuture = CompletableFuture.runAsync(() -> {
            OrderAddress orderAddress = orderAddressDao.queryOrderAddressByOrderId(id);
            orderDetailsVo.setOrderAddress(orderAddress);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(orderFuture, orderItemsFuture, orderAddressFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, orderDetailsVo);
    }

    /**
     * 订单审核列表
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public OrderListResponse pendingList(OrderListQueryRequest request, Session session) {
        if (request.getT() == null) {
            request.setT(new OrderQueryVo());
        }
        OrderQueryVo o = request.getT();
//        o.setPayState(1);
//        o.setRefundState(0);
        //订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
        o.setOrderType(1);
        //  正常 = 0,待审查 = 1,作废 = 2
        o.setOrderStatus(1);
        OrderListRequest orderListRequest = new OrderListRequest();
        BeanUtils.copyProperties(request, orderListRequest);
        List<Order> results = this.select(orderListRequest);
        Long total = this.count(orderListRequest);
        request.setTotal(total);

        List<OrderVo> orderListVos;
        try {
            orderListVos = loadOrderInfo(results);
        } catch (Exception e) {
            e.printStackTrace();
            return new OrderListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        List<OrderPendingVo> orderPendingVos = new ArrayList<>();
        orderListVos.forEach(order -> {
            OrderPendingVo orderPendingVo = new OrderPendingVo();
            BeanUtils.copyProperties(order, orderPendingVo);
            OrderReshipInfo orderReshipInfo = orderReshipInfoDao.selectByPrimaryKey(orderPendingVo.getId());
            orderPendingVo.setOriginalOrderId(orderReshipInfo.getOriginalOrderId());
            orderPendingVo.setReason(orderReshipInfo.getReason());
            orderPendingVo.setReshipTimes(orderReshipInfo.getReshipTimes());

            String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, orderPendingVo.getCustomerId().toString());
            orderPendingVo.setManagerCode(managerCode);

            BaseResponse customerResponse = umsFeignClient.customerInfo(orderPendingVo.getCustomerId());
            if (customerResponse.getCode() == ResultCode.SUCCESS_CODE) {
                CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(customerResponse.getData()), CustomerVo.class);
                if (customerVo != null) {
                    orderPendingVo.setCustomerLoginName(customerVo.getLoginName());
                }
            }

            orderPendingVos.add(orderPendingVo);
        });
        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, orderPendingVos, request);
    }

    /**
     * 申请补发订单
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse applyReshipOrder(ApplyReshipOrderRequest request, Session session) throws CustomerException {

        Order order = orderDao.selectByPrimaryKey(request.getOriginalOrderId());

        int existCount = orderDao.existPendingOrderByOriginal(request.getOriginalOrderId());
        if (existCount > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单在补发待审核中!");
        }
        int c = orderDao.existReshipOrderWaitTrackByOriginal(request.getOriginalOrderId());
        if (c > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "存在未发货的补发订单!");
        }

        if (order == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单不存在!");
        }
        if (order.getPayState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单状态异常!");
        }
        if (order.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单状态异常!");
        }
        if (order.getShipState() == null
                || order.getShipState() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单未发货!");
        }

        //正常订单才能补发
        if (order.getOrderType() == 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单不满足补发条件!");
        }

        List<ReshipOrderItemVo> items = request.getItems();
        if (items == null || items.size() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "没有订单项!");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> variantIds = new ArrayList<>();
        for (ReshipOrderItemVo reshipOrderItemVo : items) {
            Long itemId = reshipOrderItemVo.getItemId();
            if (itemId == null || reshipOrderItemVo.getQuantity() == null
                    || reshipOrderItemVo.getQuantity() <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项参数异常!");
            }
            OrderItem orderItem = orderItemDao.selectByPrimaryKey(itemId);
            if (orderItem == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项不存在!");
            }
            if (orderItem.getAdminVariantId() == null
                    || !orderItem.getAdminVariantId().
                    equals(reshipOrderItemVo.getAdminVariantId())) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项参数异常!");
            }
            if (reshipOrderItemVo.getQuantity() > orderItem.getQuantity()) {
                return new BaseResponse(ResultCode.FAIL_CODE, "补发数量异常!");
            }
            //使用新的修改的倍数和数量
            orderItem.setQuantity(reshipOrderItemVo.getQuantity());
            variantIds.add(orderItem.getAdminVariantId());
            orderItems.add(orderItem);
        }

        ShippingMethodsRequest shippingMethodsRequest = new ShippingMethodsRequest();
        shippingMethodsRequest.setIds(Collections.singletonList(request.getShipMethodId()));
        BaseResponse responseShipMethod = tmsFeignClient.listShippingMethod(shippingMethodsRequest);
        List<LinkedHashMap> mapList = (List<LinkedHashMap>) responseShipMethod.getData();
        if (responseShipMethod.getCode() == 0 || mapList == null || mapList.size() == 0) {
            return responseShipMethod;
        }
        LinkedHashMap map = mapList.get(0);
        ShippingMethodVo shippingMethodVo = JSON.parseObject(JSON.toJSONString(map), ShippingMethodVo.class);

        ListVariantsRequest r = new ListVariantsRequest();
        r.setVariantIds(variantIds);
        BaseResponse response = pmsFeignClient.listVariantByIds(r);
        if (response.getCode() == 0) {
            return response;
        }
        response.getData();
        List<LinkedHashMap> linkedHashMaps = (List<LinkedHashMap>) response.getData();
        Map<Long, BigDecimal> weightMap = new HashMap<>();
        linkedHashMaps.forEach(linkedHashMap -> {
            ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(linkedHashMap), ProductVariantTo.class);
            //0:实重 1:体积重
            if (shippingMethodVo.getWeightType() == 0) {
                weightMap.put(variantDetail.getId(), variantDetail.getWeight());
            } else {
                weightMap.put(variantDetail.getId(), variantDetail.getVolumeWeight());
            }
        });

        OrderAddress orderAddress = orderAddressDao.queryOrderAddressByOrderId(request.getOriginalOrderId());
        if (orderAddress == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "收获信息异常!");
        }

        String userCode = String.valueOf(session.getId());
        Long orderId = IdGenerate.nextId();
        order.setId(orderId);
        //使用选择的运输方式
        order.setShipMethodId(request.getShipMethodId());
        order.setPayTime(new Date());
        order.setPayAmount(BigDecimal.ZERO);
        order.setShipPrice(BigDecimal.ZERO);
        order.setProductAmount(BigDecimal.ZERO);
        order.setProductDischargeAmount(BigDecimal.ZERO);
        order.setFixFee(BigDecimal.ZERO);
        order.setPayState(1);
        order.setRefundState(0);
        order.setShipState(0);
        //订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
        order.setOrderType(1);
        //交易号
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, order.getCustomerId().toString());
        order.setManagerCode(managerCode);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        //默认待审核
        order.setOrderStatus(1);//审核状态  正常 = 0,待审查 = 1,作废 = 2

        //补发次数
        int reshipTimes = orderDao.reshipOrderTimesByOriginal(request.getOriginalOrderId());
        OrderReshipInfo orderReshipInfo = new OrderReshipInfo();
        orderReshipInfo.setOrderId(orderId);
        orderReshipInfo.setOriginalOrderId(request.getOriginalOrderId());
        orderReshipInfo.setReason(request.getReason());
        orderReshipInfo.setReshipTimes(reshipTimes + 1);

        //新增订单项
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            Long id = IdGenerate.nextId();
            orderItem.setId(id);
            orderItem.setOrderId(orderId);
            //补发订单 item
            BigDecimal w = weightMap.get(orderItem.getAdminVariantId());
            totalWeight = totalWeight.add(w.multiply(new BigDecimal(orderItem.getQuantity())));
        }
        order.setTotalWeight(totalWeight);
        orderAddress.setId(IdGenerate.nextId());
        orderAddress.setOrderId(orderId);

        orderItemDao.insertByBatch(orderItems);
        orderReshipInfoDao.insert(orderReshipInfo);
        orderAddressDao.insert(orderAddress);
        orderDao.insert(order);

        return new OrderUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 补发确认
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public OrderUpdateResponse confirmPendingOrder(UpdatePendingOrderRequest request, Session session) {
        String userCode = String.valueOf(session.getId());
        //正常 = 0,待审查 = 1,作废 = 2
        int res = orderDao.updatePendingOrderStatus(request.getIds(), 0, userCode);
        return new OrderUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS + res);
    }

    /**
     * 补发作废
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public OrderUpdateResponse cancelPendingOrder(UpdatePendingOrderRequest request, Session session) {
        String userCode = String.valueOf(session.getId());
        //正常 = 0,待审查 = 1,作废 = 2
        int res = orderDao.updatePendingOrderStatus(request.getIds(), 2, userCode);
        if (res > 0) {
            orderReshipInfoDao.updateReshipTimes(request.getIds(), 0);
        }
        return new OrderUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS + res);
    }

    /**
     * admin历史订单列表
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public OrderListResponse manageList(AppOrderListRequest request, Session session) {
        List<AppOrderVo> appOrderVos = selectAppOrderList(request);
        Long total = selectAppOrderCount(request);
        request.setTotal(total);
        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appOrderVos, request);
    }

    /**
     * 导入订单到赛盒
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean importOrderToSaihe(Long id) {
        /**
         * 获取 已支付，未发货，订单状态正常，未退款
         * 并且没有赛盒orderCode的赛盒订单信息
         */
        SaiheOrder saiheOrder = orderDao.querySaiheOrder(id);
        if (null == saiheOrder) {
            return false;
        }
        /**
         * 该订单是否在赛盒已经上传了，不用再回传
         * orderCode在数据库中未有，保存信息
         */
        Boolean isUpload = orderCommonService.checkAndSaveOrderCodeFromSaihe(saiheOrder.getClientOrderCode(), OrderType.NORMAL);
        if (isUpload) {
            return true;
        }
        /**
         * 查询该订单的订单产品
         */
        List<SaiheOrderItem> orderItemList = orderItemDao.listSaiheOrderItemByOrderId(id);
        log.warn("saihe订单上传产品：{}", orderItemList);
        for (SaiheOrderItem saiheOrderItem : orderItemList) {
            if (saiheOrderItem.getProductNum() == null
                    || saiheOrderItem.getSalePrice() == null) {
                return false;
            }
        }
        saiheOrder.setOrderItemList(orderItemList);
//        Boolean bealUpdate = orderCommonService.importOrderToSaihe(saiheOrder);
//        if (!bealUpdate){
//            return false;
//        }
        /**
         * 上传订单信息实体封装 并且上传赛盒
         */
        return orderCommonService.upLoadOrderToSaiHe(saiheOrder, OrderType.NORMAL);
    }

    /**
     * 获取/更新赛盒物流信息 标记订单已发货
     *
     * @param id
     * @return
     */

    /*public boolean getTrackingFromSaihe(Long id) {
        Order a=orderDao.selectByPrimaryKey(id);
        if(a==null||a.getSaiheOrderCode()==null||a.getShipMethodId()==null){
            return false;
        }
        if(a.getPayState()==null||a.getPayState()!=1){
            return false;
        }
        //从赛盒获取订单号
        ApiGetOrderResponse apiGetOrderResponse=  SaiheService.getOrderByCode(a.getSaiheOrderCode());
        if(apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")){
            List<ApiOrderInfo> l=apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
            if(l!=null&&l.size()>0){
                String trackNumbers=l.get(0).getTrackNumbers();
                String orderCode=l.get(0).getOrderCode();
                String logisticsOrderNo=l.get(0).getLogisticsOrderNo();
                Integer transportId=l.get(0).getTransportID();//获取运输id
                //logger.debug("orderCode:{},trackNumbers:{},logisticsOrderNo:{}",orderCode,trackNumbers,logisticsOrderNo);
                Long shippingMethodId = a.getShipMethodId();
                ShippingMethodVo shippingMethod=null;
                //优先根据赛盒运输id查询Admin运输方式
                ShippingMethodVo paramVo=new ShippingMethodVo();
                paramVo.setId(shippingMethodId);
                paramVo.setSaiheTransportId(transportId);
                BaseResponse response=tmsFeignClient.getShippingMethodByTransportId(paramVo);
                if(response.getCode()==ResultCode.SUCCESS_CODE&&response.getData()!=null){
                    shippingMethod= (ShippingMethodVo) response.getData();
                }
                //获取admin的运输方式
                String shippingMethodName = "";
                if(shippingMethod!=null&&shippingMethod.getTrackType()!=null
                   &&!StringUtils.isBlank(orderCode)&&orderCode.equals(a.getSaiheOrderCode())){
                    shippingMethodName = shippingMethod.getName();
                    //有运输商单号或真实追踪号
                    if(!StringUtils.isBlank(logisticsOrderNo)||!StringUtils.isBlank(trackNumbers)){
                        OrderTracking orderTracking = new OrderTracking();
                        orderTracking.setOrderId(a.getId());
                        orderTracking.setTrackNumbers(trackNumbers);
                        orderTracking.setLogisticsOrderNo(logisticsOrderNo);
                        orderTracking.setUpdateTime(new Date());
                        orderTracking.setShippingMethodName(shippingMethodName);
                        orderTracking.setTransportId(transportId);
                        orderTracking.setTrackType(shippingMethod.getTrackType());
                        OrderTracking old=orderTrackingDao.queryOrderTrackingByOrderId(a.getId());
                        //获取赛盒运输信息
                        if(old==null){
                            orderTracking.setState(0);
                            orderTracking.setCreateTime(new Date());
                            //标记订单为发货
                            orderDao.updateOrderAsTracked(id);
                            orderTrackingDao.insert(orderTracking);
                        //处于待回传状态，继续更新运输信息
                        }else {
                            orderTrackingDao.updateOrderTracking(orderTracking);
                        }
                        return true;
                    }
                }

            }
        }
        return false;
    }*/
    @Override
    public boolean getTrackingFromSaihe(Long id) {
        boolean b = orderCommonService.getTrackingFromSaihe(id, OrderType.NORMAL);

        return b;
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 潘达信息 分页列表
     *
     * @param record
     * @return
     */
    @Override
    public List<PandaOrderListVo> upedgeOrderPage(Page<PandaOrderListDto> record) {
        List<PandaOrderListVo> upedgeOrderListVos = orderDao.upedgeOrderPage(record);
        for (PandaOrderListVo upedgeOrderListVo : upedgeOrderListVos) {
            if (null != upedgeOrderListVo.getShipMethodId()) {
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, upedgeOrderListVo.getShipMethodId());
                upedgeOrderListVo.setShippingMethodName(shippingMethodRedis.getName());
            }
            if (StringUtils.isNotBlank(upedgeOrderListVo.getToAreaId())) {
                ArearedisVo area = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA, upedgeOrderListVo.getToAreaId());
                upedgeOrderListVo.setToAreaName(area.getName());
            }
        }
        return upedgeOrderListVos;
    }

    @Override
    public Long upedgeOrderCount(Page<PandaOrderListDto> record) {
        return orderDao.upedgeOrderCount(record);
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 数量 和 地区分布）
     *
     * @param query
     * @return
     */
    @Override
    public HashMap quantityAndArea(OrderAnalysisDto query) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //  该时间区间内下单数量
        Integer OrderAllCount = orderDao.getOrderAllCountByTime(query);
        //  该时间区间内下单未发货数量
        Integer OrderNotDeliverCount = orderDao.getOrderNotDeliverCountByTime(query);
        //  该时间区间内下单已发货数量
        Integer OrderDeliverCount = orderDao.getOrderDeliverCountByTime(query);
        //  该时间区间内订单地区分布
        List<EchartsPieVo> resultList = orderDao.orderDistributionByTime(query);
        for (EchartsPieVo echartsPieVo : resultList) {
            ArearedisVo area = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA, echartsPieVo.getName());
            echartsPieVo.setName(area.getName());
        }
        resultMap.put("countPayOrder", OrderAllCount);
        resultMap.put("countTrackedOrder", OrderNotDeliverCount);
        resultMap.put("countTrackingOrder", OrderDeliverCount);
        resultMap.put("pieJson", JSON.toJSONString(resultList));
        return resultMap;
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 按月统计下单数和下单金额）
     *
     * @param request
     * @return
     */
    @Override
    public Map quantityAndAmount(OrderAnalysisDto request) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(request.getStartTime());
        //获取当前月的第一天
        String startDay = DateTools.getMonthStartDay(date);
        //获取当前月的最后一天
        String endDay = DateTools.getMonthEndDay(date);
        List<String> dayStr = DateTools.getMonthDays(date);
        Map<String, Integer> mapDate = DateTools.mapMonthDays(date);

        List<AppOrderDataVo> dataList = new ArrayList<>();
        if (date.compareTo(new Date()) <= 0) {
            //日期在当前日期以内
            dataList = orderDao.appOrderWithDate(request.getCustomerId(), startDay, endDay);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取当前月的总天数
        int dayNumOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        ;

        long[] orderNumArr = new long[dayNumOfMonth];
        double[] orderAmountArr = new double[dayNumOfMonth];

        for (AppOrderDataVo data : dataList) {
            String day = data.getDayDate();
            long orderNum = data.getOrderNum() == null ? 0 : data.getOrderNum();
            orderNumArr[mapDate.get(day) - 1] = orderNum;
            double orderAmount = data.getOrderAmount() == null ? 0.0 : data.getOrderAmount().doubleValue();
            orderAmountArr[mapDate.get(day) - 1] = orderAmount;
        }
        HashMap<String, String> map = new HashMap<>();
        String orderNumArrJson = JSON.toJSONString(orderNumArr);
        String orderAmountArrJson = JSON.toJSONString(orderAmountArr);
        String dayStrJson = JSON.toJSONString(dayStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        map.put("today", simpleDateFormat.format(date));
        map.put("orderNumArrJson", orderNumArrJson);
        map.put("orderAmountArrJson", orderAmountArrJson);
        map.put("dayStrJson", dayStrJson);
        return map;
    }

    /**
     * 根据条件查询 Order
     *
     * @param dto
     * @return
     */
    @Override
    public List<Order> getOrderList(PandaOrderListDto dto) {

        return orderDao.getOrderList(dto);
    }

    @Override
    public ManagerActualVo getManagerActual(ManagerActualRequest request) {
        return orderDao.getManagerActual(request);
    }

    @Override
    public List<Order> selectOrderByPaymentId(Long paymentId) {

        return orderDao.selectOrderByPaymentId(paymentId);

    }

    @Override
    public BaseResponse getNormalOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        Set<String> managerCodes = allOrderAmountVo.getManagerCodeSet();
        if (ListUtils.isNotEmpty(managerCodes)) {
            for (String managerCode : managerCodes) {
                allOrderAmountVo.setManagerCode(managerCode);
                BigDecimal amount = orderDao.getNormalOrderAmountByManagerCode(allOrderAmountVo);
                bigDecimal = bigDecimal.add(amount);
            }
        }

        return BaseResponse.success(bigDecimal);
    }


    /**
     * 获取普通订单下单客户数量 根据 set<managerCode> select
     *
     * @param allOrderAmountVo
     * @return
     */
    @Override
    public BaseResponse getNormalOrderCount(AllOrderAmountVo allOrderAmountVo) {
        Set<String> normalOrderCount = orderDao.getNormalOrderCount(allOrderAmountVo);
        return BaseResponse.success(normalOrderCount);
    }

    @Override
    public BaseResponse getCustomerOrderStatistical(Long customerId) {

        CustomerOrderStatisticalVo customerOrderStatisticalVo = new CustomerOrderStatisticalVo();
        //  <!--查询已付款的潘达订单总金额 产品费+运费+手续费 已支付的-->
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            CustomerOrderStatisticalVo customerOrderStatistical = orderDao.OrderTotalAmount(customerId);
            if (customerOrderStatistical == null) {
                return;
            }
            customerOrderStatisticalVo.setOrderAllMoney(customerOrderStatistical.getOrderAllMoney());
            customerOrderStatisticalVo.setTotalPandaPaidOrderNum(customerOrderStatistical.getTotalPandaPaidOrderNum());

        }, threadPoolExecutor);
        // 取消潘达订单数
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            Integer cancelOrderCount = orderDao.getCancelOrderCount(customerId);
            customerOrderStatisticalVo.setCancelOrderCount(cancelOrderCount);
        }, threadPoolExecutor);
        //用户已支付已发货订单
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            Integer payAndShipOrderCount = orderDao.getPayAndShipOrderCount(customerId);
            customerOrderStatisticalVo.setPayAndShipOrderCount(payAndShipOrderCount);

        }, threadPoolExecutor);
        // 用户已支付未发货订单
        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
            Integer payButNoShipOrderCount = orderDao.getPayButNoShipOrderCount(customerId);
            customerOrderStatisticalVo.setPayButNoShipOrderCount(payButNoShipOrderCount);

        }, threadPoolExecutor);
        // 用户已完成
        CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> {
            Integer completeOrderCount = orderDao.getCompleteOrderCount(customerId);
            customerOrderStatisticalVo.setCompleteOrderCount(completeOrderCount);

        }, threadPoolExecutor);

        CompletableFuture<Void> future6 = CompletableFuture.runAsync(() -> {
            Integer noPayOrderCount = orderDao.getNoPayOrderCount(customerId);
            customerOrderStatisticalVo.setNoPayOrderCount(noPayOrderCount);

        }, threadPoolExecutor);


        try {
            CompletableFuture.allOf(future1, future2, future3, future4, future5, future6).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        return BaseResponse.success(customerOrderStatisticalVo);

    }

    @Override
    public int updateOrderShipMethod(Long id) {
        return orderDao.updateOrderShipMethod(id);
    }

    /**
     * 通过枚举修改各表的managerCode  不是修改一张order表  请勿随意调用
     *
     * @param changeManagerVo
     * @param tableName
     */
    @Override
    public void updateManagerCode(ChangeManagerVo changeManagerVo, String tableName) {
        orderDao.updateManagerCode(changeManagerVo, tableName);
    }

    @Override
    public List<Order> selectPage(Page<Order> page) {
        return orderDao.selectPage(page);
    }

    @Override
    public void delOrderShipInfoByProductId(Long productId) {
        orderDao.delOrderShipInfoByProductId(productId);
    }

    @Override
    public void delOrderShipInfoByVariantId(Long variantId) {
        orderDao.delOrderShipInfoByVariantId(variantId);
    }

    @Override
    public void matchingShipInfoByProductId(List<OrderItem> list) {
        for (OrderItem orderItem : list) {
            orderInitShipDetail(orderItem.getOrderId());
        }
    }

    @Override
    public void matchingShipInfoByVariantId(List<OrderItem> list) {
        List<Long> orderIds = new ArrayList<>();
        for (OrderItem orderItem : list) {
            if (orderIds.contains(orderItem.getOrderId())) {
                continue;
            }
            orderIds.add(orderItem.getOrderId());
            orderInitShipDetail(orderItem.getOrderId());
        }
    }


}