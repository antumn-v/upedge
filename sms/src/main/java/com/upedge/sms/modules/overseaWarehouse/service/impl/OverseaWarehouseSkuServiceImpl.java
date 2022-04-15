package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseSkuDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OverseaWarehouseSkuServiceImpl implements OverseaWarehouseSkuService {

    @Autowired
    private OverseaWarehouseSkuDao overseaWarehouseSkuDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long variantId) {
        OverseaWarehouseSku record = new OverseaWarehouseSku();
        record.setVariantId(variantId);
        return overseaWarehouseSkuDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseSku record) {
        return overseaWarehouseSkuDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseSku record) {
        return overseaWarehouseSkuDao.insert(record);
    }

    /**
     *
     */
    public OverseaWarehouseSku selectByPrimaryKey(Long variantId){
        OverseaWarehouseSku record = new OverseaWarehouseSku();
        record.setVariantId(variantId);
        return overseaWarehouseSkuDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseSku record) {
        return overseaWarehouseSkuDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseSku record) {
        return overseaWarehouseSkuDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseSku> select(Page<OverseaWarehouseSku> record){
        record.initFromNum();
        return overseaWarehouseSkuDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseSku> record){
        return overseaWarehouseSkuDao.count(record);
    }

}