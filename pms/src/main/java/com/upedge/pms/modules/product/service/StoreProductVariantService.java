package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.StoreProductVariantQuoteRequest;
import com.upedge.pms.modules.product.request.StoreProductVariantSplitRequest;
import com.upedge.pms.modules.product.request.StoreProductVariantUnSplitListRequest;
import com.upedge.pms.modules.product.request.StoreSplitVariantUpdateRequest;
import com.upedge.pms.modules.product.vo.SplitVariantIdVo;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductVariantService{

    void uploadShopifyImage(List<ShopifyImage> shopifyImages,Long storeProductId);

    Long countCustomerUnSplitVariant(StoreProductVariantUnSplitListRequest request);

    List<StoreProductVariant> selectCustomerUnSplitVariant(StoreProductVariantUnSplitListRequest request);

    List<StoreProductVariant> selectByIds(List<Long> ids);

    StoreProductVariant selectByPlatId(String platVariantId);

    int updateId(Long oldId, Long newId);

    List<StoreProductVariant> selectSplitVariantsByPlatVariantId(String platVariantId);

    List<StoreProductVariant> selectWrongParentVariants();

    BaseResponse variantQuote(StoreProductVariantQuoteRequest request, Session session);

    List<SplitVariantIdVo> selectSplitVariantIds();

    BaseResponse splitVariantDelete(Long storeVariantId,Session session);

    BaseResponse splitVariantUpdate(StoreSplitVariantUpdateRequest request,Session session);

    List<StoreProductVariant> selectSplitVariantByParentId(Long parentVariantId);

    BaseResponse variantSplit(StoreProductVariantSplitRequest request, Session session);

    List<StoreProductVariant> selectBySku(String sku);

    List<CustomerProductQuoteVo> selectQuoteDetailByIds(List<Long> ids);

    StoreProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductVariant record);

    int updateByPrimaryKeySelective(StoreProductVariant record);

    int insert(StoreProductVariant record);

    int insertByBatch(List<StoreProductVariant> storeProductVariants);

    int insertSelective(StoreProductVariant record);

    List<StoreProductVariant> select(Page<StoreProductVariant> record);

    long count(Page<StoreProductVariant> record);

    boolean redisCheckIfSplitVariant(Long storeVariantId);

    List<Long> getSplitVariantIdsByParentId(Long storeVariantId);

    StoreProductVariant createCustomVariant(String sku,String image,String saleLink,Session session);

    List<StoreProductRelateVo> selectStoreVariantRelateDetail(Long storeProductId);

    List<StoreProductVariant> listUseVariantProductId(Long id);

    StoreProductVariantVo selectByPlatVariantId(Long storeId, String platVariantId, String platProductId);

    List<String> selectPlatVariantIdByProductId(Long storeProductId);

    void updateByBatch(List<StoreProductVariant> updateVariants);

    void markStoreVariantAsRemovedByPlatId(Long storeProductId, List<String> platVariantIds);

    void updateAdminVariantIdByImportId(Long id, Long storeProductId);

    void updateByPlatVariantId(StoreProductVariant variant);

    List<StoreProductVariant> selectByPlatVariantIds(Long storeProductId, List<String> platVariantIds);

    void updateImageByPlatVariantIds(String newImage, Long storeProductId, List<String> platVariantIds);
}

