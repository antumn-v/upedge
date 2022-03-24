package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderAddressDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.StoreOrderAddress;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderRelateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderAddressServiceImpl implements OrderAddressService {


    @Autowired
    OrderAddressDao orderAddressDao;

    @Autowired
    StoreOrderRelateService storeOrderRelateService;

    @Autowired
    OrderService orderService;

    @Override
    public void updateByStoreOrderAddress(StoreOrderAddress storeOrderAddress) {
        if (null == storeOrderAddress){
            return;
        }
        List<Long> storeOrderIds = new ArrayList<>();
        storeOrderIds.add(storeOrderAddress.getStoreOrderId());
        List<StoreOrderRelate> storeOrderRelateList = storeOrderRelateService.selectUnPaidByStoreOrderId(storeOrderIds);
        if (ListUtils.isEmpty(storeOrderRelateList)){
            return;
        }
        String name = storeOrderAddress.getFirstName() + " " + storeOrderAddress.getLastName();
        for (StoreOrderRelate storeOrderRelate : storeOrderRelateList) {
            Order order = orderService.selectByPrimaryKey(storeOrderRelate.getOrderId());
            if (order.getPayState() > 0){
                continue;
            }
            OrderAddress orderAddress = orderAddressDao.selectByOrderId(storeOrderRelate.getOrderId());
            Long orderAddressId = orderAddress.getId();
            if (StringUtils.isBlank(orderAddress.getCountry())
                || !orderAddress.getCountry().equals(storeOrderAddress.getCountry())){
                orderService.orderUpdateToAreaId(storeOrderRelate.getOrderId(),storeOrderAddress.getCountry());
            }
            BeanUtils.copyProperties(storeOrderAddress,orderAddress);
            orderAddress.setOrderId(storeOrderRelate.getOrderId());
            orderAddress.setId(orderAddressId);
            orderAddress.setName(name);
            orderAddressDao.updateByPrimaryKey(orderAddress);
            storeOrderRelateService.updateCustomerNameByOrderId(storeOrderRelate.getOrderId(),orderAddress.getName());
        }
    }
}
