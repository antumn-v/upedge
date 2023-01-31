package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.dto.StockPurchaseOrderItemReceiveDto;
import com.upedge.oms.modules.stock.dao.StockOrderItemDao;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.oms.modules.stock.service.StockOrderItemService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockOrderItemServiceImpl implements StockOrderItemService {

    @Autowired
    private StockOrderItemDao stockOrderItemDao;

    @Autowired
    StockOrderService stockOrderService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StockOrderItem record = new StockOrderItem();
        record.setId(id);
        return stockOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StockOrderItem record) {
        return stockOrderItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StockOrderItem record) {
        return stockOrderItemDao.insert(record);
    }

    @Override
    public BaseResponse updateInboundQuantity(StockPurchaseOrderItemReceiveDto stockPurchaseOrderItemReceiveDto) {
        Long orderId = stockPurchaseOrderItemReceiveDto.getOrderId();
        Long variantId = stockPurchaseOrderItemReceiveDto.getVariantId();
        Integer quantity = stockPurchaseOrderItemReceiveDto.getQuantity();

        StockOrder stockOrder = stockOrderService.selectByPrimaryKey(orderId);
        if (stockOrder == null){
            return BaseResponse.failed("客户备库订单不存在");
        }
        Integer inboundQuantity = 0;
        Integer totalQuantity = 0;
        List<StockOrderItem> stockOrderItems = stockOrderItemDao.selectByOrderId(orderId);
        for (StockOrderItem stockOrderItem : stockOrderItems) {
            if (stockOrderItem.getVariantId().equals(variantId) && quantity > 0){
                Integer itemQuantity = stockOrderItem.getQuantity();
                Integer itemInboundQuantity = stockOrderItem.getInboundQuantity();
                if (quantity > (itemQuantity - itemInboundQuantity)){
                    quantity = itemQuantity - itemInboundQuantity;
                }
                if (quantity > 0){
                    inboundQuantity += quantity;
                    stockOrderItemDao.updateInboundQuantity(stockOrderItem.getId(),quantity);
                    quantity = 0;
                }
            }
            inboundQuantity += stockOrderItem.getInboundQuantity();
            totalQuantity += stockOrderItem.getQuantity();
        }
        if (inboundQuantity == totalQuantity){
            stockOrderService.updatePurchaseState(orderId,3);
        }else {
            stockOrderService.updatePurchaseState(orderId,2);
        }
        return BaseResponse.success();
    }

    @Override
    public int updatePurchaseInfo(Long variantId, String purchaseSku, String supplierName) {
        return stockOrderItemDao.updatePurchaseInfo(variantId, purchaseSku, supplierName);
    }

    /**
     *
     */
    public StockOrderItem selectByPrimaryKey(Long id){
        StockOrderItem record = new StockOrderItem();
        record.setId(id);
        return stockOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StockOrderItem record) {
        return stockOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StockOrderItem record) {
        return stockOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StockOrderItem> select(Page<StockOrderItem> record){
        record.initFromNum();
        return stockOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StockOrderItem> record){
        return stockOrderItemDao.count(record);
    }

}