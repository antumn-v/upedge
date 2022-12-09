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
import com.upedge.common.model.oms.order.ItemQuantityVo;
import com.upedge.common.model.oms.order.OrderExcelItemDto;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.CustomerOrderStatisticalVo;
import com.upedge.common.model.order.vo.ManagerActualVo;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.QuotedProductSelectBySkuRequest;
import com.upedge.common.model.pms.response.QuotedProductSelectBySkuResponse;
import com.upedge.common.model.pms.vo.VariantWarehouseStockModel;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductVariantTo;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.CustomStoreSelectRequest;
import com.upedge.common.model.tms.ArearedisVo;
import com.upedge.common.model.tms.ShippingUnitVo;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateTools;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.enums.OrderAttrEnum;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import com.upedge.oms.modules.order.dto.OrderAnalysisDto;
import com.upedge.oms.modules.order.dto.OrderExcelImportDto;
import com.upedge.oms.modules.order.dto.OrderListDto;
import com.upedge.oms.modules.order.entity.*;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderListResponse;
import com.upedge.oms.modules.order.response.OrderUpdateResponse;
import com.upedge.oms.modules.order.service.*;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.rules.dto.ShipRuleConditionDto;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.service.OrderShipRuleService;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.vat.service.VatRuleService;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.service.SaiheService;
import com.upedge.thirdparty.shipcompany.fpx.api.FpxCommonApi;
import com.upedge.thirdparty.shipcompany.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.shipcompany.fpx.dto.ShipPriceCalculator;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
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
    CustomerProductStockService customerProductStockService;

    @Autowired
    OrderShipRuleService orderShipRuleService;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    OrderPackageService orderPackageService;

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
    RedisTemplate redisTemplate;

    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    private OrderTrackingService orderTrackingService;
    
    @Autowired
    OrderItemService orderItemService;

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

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    StoreOrderService storeOrderService;

    @Value("${ifUploadSaihe}")
    Boolean ifUploadSaihe;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;


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
    public void deleteOrderByIds(List<Long> ids) throws CustomerException {
        List<Order> orders = orderDao.selectByIds(ids);
        if (ListUtils.isEmpty(orders)) {
            throw new CustomerException("delete order error");
        }
        for (Order order : orders) {
            if (order.getPayState() > 0) {
                throw new CustomerException("delete order error");
            }
        }
        orderDao.deleteByIds(ids);

        storeOrderItemDao.updateStateAfterRemoveOrder(ids);
    }


    @Override
    public AppOrderVo appOrderDetail(Long id) {
        AppOrderVo appOrderVo = orderDao.selectAppOrderById(id);
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(id);
        appOrderVo.setOrderCustomerName(storeOrderRelates.get(0).getOrderCustomerName());
        List<AppOrderItemVo> itemVos = orderItemDao.selectAppOrderItemByOrderId(id);
        appOrderVo.setStoreOrderVos(new HashSet<>());
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
        if (appOrderVo.getShipState() == 1) {
            OrderTracking orderTracking = orderTrackingService.queryOrderTrackingByOrderId(id, OrderType.NORMAL);
            if (orderTracking != null) {
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
        AppOrderListDto appOrderListDto = request.getT();
        if (appOrderListDto != null && StringUtils.isNotBlank(appOrderListDto.getShipCompany())){
            appOrderListDto.setShipMethodIds(getShipMethodIdsByCompany(appOrderListDto.getShipCompany()));
        }
        List<AppOrderVo> appOrderVos = orderDao.selectAppOrderList(request);
        if (ListUtils.isEmpty(appOrderVos)) {
            return new ArrayList<>();
        }
        List<Long> orderIds = new ArrayList<>();
        appOrderVos.forEach(appOrderVo -> {
            orderIds.add(appOrderVo.getId());
        });

        List<AppStoreOrderVo> appStoreOrderVos = getAppStoreOrderVos(orderIds);

        List<Long> ids = redisTemplate.opsForList().range(RedisKey.HASH_ORDER_APP_CREATE_RESHIP_APPLICATION,0,-1);

        for (AppOrderVo orderVo : appOrderVos) {
            if (orderVo.getOrderType() != 1){
                orderVo.setReshipCreateSource(0L);
            }
            if (orderVo.getPickType() == null){
                orderVo.setPickType(-1);
            }
            if (orderVo.getOrderType() == 1 && ListUtils.isNotEmpty(ids) && ids.contains(orderVo.getId())){
                orderVo.setReshipCreateSource(Constant.APP_APPLICATION_ID);
            }else if (orderVo.getOrderType() == 1){
                orderVo.setReshipCreateSource(Constant.ADMIN_APPLICATION_ID);
            }
            if (orderVo.getShipMethodId() != null) {
                orderVo.setShipPrice(orderVo.getShipPrice().add(orderVo.getServiceFee()));
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, orderVo.getShipMethodId().toString());
                if (null != shippingMethodRedis) {
                    orderVo.setShipCompany(shippingMethodRedis.getTrackingCompany());
                    orderVo.setShipMethodName(shippingMethodRedis.getName());
                    orderVo.setShipMethodDesc(shippingMethodRedis.getDesc());
                }
            } else {
                orderVo.setShipPrice(BigDecimal.ZERO);
            }
            orderVo.setStoreOrderVos(new HashSet<>());
            for (AppStoreOrderVo storeOrderVo : appStoreOrderVos) {
                if (orderVo.getId().equals(storeOrderVo.getOrderId())) {
                    orderVo.getStoreOrderVos().add(storeOrderVo);
                    orderVo.setOrderCustomerName(storeOrderVo.getOrderCustomerName());
                }
            }
            ArearedisVo arearedisVo = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA, String.valueOf(orderVo.getToAreaId()));
            if (null != arearedisVo) {
                orderVo.setCountry(arearedisVo.getEnName());
                orderVo.setCnCountry(arearedisVo.getName());
            }
            completeOrderStoreUrl(orderVo);
        }
        return appOrderVos;
    }

    private List<Long> getShipMethodIdsByCompany(String company){
        List<ShippingMethodRedis> shippingMethodRedisList = redisTemplate.opsForHash().values(RedisKey.SHIPPING_METHOD);
        List<Long> methodIds = new ArrayList<>();
        for (ShippingMethodRedis shippingMethodRedis : shippingMethodRedisList) {
            if (shippingMethodRedis.getTrackingCompany() != null
            && shippingMethodRedis.getTrackingCompany().equals(company)){
                methodIds.add(shippingMethodRedis.getId());
            }
        }
        return methodIds;
    }

    private List<AppStoreOrderVo> getAppStoreOrderVos(List<Long> orderIds){
        if (ListUtils.isEmpty(orderIds)){
            return new ArrayList<>();
        }
        List<OrderItem> orderItems = orderItemDao.selectAppOrderItemByOrderIds(orderIds);

        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderIds(orderIds);

        List<AppStoreOrderVo> appStoreOrderVos = new ArrayList<>();

        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            AppStoreOrderVo appStoreOrderVo = new AppStoreOrderVo();
            BeanUtils.copyProperties(storeOrderRelate,appStoreOrderVo);

            List<AppOrderItemVo> itemVos = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getOrderId().equals(storeOrderRelate.getOrderId())
                && orderItem.getStoreOrderId().equals(storeOrderRelate.getStoreOrderId())){
                    AppOrderItemVo appOrderItemVo = new AppOrderItemVo();
                    BeanUtils.copyProperties(orderItem,appOrderItemVo);
                    appOrderItemVo.setPrice(orderItem.getUsdPrice());
                    itemVos.add(appOrderItemVo);
                }
            }
            orderItems.removeAll(itemVos);
            appStoreOrderVo.setItemVos(itemVos);
            appStoreOrderVos.add(appStoreOrderVo);
        }
        return appStoreOrderVos;
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
        OrderAddress orderAddress =  orderAddressDao.queryOrderAddressByOrderId(id);
        if (null == orderAddress){
            return null;
        }
        if (StringUtils.isBlank(orderAddress.getPhone())){
            orderAddress.setPhone("00000000000");
        }
        return orderAddress;
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
        Order order = selectByPrimaryKey(id);
        if (order == null || order.getPayState() != 0
        || order.getQuoteState() != Order.QUOTE_QUOTED) {
            return null;
        }
        if (!shipDetail.isCouldShip()) {
            return null;
        }
        List<ShipDetail> shipDetails = null;
        if (shipDetail.getWarehouseCode().equals(ProductConstant.DEFAULT_WAREHOUSE_ID)) {
            shipDetails = orderLocalWarehouseShipMethods(order.getId(), order.getToAreaId());
        } else {
            shipDetails = orderOverseaWarehouseShipMethods(order.getId(), order.getToAreaId());
        }

        for (ShipDetail detail : shipDetails) {
            if (detail.getMethodId().equals(shipDetail.getMethodId())
                    && detail.isCouldShip()) {
                return updateShipDetailById(id, detail);
            }
        }
        ShipDetail detail = new ShipDetail();
        detail.setPrice(BigDecimal.ZERO);
        detail.setWarehouseCode(null);
        detail.setMethodId(null);
        detail.setServiceFee(BigDecimal.ZERO);
        detail.setVatAmount(BigDecimal.ZERO);
        detail.setWeight(BigDecimal.ZERO);
        orderShippingUnitService.delByOrderId(id, OrderType.NORMAL);
        orderDao.updateShipDetailById(shipDetail, id);
        return detail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateOrderAttr(OrderAttr orderAttr) {
        orderAttrDao.deleteByOrderIdAndName(orderAttr.getOrderId(), orderAttr.getAttrName());
        orderAttr.setCreateTime(new Date());
        return orderAttrDao.insert(orderAttr);
    }


//    public List<ShipDetail> orderShipList(Long id) {
//        Order order = orderDao.selectByPrimaryKey(id);
//        if (order.getToAreaId() == null) {
//            OrderAddress orderAddress = orderAddressDao.selectByOrderId(id);
//            if (StringUtils.isNotBlank(orderAddress.getCountry())) {
//                Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry());
//                order.setToAreaId(toAreaId);
//                orderDao.updateToAreaIdById(id, toAreaId);
//            } else {
//                return new ArrayList<>();
//            }
//        }
//        List<ShipDetail> shipDetails = orderShipMethods(order.getId(), order.getToAreaId(),ProductConstant.DEFAULT_WAREHOUSE_ID);
//        if (ListUtils.isEmpty(shipDetails)) {
//            shipDetails = new ArrayList<>();
//        }
//        return shipDetails;
//    }

    @Override
    public List<OrderShipMethodVo> orderShipList(Long id) {
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
        List<OrderShipMethodVo> orderShipMethodVos = new ArrayList<>();
        CompletableFuture<Void> local = CompletableFuture.runAsync(() -> {
            List<ShipDetail> localMethods = orderLocalWarehouseShipMethods(order.getId(), order.getToAreaId());
            for (ShipDetail localMethod : localMethods) {
                localMethod.setPrice(localMethod.getPrice().add(localMethod.getServiceFee()));
            }
            OrderShipMethodVo orderShipMethodVo = new OrderShipMethodVo(0, localMethods);
            orderShipMethodVos.add(orderShipMethodVo);
        }, threadPoolExecutor);
        CompletableFuture<Void> oversea = CompletableFuture.runAsync(() -> {
            List<ShipDetail> shipDetails = orderOverseaWarehouseShipMethods(order.getId(), order.getToAreaId());
            for (ShipDetail shipDetail : shipDetails) {
                shipDetail.setPrice(shipDetail.getPrice().add(shipDetail.getServiceFee()));
            }
            OrderShipMethodVo orderShipMethodVo = new OrderShipMethodVo(1, shipDetails);
            orderShipMethodVos.add(orderShipMethodVo);
        }, threadPoolExecutor);
        try {
            CompletableFuture.allOf(local, oversea).get();
            return orderShipMethodVos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
//        List<ShipDetail> shipDetails = orderShipMethods(order.getId(), order.getToAreaId());
//        shipDetails.addAll(orderOverseaWarehouseShipMethods(order.getId(),order.getToAreaId()));
//        if (ListUtils.isEmpty(shipDetails)) {
//            shipDetails = new ArrayList<>();
//        }
//        return shipDetails;
    }

//    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderShipRuleDetail matchShipRule(Long id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order.getPayState() != 0 && order.getQuoteState() != OrderConstant.QUOTE_STATE_QUOTED) {
            return null;
        }

        ShippingTemplateRedis shippingTemplateRedis = getOrderShipTemplate(id);
        if (null == shippingTemplateRedis) {
            return null;
        }
        //查询符合条件的运输规则
        ShipRuleConditionDto shipRuleConditionDto = new ShipRuleConditionDto();
        shipRuleConditionDto.setAreaId(order.getToAreaId());
        shipRuleConditionDto.setCustomerId(order.getCustomerId());
        shipRuleConditionDto.setShipTemplateId(shippingTemplateRedis.getId());
        List<OrderShipRule> rules = orderShipRuleService.selectShipRulesByCondition(shipRuleConditionDto);
        if (ListUtils.isEmpty(rules)) {
            return null;
        }
        //查询本地吃运输方式
        List<ShipDetail> shipDetails = orderLocalWarehouseShipMethods(order.getId(), order.getToAreaId());
        if (ListUtils.isEmpty(shipDetails)) {
            shipDetails = new ArrayList<>();
        }
        boolean b = false;
        if (ListUtils.isNotEmpty(shipDetails)) {
            for (OrderShipRule rule : rules) {
                //当运输规则里包含海外仓时，再查询海外仓的运输方式
                if (rule.getShipTemplateId().equals(Constant.OVERSEA_WAREHOUSE) && !b) {
                    continue;
                }
                for (ShipDetail ship : shipDetails) {
                    if (rule.getShippingMethodId().equals(ship.getMethodId())) {
                        order.setShipPrice(ship.getPrice());
                        order.setTotalWeight(ship.getWeight());
                        order.setShipMethodId(ship.getMethodId());

                        ship = updateShipDetailById(id, ship);

                        OrderShipRuleDetail detail = new OrderShipRuleDetail();
                        detail.setOrderId(id);
                        detail.setShipRuleId(rule.getId());
                        detail.setShipRuleName(rule.getTitle());
                        detail.setShipDetail(ship);
                        return detail;
                    }
                }
            }
        } else {
            ShipDetail detail = new ShipDetail();
            detail.setPrice(BigDecimal.ZERO);
            detail.setWarehouseCode(null);
            detail.setMethodId(null);
            detail.setServiceFee(BigDecimal.ZERO);
            detail.setVatAmount(BigDecimal.ZERO);
            detail.setWeight(BigDecimal.ZERO);
            orderShippingUnitService.delByOrderId(id, OrderType.NORMAL);
            orderDao.updateShipDetailById(detail, id);
        }
        return null;
    }


    @Override
    public OrderShipRuleDetail matchShipRule(Long id, OrderShipRuleVo rule) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order.getPayState() != 0 && order.getQuoteState() != OrderConstant.QUOTE_STATE_QUOTED) {
            return null;
        }

        ShippingTemplateRedis shippingTemplateRedis = getOrderShipTemplate(id);
        if (null == shippingTemplateRedis) {
            return null;
        }

        //查询本地吃运输方式
        List<ShipDetail> shipDetails = orderLocalWarehouseShipMethods(order.getId(), order.getToAreaId());
        if (ListUtils.isEmpty(shipDetails)) {
            shipDetails = new ArrayList<>();
        }
        boolean b = false;
        if (ListUtils.isNotEmpty(shipDetails)) {

            //当运输规则里包含海外仓时，再查询海外仓的运输方式
            if (rule.getShipTemplateId().equals(Constant.OVERSEA_WAREHOUSE) && !b) {
                return null;
            }
            for (ShipDetail ship : shipDetails) {
                if (rule.getShippingMethodId().equals(ship.getMethodId())) {
                    order.setShipPrice(ship.getPrice());
                    order.setTotalWeight(ship.getWeight());
                    order.setShipMethodId(ship.getMethodId());

                    ship = updateShipDetailById(id, ship);

//                    OrderAttr orderAttr = new OrderAttr();
//                    orderAttr.setOrderId(id);
//                    orderAttr.setAttrName(OrderAttrEnum.SHIP_RULE_ID.name());
//                    orderAttr.setAttrValue(String.valueOf(rule.getId()));
//                    orderAttr.setCreateTime(new Date());
//                    orderAttrDao.deleteByOrderIdAndName(id, OrderAttrEnum.SHIP_RULE_ID.name());
//                    orderAttrDao.insert(orderAttr);

                    OrderShipRuleDetail detail = new OrderShipRuleDetail();
                    detail.setOrderId(id);
                    detail.setShipRuleId(rule.getId());
                    detail.setShipRuleName(rule.getTitle());
                    detail.setShipDetail(ship);
                    return detail;
                }
            }

        }
        return null;
    }

    @Transactional
    @Override
    public BaseResponse importExcelOrder(OrderExcelImportRequest request, Session session) {
        Date date = new Date();
        List<OrderExcelImportDto> orderExcels = request.getOrderExcels();
        if (ListUtils.isEmpty(orderExcels)) {
            return BaseResponse.failed();
        }
        List<String> storeNames = new ArrayList<>();
        Map<String, List<String>> orderSkusMap = new HashMap<>();
        List<OrderExcelItemDto> excelItemDtos = new ArrayList<>();
        Map<String, Integer> orderSkuQuantity = new HashMap<>();
        Map<String, OrderAddress> orderAddressMap = new HashMap<>();
        List<String> skuList = new ArrayList<>();
        for (OrderExcelImportDto orderExcel : orderExcels) {
            try {
                orderExcel.checkNotNullFiled();
            } catch (CustomerException e) {
                return BaseResponse.failed(e.getMessage());
            }
            String storeName = orderExcel.getStoreName();
            if (StringUtils.isBlank(storeName)) {
                orderExcel.setStoreName("default store");
            }
            String orderNo = orderExcel.getStoreName() + ":" + orderExcel.getOrderNumber();
            storeNames.add(orderExcel.getStoreName());
            if (orderSkusMap.containsKey(orderNo)) {
                orderSkusMap.get(orderNo).add(orderExcel.getSku());
            } else {
                List<String> skus = new ArrayList<>();
                skus.add(orderExcel.getSku());
                orderSkusMap.put(orderNo, skus);
            }
            skuList.add(orderExcel.getSku());

            OrderExcelItemDto excelItemDto = new OrderExcelItemDto();
            BeanUtils.copyProperties(orderExcel, excelItemDto);
            excelItemDtos.add(excelItemDto);
            String orderSku = orderNo + "_" + orderExcel.getSku();
            orderSkuQuantity.put(orderSku, orderExcel.getQuantity());

            OrderAddress orderAddress = new OrderAddress();
            BeanUtils.copyProperties(orderExcel, orderAddress);
            orderAddressMap.put(orderNo, orderAddress);
        }
        QuotedProductSelectBySkuRequest quotedProductSelectBySkuRequest = new QuotedProductSelectBySkuRequest();
        quotedProductSelectBySkuRequest.setCustomerId(session.getCustomerId());
        quotedProductSelectBySkuRequest.setSkus(skuList);
        QuotedProductSelectBySkuResponse quotedProductSelectBySkuResponse = pmsFeignClient.selectQuoteProductBySkus(quotedProductSelectBySkuRequest);
        List<CustomerProductQuoteVo> customerProductQuoteVos = quotedProductSelectBySkuResponse.getCustomerProductQuoteVos();
        if (ListUtils.isEmpty(customerProductQuoteVos)) {
            return BaseResponse.failed("sku error");
        }
        Map<String, CustomerProductQuoteVo> skuQuote = new HashMap<>();
        for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
            skuQuote.put(customerProductQuoteVo.getVariantSku(), customerProductQuoteVo);
        }
        CustomStoreSelectRequest customStoreSelectRequest = new CustomStoreSelectRequest();
        customStoreSelectRequest.setSession(session);
        customStoreSelectRequest.setStoreNames(storeNames);
        List<StoreVo> storeVos = umsFeignClient.selectCustomStore(customStoreSelectRequest);
        Map<String, StoreVo> storeVoMap = new HashMap<>();
        for (StoreVo storeVo : storeVos) {
            storeVoMap.put(storeVo.getStoreName(), storeVo);
        }

        for (String orderNumber : orderSkusMap.keySet()) {
            List<OrderItem> items = new ArrayList<>();
            String storeName = orderNumber.substring(0, orderNumber.indexOf(":"));
            String orderName = orderNumber.substring(orderNumber.indexOf(":") + 1, orderNumber.length());
            Long orderId = IdGenerate.nextId();
            BigDecimal productAmount = BigDecimal.ZERO;
            BigDecimal cnyProductAmount = BigDecimal.ZERO;
            BigDecimal totalWeight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            List<String> skus = orderSkusMap.get(orderNumber);
            for (String sku : skus) {
                if (!skuQuote.containsKey(sku)) {
                    continue;
                }
                Integer quantity = orderSkuQuantity.get(orderNumber + "_" + sku);
                BigDecimal itemQuantity = new BigDecimal(quantity);
                CustomerProductQuoteVo customerProductQuoteVo = skuQuote.get(sku);
                if (customerProductQuoteVo.getQuoteState() != 1) {
                    continue;
                }
                OrderItem orderItem = new OrderItem();
                BeanUtils.copyProperties(customerProductQuoteVo, orderItem);
                orderItem.setStoreVariantImage(customerProductQuoteVo.getVariantImage());
                orderItem.setOriginalQuantity(quantity);
                orderItem.setOrderId(orderId);
                orderItem.setStoreOrderId(orderId);
                orderItem.setStoreOrderItemId(0l);
                orderItem.setDischargeQuantity(0);
                orderItem.setItemType(0);
                orderItem.setQuantity(quantity);
                orderItem.setStoreVariantId(0L);
                orderItem.setId(IdGenerate.nextId());
                orderItem.quoteProductToItem(customerProductQuoteVo);
                customerProductQuoteVo.setQuoteScale(1);
                cnyProductAmount = cnyProductAmount.add(orderItem.getCnyPrice().multiply(itemQuantity));
                productAmount = productAmount.add(orderItem.getUsdPrice().multiply(itemQuantity));
                totalWeight = totalWeight.add(customerProductQuoteVo.getWeight().multiply(itemQuantity));
                volume = volume.add(customerProductQuoteVo.getVolume().multiply(itemQuantity));
                items.add(orderItem);
            }
            if (ListUtils.isEmpty(items)) {
                continue;
            }
            StoreVo storeVo = storeVoMap.get(storeName);
            Order order = new Order();
            String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, session.getCustomerId().toString());
            if (StringUtils.isBlank(managerCode)) {
                managerCode = "system";
            }
            order.setManagerCode(managerCode);
            order.setQuoteState(Order.QUOTE_QUOTED);
            order.setId(orderId);
            order.setOrgId(storeVo.getOrgId());
            order.setOrgPath(storeVo.getOrgPath());
            order.setCustomerId(session.getCustomerId());
            order.setCreateTime(date);
            order.setUpdateTime(date);
            order.setStoreId(storeVo.getId());
            order.setOrderType(4);
            order.initOrder();
            OrderAddress orderAddress = orderAddressMap.get(orderNumber);
            orderAddress.setId(IdGenerate.nextId());
            orderAddress.setOrderId(orderId);
            if (orderAddress.getCountry() != null) {
                order.setToAreaId((Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry()));
            }
            order.setCnyProductAmount(cnyProductAmount);
            order.setProductAmount(productAmount);
            order.setTotalWeight(totalWeight);
            orderDao.insert(order);
            orderItemService.insertByBatch(items);
            orderAddressDao.insert(orderAddress);

            StoreOrderRelate storeOrderRelate = new StoreOrderRelate();
            storeOrderRelate.setStoreOrderId(orderId);
            storeOrderRelate.setOrderId(orderId);
            storeOrderRelate.setPlatOrderName(orderName);
            storeOrderRelate.setOrderCustomerName(orderAddress.getName());
            storeOrderRelate.setFinancialStatus(0);
            storeOrderRelate.setFulfillmentStatus(0);
            storeOrderRelate.setPlatOrderCreateTime(date);
            storeOrderRelate.setStoreName(storeName);
            storeOrderRelateDao.insert(storeOrderRelate);
        }

        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse orderCustomCreate(OrderCustomCreateRequest request, Session session) {
        String storeName = request.getStoreName();
        if (StringUtils.isBlank(storeName)) {
            storeName = "default store";
        }
        List<String> storeNames = new ArrayList<>();
        storeNames.add(storeName);
        CustomStoreSelectRequest customStoreSelectRequest = new CustomStoreSelectRequest();
        customStoreSelectRequest.setSession(session);
        customStoreSelectRequest.setStoreNames(storeNames);
        List<StoreVo> storeVos = umsFeignClient.selectCustomStore(customStoreSelectRequest);
        Date date = new Date();
        List<OrderExcelItemDto> itemDtos = request.getItemDtos();

        List<String> skuList = new ArrayList<>();
        for (OrderExcelItemDto itemDto : itemDtos) {
            skuList.add(itemDto.getSku());
        }
        StoreVo storeVo = storeVos.get(0);

        QuotedProductSelectBySkuRequest quotedProductSelectBySkuRequest = new QuotedProductSelectBySkuRequest();
        quotedProductSelectBySkuRequest.setCustomerId(session.getCustomerId());
        quotedProductSelectBySkuRequest.setSkus(skuList);
        QuotedProductSelectBySkuResponse quotedProductSelectBySkuResponse = pmsFeignClient.selectQuoteProductBySkus(quotedProductSelectBySkuRequest);
        List<CustomerProductQuoteVo> customerProductQuoteVos = quotedProductSelectBySkuResponse.getCustomerProductQuoteVos();
        if (ListUtils.isEmpty(customerProductQuoteVos)) {
            return BaseResponse.failed("sku error");
        }
        Map<String, CustomerProductQuoteVo> skuQuote = new HashMap<>();
        for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
            skuQuote.put(customerProductQuoteVo.getVariantSku(), customerProductQuoteVo);
        }
        List<OrderItem> items = new ArrayList<>();
        Long orderId = IdGenerate.nextId();

        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal cnyProductAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        for (OrderExcelItemDto itemDto : itemDtos) {
            Integer quantity = itemDto.getQuantity();
            String sku = itemDto.getSku();
            BigDecimal itemQuantity = new BigDecimal(quantity);
            CustomerProductQuoteVo customerProductQuoteVo = skuQuote.get(sku);
            if (customerProductQuoteVo.getQuoteState() != 1) {
                continue;
            }
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(customerProductQuoteVo, orderItem);
            orderItem.setBarcode(customerProductQuoteVo.getBarcode());
            orderItem.setStoreVariantImage(customerProductQuoteVo.getVariantImage());
            orderItem.setStoreVariantSku(customerProductQuoteVo.getVariantSku());
            orderItem.setStoreVariantName(customerProductQuoteVo.getVariantName());
            orderItem.setOriginalQuantity(quantity);
            orderItem.setOrderId(orderId);
            orderItem.setStoreOrderId(orderId);
            orderItem.setStoreOrderItemId(0l);
            orderItem.setDischargeQuantity(0);
            orderItem.setItemType(0);
            orderItem.setQuantity(quantity);
            orderItem.setStoreVariantId(0L);
            orderItem.setId(IdGenerate.nextId());
            orderItem.setBarcode(customerProductQuoteVo.getBarcode());
            orderItem.quoteProductToItem(customerProductQuoteVo);
            customerProductQuoteVo.setQuoteScale(1);
            cnyProductAmount = cnyProductAmount.add(orderItem.getCnyPrice().multiply(itemQuantity));
            productAmount = productAmount.add(orderItem.getUsdPrice().multiply(itemQuantity));
            totalWeight = totalWeight.add(customerProductQuoteVo.getWeight().multiply(itemQuantity));
            volume = volume.add(customerProductQuoteVo.getVolume().multiply(itemQuantity));
            items.add(orderItem);
        }

        Order order = new Order();
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, session.getCustomerId().toString());
        if (StringUtils.isBlank(managerCode)) {
            managerCode = "system";
        }
        order.setManagerCode(managerCode);
        order.setQuoteState(Order.QUOTE_QUOTED);
        order.setId(orderId);
        order.setOrgId(storeVo.getOrgId());
        order.setOrgPath(storeVo.getOrgPath());
        order.setCustomerId(session.getCustomerId());
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setStoreId(storeVo.getId());
        order.setOrderType(4);
        order.initOrder();
        OrderAddress orderAddress = request.getAddress();
        orderAddress.setId(IdGenerate.nextId());
        orderAddress.setOrderId(orderId);
        if (orderAddress.getCountry() != null) {
            Long areaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry());
            if (areaId == null){
                return BaseResponse.failed("country error");
            }
            order.setToAreaId(areaId);
            ArearedisVo arearedisVo = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA,areaId.toString());
            orderAddress.setCountryCode(arearedisVo.getAreaCode());
        }
        order.setCnyProductAmount(cnyProductAmount);
        order.setProductAmount(productAmount);
        order.setTotalWeight(totalWeight);
        orderDao.insert(order);
        orderItemService.insertByBatch(items);
        orderAddressDao.insert(orderAddress);

        StoreOrderRelate storeOrderRelate = new StoreOrderRelate();
        storeOrderRelate.setStoreOrderId(orderId);
        storeOrderRelate.setOrderId(orderId);
        storeOrderRelate.setPlatOrderName(request.getOrderNum());
        storeOrderRelate.setOrderCustomerName(orderAddress.getName());
        storeOrderRelate.setFinancialStatus(0);
        storeOrderRelate.setFulfillmentStatus(0);
        storeOrderRelate.setPlatOrderCreateTime(date);
        storeOrderRelate.setStoreName(storeVo.getStoreName());
        storeOrderRelateDao.insert(storeOrderRelate);

        matchShipRule(orderId);
        return BaseResponse.success();
    }


    List<ShipDetail> orderShipMethods(Long orderId, Long areaId) {
        return orderLocalWarehouseShipMethods(orderId, areaId);
    }

    @Override
    public List<ShipDetail> orderLocalWarehouseShipMethods(Long orderId, Long areaId) {
        try {
            Page<OrderItem> page = new Page<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            page.setT(orderItem);
            List<OrderItem> items = orderItemDao.select(page);
            BigDecimal weight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            ShippingTemplateRedis shippingTemplateRedis = null;
            for (OrderItem item : items) {
                if (null == item.getAdminVariantId()){
                    continue;
                }
                if (null == item.getAdminVariantWeight()
                        || null == item.getAdminVariantVolume()
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantWeight()) == 0
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantVolume()) == 0) {
                    return new ArrayList<>();
                }
                weight = weight.add(item.getAdminVariantWeight().multiply(new BigDecimal(item.getQuantity())));
                volume = volume.add(item.getAdminVariantVolume().multiply(new BigDecimal(item.getQuantity())));
                ShippingTemplateRedis templateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(item.getShippingId()));
                if (null != templateRedis) {
                    if (null == shippingTemplateRedis) {
                        shippingTemplateRedis = templateRedis;
                    } else {
                        if (templateRedis.getSeq() < shippingTemplateRedis.getSeq()) {
                            shippingTemplateRedis = templateRedis;
                        }
                    }
                }
            }
            if (null == shippingTemplateRedis) {
                return new ArrayList<>();
            }
            Set<Object> shipMethodIds = redisTemplate.opsForSet().members(RedisKey.SHIPPING_TEMPLATED_METHODS + shippingTemplateRedis.getId());
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
                    for (ShipDetail shipDetail : shipDetails) {
                        shipDetail.setWarehouseCode(ProductConstant.DEFAULT_WAREHOUSE_ID);
                    }
                    return shipDetails;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    @Override
    public List<ShipDetail> orderOverseaWarehouseShipMethods(Long orderId, Long areaId) {
        try {
            String warehouseCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AVAILABLE_OVERSEA_WAREHOUSE, areaId.toString());
            if (null == warehouseCode) {
                return new ArrayList<>();
            }
            List<Long> methodIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_WAREHOUSE_METHOD, warehouseCode);
            if (ListUtils.isEmpty(methodIds)) {
                return new ArrayList<>();
            }
            OrderAddress orderAddress = orderAddressDao.selectByOrderId(orderId);
            if (orderAddress.getZip() == null) {
                return new ArrayList<>();
            }
            Order order = selectByPrimaryKey(orderId);
            Long customerId = order.getCustomerId();
            List<OrderItem> items = orderItemDao.selectItemByOrderId(orderId);
            BigDecimal weight = BigDecimal.ZERO;
            boolean couldShip = true;
            for (OrderItem item : items) {
                if (null == item.getAdminVariantId()){
                    continue;
                }
                if (null == item.getAdminVariantWeight()
                        || null == item.getAdminVariantVolume()
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantWeight()) == 0
                        || BigDecimal.ZERO.compareTo(item.getAdminVariantVolume()) == 0) {
                    return new ArrayList<>();
                }
                weight = weight.add(item.getAdminVariantWeight().multiply(new BigDecimal(item.getQuantity())));
                boolean b = customerProductStockService.redisCheckCustomerVariantStock(customerId, item.getAdminVariantId(), warehouseCode, item.getQuantity());
                if (!b) {
                    couldShip = false;
                }
            }

            Map<String, ShippingMethodRedis> codeShipMethodMap = new HashMap<>();
            List<String> methodCodes = new ArrayList<>();
            for (Long methodId : methodIds) {
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, String.valueOf(methodId));
                if (shippingMethodRedis != null
                        && StringUtils.isNotBlank(shippingMethodRedis.getMethodCode())) {
                    methodCodes.add(shippingMethodRedis.getMethodCode());
                    codeShipMethodMap.put(shippingMethodRedis.getMethodCode(), shippingMethodRedis);
                }
            }

            if (ListUtils.isEmpty(methodCodes)) {
                return new ArrayList<>();
            }

            int w = weight.intValue();
            ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
            destinationDTO.setCountry(orderAddress.getCountryCode());
            destinationDTO.setPost_code(orderAddress.getZip());
            ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
            priceCalculator.setHeight("1");
            priceCalculator.setLength("1");
            priceCalculator.setWidth("1");
            priceCalculator.setWeight(String.valueOf(w));
            priceCalculator.setService_code("FB4");
            priceCalculator.setProduct_codes(methodCodes);
            priceCalculator.setWarehouse_code(warehouseCode);
            priceCalculator.setBilling_time(System.currentTimeMillis());
            priceCalculator.setDestination(destinationDTO);

            List<PriceCalculatorDTO> priceCalculatorDTOS = FpxCommonApi.priceCalculator(priceCalculator);
            if (ListUtils.isNotEmpty(priceCalculatorDTOS)) {
                List<ShipDetail> shipDetails = new ArrayList<>();
                for (PriceCalculatorDTO priceCalculatorDTO : priceCalculatorDTOS) {
                    BigDecimal price = new BigDecimal(priceCalculatorDTO.getTotalAmount());
                    if (StringUtils.isBlank(priceCalculatorDTO.getCurrency())) {
                        continue;
                    }
                    switch (priceCalculatorDTO.getCurrency()) {
                        case "CNY":
                            price = PriceUtils.cnyToUsdByDefaultRate(price);
                            break;
                        case "USD":
                            break;
                        case "EUR":
                            price = PriceUtils.eurToUsdByDefaultRate(price);
                            break;
                        default:
                            continue;
                    }
                    ShippingMethodRedis shippingMethodRedis = codeShipMethodMap.get(priceCalculatorDTO.getProductCode());
                    if (shippingMethodRedis == null) {
                        continue;
                    }
                    ShipDetail shipDetail = new ShipDetail(shippingMethodRedis);

                    shipDetail.setWeight(weight);
                    shipDetail.setDays(priceCalculatorDTO.getTimely().replace("天", "").replace("个工作日", ""));
                    shipDetail.setPrice(price);
                    shipDetail.setServiceFee(new BigDecimal("1.5"));
                    shipDetail.setWarehouseCode(warehouseCode);
                    shipDetail.setCouldShip(couldShip);
                    shipDetails.add(shipDetail);
                }
                shipDetails.sort(new Comparator<ShipDetail>() {
                    @Override
                    public int compare(ShipDetail o1, ShipDetail o2) {
                        return o1.getPrice().compareTo(o2.getPrice());
                    }
                });
                return shipDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
            if (item.getStoreVariantId() != null && item.getState() == 0){
                storeVariantIds.add(item.getStoreVariantId());
            }
        });
        if (ListUtils.isEmpty(storeVariantIds)){
            return null;
        }
        //查询产品报价
//        CustomerProductQuoteSearchRequest customerProductQuoteSearchRequest = new CustomerProductQuoteSearchRequest();
//        customerProductQuoteSearchRequest.setStoreVariantIds(storeVariantIds);
//        List<CustomerProductQuoteVo> customerProductQuoteVos = pmsFeignClient.searchCustomerProductQuote(customerProductQuoteSearchRequest);
//
//        Map<Long, CustomerProductQuoteVo> customerProductQuoteVoMap = new HashMap<>();
//        if (ListUtils.isNotEmpty(customerProductQuoteVos)) {
//            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
//                customerProductQuoteVoMap.put(customerProductQuoteVo.getStoreVariantId(), customerProductQuoteVo);
//            }
//        }
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
        Integer itemSize = storeOrderItems.size();
        Integer quotedItem = 0;
        Integer unQuotedItem = 0;
        Integer quotingItem = 0;

        for (StoreOrderItem item : storeOrderItems) {
            if (item.getStoreVariantId() == null){
                continue;
            }
            //判断是否是拆分的变体
            List<Long> splitVariantIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_STORE_SPLIT_VARIANT, String.valueOf(item.getStoreVariantId()));
            if (ListUtils.isNotEmpty(splitVariantIds)) {
                //判断拆分的变体是否已报价
                boolean quoted = false;
                for (Long splitVariantId : splitVariantIds) {
                    CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + splitVariantId);
                    if (customerProductQuoteVo != null && customerProductQuoteVo.getQuotePrice() != null) {
                        quoted = true;
                    } else {
                        continue;
                    }
                    if (customerProductQuoteVo.getQuoteScale() == null) {
                        customerProductQuoteVo.setQuoteScale(1);
                    }
                    BigDecimal itemQuantity = new BigDecimal(item.getQuantity()).multiply(new BigDecimal(customerProductQuoteVo.getQuoteScale()));
                    OrderItem orderItem = new OrderItem();
                    BeanUtils.copyProperties(item, orderItem);
                    orderItem.setQuantity(itemQuantity.intValue());
                    orderItem.setOriginalQuantity(item.getQuantity());
                    orderItem.quoteProductToItem(customerProductQuoteVo);
                    orderItem.setStoreVariantId(customerProductQuoteVo.getStoreVariantId());
                    orderItem.setStoreProductId(customerProductQuoteVo.getStoreProductId());
                    orderItem.setStoreVariantSku(customerProductQuoteVo.getStoreVariantSku());
                    orderItem.setStoreVariantName(customerProductQuoteVo.getStoreVariantName());
                    orderItem.setStoreVariantImage(customerProductQuoteVo.getStoreVariantImage());
                    orderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                    orderItem.setQuoteScale(customerProductQuoteVo.getQuoteScale());
                    try {
                        cnyProductAmount = cnyProductAmount.add(orderItem.getCnyPrice().multiply(itemQuantity));
                    } catch (Exception e) {
                        continue;
                    }
                    productAmount = productAmount.add(orderItem.getUsdPrice().multiply(itemQuantity));
                    totalWeight = totalWeight.add(customerProductQuoteVo.getWeight().multiply(itemQuantity));
                    volume = volume.add(customerProductQuoteVo.getVolume().multiply(itemQuantity));
                    orderItem.setOrderId(orderId);
                    orderItem.setStoreOrderItemId(item.getId());
                    orderItem.setDischargeQuantity(0);
                    orderItem.setItemType(2);
                    orderItem.setId(IdGenerate.nextId());
                    strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + orderItem.getShippingId());
                    items.add(orderItem);
                }
                if (quoted) {
                    quotedItem++;
                } else {
                    unQuotedItem++;
                }
                continue;
            }
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(item, orderItem);
            orderItem.setOriginalQuantity(item.getQuantity());
            CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + item.getStoreVariantId());
            if (customerProductQuoteVo != null) {
                if (customerProductQuoteVo.getQuoteType() == 5) {
                    //报价中
                    quotingItem++;
                    orderItem.setQuoteState(5);
                } else if (customerProductQuoteVo.getQuoteState() == 0) {
                    //产品报价失败
                    orderItem.setQuoteState(4);
                } else {
                    //报价成功
                    quotedItem++;
                    if (customerProductQuoteVo.getQuoteScale() == null) {
                        customerProductQuoteVo.setQuoteScale(1);
                    }
                    BigDecimal itemQuantity = new BigDecimal(item.getQuantity()).multiply(new BigDecimal(customerProductQuoteVo.getQuoteScale()));
                    orderItem.setQuantity(itemQuantity.intValue());
                    orderItem.quoteProductToItem(customerProductQuoteVo);
                    orderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                    orderItem.setQuoteScale(customerProductQuoteVo.getQuoteScale());
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
                orderItem.setQuoteState(0);
                unQuotedItem++;
            }
            if (null == orderItem.getQuoteScale()) {
                orderItem.setQuoteScale(1);
            }
            orderItem.setOriginalQuantity(item.getQuantity());
            orderItem.setOrderId(orderId);
            orderItem.setStoreOrderItemId(item.getId());
            orderItem.setDischargeQuantity(0);
            orderItem.setItemType(0);
            orderItem.setId(IdGenerate.nextId());
            strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + orderItem.getShippingId());
            items.add(orderItem);
        }
        order.setQuoteState(Order.QUOTE_PARTIAL);
        if (quotedItem == itemSize) {
            order.setQuoteState(Order.QUOTE_QUOTED);
        }
        if (unQuotedItem == itemSize) {
            order.setQuoteState(Order.QUOTE_UNQUOTED);
        }
        if (quotingItem > 0) {
            order.setQuoteState(Order.QUOTE_QUOTING);
        }
        order.setCnyProductAmount(cnyProductAmount);
        order.setProductAmount(productAmount);
        order.setTotalWeight(totalWeight);
        orderDao.insert(order);
        orderItemService.insertByBatch(items);
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

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public BaseResponse syncUnpaidOrder(Long customerId) {
        List<Long> orderIds = orderDao.selectUnpaidIdsByCustomer(customerId);
        if (ListUtils.isEmpty(orderIds)){
            return BaseResponse.failed("没有未支付的订单需要更新");
        }
        for (Long orderId : orderIds) {
            try {
                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(orderId);
                        StoreOrderRelate storeOrderRelate = storeOrderRelates.get(0);
                        StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderRelate.getStoreOrderId());
                        if(!storeOrder.getStoreName().equals("www.evershape.at")){
                            storeOrderService.getSingleOrder(storeOrder.getStoreId(),storeOrder.getPlatOrderId());
                        }
                    }
                },threadPoolExecutor);
            } catch (Exception e) {

            }
        }
        return BaseResponse.success();
    }

    @Override
    public int updateTrackingCodeTypeById(Long id, Integer trackingCodeType) {
        return orderDao.updateTrackingCodeTypeById(id, trackingCodeType);
    }

    @Override
    public BaseResponse processRepeatOrder() {
        List<Long> repeatPaidOrderIds = new ArrayList<>();
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectRepeatOrder();
        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            List<Order> orders = orderDao.selectByPlatOrderName(storeOrderRelate.getStoreName(),storeOrderRelate.getPlatOrderName());
            if (orders.size() == 1){
                continue;
            }
            LinkedList<Long> deleteIds = new LinkedList<>();
            List<Long> paidOrderIds = new ArrayList<>();
            for (Order order : orders) {
                if (order.getPayState() > 0){
                    paidOrderIds.add(order.getId());
                }else {
                    deleteIds.add(order.getId());
                }
            }
            if (paidOrderIds.size() > 1){
                repeatPaidOrderIds.addAll(paidOrderIds);
            }
            if (deleteIds.size() == orders.size()){
                deleteIds.removeFirst();
            }
            if(ListUtils.isNotEmpty(deleteIds)){
                try {
                    deleteOrderByIds(deleteIds);
                } catch (CustomerException e) {
                    e.printStackTrace();
                }
            }
        }
        return BaseResponse.success(repeatPaidOrderIds);
    }

    @Override
    public int updateOrderPackStateToPending(Long id) {
        return orderDao.updateOrderPackStateToPending(id);
    }

    @Override
    public int updateOrderWaveRelease(Integer waveNo) {
        return orderDao.updateOrderWaveRelease(waveNo);
    }

    @Override
    public List<Order> selectByWaveNo(Integer waveNo) {
        Page<Order> page = new Page<>();
        Order order = new Order();
        order.setWaveNo(waveNo);
        page.setT(order);
        page.setPageSize(-1);
        List<Order> orders = select(page);
        return orders;
    }

    @Override
    public BaseResponse updateActualShipMethod(OrderUpdateActualShipMethodRequest request, Session session) {
        Long actualMethodId = request.getShipMethodId();
        Long orderId = request.getOrderId();
        Order order = selectByPrimaryKey(request.getOrderId());
        if (order.getPayState() != 1
        || order.getRefundState() != 0
        || order.getShipState() != 0){
            return BaseResponse.failed();
        }
        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,actualMethodId.toString());
        if (null == shippingMethodRedis){
            return BaseResponse.failed("运输方式错误");
        }
        orderDao.updateActualShipMethodById(request.getOrderId(),actualMethodId);

        orderPackageService.reCreatePackage(orderId);
        return BaseResponse.success();
    }

    @Override
    public int updateOrderAsTracked(Long id, String trackNum,Integer trackingCodeType) {
        return orderDao.updateOrderAsTracked(id, trackNum,trackingCodeType);
    }

    @Override
    public int initPickType(Long id) {
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        if (ListUtils.isEmpty(orderItems)){
            return 0;
        }
        Integer pickType = 0;
        if (orderItems.size() > 1){
            Set<Long> adminVariantIds = new HashSet<>();
            for (OrderItem orderItem : orderItems) {
                adminVariantIds.add(orderItem.getAdminVariantId());
            }
            if (adminVariantIds.size() > 1){
                pickType = 2;
            }else {
                pickType = 1;
            }

        }else {
            int totalQuantity = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
            if (totalQuantity > 1){
                pickType = 1;
            }else {
                pickType = 0;
            }
        }
        orderDao.updatePickType(id,pickType);
        return 1;
    }

    @Override
    public int updateOrderPackInfo(Long id, Integer packageState, Long packNo) {
        return orderDao.updateOrderPackInfo(id, packageState, packNo);
    }

    @Override
    public List<Order> selectByIds(List<Long> ids) {
        if (ListUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return orderDao.selectByIds(ids);
    }

    @Override
    public List<OrderItemQuantityVo> selectOrderItemQuantities(OrderItemQuantityDto orderItemQuantityDto) {
        if (null == orderItemQuantityDto){
            return null;
        }
        if (orderItemQuantityDto.getVariantId() != null){
            List<Long> orderIds = orderItemDao.selectOutStockOrderIdsByVariantId(orderItemQuantityDto.getVariantId());
            if (ListUtils.isEmpty(orderIds)){
                return null;
            }
            orderItemQuantityDto.setOrderIds(orderIds);
        }
        return orderDao.selectOrderItemQuantities(orderItemQuantityDto);
    }

    @Override
    public OrderItemQuantityVo selectOrderItemQuantitiesByOrderId(Long orderId) {
        return orderDao.selectOrderItemQuantitiesByOrderId(orderId);
    }

    @Override
    public int updateShipState(Long id, Integer shipState) {
        return orderDao.updateShipState(id, shipState);
    }

    @Override
    public int updateOrderPickState(List<Long> orderIds, Integer state,Integer waveNo) {

        if(ListUtils.isNotEmpty(orderIds)){
            return orderDao.updateOrderPickState(orderIds,state,waveNo);
        }
        return 0;
    }

    @Override
    public void updatePickType(Long id, Integer pickType) {
        orderDao.updatePickType(id, pickType);
    }

    @Override
    public int updateStockState(Long id, Integer stockState) {
//        Order order = selectByPrimaryKey(id);
//        if (order != null && order.getStockState() == stockState){
//            return 1;
//        }
        return orderDao.updateStockState(id, stockState);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int updateStockState(Long id, List<ItemQuantityVo> itemQuantityVos) {
        int stockState = 1;
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        if(ListUtils.isEmpty(orderItems)){
            return 0;
        }
        List<OrderItem> updateItems = new ArrayList<>();
        a:
        for (OrderItem orderItem : orderItems) {
            Integer unLockQuantity = orderItem.getQuantity() - orderItem.getLockedQuantity();
            for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
                if (itemQuantityVo.getItemId().equals(orderItem.getId())){
                    Integer lockQuantity = itemQuantityVo.getLockQuantity();
                    if (unLockQuantity == 0 && lockQuantity == 0){
                        //do nothing
                    }else {
                        if (lockQuantity > unLockQuantity){//分配库存数量＞所需数量
                            return 0;
                        }
                        if (lockQuantity < unLockQuantity){//分配库存数量＜所需数量  订单状态修改为缺货
                            stockState = 0;
                        }
                    }
                    orderItem.setLockedQuantity(lockQuantity);
                    updateItems.add(orderItem);
                    itemQuantityVos.remove(itemQuantityVo);
                    continue a;
                }
            }
            return 0;
        }
        if (itemQuantityVos.size() != 0){//库存分配异常
            log.warn("库存分配异常:{}",itemQuantityVos.toString());
        }
        updateStockState(id,stockState);
        if (ListUtils.isNotEmpty(updateItems)){
            orderItemDao.increaseLockQuantity(updateItems);
        }
        return 1;
    }

    @Override
    public BaseResponse updateOrderShippingWarehouse(Long shipMethodId) {

        ShippingMethodRedis shippingMethodVo = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, String.valueOf(shipMethodId));
        if (null == shippingMethodVo) {
            return BaseResponse.failed();
        }
        orderDao.updateWarehouseByMethodId(shipMethodId, shippingMethodVo.getWarehouseCode());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse orderAddItem(OrderAddItemRequest request, Session session) {
        Long orderId = request.getOrderId();
        Order order = selectByPrimaryKey(orderId);
        if (null == order
                || order.getPayState() != OrderConstant.PAY_STATE_UNPAID
                || order.getOrderType() > 2) {
            return BaseResponse.failed();
        }
        List<OrderItem> items = orderItemDao.selectItemByOrderId(orderId);

        List<OrderItem> addItems = new ArrayList<>();
        OrderItem item = items.get(0);
        List<OrderItem> orderItems = request.getItems();
        a:
        for (OrderItem orderItem : orderItems) {
            b:
            for (OrderItem oItem : items) {
                if (oItem.getStoreVariantId().equals(orderItem.getStoreVariantId())) {
                    orderItemDao.increaseQuantityById(oItem.getId(), orderItem.getQuantity());
                    continue a;
                }
            }
            orderItem.setStoreOrderItemId(0L);
            orderItem.setStoreOrderId(item.getStoreOrderId());
            orderItem.setId(IdGenerate.nextId());
            orderItem.setOrderId(orderId);
            orderItem.setOriginalQuantity(1);
            orderItem.setQuoteScale(1);
            orderItem.setDischargeQuantity(0);
            orderItem.setStoreProductTitle(orderItem.getStoreVariantName());
            Long storeVariantId = orderItem.getStoreVariantId();
            CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + storeVariantId);
            if (null == customerProductQuoteVo) {
                List<Long> splitVariantIds = redisTemplate.opsForList().range(RedisKey.LIST_QUOTING_STORE_VARIANT, 0, -1);
                if (splitVariantIds != null && splitVariantIds.contains(storeVariantId)) {
                    orderItem.setQuoteState(OrderItem.QUOTE_STATE_QUOTING);
                } else {
                    orderItem.setQuoteState(OrderItem.QUOTE_STATE_UNQU0TED);
                }
            } else {
                //报价成功，未报价订单改为部分报价
                if (customerProductQuoteVo.getQuoteState() == 1) {
                    orderItem.quoteProductToItem(customerProductQuoteVo);
                    //报价失败，已报价订单改为部分报价
                } else {
                    orderItem.setQuoteState(OrderItem.QUOTE_STATE_NO_STOCK);
                }
            }
            orderItem.setItemType(1);
            addItems.add(orderItem);
        }
        if (ListUtils.isNotEmpty(addItems)) {
            orderItemService.insertByBatch(addItems);
        }
        return BaseResponse.success();
    }

    @Override
    public List<Long> selectUploadSaiheFailedIds() {
        return orderDao.selectUploadSaiheFailedIds();
    }

    @Override
    public List<Long> selectUnPaidIdsByShipRule(OrderShipRuleVo shipRuleVo, Long areaId) {

        if (shipRuleVo == null
                || areaId == null) {
            return null;
        }
        return orderDao.selectUnPaidIdsByShipRule(shipRuleVo, areaId);
    }

    @Override
    public BaseResponse orderCancelUploadSaihe(List<Long> orderIds) {
        if (ListUtils.isEmpty(orderIds)){
            return BaseResponse.failed();
        }
        orderDao.orderCancelUploadSaihe(orderIds);
        return BaseResponse.success();
    }

    @Override
    public int cancelOrderByIds(List<Long> ids) {
        if (ListUtils.isEmpty(ids)) {
            return 0;
        }
        return orderDao.cancelOrderByIds(ids);
    }

    @Override
    public void initShipByShipUnitId(Long shipUnitId) {
        List<Long> orderIds = orderDao.selectUnpaidOrderIdsByShipUnitId(shipUnitId);
        if (ListUtils.isEmpty(orderIds)){
            return;
        }

        if (shipUnitId != null) {
            orderDao.initShipByShipUnitId(shipUnitId);
            orderShippingUnitService.deleteByShipUnitId(shipUnitId);
        }
        for (Long orderId : orderIds) {
            matchShipRule(orderId);
        }
    }

    @Override
    public int updateOrderVatAmountByAreaId(List<Long> areaIds, BigDecimal vatAmount) {
        if (ListUtils.isNotEmpty(areaIds)
                && vatAmount != null) {
            return orderDao.updateOrderVatAmountByAreaId(areaIds, vatAmount);
        }
        return 0;
    }

    @Transactional
    @Override
    public BaseResponse createReshipOrder(CreateReshipOrderRequest request, Session session) {
        Long id = request.getOrderId();
        List<Long> itemIds = request.getItemIds();
        Order order = orderDao.selectByPrimaryKey(id);
        if (order == null || order.getOrderType() == 1) {
            return BaseResponse.failed("订单不存在或补发订单不能补发");
        }
        if (session.getApplicationId().equals(Constant.APP_APPLICATION_ID)){
            if (!session.getCustomerId().equals(order.getCustomerId())){
                return BaseResponse.failed();
            }
        }
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        Long reshipOrderId = IdGenerate.nextId();
        Order reshipOrder = new Order();
        BeanUtils.copyProperties(order, reshipOrder);
        reshipOrder.initOrder();

        reshipOrder.setOrderType(1);
        ShipDetail shipDetail = request.getShipDetail();
        if (null != shipDetail
                && shipDetail.isCouldShip()) {
            reshipOrder.setActualShipMethodId(shipDetail.getMethodId());
            reshipOrder.setShipMethodId(shipDetail.getMethodId());
            reshipOrder.setShipPrice(shipDetail.getPrice().subtract(shipDetail.getServiceFee()));
            reshipOrder.setServiceFee(shipDetail.getServiceFee());
            reshipOrder.setShippingWarehouse(shipDetail.getWarehouseCode());
            reshipOrder.setTotalWeight(shipDetail.getWeight());
            if (!request.getNeedPay() && session.getApplicationId().equals(Constant.ADMIN_APPLICATION_ID)) {
                reshipOrder.setPayState(1);
                reshipOrder.setPayTime(new Date());
                reshipOrder.setPayMethod(0);
            }
        } else {
            if (request.getNeedPay()) {
                return BaseResponse.failed("需要支付的订单需选择运输方式");
            }
        }
        reshipOrder.setQuoteState(order.getQuoteState());
        reshipOrder.setId(reshipOrderId);
        reshipOrder.setCnyProductAmount(order.getCnyProductAmount());
        reshipOrder.setProductAmount(order.getProductAmount());
        reshipOrder.setTotalWeight(order.getTotalWeight());

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
        List<OrderItem> reshipOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (!itemIds.contains(orderItem.getId())) {
                continue;
            }
            OrderItem reshipOrderItem = new OrderItem();
            BeanUtils.copyProperties(orderItem, reshipOrderItem);
            if (order.getOrderType() != 4){
                CustomerProductQuoteVo customerProductQuoteVo = map.get(orderItem.getStoreVariantId());
                if (null == customerProductQuoteVo || customerProductQuoteVo.getQuoteState() != 1) {
                    return BaseResponse.failed("sku: " + orderItem.getStoreVariantSku() + " 报价信息不存在");
                }
                reshipOrderItem.quoteProductToItem(customerProductQuoteVo);
            }

            reshipOrderItem.setDischargeQuantity(0);
            reshipOrderItem.setOrderId(reshipOrderId);
            reshipOrderItem.setId(IdGenerate.nextId());

            reshipOrderItem.setQuantity(orderItem.getQuantity());
            reshipOrderItems.add(reshipOrderItem);

            BigDecimal itemQuantity = new BigDecimal(orderItem.getQuantity());
            productAmount = productAmount.add(reshipOrderItem.getUsdPrice().multiply(itemQuantity));
            cnyProductAmount = cnyProductAmount.add(reshipOrderItem.getCnyPrice().multiply(itemQuantity));
            totalWeight = totalWeight.add(reshipOrderItem.getAdminVariantWeight().multiply(itemQuantity));
        }
        if (ListUtils.isEmpty(reshipOrderItems)) {
            return BaseResponse.failed("产品信息异常");
        }
        reshipOrder.setCnyProductAmount(cnyProductAmount);
        reshipOrder.setProductAmount(productAmount);
        reshipOrder.setTotalWeight(totalWeight);

        orderDao.insert(reshipOrder);
        orderItemService.insertByBatch(reshipOrderItems);

        OrderAddress orderAddress = orderAddressDao.selectByOrderId(id);
        orderAddress.setOrderId(reshipOrderId);
        orderAddress.setId(IdGenerate.nextId());
        orderAddressDao.insert(orderAddress);

        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(id);
        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            storeOrderRelate.setOrderId(reshipOrderId);
            storeOrderRelate.setOrderCreateTime(new Date());
            storeOrderRelate.setId(null);
            storeOrderRelateDao.insert(storeOrderRelate);
        }


        OrderReshipInfo orderReshipInfo = new OrderReshipInfo();
        orderReshipInfo.setOrderId(reshipOrderId);
        orderReshipInfo.setOriginalOrderId(id);
        orderReshipInfo.setReason(request.getReshipReason());
        orderReshipInfo.setReshipTimes(1);
        orderReshipInfo.setNeedPay(request.getNeedPay());
        orderReshipInfo.setCreatorId(session.getId());
        orderReshipInfo.setCreateApplicationId(session.getApplicationId());
        orderReshipInfoDao.insert(orderReshipInfo);
        if (session.getApplicationId().equals(Constant.APP_APPLICATION_ID)){
            redisTemplate.opsForList().leftPush(RedisKey.HASH_ORDER_APP_CREATE_RESHIP_APPLICATION,reshipOrderId);
        }
        if (null != shipDetail
                && shipDetail.isCouldShip()
                && shipDetail.getWarehouseCode().equals(ProductConstant.DEFAULT_WAREHOUSE_ID)) {
            updateOrderShipUnit(reshipOrderId, request.getShipUnitId());
        }
        return BaseResponse.success(reshipOrder, request);
    }

    @Override
    public void initQuoteState(Long id) {
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(id);
        Integer quoteProducts = 0;
        Integer quoteState = 0;
        Integer quoteFailed = 0;
        if (ListUtils.isNotEmpty(orderItems)) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getQuantity() == 0){
                    quoteProducts++;
                    continue;
                }
                if (orderItem.getQuoteState() == OrderItem.QUOTE_STATE_QUOTING) {
                    quoteState = OrderConstant.QUOTE_STATE_QUOTING;
                    break;
                }
                if (orderItem.getQuoteState() == OrderItem.QUOTE_STATE_QUOTED
                        || orderItem.getQuoteState() == OrderItem.QUOTE_STATE_UPEDGE) {
                    quoteProducts++;
                } else {
                    quoteFailed++;
                }
            }
        }
        if (quoteState != OrderConstant.QUOTE_STATE_QUOTING) {
            if (quoteFailed == orderItems.size()) {
                quoteState = OrderConstant.QUOTE_STATE_PART_UNQUOTED;
            } else {
                if (quoteProducts == 0) {
                    quoteState = OrderConstant.QUOTE_STATE_UNQUOTED;
                } else if (quoteProducts == orderItems.size()) {
                    quoteState = OrderConstant.QUOTE_STATE_QUOTED;
                } else {
                    quoteState = OrderConstant.QUOTE_STATE_PART_UNQUOTED;
                }
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
    public void updateSaiheOrderCode(String id, String saiheOrderCode) {
        orderDao.updateSaiheOrderCode(id, saiheOrderCode);
    }

    @Override
    public int initOrderProductAmount(List<Long> orderIds) {
        return orderDao.initProductAmountById(orderIds);
    }

    @Async
    @Override
    public void initOrderVatAmountByAreaId(Long areaId,Long customerId) {
        List<Order> orders = orderDao.selectUnPaidOrderByAreaId(areaId,customerId);
        for (Order order : orders) {
            BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), order.getShipPrice(), order.getToAreaId(), order.getCustomerId());
            if (vatAmount.compareTo(order.getVatAmount()) != 0) {
                orderDao.updateOrderVatAmountById(order.getId(), vatAmount);
            }
        }
    }

    @Override
    public BaseResponse revokeReshipOrder(Long orderId, Session session) {
        Order order = selectByPrimaryKey(orderId);
        if (order == null
                || order.getOrderType() != 1) {
            return BaseResponse.failed();
        }
        OrderReshipInfo orderReshipInfo = orderReshipInfoDao.selectByPrimaryKey(orderId);
        if (!orderReshipInfo.getNeedPay() || order.getPayState() < 1) {
            try {
                revokeSaiheOrder(order.getSaiheOrderCode());
            } catch (CustomerException e) {
                e.printStackTrace();
                return BaseResponse.failed(e.getMessage());
            }
            orderDao.deleteReshipOrder(orderId);
            return BaseResponse.success();
        }
        return BaseResponse.failed("订单已支付，请申请退款");
    }

    public ShipDetail updateShipDetailById(Long id, ShipDetail shipDetail) {
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + shipDetail.getWarehouseCode());
        if (null == warehouseVo) {
            return null;
        }
        orderAttrDao.deleteByOrderIdAndName(id, OrderAttrEnum.SHIP_RULE_ID.name());
        Order order = orderDao.selectByPrimaryKey(id);
        // 删除原来的unit  并插入新的冗余
        orderShippingUnitService.delByOrderId(id, OrderType.NORMAL);
        //本地仓更新orderShipUnit
        if (warehouseVo.getWarehouseType() == WarehouseVo.LOCAL) {
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
        }
        //vat
        BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), order.getShipPrice(), order.getToAreaId(), order.getCustomerId());
        shipDetail.setVatAmount(vatAmount);
        orderDao.updateShipDetailById(shipDetail, id);
        shipDetail.setPrice(shipDetail.getPrice().add(shipDetail.getServiceFee()));
        return shipDetail;
    }

    @Override
    public ShipDetail orderInitShipDetail(Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (null == order
                || order.getPayState() != OrderConstant.PAY_STATE_UNPAID
                || order.getQuoteState() != OrderConstant.QUOTE_STATE_QUOTED) {
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
            orderDao.updateOrderShipMethod(orderId);
        } else {
            return shipRuleDetail.getShipDetail();
        }
        return null;
    }

    @Transactional
    @Override
    public ShipDetail orderInitShipDetail(Long orderId, String warehouseCode) {

        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + warehouseCode);
        if (warehouseVo == null
                || warehouseVo.getWarehouseType() == WarehouseVo.LOCAL) {

        }

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
            orderVo.setShipPrice(order.getShipPrice().add(order.getServiceFee()));
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

        orderItemService.insertByBatch(orderItems);
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
        AppOrderListDto appOrderListDto = request.getT();
        if (appOrderListDto != null
                && StringUtils.isNotBlank(appOrderListDto.getTags())
                && appOrderListDto.getTags().equals("REFUNDS")) {
            request.setCondition("o.refund_state > 0");
        }
        List<AppOrderVo> appOrderVos = selectAppOrderList(request);
        appOrderVos.forEach(appOrderVo -> {
            if (appOrderVo.getPackState() == 2){
                String reason = (String) redisTemplate.opsForHash().get(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,appOrderVo.getId().toString());
                appOrderVo.setCreatePackFailedReason(reason);
            }
            Long waveNo = appOrderVo.getWaveNo();
            if (null != waveNo){
                boolean b = redisTemplate.opsForHash().hasKey(RedisKey.HASH_ORDER_PICK_WAVE_PRINTED,waveNo.toString());
                appOrderVo.setIsPrintPick(b);
            }else {
                appOrderVo.setIsPrintPick(false);
            }
            Set<AppStoreOrderVo> appStoreOrderVos = appOrderVo.getStoreOrderVos();
            if (appOrderVo.getPayState() == 1){
                Long actualShipMethodId = appOrderVo.getActualShipMethodId();
                if (actualShipMethodId != null){
                    ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,actualShipMethodId.toString());
                    if (shippingMethodRedis != null){
                        appOrderVo.setActualShipMethodName(shippingMethodRedis.getDesc());
                    }
                }

                appStoreOrderVos.forEach(appStoreOrderVo -> {
                    List<AppOrderItemVo> itemVos = appStoreOrderVo.getItemVos();
                    itemVos.forEach(appOrderItemVo -> {
                        if(appOrderItemVo.getAdminVariantId() != null){
                            VariantWarehouseStockModel variantWarehouseStockModel = (VariantWarehouseStockModel) redisTemplate.opsForHash().get(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK + appOrderVo.getShippingWarehouse(),appOrderItemVo.getAdminVariantId().toString());
                            if (variantWarehouseStockModel != null){
                                appOrderItemVo.setAvailableStock(variantWarehouseStockModel.getAvailableStock());
                            }
                        }
                    });
                });
            }
        });
        appOrderVos = completePackageInfo(appOrderVos);
        Long total = selectAppOrderCount(request);
        request.setTotal(total);
        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appOrderVos, request);
    }

    public List<AppOrderVo> completePackageInfo(List<AppOrderVo> appOrderVos){
        if (ListUtils.isEmpty(appOrderVos)){
            return appOrderVos;
        }
        List<Long> orderIds = new ArrayList<>();
        appOrderVos.forEach(appOrderVo -> {
            orderIds.add(appOrderVo.getId());
        });
        List<OrderPackage> orderPackages = orderPackageService.selectByOrderIds(orderIds);
        a:
        for (AppOrderVo appOrderVo : appOrderVos) {
            for (OrderPackage orderPackage : orderPackages) {
                if (appOrderVo.getId().equals(orderPackage.getOrderId())){
                    appOrderVo.setPackageInfo(orderPackage);
                    orderPackages.remove(orderPackage);
                    continue a;
                }
            }
        }
        return appOrderVos;
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
//        if (!ifUploadSaihe) {
//            return false;
//        }
        String key = "order:upload:saihe:" + id;
        boolean b = RedisUtil.lock(redisTemplate, key, 10L, 20 * 1000L);
        if (!b) {
            return false;
        }
        /**
         * 获取 已支付，未发货，订单状态正常，未退款
         * 并且没有赛盒orderCode的赛盒订单信息
         */
        Order order = selectByPrimaryKey(id);
        if (order.getPayState() != 1
        || order.getShipState() != 0
        || order.getRefundState() != 0
        || order.getOrderStatus() != 0
        || (order.getSaiheOrderCode() != null && !order.getSaiheOrderCode().equals("0"))){
            return false;
        }

        SaiheOrder saiheOrder = orderDao.querySaiheOrder(id);
        if (null == saiheOrder
                || saiheOrder.getCustomerId().equals(1499564543207194625L)) {
            return false;
        }
        /**
         * 该订单是否在赛盒已经上传了，不用再回传
         * orderCode在数据库中未有，保存信息
         */
        Boolean isUpload = orderCommonService.checkAndSaveOrderCodeFromSaihe(saiheOrder.getClientOrderCode(), OrderType.NORMAL);
        if (isUpload) {
            RedisUtil.unLock(redisTemplate, key);
            return true;
        }
        /**
         * 查询该订单的订单产品
         */
        List<SaiheOrderItem> orderItemList = orderItemDao.listSaiheOrderItemByOrderId(id);
        for (SaiheOrderItem saiheOrderItem : orderItemList) {
            if (saiheOrderItem.getProductNum() == null
                    || saiheOrderItem.getSalePrice() == null) {
                RedisUtil.unLock(redisTemplate, key);
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
        b = orderCommonService.upLoadOrderToSaiHe(saiheOrder, OrderType.NORMAL);
        RedisUtil.unLock(redisTemplate, key);
        return b;
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
    public List<PandaOrderListVo> upedgeOrderPage(Page<OrderListDto> record) {
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
    public Long upedgeOrderCount(Page<OrderListDto> record) {
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
    public List<Order> getOrderList(OrderListDto dto) {

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
    public void orderInitShipDetailByIds(List<Long> ids) {
        for (Long id : ids) {
            orderInitShipDetail(id);
        }
    }


    private ShippingTemplateRedis getOrderShipTemplate(Long orderId) {
        ShippingTemplateRedis shippingTemplateRedis = null;
        List<OrderItem> orderItems = orderItemDao.selectItemByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            ShippingTemplateRedis templateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(orderItem.getShippingId()));
            if (null != templateRedis) {
                if (null == shippingTemplateRedis) {
                    shippingTemplateRedis = templateRedis;
                } else {
                    if (templateRedis.getSeq() < shippingTemplateRedis.getSeq()) {
                        shippingTemplateRedis = templateRedis;
                    }
                }
            }
        }
        return shippingTemplateRedis;
    }


    @Override
    public ApiOrderInfo revokeSaiheOrder(String saiheOrderCode) throws CustomerException {
        if (StringUtils.isBlank(saiheOrderCode)) {
            return null;
        }
        ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(saiheOrderCode);
        if (!apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
            throw new CustomerException(ResultCode.FAIL_CODE, apiGetOrderResponse.getGetOrdersResult().getMsg());
        }
        List<ApiOrderInfo> apiOrderInfos = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
        if (ListUtils.isEmpty(apiOrderInfos)) {
            return null;
        }
        ApiOrderInfo apiOrderInfo = apiOrderInfos.get(0);
        //赛盒发货状态 orderState 订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
        //订单状态OrderStatus(正常 = 0,待审查 = 1,作废 = 2,锁定 = 3,只锁定发货 = 4,已完成 = 5)
        Integer orderState = apiOrderInfo.getOrderState();
        Integer orderStatus = apiOrderInfo.getOrderStatus();
        //赛盒未发货
        if (orderState == 0) {
            //订单已作废
            if (orderStatus == 2) {
                //message.append("赛盒未发货,订单已作废!</br> ");
            } else {
                //作废订单
                //作废成功
                ApiCancelOrderResponse apiCancelOrderResponse = SaiheService.cancelOrderInfo(saiheOrderCode);
                if (apiCancelOrderResponse.getCancelOrderResult().getStatus().equals("OK") &&
                        apiCancelOrderResponse.getCancelOrderResult().getSuccess()) {
                    //message.append("赛盒未发货,订单作废成功!</br> ");
                } else {
                    log.warn("赛盒订单号:{},作废失败原因:{}", saiheOrderCode, apiCancelOrderResponse.getCancelOrderResult().getMsg());
                    //作废失败
                    //throw newValidationException("赛盒未发货,订单作废失败!</br> ");
                    throw new CustomerException(ResultCode.FAIL_CODE, apiCancelOrderResponse.getCancelOrderResult().getMsg());
                }
            }
        }
        return apiOrderInfo;


    }

    @Override
    public void addNewStoreOrderItem(StoreOrder storeOrder, List<StoreOrderItem> storeOrderItems) {
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByStoreOrderId(storeOrder.getId());
        if (ListUtils.isEmpty(storeOrderRelates)) {
            return;
        }
        List<OrderItem> orderItems = new ArrayList<>();
        boolean newOrder = true;
        Long orderId = IdGenerate.nextId();
        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            Order order = selectByPrimaryKey(storeOrderRelate.getOrderId());
            if (order.getPayState() == OrderConstant.PAY_STATE_PAID
                    || order.getOrderType() != 0) {
                continue;
            }
            newOrder = false;
            orderId = order.getId();
        }
        if(newOrder){
            return;
        }
        Integer quoteProducts = 0;
        Integer quoteState = 0;
        Integer quoteFailed = 0;
        for (StoreOrderItem item : storeOrderItems) {
            Long storeVariantId = item.getStoreVariantId();
            if (storeVariantId == null) {
                continue;
            }
            List<Long> splitVariantIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_STORE_SPLIT_VARIANT, String.valueOf(storeVariantId));
            if (ListUtils.isNotEmpty(splitVariantIds)) {
                //判断拆分的变体是否已报价
                for (Long splitVariantId : splitVariantIds) {
                    CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + splitVariantId);
                    if (customerProductQuoteVo == null) {
                        continue;
                    }
                    if (customerProductQuoteVo.getQuoteScale() == null) {
                        customerProductQuoteVo.setQuoteScale(1);
                    }
                    BigDecimal itemQuantity = new BigDecimal(item.getQuantity()).multiply(new BigDecimal(customerProductQuoteVo.getQuoteScale()));
                    OrderItem orderItem = new OrderItem();
                    BeanUtils.copyProperties(item, orderItem);
                    orderItem.setQuantity(itemQuantity.intValue());
                    orderItem.setOriginalQuantity(item.getQuantity());
                    orderItem.quoteProductToItem(customerProductQuoteVo);
                    orderItem.setStoreVariantId(customerProductQuoteVo.getStoreVariantId());
                    orderItem.setStoreProductId(customerProductQuoteVo.getStoreProductId());
                    orderItem.setStoreVariantSku(customerProductQuoteVo.getStoreVariantSku());
                    orderItem.setStoreVariantName(customerProductQuoteVo.getStoreVariantName());
                    orderItem.setStoreVariantImage(customerProductQuoteVo.getStoreVariantImage());
                    orderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                    orderItem.setQuoteScale(customerProductQuoteVo.getQuoteScale());

                    orderItem.setOrderId(orderId);
                    orderItem.setStoreOrderItemId(item.getId());
                    orderItem.setDischargeQuantity(0);
                    orderItem.setItemType(2);
                    orderItem.setId(IdGenerate.nextId());
                    orderItems.add(orderItem);
                }
                OrderItem orderItem = new OrderItem();
                BeanUtils.copyProperties(item, orderItem);
                orderItem.setOriginalQuantity(item.getQuantity());
                continue;
            }

            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(item, orderItem);
            orderItem.setOriginalQuantity(item.getQuantity());
            CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + storeVariantId);
            if (customerProductQuoteVo != null) {
                if (customerProductQuoteVo.getQuoteType() == 5) {
                    //报价中
                    quoteState = Order.QUOTE_QUOTING;
                    orderItem.setQuoteState(5);
                } else if (customerProductQuoteVo.getQuoteState() == 0) {
                    //产品报价失败
                    quoteFailed ++;
                    orderItem.setQuoteState(4);
                } else {
                    quoteProducts++;
                    //报价成功
                    if (customerProductQuoteVo.getQuoteScale() == null) {
                        customerProductQuoteVo.setQuoteScale(1);
                    }
                    BigDecimal itemQuantity = new BigDecimal(item.getQuantity()).multiply(new BigDecimal(customerProductQuoteVo.getQuoteScale()));
                    orderItem.setQuantity(itemQuantity.intValue());
                    orderItem.quoteProductToItem(customerProductQuoteVo);
                    orderItem.setQuoteState(customerProductQuoteVo.getQuoteType());
                    orderItem.setQuoteScale(customerProductQuoteVo.getQuoteScale());
                }
            } else {
                orderItem.setQuoteState(0);
            }
            if (null == orderItem.getQuoteScale()) {
                orderItem.setQuoteScale(1);
            }
            orderItem.setOriginalQuantity(item.getQuantity());
            orderItem.setOrderId(orderId);
            orderItem.setStoreOrderItemId(item.getId());
            orderItem.setDischargeQuantity(0);
            orderItem.setItemType(0);
            orderItem.setId(IdGenerate.nextId());
            orderItems.add(orderItem);
        }
//        if (newOrder){
//            OrderAddress orderAddress = new OrderAddress();
//            StoreOrderAddress storeOrderAddress = storeOrder.getAddress();
//            BeanUtils.copyProperties(storeOrderAddress,orderAddress);
//            orderAddress.setOrderId(orderId);
//            orderAddress.setId(IdGenerate.nextId());
//            orderAddressDao.insert(orderAddress);
//
//            Order nOrder = new Order();
//            BeanUtils.copyProperties(storeOrder,nOrder);
//            nOrder.initOrder();
//            nOrder.setTotalWeight(BigDecimal.ZERO);
//            nOrder.setProductAmount(BigDecimal.ZERO);
//            nOrder.setOrderType(0);
//            if (orderAddress.getCountry() != null) {
//                nOrder.setToAreaId((Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, orderAddress.getCountry()));
//            }
//            nOrder.setId(orderId);
//
//            if (quoteState != OrderConstant.QUOTE_STATE_QUOTING) {
//                if (quoteFailed == orderItems.size()) {
//                    quoteState = OrderConstant.QUOTE_STATE_PART_UNQUOTED;
//                } else {
//                    if (quoteProducts == 0) {
//                        quoteState = OrderConstant.QUOTE_STATE_UNQUOTED;
//                    } else if (quoteProducts == orderItems.size()) {
//                        quoteState = OrderConstant.QUOTE_STATE_QUOTED;
//                    } else {
//                        quoteState = OrderConstant.QUOTE_STATE_PART_UNQUOTED;
//                    }
//                }
//            }
//            nOrder.setQuoteState(quoteState);
//            insert(nOrder);
//
//            StoreOrderRelate storeOrderRelate = new StoreOrderRelate(storeOrder);
//            storeOrderRelate.setOrderId(orderId);
//            storeOrderRelate.setOrderCreateTime(new Date());
//            storeOrderRelate.setStoreName(storeOrder.getStoreName());
//            if (null != orderAddress) {
//                storeOrderRelate.setOrderCustomerName(orderAddress.getFirstName() + " " + orderAddress.getLastName());
//            }
//            storeOrderRelateDao.insert(storeOrderRelate);
//        }
        orderItemService.insertByBatch(orderItems);
        platformTransactionManager.commit(transaction);
        initQuoteState(orderId);
//        matchShipRule(orderId);
        return;
    }


}