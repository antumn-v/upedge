package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductPurchaseInfoDao{

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
