package com.upedge.sms.modules.wholesale.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.oms.WholesaleOrderItemLockStockRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderItemUpdateDischargeQuantityRequest;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleOrderItemServiceImpl implements WholesaleOrderItemService {

    @Autowired
    private WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    OmsFeignClient omsFeignClient;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrderItem record = new WholesaleOrderItem();
        record.setId(id);
        return wholesaleOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<WholesaleOrderItem> wholesaleOrderItems) {
        if (ListUtils.isNotEmpty(wholesaleOrderItems)){
            return wholesaleOrderItemDao.insertByBatch(wholesaleOrderItems);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    @Override
    public BaseResponse customUpdateDischargeQuantity(WholesaleOrderItemUpdateDischargeQuantityRequest request) {
        Long orderId = request.getOrderId();
        Long itemId = request.getItemId();
        Integer dischargeQuantity = request.getDischargeQuantity();;

        WholesaleOrder wholesaleOrder = wholesaleOrderService.selectByPrimaryKey(orderId);
        if (wholesaleOrder == null || wholesaleOrder.getPayState() != 0){
            return BaseResponse.failed();
        }
        WholesaleOrderItem wholesaleOrderItem = selectByPrimaryKey(itemId);
        if (dischargeQuantity > wholesaleOrderItem.getQuantity()){
            return BaseResponse.failed();
        }
        Integer originalDischargeQuantity = wholesaleOrderItem.getDischargeQuantity();
        if (originalDischargeQuantity == null){
            originalDischargeQuantity = 0;
        }
        if (originalDischargeQuantity.equals(dischargeQuantity)){
            return BaseResponse.success();
        }
        WholesaleOrderItemLockStockRequest wholesaleOrderItemLockStockRequest = new WholesaleOrderItemLockStockRequest();
        wholesaleOrderItemLockStockRequest.setDischargeQuantity(dischargeQuantity);
        wholesaleOrderItemLockStockRequest.setOriginalDischargeQuantity(originalDischargeQuantity);
        wholesaleOrderItemLockStockRequest.setVariantId(wholesaleOrderItem.getVariantId());
        wholesaleOrderItemLockStockRequest.setCustomerId(wholesaleOrder.getCustomerId());
        BaseResponse response = omsFeignClient.lockByWholesale(wholesaleOrderItemLockStockRequest);
        if (response.getCode() == ResultCode.FAIL_CODE){
            return response;
        }
        updateDischargeQuantityById(itemId,dischargeQuantity);
        return BaseResponse.success();
    }

    @Override
    public int updateDischargeQuantityById(Long id, Integer dischargeQuantity) {
        return wholesaleOrderItemDao.updateDischargeQuantityById(id,dischargeQuantity);
    }

    @Override
    public List<WholesaleOrderItem> selectByOrderId(Long orderId) {
        return wholesaleOrderItemDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public WholesaleOrderItem selectByPrimaryKey(Long id){
        WholesaleOrderItem record = new WholesaleOrderItem();
        record.setId(id);
        return wholesaleOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record){
        record.initFromNum();
        return wholesaleOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleOrderItem> record){
        return wholesaleOrderItemDao.count(record);
    }

}