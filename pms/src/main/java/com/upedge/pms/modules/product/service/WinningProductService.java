package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.request.PrivateWinningProductsRequest;

public interface WinningProductService {

    BaseResponse customerPrivateProducts(PrivateWinningProductsRequest request, Session session);
}
