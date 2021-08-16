package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RechargeRequestAttrDao;
import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import com.upedge.ums.modules.account.service.RechargeRequestAttrService;


@Service
public class RechargeRequestAttrServiceImpl implements RechargeRequestAttrService {

    @Autowired
    private RechargeRequestAttrDao rechargeRequestAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        RechargeRequestAttr record = new RechargeRequestAttr();
        record.setId(id);
        return rechargeRequestAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RechargeRequestAttr record) {
        return rechargeRequestAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RechargeRequestAttr record) {
        return rechargeRequestAttrDao.insert(record);
    }

    /**
     *
     */
    public RechargeRequestAttr selectByPrimaryKey(Integer id){
        RechargeRequestAttr record = new RechargeRequestAttr();
        record.setId(id);
        return rechargeRequestAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RechargeRequestAttr record) {
        return rechargeRequestAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RechargeRequestAttr record) {
        return rechargeRequestAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RechargeRequestAttr> select(Page<RechargeRequestAttr> record){
        record.initFromNum();
        return rechargeRequestAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RechargeRequestAttr> record){
        return rechargeRequestAttrDao.count(record);
    }

}