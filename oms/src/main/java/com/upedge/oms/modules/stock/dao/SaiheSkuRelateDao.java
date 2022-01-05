package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;

import java.util.List;

/**
 * @author gx
 */
public interface SaiheSkuRelateDao{

    SaiheSkuRelate selectByPrimaryKey(String variantSku);

    int deleteByPrimaryKey(SaiheSkuRelate record);

    int updateByPrimaryKey(SaiheSkuRelate record);

    int updateByPrimaryKeySelective(SaiheSkuRelate record);

    int insert(SaiheSkuRelate record);

    int insertSelective(SaiheSkuRelate record);

    int insertByBatch(List<SaiheSkuRelate> list);

    List<SaiheSkuRelate> select(Page<SaiheSkuRelate> record);

    long count(Page<SaiheSkuRelate> record);

}
