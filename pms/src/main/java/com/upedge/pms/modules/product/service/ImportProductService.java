package com.upedge.pms.modules.product.service;

import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;

/**
 * @author 海桐
 */
public interface ImportProductService {


    /**
     * 添加速卖通产品
     * @param url 速卖通产品链接
     * @return
     */
    ImportAeProductResponse importAeProduct(String url, Session session, String aeToken);

    ImportAddAppProductResponse addAppProduct(ImportAddAppProductRequest request);

    /**
     * 查询产品变体
     * @param productId
     * @return
     */
    ImportProductVariantListResponse selectProductVariants(Long productId);

    ImportProductDescriptionListResponse selectProductDescription(Long productId);

    ImportVariantBatchUpdateResponse batchUpdateVariantPrice(ImportVariantBatchUpdateRequest request);

    VariantUpdateStateResponse updateVariantState(ImportVariantUpdateStateRequest request);

    ImportProductAttributeDelResponse importProductRemove(ImportListRemoveRequest request);

    /**
     * 导入产品到shopify
     * @param productId
     * @return
     */
    Boolean uploadProductToShopify(StoreVo storeVo, Long productId);

    /**
     * 导入产品到woocommerce
     * @param productId
     * @return
     */
    Boolean uploadProductToWoocommerce(StoreVo storeVo, Long productId);

}
