package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductPurchaseInfoService{

    ProductPurchaseInfo selectByPrimaryKey(String purchaseSku);

    int deleteByPrimaryKey(String purchaseSku);

    int updateByPrimaryKey(ProductPurchaseInfo record);

    int updateByPrimaryKeySelective(ProductPurchaseInfo record);

    int insert(ProductPurchaseInfo record);

    int insertByBatch(List<ProductPurchaseInfo> records);

    int insertSelective(ProductPurchaseInfo record);

    List<ProductPurchaseInfo> select(Page<ProductPurchaseInfo> record);

    long count(Page<ProductPurchaseInfo> record);
}

