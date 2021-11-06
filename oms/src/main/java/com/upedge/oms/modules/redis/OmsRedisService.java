package com.upedge.oms.modules.redis;

import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.store.StoreVo;

import java.util.Set;

public interface OmsRedisService {

    ManagerInfoVo getCustomerManager(String managerCode, Long customerId);

    /**
     * Wooc插件回传 未配置运输方式缓存
     * @param customerId
     * @return
     */
    Set<Object> getWoocShipMethod(Long customerId);
    void setWoocShipMethod(Long customerId, String methodName);

    /**
     * 回传物流 店铺信息缓存
     */
    StoreVo getStoreVo(Long storeId);
    void setStoreVo(Long storeId, StoreVo storeVo);
}
