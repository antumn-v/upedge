package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockService{


    BaseResponse updateVariantStock(VariantStockUpdateRequest request, Session session);

    VariantWarehouseStock selectByPrimaryKey(Long variantId,String warehouseCode);

    int deleteByPrimaryKey(Long variantId);

    int updateByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKeySelective(VariantWarehouseStock record);

    int insert(VariantWarehouseStock record);

    int insertSelective(VariantWarehouseStock record);

    List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record);

    long count(Page<VariantWarehouseStock> record);
}

