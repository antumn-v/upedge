package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductImage;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author author
 */
public interface ImportProductImageDao{

    LinkedList<ShopifyImage> selectForShopifyImage(Long productId);

    ImportProductImage selectByPrimaryKey(ImportProductImage record);

    int deleteImageByProductId(Long productId);

    int deleteByPrimaryKey(ImportProductImage record);

    int updateByPrimaryKey(ImportProductImage record);

    int updateByPrimaryKeySelective(ImportProductImage record);

    int insert(ImportProductImage record);

    int insertSelective(ImportProductImage record);

    int insertByBatch(List<ImportProductImage> list);

    LinkedList<ImportProductImage> select(Page<ImportProductImage> record);

    long count(Page<ImportProductImage> record);

}
