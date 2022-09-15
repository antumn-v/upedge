package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderItemDao;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderListDto;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdatePriceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdateQuantityRequest;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    @Autowired
    private PurchaseOrderItemDao purchaseOrderItemDao;

    @Autowired
    PurchaseOrderService purchaseOrderService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrderItem record = new PurchaseOrderItem();
        record.setId(id);
        return purchaseOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrderItem record) {
        return purchaseOrderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<PurchaseOrderItem> records) {
        if (ListUtils.isNotEmpty(records)){
            return purchaseOrderItemDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrderItem record) {
        return purchaseOrderItemDao.insert(record);
    }

    @Override
    public int updatePriceBySpecId(List<PurchaseOrderItem> items) {
        if (ListUtils.isNotEmpty(items)){
            return purchaseOrderItemDao.updatePriceBySpecId(items);
        }
        return 0;
    }

    @Override
    public BaseResponse updatePriceById(PurchaseOrderItemUpdatePriceRequest request) {
        Long id = request.getId();
        BigDecimal price = request.getPrice();
        if (price.compareTo(BigDecimal.ZERO) != 1){
            return BaseResponse.failed();
        }
        PurchaseOrderItem purchaseOrderItem = selectByPrimaryKey(id);
        if (null == purchaseOrderItem){
            return BaseResponse.failed();
        }
        if (price.compareTo(purchaseOrderItem.getPrice()) == 0){
            return BaseResponse.success();
        }
        Long orderId = purchaseOrderItem.getOrderId();
        PurchaseOrder purchaseOrder = purchaseOrderService.selectByPrimaryKey(orderId);
        if (purchaseOrder.getEditState() != 0){
            return BaseResponse.failed("订单未处于可编辑状态");
        }
        purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setId(id);
        purchaseOrderItem.setPrice(price);
        updateByPrimaryKeySelective(purchaseOrderItem);

        updateOrderProductAmount(orderId);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse updateQuantityById(PurchaseOrderItemUpdateQuantityRequest request) {
        Long id = request.getId();
        Integer quantity = request.getQuantity();
        if (quantity < 0){
            return BaseResponse.failed();
        }
        PurchaseOrderItem purchaseOrderItem = selectByPrimaryKey(id);
        if (null == purchaseOrderItem){
            return BaseResponse.failed();
        }
        if (quantity == purchaseOrderItem.getQuantity()){
            return BaseResponse.success();
        }
        Long orderId = purchaseOrderItem.getOrderId();
        PurchaseOrder purchaseOrder = purchaseOrderService.selectByPrimaryKey(orderId);
        if (purchaseOrder.getEditState() != 0){
            return BaseResponse.failed("订单未处于可编辑状态");
        }
        purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setId(id);
        purchaseOrderItem.setQuantity(quantity);
        updateByPrimaryKeySelective(purchaseOrderItem);

        updateOrderProductAmount(orderId);
        return BaseResponse.success();
    }

    void updateOrderProductAmount(Long orderId){
        List<PurchaseOrderItem> purchaseOrderItems = selectByOrderId(orderId);
        BigDecimal productAmount = BigDecimal.ZERO;
        for (PurchaseOrderItem orderItem : purchaseOrderItems) {
            BigDecimal itemAmount = new BigDecimal(orderItem.getQuantity().toString()).multiply(orderItem.getPrice());
            productAmount = productAmount.add(itemAmount);
        }
        purchaseOrderService.updateProductAmount(orderId,productAmount);
    }

    @Override
    public List<PurchaseOrderItem> selectByOrderListDto(PurchaseOrderListDto purchaseOrderListDto) {
        if ( null == purchaseOrderListDto){
            return new ArrayList<>();
        }
        if (null != purchaseOrderListDto.getPurchaseSku()
        || null != purchaseOrderListDto.getPurchaseLink()
        || null != purchaseOrderListDto.getVariantName()
        || null != purchaseOrderListDto.getProductId()
        || null != purchaseOrderListDto.getVariantSku()
        || null != purchaseOrderListDto.getBarcode()){
            return purchaseOrderItemDao.selectByOrderListDto(purchaseOrderListDto);
        }
        return new ArrayList<>();
    }

    @Override
    public List<PurchaseOrderItem> selectByOrderId(Long orderId) {
        return purchaseOrderItemDao.selectByOrderId(orderId);
    }

    @Override
    public List<PurchaseOrderItem> selectByOrderIds(List<Long> orderIds) {
        if (ListUtils.isNotEmpty(orderIds)){
            return purchaseOrderItemDao.selectByOrderIds(orderIds);
        }
        return new ArrayList<>();
    }

    /**
     *
     */
    public PurchaseOrderItem selectByPrimaryKey(Long id){
        PurchaseOrderItem record = new PurchaseOrderItem();
        record.setId(id);
        return purchaseOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrderItem record) {
        return purchaseOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrderItem record) {
        return purchaseOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrderItem> select(Page<PurchaseOrderItem> record){
        record.initFromNum();
        return purchaseOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrderItem> record){
        return purchaseOrderItemDao.count(record);
    }

}