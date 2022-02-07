package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.request.PrivateWinningProductsRequest;
import com.upedge.pms.modules.product.service.CustomerPrivateProductService;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.WinningProductService;
import com.upedge.pms.modules.product.vo.AppProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinningProductServiceImpl implements WinningProductService {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerPrivateProductService customerPrivateProductService;

    @Override
    public BaseResponse customerPrivateProducts(PrivateWinningProductsRequest request, Session session) {
        request.setCustomerId(session.getCustomerId());
        List<AppProductVo> appProductVos = customerPrivateProductService.selectPrivateWinningProducts(request);
        productService.checkImportProducts(appProductVos,session.getCustomerId());
        Long total = customerPrivateProductService.countPrivateWinningProducts(request);
        request.setTotal(total);
        return BaseResponse.success(appProductVos,request);
    }
}
