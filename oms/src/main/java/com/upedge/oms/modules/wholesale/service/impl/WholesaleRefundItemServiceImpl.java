package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.dao.WholesaleRefundItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleRefundItemServiceImpl implements WholesaleRefundItemService {

    @Autowired
    private WholesaleRefundItemDao wholesaleRefundItemDao;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        WholesaleRefundItem record = new WholesaleRefundItem();
        record.setId(id);
        return wholesaleRefundItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleRefundItem record) {
        return wholesaleRefundItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleRefundItem record) {
        return wholesaleRefundItemDao.insert(record);
    }

    /**
     *
     */
    public WholesaleRefundItem selectByPrimaryKey(Integer id){
        WholesaleRefundItem record = new WholesaleRefundItem();
        record.setId(id);
        return wholesaleRefundItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleRefundItem record) {
        return wholesaleRefundItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleRefundItem record) {
        return wholesaleRefundItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleRefundItem> select(Page<WholesaleRefundItem> record){
        record.initFromNum();
        return wholesaleRefundItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleRefundItem> record){
        return wholesaleRefundItemDao.count(record);
    }

}