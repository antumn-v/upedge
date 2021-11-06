package com.upedge.ums.modules.account.service;

import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.request.AdminCurrencyRateAddRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateListRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateUpdateRequest;
import com.upedge.ums.modules.account.response.AdminCurrencyRateAddResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateInfoResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateListResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateUpdateResponse;

/**
 * @author author
 */
public interface AdminCurrencyRateService{

    AdminCurrencyRateListResponse adminList(AdminCurrencyRateListRequest request);

    AdminCurrencyRateInfoResponse info(Long id);

    AdminCurrencyRateAddResponse addCurrencyRate(AdminCurrencyRateAddRequest request, Session session);

    AdminCurrencyRateUpdateResponse updateCurrencyRate(AdminCurrencyRateUpdateRequest request, Session session);
}

