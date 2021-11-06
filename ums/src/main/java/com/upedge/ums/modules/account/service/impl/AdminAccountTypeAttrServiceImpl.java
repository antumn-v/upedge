package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.account.dao.AdminAccountTypeAttrDao;
import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;
import com.upedge.ums.modules.account.response.AdminAccountTypeAttrListResponse;
import com.upedge.ums.modules.account.service.AdminAccountTypeAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminAccountTypeAttrServiceImpl implements AdminAccountTypeAttrService {

    @Autowired
    private AdminAccountTypeAttrDao adminAccountTypeAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AdminAccountTypeAttr record = new AdminAccountTypeAttr();
        record.setId(id);
        return adminAccountTypeAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AdminAccountTypeAttr record) {
        return adminAccountTypeAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AdminAccountTypeAttr record) {
        return adminAccountTypeAttrDao.insert(record);
    }

    /**
     *
     */
    public AdminAccountTypeAttr selectByPrimaryKey(Long id){
        AdminAccountTypeAttr record = new AdminAccountTypeAttr();
        record.setId(id);
        return adminAccountTypeAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AdminAccountTypeAttr record) {
        return adminAccountTypeAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AdminAccountTypeAttr record) {
        return adminAccountTypeAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AdminAccountTypeAttr> select(Page<AdminAccountTypeAttr> record){
        record.initFromNum();
        return adminAccountTypeAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AdminAccountTypeAttr> record){
        return adminAccountTypeAttrDao.count(record);
    }

    /**
     * 获取账户类型参数列表
     * @param accountType
     * @return
     */
    @Override
    public AdminAccountTypeAttrListResponse listByAccountType(Integer accountType) {
        List<AdminAccountTypeAttr> results = adminAccountTypeAttrDao.listByAccountType(accountType);
        return new AdminAccountTypeAttrListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,null);
    }
}