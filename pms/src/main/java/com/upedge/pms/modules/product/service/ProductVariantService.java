package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.vo.SaiheSkuVo;
import com.upedge.pms.modules.product.vo.VariantAttrVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gx
 */
public interface ProductVariantService{

    BaseResponse updateSku(ProductVariantUpdateSkuRequest request,Session session);

    BaseResponse updateAttrs(ProductVariantUpdateRequest request,Session session);

    BaseResponse addVariant(ProductVariantAddRequest request,Session session);

    int updateSaiheSku(List<ProductVariant> variants);

    int updateLatestQuotePrice(Long id,BigDecimal quotePrice);

    List<SaiheSkuVo> selectSaiheSkuVoByProductId(Long productId);

    SaiheSkuVo selectSaiheSkuVoById(Long id);

    ProductVariantUpdateWeightResponse updateWeight(ProductVariantUpdateWeightRequest request, Session session) throws CustomerException;

    ProductVariantUpdateVolumeWeightResponse updateVolumeWeight(ProductVariantUpdateVolumeWeightRequest request, Session session) throws CustomerException;

    ProductVariantUpdateVariantImageResponse updateVariantImage(ProductVariantUpdateVariantImageRequest request, Session session);

    ProductVariantEnableResponse enableVariant(ProductVariantEnableRequest request);

    ProductVariantDisableResponse disableVariant(ProductVariantDisableRequest request);

    ProductVariantUpdateAttrResponse updateAttr(ProductVariantUpdateAttrRequest request);

    ProductVariantUpdatePriceResponse updatePrice(ProductVariantUpdatePriceRequest request, Session session) throws CustomerException;

    List<ProductVariant> listVariantByIds(List<Long> variantIds);

    Map<String, BigDecimal> selectVariantPriceRange(Long productId);

    List<SaiheSkuVo> listSaiheSkuVo(@Param("ids") List<Long> ids);

    List<ProductVariant> getProductVariantList(List<Long> variantIds, Map<String, VariantAttrVo> attrMap, Map<String, Set<String>> attrValSet, Map<Long, ProductVariant> productVariantMap);

    List<ProductVariant> selectByProductId(Long productId);

    int insertByBatch(List<ProductVariant> productVariants);

    ProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductVariant record);

    int updateByPrimaryKeySelective(ProductVariant record);

    int insert(ProductVariant record);

    int insertSelective(ProductVariant record) throws Exception;

    List<ProductVariant> select(Page<ProductVariant> record);

    long count(Page<ProductVariant> record);

    ProductVariant selectBySku(String variantSku);

    List<CustomerProductQuoteVo> selectQuoteProductBySkus(List<String> skus);
}

