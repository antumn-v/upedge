package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.vo.SaiheSkuVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author gx
 */
public interface ProductVariantDao {

    int updatePurchaseSkuByOriginalId(Long productId);

    int refreshTransformVariant(@Param("productId") Long productId, @Param("storeProductId") Long storeProductId);

    ProductVariant selectByBarcode(String barcode);

    Long selectMaxBarcode();

    void updateByBatch(List<ProductVariant> list);

    void updateWeight(@Param("ids") List<Long> ids, @Param("weight") BigDecimal weight);

    void updateVolumeWeight(@Param("ids") List<Long> ids,
                            @Param("volumeWeight") BigDecimal volumeWeight,
                            @Param("width") BigDecimal width,
                            @Param("height") BigDecimal height,
                            @Param("length") BigDecimal length);

    void updateVariantImage(@Param("ids") List<Long> ids, @Param("variantImage") String variantImage);

    int disableVariant(@Param("ids") List<Long> ids);

    int enableVariant(@Param("ids") List<Long> ids);

    void updatePrice(@Param("ids") List<Long> ids,
                     @Param("price") BigDecimal price,
                     @Param("usdPrice") BigDecimal usdPrice);

    int updateSaiheSku(@Param("variants") List<ProductVariant> variants);

    List<ProductVariant> listProductVariantByIds(@Param("ids") List<Long> ids);

    ProductVariant selectBySku(String sku);

    Map<String, BigDecimal> selectVariantPriceRange(Long productId);

    List<SaiheSkuVo> listSaiheSkuVo(@Param("ids") List<Long> ids);

    List<SaiheSkuVo> selectSaiheSkuVoByProductId(Long productId);

    SaiheSkuVo selectSaiheSkuVoById(Long id);

    List<ProductVariant> selectByProductId(Long productId);

    ProductVariant selectByPrimaryKey(ProductVariant record);

    int deleteByPrimaryKey(ProductVariant record);

    int updateByPrimaryKey(ProductVariant record);

    int updateByPrimaryKeySelective(ProductVariant record);

    int insert(ProductVariant record);

    int insertByBatch(List<ProductVariant> list);

    List<ProductVariant> select(Page<ProductVariant> record);

    long count(Page<ProductVariant> record);

    List<CustomerProductQuoteVo> selectQuoteProductBySkus(@Param("skus") List<String> skus);

}
