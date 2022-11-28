package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.constant.OrderActionType;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import com.upedge.oms.modules.order.entity.*;
import com.upedge.oms.modules.order.request.AppOrderListRequest;
import com.upedge.oms.modules.order.request.MergeOrderRequest;
import com.upedge.oms.modules.order.request.SplitNormalOrderRequest;
import com.upedge.oms.modules.order.request.SplitNormalOrderRequest.OrderSplitModule;
import com.upedge.oms.modules.order.request.SplitNormalOrderRequest.OrderSplitModule.ItemQuantity;
import com.upedge.oms.modules.order.service.OrderActionService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AppOrderItemVo;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.order.vo.SameAddressOrderVo;
import com.upedge.oms.modules.vat.service.VatRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderActionServiceImpl implements OrderActionService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderAddressDao orderAddressDao;

    @Autowired
    OrderActionLogDao orderActionLogDao;

    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;

    @Autowired
    StoreOrderAddressDao storeOrderAddressDao;

    @Autowired
    StoreOrderDao storeOrderDao;

    @Autowired
    OrderService orderService;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    OrderReshipInfoDao orderReshipInfoDao;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Override
    public List<SameAddressOrderVo> selectSameAddressOrderByStore(Long customerId) {
        List<SameAddressOrderVo> sameAddressOrderVoList = orderAddressDao.selectSameAddressOrderByStore(customerId);
        if (null == sameAddressOrderVoList) {
            sameAddressOrderVoList = new ArrayList<>();
        } else {
            Iterator<SameAddressOrderVo> iterator = sameAddressOrderVoList.iterator();
            while (iterator.hasNext()) {
                SameAddressOrderVo sameAddressOrderVo = iterator.next();
                if (ListUtils.isEmpty(sameAddressOrderVo.getOrderIds()) || sameAddressOrderVo.getOrderIds().size() < 2) {
                    iterator.remove();
                } else {
                    AppOrderListRequest request = new AppOrderListRequest();
                    request.setT(new AppOrderListDto());
                    request.getT().setOrderIds(sameAddressOrderVo.getOrderIds());
                    request.setPageSize(-1);
                    List<AppOrderVo> appOrderVos = orderService.selectAppOrderList(request);
                    if (ListUtils.isNotEmpty(appOrderVos)) {
                        sameAddressOrderVo.setAppOrderVos(appOrderVos);
                    } else {
                        iterator.remove();
                    }
                }
            }
            if (null == sameAddressOrderVoList) {
                sameAddressOrderVoList = new ArrayList<>();
            }
        }
        return sameAddressOrderVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String splitNormalOrder(Long orderId, SplitNormalOrderRequest request) throws CustomerException {
        List<OrderSplitModule> orderSplitModules = request.getOrderSplitModules();
        if (ListUtils.isEmpty(orderSplitModules)
                || orderSplitModules.size() < 2) {
            return Constant.MESSAGE_FAIL;
        }

        Order order = orderDao.selectByPrimaryKey(orderId);

        if (null == order || order.getPayState() != 0 || order.getOrderType() > 1) {
            return "Only unpaid ordinary orders can be combined";
        }
        OrderAddress orderAddress = orderAddressDao.selectByOrderId(orderId);
        if (orderAddress == null) {
            orderAddress = new OrderAddress();
            orderAddress.setOrderId(orderId);
            orderAddress.setId(IdGenerate.nextId());
        }
        StoreOrderRelate storeOrderRelate = storeOrderRelateDao.selectByOrderId(orderId).get(0);

        Map<Long, OrderItem> itemMap = new HashMap<>();

        List<OrderActionLog> actionLogs = new ArrayList<>();

        List<Order> orders = new ArrayList<>();

        List<OrderItem> orderItems = new ArrayList<>();

        List<OrderAddress> orderAddresses = new ArrayList<>();

        List<StoreOrderRelate> storeOrderRelates = new ArrayList<>();

        Date date = new Date();

        for (OrderSplitModule module : orderSplitModules) {
            Order newOrder = new Order();
            Long newOrderId = IdGenerate.nextId();
            BigDecimal productAmount = BigDecimal.ZERO;
            BigDecimal cnyProductAmount = BigDecimal.ZERO;
            List<ItemQuantity> itemQuantities = module.getItemQuantities();
            Integer totalQuantity = 0;
            for (ItemQuantity itemQuantity : itemQuantities) {
                Long itemId = itemQuantity.getItemId();
                OrderItem orderItem = null;
                if (itemMap.containsKey(itemId)) {
                    orderItem = itemMap.get(itemId);
                } else {
                    orderItem = orderItemDao.selectByPrimaryKey(itemId);
                    itemMap.put(itemId, orderItem);
                }
                if (null == orderItem) {
                    continue;
                }
                Long newItemId = IdGenerate.nextId();
                OrderActionLog actionLog = new OrderActionLog();
                actionLog.setActionType(OrderActionType.SPLIT_NORMAL_ORDER);
                actionLog.setCreateTime(date);
                actionLog.setNewItemId(newItemId);
                actionLog.setNewOrderId(newOrderId);
                actionLog.setNewQuantity(itemQuantity.getQuantity());
                actionLog.setOldItemId(itemId);
                actionLog.setOldOrderId(orderId);
                actionLog.setOldQuantity(orderItem.getQuantity());
                actionLogs.add(actionLog);

                if (itemQuantity.getQuantity() < 1){
                    continue;
                }
                totalQuantity += itemQuantity.getQuantity();
                OrderItem newItem = new OrderItem();
                BeanUtils.copyProperties(orderItem, newItem);
                newItem.setId(newItemId);
                newItem.setOrderId(newOrderId);
                newItem.setQuantity(itemQuantity.getQuantity());
                newItem.setStoreOrderId(storeOrderRelate.getStoreOrderId());
                newItem.setStoreVariantSku(itemMap.get(itemId).getStoreVariantSku());
                newItem.setAdminVariantId(itemMap.get(itemId).getAdminVariantId());
                newItem.setItemType(itemMap.get(itemId).getItemType());
                orderItems.add(newItem);
                if (null != orderItem.getUsdPrice()) {
                    productAmount = productAmount.add(new BigDecimal(newItem.getQuantity()).multiply(orderItem.getUsdPrice()));
                }
                if (null != orderItem.getCnyPrice()) {
                    cnyProductAmount = cnyProductAmount.add(new BigDecimal(newItem.getQuantity()).multiply(orderItem.getCnyPrice()));
                }

            }
            BeanUtils.copyProperties(order, newOrder);
            if (totalQuantity < 1){
                continue;
            }
            newOrder.setId(newOrderId);
            newOrder.setCreateTime(date);
            newOrder.setUpdateTime(date);
            newOrder.setOrderType(2);
            newOrder.setProductAmount(productAmount);
            newOrder.setOrgId(order.getOrgId());
            newOrder.setOrgPath(order.getOrgPath());
            newOrder.setCnyProductAmount(cnyProductAmount);
            newOrder.setShipPrice(BigDecimal.ZERO);
            newOrder.setShipMethodId(null);
            orders.add(newOrder);

            OrderAddress newAddress = new OrderAddress();
            BeanUtils.copyProperties(orderAddress, newAddress);
            newAddress.setId(IdGenerate.nextId());
            newAddress.setOrderId(newOrderId);
            orderAddresses.add(newAddress);

            StoreOrderRelate newRelate = new StoreOrderRelate();
            BeanUtils.copyProperties(storeOrderRelate, newRelate);
            newRelate.setOrderId(newOrderId);
            newRelate.setOrderCreateTime(date);
            newRelate.setId(null);
            storeOrderRelates.add(newRelate);
        }
        orderDao.insertByBatch(orders);
        orderItemService.insertByBatch(orderItems);
        orderActionLogDao.insertByBatch(actionLogs);
        orderAddressDao.insertByBatch(orderAddresses);
        storeOrderRelateDao.insertByBatch(storeOrderRelates);
        for (Order order1 : orders) {
            orderService.initQuoteState(order1.getId());
        }
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        orderService.deleteOrderByIds(orderIds);
        return "success";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String restoreSplitOrder(Order order) throws CustomerException {
        if (0 != order.getPayState() || 2 != order.getOrderType()) {
            return "order error";
        }
        Long orderId = order.getId();

        Page<OrderActionLog> page = new Page<>();
        OrderActionLog actionLog = new OrderActionLog();
        actionLog.setNewOrderId(orderId);
        page.setT(actionLog);
        List<OrderActionLog> actionLogs = orderActionLogDao.select(page);
        Long oldOrderId = actionLogs.get(0).getOldOrderId();
        actionLog = new OrderActionLog();
        actionLog.setOldOrderId(oldOrderId);
        page.setT(actionLog);
        actionLogs = orderActionLogDao.select(page);

        Long id = IdGenerate.nextId();

        Map<Long, OrderItem> itemMap = new HashMap<>();

        List<OrderItem> items = new ArrayList<>();

        List<Long> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        List<OrderActionLog> orderActionLogs = new ArrayList<>();

        Date date = new Date();
        for (OrderActionLog orderActionLog : actionLogs) {
            Long newOrderId = orderActionLog.getNewOrderId();
            Order newOrder = null;
            if (!orderIds.contains(newOrderId)) {
                newOrder = orderDao.selectByPrimaryKey(newOrderId);
                if (null == newOrder ||0 != newOrder.getPayState() || 2 != order.getOrderType()) {
                    continue;
                }
                orderIds.add(newOrderId);
            }

            OrderItem orderItem = null;
            boolean b = false;
            if (itemMap.containsKey(orderActionLog.getOldItemId())) {
                orderItem = itemMap.get(orderActionLog.getOldItemId());
            } else {
                b = true;
                orderItem = orderItemDao.selectByPrimaryKey(orderActionLog.getNewItemId());
                itemMap.put(orderActionLog.getOldItemId(), orderItem);
            }
            OrderActionLog log = new OrderActionLog();
            log.setOldOrderId(orderItem.getOrderId());
            log.setOldQuantity(orderItem.getQuantity());
            log.setOldItemId(orderItem.getId());
            log.setNewOrderId(id);
            log.setNewItemId(orderActionLog.getOldItemId());
            log.setNewQuantity(orderActionLog.getOldQuantity());
            log.setActionType(OrderActionType.MERGE_SPLIT_ORDER);
            log.setCreateTime(date);
            orderActionLogs.add(log);
            if (b) {
                OrderItem item = new OrderItem();
                BeanUtils.copyProperties(orderItem, item);
                item.setId(orderActionLog.getOldItemId());
                item.setOrderId(id);
                item.setStoreOrderId(orderItem.getStoreOrderId());
                item.setStoreProductId(orderItem.getStoreProductId());
                item.setQuantity(orderActionLog.getOldQuantity());
                items.add(item);
            }

        }
        if (ListUtils.isEmpty(orderIds)) {
            return "No matching order";
        }
        OrderReshipInfo orderReshipInfo = orderReshipInfoDao.selectByPrimaryKey(oldOrderId);
        if (null != orderReshipInfo){
            order.setOrderType(1);
        }

        OrderAddress orderAddress = orderAddressDao.selectByOrderId(orderId);
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(orderId);
        log.info("订单拆分即将删除的原订单：{}", orderIds);
        orderService.deleteOrderByIds(orderIds);
        order.setId(id);
        order.setCreateTime(date);
        order.setOrderType(0);
        order.setCnyProductAmount(BigDecimal.ZERO);
        orderDao.insert(order);
        orderItemService.insertByBatch(items);
        orderActionLogDao.insertByBatch(orderActionLogs);

        StoreOrderRelate storeOrderRelate = storeOrderRelates.get(0);
        storeOrderRelate.setOrderId(id);
        storeOrderRelate.setOrderCreateTime(date);
        storeOrderRelate.setId(null);
        storeOrderRelateDao.insert(storeOrderRelate);
        orderAddress.setOrderId(id);
        orderAddress.setId(IdGenerate.nextId());
        orderAddressDao.insert(orderAddress);
        orderService.initQuoteState(id);
        return "success";
    }

    @Transactional
    @Override
    public String mergeNormalOrder(MergeOrderRequest request) {

        List<Long> orderIds = request.getOrderIds();
        orderIds = orderIds.stream().distinct().collect(Collectors.toList());
        ShipDetail shipDetail = request.getShipDetail();
        if (ListUtils.isEmpty(orderIds) || orderIds.size() < 2) {
            return "request failed";
        }

        List<SameAddressOrderVo> sameAddressOrderVos = orderAddressDao.selectSameAddressByOrderIds(orderIds);
        if (ListUtils.isEmpty(sameAddressOrderVos) || 1 != sameAddressOrderVos.size()) {
            return "The combined order address must be the same";
        }
        List<AppOrderVo> appOrderVos = orderDao.selectAppOrderByIds(orderIds);
        Integer orderType = null;
        List<Long> storeOrderIds = new ArrayList<>();
        for (AppOrderVo appOrderVo : appOrderVos) {
            if (appOrderVo.getOrderType() > 1){
                return "request failed";
            }
            if (orderType == null){
                orderType = appOrderVo.getOrderType();
            }else if (!orderType.equals(appOrderVo.getOrderType())){
                return "Only orders of the same type can be combined";
            }

        }
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderIds(orderIds);
        for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
            if (storeOrderIds.contains(storeOrderRelate.getStoreOrderId())){
                storeOrderRelateDao.deleteByOrderAndStoreOrder(storeOrderRelate.getOrderId(), storeOrderRelate.getStoreOrderId());
            }else {
                storeOrderIds.add(storeOrderRelate.getStoreOrderId());
            }
        }

        AppOrderVo appOrderVo = appOrderVos.get(0);

        appOrderVos.remove(appOrderVo);
        Long orderId = appOrderVo.getId();
        Long storeId = appOrderVo.getStoreId();
        Date date = new Date();
        List<OrderActionLog> actionLogs = new ArrayList<>();
        for (AppOrderVo orderVo : appOrderVos) {
            if (0 != orderVo.getPayState()
                    || !storeId.equals(orderVo.getStoreId())) {
                return "Only unpaid ordinary orders can be combined";
            }
            List<AppOrderItemVo> itemVos = orderItemDao.selectAppOrderItemByOrderId(orderVo.getId());
            for (AppOrderItemVo itemVo : itemVos) {
                OrderActionLog log = new OrderActionLog();
                log.setOldQuantity(itemVo.getQuantity());
                log.setOldOrderId(itemVo.getOrderId());
                log.setOldItemId(itemVo.getId());
                log.setNewOrderId(orderId);
                log.setNewQuantity(itemVo.getQuantity());
                log.setNewItemId(itemVo.getId());
                log.setActionType(OrderActionType.MERGE_NORMAL_ORDER);
                log.setCreateTime(date);
                actionLogs.add(log);
            }
        }
        orderIds.remove(orderId);
        orderItemDao.updateOrderIdByOrderIds(orderIds, orderId);
        orderDao.deleteOrderByIds(orderIds);
        orderAddressDao.deleteByOrderIds(orderIds);

        storeOrderRelateDao.updateOrderId(orderIds, orderId);

        orderActionLogDao.insertByBatch(actionLogs);

        orderDao.updateOrderType(orderId, 3);
        orderIds.clear();
        orderIds.add(orderId);
        orderDao.initProductAmountById(orderIds);
        shipDetail.setPrice(shipDetail.getPrice().subtract(shipDetail.getServiceFee()));
        Order order = orderDao.selectByPrimaryKey(orderId);
        BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), shipDetail.getPrice(), order.getToAreaId(), order.getCustomerId());
        shipDetail.setVatAmount(vatAmount);
        orderDao.updateShipDetailById(shipDetail, orderId);
        orderService.initQuoteState(orderId);
        return "success";
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String revertMergedOrder(Order order) {
        if (0 != order.getPayState() || 3 != order.getOrderType()) {
            return "order error";
        }
        Long orderId = order.getId();
        Page<OrderActionLog> page = new Page<>();
        OrderActionLog actionLog = new OrderActionLog();
        actionLog.setNewOrderId(orderId);
        page.setT(actionLog);
        List<OrderActionLog> actionLogs = orderActionLogDao.select(page);

        Integer orderType = 0;
        Long oldOrderId = actionLogs.get(0).getOldOrderId();
        OrderReshipInfo orderReshipInfo = orderReshipInfoDao.selectByPrimaryKey(oldOrderId);
        if (null != orderReshipInfo){
            orderType = 1;
        }

        List<StoreOrderRelate> storeOrderRelates = new ArrayList<>();
        Map<Long, List<Long>> orderItemMap = new HashMap<>();
        Map<Long, Long> newOrderIdMap = new HashMap<>();
        Date date = new Date();
        List<Order> orders = new ArrayList<>();
        List<OrderAddress> orderAddresses = new ArrayList<>();
        List<OrderActionLog> actionLogList = new ArrayList<>();

        for (OrderActionLog orderActionLog : actionLogs) {
            Long id = orderActionLog.getOldOrderId();
            Long itemId = orderActionLog.getNewItemId();
            Long newOrderId = null;

            if (newOrderIdMap.containsKey(id)) {
                newOrderId = newOrderIdMap.get(id);
                if (orderItemMap.get(newOrderId).contains(itemId)) {
                    continue;
                }
            } else {
                Order newOrder = new Order();
                newOrderId = orderActionLog.getOldOrderId();
                BeanUtils.copyProperties(order, newOrder);
                newOrder.setId(newOrderId);
                newOrder.setOrderType(0);
                newOrder.setUpdateTime(date);
                newOrder.setCnyProductAmount(BigDecimal.ZERO);
                orders.add(newOrder);
                newOrderIdMap.put(id, newOrderId);

                Long storeOrderId = orderItemDao.selectStoreOrderIdById(itemId);
                StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderId);
                StoreOrderRelate storeOrderRelate = new StoreOrderRelate(storeOrder);
                storeOrderRelate.setOrderId(newOrderId);
                storeOrderRelate.setOrderCreateTime(date);
                storeOrderRelates.add(storeOrderRelate);
                storeOrderRelateDao.deleteByOrderAndStoreOrder(orderId, storeOrderId);

                StoreOrderAddress storeOrderAddress = storeOrderAddressDao.selectByStoreOrderId(storeOrderId);
                OrderAddress address = new OrderAddress();
                BeanUtils.copyProperties(storeOrderAddress, address);
                address.setOrderId(newOrderId);
                orderAddresses.add(address);

                orderItemMap.put(newOrderId, new ArrayList<>());
            }

            if (orderItemMap.get(newOrderId).contains(itemId)) {
                continue;
            } else {
                orderItemMap.get(newOrderId).add(itemId);

                OrderActionLog log = new OrderActionLog();
                BeanUtils.copyProperties(orderActionLog, log);
                log.setCreateTime(date);
                log.setActionType(OrderActionType.SPLIT_MERGE_ORDER);
                log.setNewOrderId(newOrderId);
                log.setOldOrderId(orderId);
                log.setId(null);
                actionLogList.add(log);
            }
        }

        orderDao.updateOrderType(orderId, orderType);
        orderDao.insertByBatch(orders);
        orderItemDao.updateOrderIdByOrderItemMap(orderItemMap);
        orderAddressDao.insertByBatch(orderAddresses);
        storeOrderRelateDao.insertByBatch(storeOrderRelates);
        orderActionLogDao.insertByBatch(actionLogList);
        for (Order order1 : orders) {
            orderService.initQuoteState(order1.getId());
        }

        return "success";
    }


    @Override
    public List<ShipDetail> mergeOrderShipList(List<Long> orderIds) {
        Order order = orderDao.selectByPrimaryKey(orderIds.get(0));

        List<VariantDetail> variantDetails = orderItemDao.selectAdminVariantDetailByOrderIds(orderIds);

        Long areaId = order.getToAreaId();

        ProductVariantShipsRequest request = new ProductVariantShipsRequest();
        request.setAreaId(areaId);
        request.setCustomerId(order.getCustomerId());
        request.setVariantDetails(variantDetails);
        BaseResponse response = pmsFeignClient.productSearchShips(request);
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(ShipDetail.class);
            return shipDetails;
        }
        return null;
    }
}
