package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.AccountPayMethodMapper;
import com.upedge.ums.modules.account.dao.PayMethodAttrMapper;
import com.upedge.ums.modules.account.dao.PayMethodMapper;
import com.upedge.ums.modules.account.entity.PayMethod;
import com.upedge.ums.modules.account.entity.PayMethodAttr;
import com.upedge.ums.modules.account.request.PayMethodAddUpdateRequest;
import com.upedge.ums.modules.account.request.PayMethodAttrsListRequest;
import com.upedge.ums.modules.account.request.PayMethodListRequest;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.PayMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guoxing on 2020/10/28.
 */
@Service
public class PayMethodServiceImpl implements PayMethodService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PayMethodMapper payMethodMapper;

    @Autowired
    PayMethodAttrMapper payMethodAttrMapper;

    @Autowired
    AccountPayMethodMapper accountPayMethodMapper;

    /**
     * 支付方式列表
     * @return
     */
    @Override
    public PayMethodListResponse listPayMethod(PayMethodListRequest request) {
        if(request.getT() == null){
            request.setT(new PayMethod());
        }

        List<PayMethod> list=payMethodMapper.listPayMethod(request);

        Long total = payMethodMapper.countPayMethod(request);
        request.setTotal(total);

        return new PayMethodListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,list,request);
    }

    @Override
    public PayMethodAttrsListResponse selectPayMethodAttr(PayMethodAttrsListRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        Integer userType = session.getUserType();
        if(null == request.getT()){
            request.setT(new PayMethodAttr());
        }
        if(userType != 0){
            request.getT().setObtainType(0);
        }

        List<PayMethodAttr> attrs = payMethodAttrMapper.selectPayMethodAttrs(request);

        Long total = payMethodAttrMapper.countPayMethodAttrs(request);

        request.setTotal(total);

        return new PayMethodAttrsListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,attrs,request);
    }

    @Transactional
    @Override
    public PayMethodAddUpdateResponse addPayMethod(PayMethodAddUpdateRequest request) {
        PayMethod payMethod = payMethodMapper.selectByRouteName(request.getRouteName());
        if(null == payMethod){
            payMethod = request.toPayMethod();
            payMethodMapper.insert(payMethod);
            addPaymethodAttrs(request,payMethod.getId());
        }else {
            if(payMethod.getStatus() == 1){
                return new PayMethodAddUpdateResponse(ResultCode.FAIL_CODE,"Cannot add the same payment method");
            }else {
                Integer id = payMethod.getId();
                updatePaymethod(request,id);
            }
        }
        return new PayMethodAddUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public PayMethodAddUpdateResponse updatePayMethod(Integer payMethodId, PayMethodAddUpdateRequest request) {
        PayMethod payMethod = payMethodMapper.selectByPrimaryKey(payMethodId);
        if(null == payMethod || payMethod.getStatus() == 0){
            return new PayMethodAddUpdateResponse(ResultCode.FAIL_CODE,"PayMethod does not exist");
        }
        updatePaymethod(request,payMethodId);

        return new PayMethodAddUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }


    @Transactional
    @Override
    public PayMethodDisableResponse disablePayMethod(Integer id) {

        payMethodMapper.disablePayMethodById(id);

        accountPayMethodMapper.disableAccountPayMethod(id);

        return new PayMethodDisableResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public PayMethodEnableResponse enablePayMethod(Integer id) {

        payMethodMapper.enablePayMethodById(id);

        return new PayMethodEnableResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 插入支付属性
     * @param request
     * @param paymethodId
     */
    private void addPaymethodAttrs(PayMethodAddUpdateRequest request, Integer paymethodId){

        List<PayMethodAttr> methodAttrs = request.toPayMethodAttrs(paymethodId);
        if(null != methodAttrs){
            payMethodAttrMapper.batchInsert(methodAttrs);
        }
    }

    /**
     * 修改支付方式，重新插入属性
     * @param request
     * @param paymethodId
     */
    private void updatePaymethod(PayMethodAddUpdateRequest request, Integer paymethodId){

        PayMethod payMethod = request.toPayMethod();
        payMethod.setId(paymethodId);
        payMethodMapper.updateByPrimaryKey(payMethod);

        payMethodAttrMapper.deleteByPayMethodId(paymethodId);
        addPaymethodAttrs(request,paymethodId);
    }
}
