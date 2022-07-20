package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;



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