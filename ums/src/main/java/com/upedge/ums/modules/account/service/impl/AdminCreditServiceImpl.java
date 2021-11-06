package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.dao.AccountMapper;
import com.upedge.ums.modules.account.dao.AdminCreditDao;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AdminCredit;
import com.upedge.ums.modules.account.request.AdminCreditApplyRequest;
import com.upedge.ums.modules.account.response.AdminCreditApplyResponse;
import com.upedge.ums.modules.account.response.AdminCreditUpdateResponse;
import com.upedge.ums.modules.account.service.AdminCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class AdminCreditServiceImpl implements AdminCreditService {

    @Autowired
    private AdminCreditDao adminCreditDao;
    @Autowired
    private AccountMapper accountMapper;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AdminCredit record = new AdminCredit();
        record.setId(id);
        return adminCreditDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AdminCredit record) {
        return adminCreditDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AdminCredit record) {
        return adminCreditDao.insert(record);
    }

    /**
     *
     */
    public AdminCredit selectByPrimaryKey(Long id){
        return adminCreditDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AdminCredit record) {
        return adminCreditDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AdminCredit record) {
        return adminCreditDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AdminCredit> select(Page<AdminCredit> record){
        record.initFromNum();
        return adminCreditDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AdminCredit> record){
        return adminCreditDao.count(record);
    }

    /**
     * 申请信用额度上限
     * @param request
     * @param session
     * @return
     */
    @Override
    public AdminCreditApplyResponse applyCreditLimit(AdminCreditApplyRequest request, Session session) {
        if(request.getApplyCredit().compareTo(BigDecimal.ZERO)<=0){
            return new AdminCreditApplyResponse(ResultCode.FAIL_CODE,"额度异常！");
        }
        Account account=accountMapper.customerAccount(request.getCustomerId());
        if(account==null){
            return new AdminCreditApplyResponse(ResultCode.FAIL_CODE,"无客户账户！");
        }
        AdminCredit adminCredit=adminCreditDao.queryAdminCreditByCustomerId(request.getCustomerId());
        if(adminCredit!=null){
            return new AdminCreditApplyResponse(ResultCode.FAIL_CODE, "已有信用额度在申请中！");
        }
        String adminUserId= String.valueOf(session.getId());
        adminCredit=request.toAdminCredit(adminUserId);
        adminCreditDao.insert(adminCredit);
        return new AdminCreditApplyResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 确认信用额度上限
     * @param id
     * @param session
     * @return
     */
    @Override
    public AdminCreditUpdateResponse confirmApply(Long id, Session session) {
        AdminCredit adminCredit=adminCreditDao.selectByPrimaryKey(id);
        if(adminCredit==null||adminCredit.getState()==null
                ||adminCredit.getCustomerId()==null
                ||adminCredit.getApplyCredit()==null
                ||adminCredit.getApplyCredit().compareTo(BigDecimal.ZERO)<0){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE, "参数异常！");
        }
        if(adminCredit.getState()!=0){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE, "状态已过期！");
        }
        Account account=accountMapper.customerAccount(adminCredit.getCustomerId());
        if(account==null){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE,"客户账户不存在！");
        }
        adminCredit.setExecuteUserId(session.getId());
        adminCredit.setUpdateTime(new Date());
        adminCredit.setState(1);
        adminCredit.setCurrCredit(account.getCreditLimit());
        int res=adminCreditDao.confirmApply(adminCredit);
        if(res==0){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE,"确认失败！");
        }
        //更新账户信用额度上限
        accountMapper.updateCreditLimit(account.getId(),adminCredit.getApplyCredit());
        return new AdminCreditUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 驳回信用额度上限
     * @param id
     * @param session
     * @return
     */
    @Override
    public AdminCreditUpdateResponse rejectApply(Long id, Session session) {
        AdminCredit adminCredit=adminCreditDao.selectByPrimaryKey(id);
        if(adminCredit==null||adminCredit.getState()==null){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE,"参数异常！");
        }
        if(adminCredit.getState()!=0){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE,"状态已过期！");
        }
        adminCredit.setState(2);
        adminCredit.setExecuteUserId(session.getId());
        adminCredit.setUpdateTime(new Date());
        int res=adminCreditDao.rejectApply(adminCredit);
        if(res==0){
            return new AdminCreditUpdateResponse(ResultCode.FAIL_CODE,"驳回失败！");
        }
        return new AdminCreditUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}