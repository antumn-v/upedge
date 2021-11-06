package com.upedge.pms.modules.inventory.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.pms.modules.inventory.entity.ProductSaiheInventory;
import com.upedge.pms.modules.inventory.request.SaiheInvebtoryRequest;

import java.util.List;

/**
 * @author Ï¦Îí
 */
public interface ProductSaiheInventoryService{

    ProductSaiheInventory selectByPrimaryKey(String variantSku);

    int deleteByPrimaryKey(String variantSku);

    int updateByPrimaryKey(ProductSaiheInventory record);

    int updateByPrimaryKeySelective(ProductSaiheInventory record);

    int insert(ProductSaiheInventory record);

    int insertSelective(ProductSaiheInventory record);

    List<ProductSaiheInventory> select(Page<ProductSaiheInventory> record);

    long count(Page<ProductSaiheInventory> record);

    /**
     * 从赛盒同步库存信息
     */
    void batchRefreshInventory() throws CustomerException;

    List<ProductSaiheInventoryVo> queryProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory);

    void insertProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory);

    /**
     * 分页列表
     * @param request
     * @return
     */
    BaseResponse selectProductSaiheInventoryPage(SaiheInvebtoryRequest request);

    /**
     * 客户库存分析列表   所有库存金额
     * @param request
     * @return
     */
    BaseResponse selectProductSaiheInventoryListSumMoney(SaiheInvebtoryRequest request);
}

