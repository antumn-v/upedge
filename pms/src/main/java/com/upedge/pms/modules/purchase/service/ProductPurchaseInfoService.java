package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;

import java.util.List;
import java.util.Set;

/**
 * @author gx
 */
public interface ProductPurchaseInfoService{

    List<ProductPurchaseInfo> selectByPurchaseSkus( Set<String> purchaseSkus);

    List<ProductPurchaseInfo> selectByPurchaseLink( String purchaseLink);

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

