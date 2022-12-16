package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.dto.VariantPurchaseInfoDto;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author gx
 */
public interface ProductPurchaseInfoDao {

    List<VariantPurchaseInfoDto> selectByVariantIds(@Param("variantIds") List<Long> variantIds);

    void updateInventory(@Param("purchaseSku") String purchaseSku, @Param("productLink") String productLink, @Param("inventory") Integer inventory,@Param("minOrderQuantity") Integer minOrderQuantity);

    List<ProductPurchaseInfo> selectByPurchaseLink(String purchaseLink);

    List<ProductPurchaseInfo> selectByPurchaseSkus(@Param("purchaseSkus") Set<String> purchaseSkus);

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
