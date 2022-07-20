package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderItemDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    @Autowired
    private PurchaseOrderItemDao purchaseOrderItemDao;



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