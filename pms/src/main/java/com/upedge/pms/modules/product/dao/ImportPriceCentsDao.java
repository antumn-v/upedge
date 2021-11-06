package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportPriceCents;

import java.util.List;

/**
 * @author gx
 */
public interface ImportPriceCentsDao{

    ImportPriceCents selectByCustomerId(Long customerId);

    int deleteByCustomerId(Long customerId);

    ImportPriceCents selectByPrimaryKey(ImportPriceCents record);

    int deleteByPrimaryKey(ImportPriceCents record);

    int updateByPrimaryKey(ImportPriceCents record);

    int updateByPrimaryKeySelective(ImportPriceCents record);

    int insert(ImportPriceCents record);

    int insertSelective(ImportPriceCents record);

    int insertByBatch(List<ImportPriceCents> list);

    List<ImportPriceCents> select(Page<ImportPriceCents> record);

    long count(Page<ImportPriceCents> record);

}
