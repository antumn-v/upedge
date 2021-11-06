package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.dao.PackageOrderDao;
import com.upedge.oms.modules.pack.entity.PackageOrder;
import com.upedge.oms.modules.pack.service.PackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PackageOrderServiceImpl implements PackageOrderService {

    @Autowired
    private PackageOrderDao packageOrderDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String clientOrderCode) {
        PackageOrder record = new PackageOrder();
        record.setClientOrderCode(clientOrderCode);
        return packageOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PackageOrder record) {
        return packageOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PackageOrder record) {
        return packageOrderDao.insert(record);
    }

    /**
     *
     */
    public PackageOrder selectByPrimaryKey(String clientOrderCode){
        PackageOrder record = new PackageOrder();
        record.setClientOrderCode(clientOrderCode);
        return packageOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PackageOrder record) {
        return packageOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PackageOrder record) {
        return packageOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PackageOrder> select(Page<PackageOrder> record){
        record.initFromNum();
        return packageOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PackageOrder> record){
        return packageOrderDao.count(record);
    }

}