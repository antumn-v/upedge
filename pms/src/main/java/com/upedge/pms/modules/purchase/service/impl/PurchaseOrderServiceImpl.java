package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    PurchaseOrderItemService purchaseOrderItemService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrder record = new PurchaseOrder();
        record.setId(id);
        return purchaseOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrder record) {
        return purchaseOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrder record) {
        return purchaseOrderDao.insert(record);
    }

    @Override
    public List<PurchaseOrderVo> orderList(PurchaseOrderListRequest request) {
        List<PurchaseOrderVo> purchaseOrderVos = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = select(request);
        if (ListUtils.isNotEmpty(purchaseOrders)){
            List<Long> orderIds = new ArrayList<>();
            purchaseOrders.forEach(purchaseOrder -> {
                orderIds.add(purchaseOrder.getId());
            });
            List<PurchaseOrderItem> orderItems = purchaseOrderItemService.selectByOrderIds(orderIds);
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
                BeanUtils.copyProperties(purchaseOrder,purchaseOrderVo);
                List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
                for (PurchaseOrderItem orderItem : orderItems) {
                    if (orderItem.getOrderId().equals(purchaseOrder.getId())){
                        purchaseOrderItems.add(orderItem);
                    }
                }
                orderItems.removeAll(purchaseOrderItems);
                purchaseOrderVo.setPurchaseItemVos(purchaseOrderItems);
                purchaseOrderVos.add(purchaseOrderVo);
            }
        }
        return purchaseOrderVos;
    }

    /**
     *
     */
    public PurchaseOrder selectByPrimaryKey(Long id){
        PurchaseOrder record = new PurchaseOrder();
        record.setId(id);
        return purchaseOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrder record) {
        return purchaseOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrder record) {
        return purchaseOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrder> select(Page<PurchaseOrder> record){
        record.initFromNum();
        return purchaseOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrder> record){
        return purchaseOrderDao.count(record);
    }

}