package com.upedge.pms.modules.inventory.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.pms.modules.inventory.entity.ProductSaiheInventory;
import com.upedge.pms.modules.inventory.request.SaiheInvebtoryRequest;
import com.upedge.pms.modules.inventory.vo.SaiheInvebtoryVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ï¦Îí
 */
public interface ProductSaiheInventoryDao{

    ProductSaiheInventory selectByPrimaryKey(ProductSaiheInventory record);

    int deleteByPrimaryKey(ProductSaiheInventory record);

    int updateByPrimaryKey(ProductSaiheInventory record);

    int updateByPrimaryKeySelective(ProductSaiheInventory record);

    int insert(ProductSaiheInventory record);

    int insertSelective(ProductSaiheInventory record);

    int insertByBatch(List<ProductSaiheInventory> list);

    List<ProductSaiheInventory> select(Page<ProductSaiheInventory> record);

    long count(Page<ProductSaiheInventory> record);

    void updateCustomerStock(List<CustomerProductStockNumVo> result);

    int saveProductSaiheInventory(List<ProductSaiheInventory> inventoryList);

     List<ProductSaiheInventoryVo> queryProductSaiheInventory(@Param("t") ProductSaiheInventoryVo productSaiheInventory);

    void insertProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory);

    List<SaiheInvebtoryVo> selectSSPage(SaiheInvebtoryRequest request);

    Long pageSSCount(SaiheInvebtoryRequest request);

    BigDecimal selectSumMoney(SaiheInvebtoryRequest request);
}
