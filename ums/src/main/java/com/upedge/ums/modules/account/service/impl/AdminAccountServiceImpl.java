package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.dao.AdminAccountAttrDao;
import com.upedge.ums.modules.account.dao.AdminAccountDao;
import com.upedge.ums.modules.account.dao.AdminAccountTypeAttrDao;
import com.upedge.ums.modules.account.entity.AdminAccount;
import com.upedge.ums.modules.account.entity.AdminAccountAttr;
import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;
import com.upedge.ums.modules.account.request.AdminAccountAddRequest;
import com.upedge.ums.modules.account.request.AdminAccountListRequest;
import com.upedge.ums.modules.account.request.AdminAccountUpdateRequest;
import com.upedge.ums.modules.account.response.AdminAccountAddResponse;
import com.upedge.ums.modules.account.response.AdminAccountInfoResponse;
import com.upedge.ums.modules.account.response.AdminAccountListResponse;
import com.upedge.ums.modules.account.response.AdminAccountUpdateResponse;
import com.upedge.ums.modules.account.service.AdminAccountService;
import com.upedge.ums.modules.account.vo.AdminAccountAttrVo;
import com.upedge.ums.modules.account.vo.AdminAccountVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    @Autowired
    private AdminAccountDao adminAccountDao;
    @Autowired
    private AdminAccountAttrDao adminAccountAttrDao;
    @Autowired
    private AdminAccountTypeAttrDao adminAccountTypeAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AdminAccount record = new AdminAccount();
        record.setId(id);
        return adminAccountDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AdminAccount record) {
        return adminAccountDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AdminAccount record) {
        record.setId(IdGenerate.nextId());
        return adminAccountDao.insert(record);
    }

    /**
     *
     */
    public AdminAccount selectByPrimaryKey(Long id){
        return adminAccountDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AdminAccount record) {
        return adminAccountDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AdminAccount record) {
        return adminAccountDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AdminAccount> select(Page<AdminAccount> record){
        record.initFromNum();
        return adminAccountDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AdminAccount> record){
        return adminAccountDao.count(record);
    }

    @Override
    public AdminAccountListResponse all() {
        List<AdminAccount> adminAccountList=adminAccountDao.allAdminAccount();
        for(AdminAccount receivingAccount:adminAccountList){
            Integer type=receivingAccount.getAccountType();
            String accountType="";
            if(type!=null&&type==1){
                accountType="Payoneer";
            }
            if(type!=null&&type==2){
                accountType="PayPal";
            }
            if(type!=null&&type==3){
                accountType="AirWallex";
            }
            if(type!=null&&type==4){
                accountType="Transferwise";
            }
            if(type!=null&&type==5){
                accountType="银行账号";
            }
            String accountName=receivingAccount.getAccountName()==null?"":receivingAccount.getAccountName();
            String accountNumber=receivingAccount.getAccountNumber()==null?"":receivingAccount.getAccountNumber();
            receivingAccount.setAccountName(accountType+" "+accountNumber+" "+accountName);
        }
        AdminAccountListResponse res = new AdminAccountListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,adminAccountList,null);
        return res;
    }

    /**
     * admin账户列表
     * @param request
     * @return
     */
    @Override
    public AdminAccountListResponse listAdminAccount(AdminAccountListRequest request) {
        request.initFromNum();
        List<AdminAccount> results = adminAccountDao.select(request);
        Long total = adminAccountDao.count(request);
        request.setTotal(total);
        List<AdminAccountVo> adminAccountVoList=new ArrayList<>();
        results.forEach(adminAccount -> {
            AdminAccountVo adminAccountVo=new AdminAccountVo();
            BeanUtils.copyProperties(adminAccount,adminAccountVo);
            List<AdminAccountAttrVo> attrs=adminAccountAttrDao.listAttrsByAccountId(adminAccount.getId(),adminAccount.getAccountType());
            adminAccountVo.setAttrs(attrs);
            adminAccountVoList.add(adminAccountVo);
        });
        return new AdminAccountListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,adminAccountVoList,request);
    }

    /**
     * 添加潘达账户
     * @param request
     * @return
     */
    @Override
    @Transactional
    public AdminAccountAddResponse addAdminAccount(AdminAccountAddRequest request) {
        AdminAccount entity=request.toAdminAccount();
        Long accountId=IdGenerate.nextId();
        entity.setId(accountId);
        adminAccountDao.insert(entity);
        List<AdminAccountAttr> attrs=request.getAttrs();
        if(attrs!=null&&attrs.size()>0){
            attrs.forEach(adminAccountAttr -> {
                adminAccountAttr.setAccountId(accountId);
            });
            adminAccountAttrDao.saveByBatch(attrs);
        }
        return new AdminAccountAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新账户信息
     * @param id
     * @param request
     * @return
     */
    @Transactional
    @Override
    public AdminAccountUpdateResponse updateAdminAccount(Long id, AdminAccountUpdateRequest request) {
        AdminAccount entity=request.toAdminAccount(id);
        adminAccountDao.updateByPrimaryKeySelective(entity);
        List<AdminAccountAttr> attrs=request.getAttrs();
        if(attrs!=null&&attrs.size()>0){
            adminAccountAttrDao.saveByBatch(attrs);
        }
        return new AdminAccountUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 账户信息
     * @param id
     * @return
     */
    @Override
    public AdminAccountInfoResponse info(Long id) {
        AdminAccount adminAccount = adminAccountDao.selectByPrimaryKey(id);
        AdminAccountVo adminAccountVo=new AdminAccountVo();
        BeanUtils.copyProperties(adminAccount,adminAccountVo);
        List<AdminAccountAttrVo> attrs=adminAccountAttrDao.listAttrsByAccountId(adminAccount.getId(),adminAccount.getAccountType());
        Set<String> keySet=new HashSet<>();
        attrs.forEach(adminAccountAttrVo -> {
            keySet.add(adminAccountAttrVo.getAttrKey());
        });
        List<AdminAccountTypeAttr> results = adminAccountTypeAttrDao.listByAccountType(adminAccount.getAccountType());
        results.forEach(adminAccountTypeAttr -> {
            if(!keySet.contains(adminAccountTypeAttr.getAttrKey())){
                AdminAccountAttrVo adminAccountAttrVo=new AdminAccountAttrVo();
                adminAccountAttrVo.setAccountId(id);
                adminAccountAttrVo.setAttrKey(adminAccountTypeAttr.getAttrKey());
                adminAccountAttrVo.setAttrName(adminAccountTypeAttr.getAttrName());
                attrs.add(adminAccountAttrVo);
            }
        });
        adminAccountVo.setAttrs(attrs);
        return new AdminAccountInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,adminAccountVo,id);
    }

}