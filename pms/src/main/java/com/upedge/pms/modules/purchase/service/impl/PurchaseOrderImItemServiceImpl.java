package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderImItemDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImItem;
import com.upedge.pms.modules.purchase.service.PurchaseOrderImItemService;


@Service
public class PurchaseOrderImItemServiceImpl implements PurchaseOrderImItemService {

    @Autowired
    private PurchaseOrderImItemDao purchaseOrderImItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrderImItem record = new PurchaseOrderImItem();
        record.setId(id);
        return purchaseOrderImItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrderImItem record) {
        return purchaseOrderImItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrderImItem record) {
        return purchaseOrderImItemDao.insert(record);
    }

    /**
     *
     */
    public PurchaseOrderImItem selectByPrimaryKey(Long id){
        PurchaseOrderImItem record = new PurchaseOrderImItem();
        record.setId(id);
        return purchaseOrderImItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrderImItem record) {
        return purchaseOrderImItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrderImItem record) {
        return purchaseOrderImItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrderImItem> select(Page<PurchaseOrderImItem> record){
        record.initFromNum();
        return purchaseOrderImItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrderImItem> record){
        return purchaseOrderImItemDao.count(record);
    }

}