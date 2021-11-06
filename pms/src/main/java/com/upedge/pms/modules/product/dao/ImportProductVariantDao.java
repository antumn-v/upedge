package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductVariant;
import com.upedge.pms.modules.product.request.ImportVariantBatchUpdateRequest;
import com.upedge.pms.modules.product.vo.ImportVariantVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyVariant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductVariantDao{

    int updateStateByIds(@Param("ids") List<Long> ids, Integer state);

    int updateVariantPriceByProduct(ImportVariantBatchUpdateRequest request);

    List<ImportProductVariant> selectByEntity(ImportProductVariant variant);

    Long countByEntity(ImportProductVariant variant);

    List<ImportVariantVo> selectByProduct(Long productId);

    List<ImportVariantVo> selectForUploadStore(Long productId);

    ImportProductVariant selectByPrimaryKey(ImportProductVariant record);

    int updatePlatIdBySku(@Param("variants") List<ShopifyVariant> variants,
                          @Param("productId") Long productId);



    int deleteVariantByProductId(Long productId);

    int deleteByPrimaryKey(ImportProductVariant record);

    int updateByPrimaryKey(ImportProductVariant record);

    int updateByPrimaryKeySelective(ImportProductVariant record);

    int insert(ImportProductVariant record);

    int insertSelective(ImportProductVariant record);

    int insertByBatch(List<ImportProductVariant> list);

    List<ImportProductVariant> select(Page<ImportProductVariant> record);

    long count(Page<ImportProductVariant> record);

}
