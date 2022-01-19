package com.upedge.oms.modules.order.service.impl;

import com.upedge.oms.modules.order.dao.StoreOrderRelateDao;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.service.StoreOrderRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreOrderRelateServiceImpl implements StoreOrderRelateService {

    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;

    @Override
    public List<StoreOrderRelate> selectUnPaidByStoreOrderId(List<Long> storeOrderIds) {
        return storeOrderRelateDao.selectUnPaidByStoreOrderId(storeOrderIds);
    }

    @Override
    public List<StoreOrderRelate> selectByStoreOrderId(Long storeOrderId) {
        return storeOrderRelateDao.selectByStoreOrderId(storeOrderId);
    }

    @Override
    public int updateCustomerNameByOrderId(Long orderId, String customerName) {
        return storeOrderRelateDao.updateCustomerNameByOrderId(orderId, customerName);
    }
}
