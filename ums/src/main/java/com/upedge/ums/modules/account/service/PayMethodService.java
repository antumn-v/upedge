package com.upedge.ums.modules.account.service;


import com.upedge.ums.modules.account.request.PayMethodAddUpdateRequest;
import com.upedge.ums.modules.account.request.PayMethodAttrsListRequest;
import com.upedge.ums.modules.account.request.PayMethodListRequest;
import com.upedge.ums.modules.account.response.*;


/**
 * @author 海桐
 */
public interface PayMethodService {
    /**
     * 支付方式列表
     * @return
     */
    PayMethodListResponse listPayMethod(PayMethodListRequest request);

    /**
     * 查询支付方式属性
     * @param request
     * @return
     */
    PayMethodAttrsListResponse selectPayMethodAttr(PayMethodAttrsListRequest request);

    /**
     * 添加支付方式
     * @param request
     * @return
     */
    PayMethodAddUpdateResponse addPayMethod(PayMethodAddUpdateRequest request);

    /**
     * 修改支付方式
     * @param payMethodId
     * @param request
     * @return
     */
    PayMethodAddUpdateResponse updatePayMethod(Integer payMethodId, PayMethodAddUpdateRequest request);

    /**
     * 禁用支付方式
     * @param id
     * @return
     */
    PayMethodDisableResponse disablePayMethod(Integer id);

    /**
     * 启用支付方式
     * @param id
     * @return
     */
    PayMethodEnableResponse enablePayMethod(Integer id);



}
