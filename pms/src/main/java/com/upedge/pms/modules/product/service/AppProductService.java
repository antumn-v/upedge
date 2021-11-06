package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.response.ProductImgListResponse;
import com.upedge.pms.modules.product.request.AppVariantShipsRequest;
import com.upedge.pms.modules.product.request.MarketPlaceListRequest;
import com.upedge.pms.modules.product.request.PrivateProductListRequest;
import com.upedge.pms.modules.product.response.AppVariantShipsResponse;
import com.upedge.pms.modules.product.response.MarketPlaceListResponse;
import com.upedge.pms.modules.product.vo.AppProductVariantVo;

import java.util.List;

/**
 * @author 海桐
 */
public interface AppProductService {

    MarketPlaceListResponse marketPlaceList(MarketPlaceListRequest request, Session session);

    /**
     * 私有产品
     * @param request
     * @return
     */
    MarketPlaceListResponse customerPrivateProduct(PrivateProductListRequest request);

    List<AppProductVariantVo> productVariants(Long productId);

    ProductInfo selectProductInfo(Long productId);

    ProductImgListResponse selectProductImages(Long productId);

    AppVariantShipsResponse variantShips(AppVariantShipsRequest request, Session session);

    BaseResponse productVariantsShipList(ProductVariantShipsRequest request);
}
