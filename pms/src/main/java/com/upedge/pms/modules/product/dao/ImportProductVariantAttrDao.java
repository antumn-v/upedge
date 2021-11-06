package com.upedge.pms.modules.product.dao;

import com.upedge.thirdparty.woocommerce.moudles.product.entity.WoocProductAttr;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyOptions;


import java.util.List;

/**
 * @author author
 */
public interface ImportProductVariantAttrDao{

    List<WoocProductAttr> selectForWoocAttributeByProduct(Long productId);

    List<ShopifyOptions> selectForShopifyOption(Long productId);

    ImportProductVariantAttr selectByPrimaryKey(ImportProductVariantAttr record);

    int updateShipFromValueByProduct(Long productId);

    int deleteByProductId(Long productId);

    int deleteByPrimaryKey(ImportProductVariantAttr record);

    int updateByPrimaryKey(ImportProductVariantAttr record);

    int updateByPrimaryKeySelective(ImportProductVariantAttr record);

    int insert(ImportProductVariantAttr record);

    int insertSelective(ImportProductVariantAttr record);

    int insertByBatch(List<ImportProductVariantAttr> list);

    List<ImportProductVariantAttr> select(Page<ImportProductVariantAttr> record);

    long count(Page<ImportProductVariantAttr> record);

}
