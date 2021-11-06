package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.vo.AppProductVariantVo;

import java.util.List;

public interface AppProductVariantDao {

    /**
     * 变体信息和属性
     * @param productId
     * @return
     */
    List<AppProductVariantVo> selectProductVariantsByProductId(Long productId);
}
