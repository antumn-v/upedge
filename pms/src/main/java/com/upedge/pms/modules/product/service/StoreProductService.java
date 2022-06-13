package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.store.StoreVo;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.response.StoreProductListResponse;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyProduct;
import com.upedge.thirdparty.shoplazza.moudles.product.entity.ShoplazzaProduct;
import com.upedge.thirdparty.woocommerce.moudles.product.entity.WoocProduct;

import java.util.List;

/**
 * @author 海桐
 */
public interface StoreProductService {

    /**
     * 店铺产品关联详情
     * @param storeProductId
     * @return
     */
    List<StoreProductRelateVo> selectStoreVariantRelateDetail(Long storeProductId);

    BaseResponse toNormalProduct(Long id, Long managerId);

    List<StoreProductVariantVo> selectVariantByPlatId(PlatIdSelectStoreVariantRequest request);

    StoreProductListResponse storeProductList(StoreProductListRequest request);

    Long saveWoocProduct(WoocProduct product, StoreVo storeVo);

    Long saveShopifyProduct(ShopifyProduct product, StoreVo storeVo);

    Long saveShoplazzaProduct(ShoplazzaProduct product, StoreVo storeVo);

    void storeProductDeleteByStore(String platProductId, Long storeId);

    BaseResponse storeProductListCount(StoreProductListRequest request);
}
