package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.dao.ManagerMonthCommissionDao;
import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import com.upedge.ums.modules.manager.service.ManagerMonthCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ManagerMonthCommissionServiceImpl implements ManagerMonthCommissionService {

    @Autowired
    private ManagerMonthCommissionDao managerMonthCommissionDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ManagerMonthCommission record = new ManagerMonthCommission();
        return managerMonthCommissionDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ManagerMonthCommission record) {
        return managerMonthCommissionDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ManagerMonthCommission record) {
        return managerMonthCommissionDao.insert(record);
    }

    @Override
    public ManagerMonthCommission selectByManagerAndMonth(Long managerId, String month) {
        return managerMonthCommissionDao.selectByManagerAndMonth(managerId, month);
    }

    /**
     *
     */
    public ManagerMonthCommission selectByPrimaryKey(Long id){
        ManagerMonthCommission record = new ManagerMonthCommission();
        return managerMonthCommissionDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ManagerMonthCommission record) {
        return managerMonthCommissionDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ManagerMonthCommission record) {
        return managerMonthCommissionDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ManagerMonthCommission> select(Page<ManagerMonthCommission> record){
        record.initFromNum();
        return managerMonthCommissionDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ManagerMonthCommission> record){
        return managerMonthCommissionDao.count(record);
    }

}