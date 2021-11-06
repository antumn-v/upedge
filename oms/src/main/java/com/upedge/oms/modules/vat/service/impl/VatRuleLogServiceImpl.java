package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.dao.VatRuleLogDao;
import com.upedge.oms.modules.vat.entity.VatRuleLog;
import com.upedge.oms.modules.vat.service.VatRuleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VatRuleLogServiceImpl implements VatRuleLogService {

    @Autowired
    private VatRuleLogDao vatRuleLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        VatRuleLog record = new VatRuleLog();
        record.setId(id);
        return vatRuleLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VatRuleLog record) {
        return vatRuleLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VatRuleLog record) {
        return vatRuleLogDao.insert(record);
    }

    /**
     *
     */
    public VatRuleLog selectByPrimaryKey(Long id){
        VatRuleLog record = new VatRuleLog();
        record.setId(id);
        return vatRuleLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VatRuleLog record) {
        return vatRuleLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VatRuleLog record) {
        return vatRuleLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VatRuleLog> select(Page<VatRuleLog> record){
        record.initFromNum();
        return vatRuleLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VatRuleLog> record){
        return vatRuleLogDao.count(record);
    }

}