package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.request.ManualAddCustomerStockRequest;

import java.util.List;

/**
 * @author author
 */
public interface CustomerProductStockService{


    BaseResponse revokeManualAddRecord(Long recordId,Session session);

    BaseResponse manualAddCustomerVariantStock(ManualAddCustomerStockRequest request, Session session);

    CustomerProductStock selectByPrimaryKey(Long id);

    boolean orderItemStockCheck(List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId);

    /**
     * 支付订单时锁定已使用的库存
     * @param customerId
     * @param items
     * @return
     */
    boolean increaseCustomerLockStock(Long customerId,
                                      List<ItemDischargeQuantityVo> items);

    /**
     * 从锁定的库存中减少库存（支付订单已完成）
     * @param customerId
     * @param items
     * @return
     */
    int reduceFromLockStock(Long customerId,
                            List<ItemDischargeQuantityVo> items);

    /**
     * 从锁定的库存中恢复库存（支付订单失败）
     * @param customerId
     * @param items
     * @return
     */
    int increaseFromLockStock(Long customerId,
                              List<ItemDischargeQuantityVo> items);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerProductStock record);

    int updateByPrimaryKeySelective(CustomerProductStock record);

    int insert(CustomerProductStock record);

    int insertSelective(CustomerProductStock record);

    List<CustomerProductStock> select(Page<CustomerProductStock> record);

    long count(Page<CustomerProductStock> record);

    List<CustomerProductStockNumVo> getCustomerStockNum();

    /**
     * 备库管理/客户商品库存/同步库存
     * @param id
     * @return
     */
    BaseResponse refreshSaiheInventory(Long id) throws CustomerException;

}

