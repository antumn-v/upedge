package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.AdminVariantSku;

import java.util.List;

/**
 * @author author
 */
public interface AdminVariantSkuDao{

    AdminVariantSku selectByPrimaryKey(String variantSku);

    int deleteByPrimaryKey(AdminVariantSku record);

    int updateByPrimaryKey(AdminVariantSku record);

    int updateByPrimaryKeySelective(AdminVariantSku record);

    int insert(AdminVariantSku record);

    int insertSelective(AdminVariantSku record);

    int insertByBatch(List<AdminVariantSku> list);

    List<AdminVariantSku> select(Page<AdminVariantSku> record);

    long count(Page<AdminVariantSku> record);

}
