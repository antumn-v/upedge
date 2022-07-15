package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface ProductPurchaseInfoDao {

    List<ProductPurchaseInfo> selectByPurchaseSkus(@Param("purchaseSkus") List<String> purchaseSkus);

    ProductPurchaseInfo selectByPrimaryKey(ProductPurchaseInfo record);

    int deleteByPrimaryKey(ProductPurchaseInfo record);

    int updateByPrimaryKey(ProductPurchaseInfo record);

    int updateByPrimaryKeySelective(ProductPurchaseInfo record);

    int insert(ProductPurchaseInfo record);

    int insertSelective(ProductPurchaseInfo record);

    int insertByBatch(List<ProductPurchaseInfo> list);

    List<ProductPurchaseInfo> select(Page<ProductPurchaseInfo> record);

    long count(Page<ProductPurchaseInfo> record);

}
