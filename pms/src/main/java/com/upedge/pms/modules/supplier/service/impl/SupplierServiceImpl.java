package com.upedge.pms.modules.supplier.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.supplier.dao.SupplierDao;
import com.upedge.pms.modules.supplier.entity.Supplier;
import com.upedge.pms.modules.supplier.service.SupplierService;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        Supplier record = new Supplier();
        record.setId(id);
        return supplierDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Supplier record) {
        return supplierDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Supplier record) {
        return supplierDao.insert(record);
    }

    @Override
    public Supplier selectByLoginId(String supplierLoginId) {
        return supplierDao.selectByLoginId(supplierLoginId);
    }

    /**
     *
     */
    public Supplier selectByPrimaryKey(Integer id){
        Supplier record = new Supplier();
        record.setId(id);
        return supplierDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Supplier record) {
        return supplierDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Supplier record) {
        return supplierDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Supplier> select(Page<Supplier> record){
        record.initFromNum();
        return supplierDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Supplier> record){
        return supplierDao.count(record);
    }

}