package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
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
            if (orderAddress.getIfEdited()){
                continue;
            }
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


    @Override
    public BaseResponse update(OrderAddress orderAddress, Session session) {
        OrderAddress address = orderAddressDao.selectByPrimaryKey(orderAddress.getId());
        if (null == address){
            return BaseResponse.failed();
        }
        Order order = orderService.selectByPrimaryKey(address.getOrderId());
        if (order.getPayState() != 0 || !session.getCustomerId().equals(order.getCustomerId())){
            return BaseResponse.failed("The address cannot be modified for a paid order");
        }
        orderAddress.setOrderId(order.getId());
        orderAddressDao.updateByPrimaryKey(orderAddress);
        if (!orderAddress.getCountryCode().equals(address.getCountryCode())){
            orderService.orderUpdateToAreaId(order.getId(), orderAddress.getCountry());
        }
        storeOrderRelateService.updateCustomerNameByOrderId(order.getId(),orderAddress.getName());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse managerUpdate(OrderAddress orderAddress, Session session) {
        OrderAddress address = orderAddressDao.selectByPrimaryKey(orderAddress.getId());
        if (null == address){
            return BaseResponse.failed();
        }
        Order order = orderService.selectByPrimaryKey(address.getOrderId());
        if (order.getPackState() == 1
        || order.getShipState() == 1){
            return BaseResponse.failed("已生包的订单不能修改地址");
        }
        if(!orderAddress.getCountryCode().equals(address.getCountryCode())){
            return BaseResponse.failed("不能修改国家信息");
        }
        orderAddress.setOrderId(orderAddress.getOrderId());
        orderAddressDao.updateByPrimaryKey(orderAddress);

        storeOrderRelateService.updateCustomerNameByOrderId(order.getId(),orderAddress.getName());
        return BaseResponse.success();
    }
}
