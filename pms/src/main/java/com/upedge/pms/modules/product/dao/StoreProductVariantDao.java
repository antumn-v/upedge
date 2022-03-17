package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.vo.CustomerProductVariantRelateVo;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductVariantDao {

    List<StoreProductVariant> selectSplitVariantByParentId(Long parentVariantId);

    List<StoreProductVariant> selectBySku(String sku);

    boolean updateAdminVariantIdByImportId(@Param("importProductId") Long importProductId, @Param("storeProductId") Long storeProductId);

    /**
     * 店铺产品关联详情
     *
     * @param storeProductId
     * @return
     */
    List<StoreProductRelateVo> selectStoreVariantRelateDetail(Long storeProductId);

    List<CustomerProductQuoteVo> selectQuoteDetailByIds(@Param("ids") List<Long> ids);

    StoreProductVariantVo selectByPlatVariantId(@Param("storeId") Long storeId,
                                                @Param("platVariantId") String platVariantId,
                                                @Param("platProductId") String platProductId);

    List<StoreProductVariant> selectByIds(@Param("ids") List<Long> ids);

    List<StoreProductVariant> selectUnRelateVariantByProduct(Long storeProductId);


    List<Long> selectIdByProductId(Long productId);

    int updateByPlatVariantId(StoreProductVariant variant);

    List<String> selectPlatVariantIdByProductId(Long productId);

    StoreProductVariant selectByPrimaryKey(Long id);

    /**
     * 店铺变体标记为已删除
     *
     * @param productId
     * @param platVariantIds
     * @return
     */
    int markStoreVariantAsRemovedByPlatId(@Param("productId") Long productId,
                                          @Param("platVariantIds") List<String> platVariantIds);

    /**
     * 修改shopify变体图片
     *
     * @param productId
     * @param image
     * @return
     */
    int updateVariantImageByShopifyImage(@Param("productId") Long productId,
                                         @Param("images") List<ShopifyImage> images);

    int deleteByPrimaryKey(StoreProductVariant record);

    int updateByPrimaryKey(StoreProductVariant record);

    int updateByPrimaryKeySelective(StoreProductVariant record);

    int insert(StoreProductVariant record);

    int insertSelective(StoreProductVariant record);

    int insertByBatch(List<StoreProductVariant> list);

    int updateByBatch(@Param("variants") List<StoreProductVariant> variants);

    List<StoreProductVariant> select(Page<StoreProductVariant> record);

    long count(Page<StoreProductVariant> record);

    List<StoreProductVariant> listUseVariantProductId(Long productId);

    List<CustomerProductVariantRelateVo> listUseVariantRelateInfoProductId(Long productId);

    void insertReplace(StoreProductVariant storeProductVariant);
}
