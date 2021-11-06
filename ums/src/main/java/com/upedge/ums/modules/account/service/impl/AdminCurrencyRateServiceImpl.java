package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.dao.AdminAccountDao;
import com.upedge.ums.modules.account.dao.AdminCurrencyRateDao;
import com.upedge.ums.modules.account.entity.AdminAccount;
import com.upedge.ums.modules.account.entity.AdminCurrencyRate;
import com.upedge.ums.modules.account.request.AdminCurrencyRateAddRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateListRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateUpdateRequest;
import com.upedge.ums.modules.account.response.AdminCurrencyRateAddResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateInfoResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateListResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateUpdateResponse;
import com.upedge.ums.modules.account.service.AdminCurrencyRateService;
import com.upedge.ums.modules.account.vo.AdminCurrencyRateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AdminCurrencyRateServiceImpl implements AdminCurrencyRateService {

    @Autowired
    private AdminCurrencyRateDao adminCurrencyRateDao;
    @Autowired
    private AdminAccountDao adminAccountDao;

    /**
     * 货币汇率信息
     * @param id
     * @return
     */
    @Override
    public AdminCurrencyRateInfoResponse info(Long id) {
        AdminCurrencyRate result = adminCurrencyRateDao.selectByPrimaryKey(id);
        return new AdminCurrencyRateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
    }

    /**
     * 添加货币汇率
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional
    public AdminCurrencyRateAddResponse addCurrencyRate(AdminCurrencyRateAddRequest request, Session session) {
        AdminCurrencyRate adminCurrencyRate=adminCurrencyRateDao.queryEntityByCurrency(request.getCurrencyCode());
        if(adminCurrencyRate!=null){
            return new AdminCurrencyRateAddResponse(ResultCode.FAIL_CODE,"货币代码重复");
        }
        AdminCurrencyRate entity=request.toAdminCurrencyRate(String.valueOf(session.getId()));
        entity.setCreateTime(new Date());
        adminCurrencyRateDao.insert(entity);
        return new AdminCurrencyRateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新货币汇率
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional
    public AdminCurrencyRateUpdateResponse updateCurrencyRate(AdminCurrencyRateUpdateRequest request, Session session) {
        AdminCurrencyRate entity=request.toAdminCurrencyRate(String.valueOf(session.getId()));
        entity.setUpdateTime(new Date());
        adminCurrencyRateDao.updateByPrimaryKeySelective(entity);
        return new AdminCurrencyRateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * admin货币汇率列表
     * @param request
     * @return
     */
    @Override
    public AdminCurrencyRateListResponse adminList(AdminCurrencyRateListRequest request) {
        request.initFromNum();
        List<AdminCurrencyRate> results = adminCurrencyRateDao.select(request);
        Long total = adminCurrencyRateDao.count(request);
        request.setTotal(total);
        List<AdminCurrencyRateVo> adminCurrencyRateVoList=new ArrayList<>();
        results.forEach(adminCurrencyRate -> {
            AdminCurrencyRateVo adminCurrencyRateVo=new AdminCurrencyRateVo();
            BeanUtils.copyProperties(adminCurrencyRate,adminCurrencyRateVo);
            if(adminCurrencyRate.getAccountId()!=null){
                AdminAccount adminAccount=adminAccountDao.selectByPrimaryKey(adminCurrencyRate.getAccountId());
                adminCurrencyRateVo.setAccountName(adminAccount.getAccountName());
            }
            adminCurrencyRateVoList.add(adminCurrencyRateVo);
        });
        return new AdminCurrencyRateListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,adminCurrencyRateVoList,request);
    }
}