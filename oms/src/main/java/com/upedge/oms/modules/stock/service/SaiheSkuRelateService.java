package com.upedge.oms.modules.stock.service;

import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface SaiheSkuRelateService{

    SaiheSkuRelate selectByPrimaryKey(String variantSku);

    int deleteByPrimaryKey(String variantSku);

    int updateByPrimaryKey(SaiheSkuRelate record);

    int updateByPrimaryKeySelective(SaiheSkuRelate record);

    int insert(SaiheSkuRelate record);

    int insertSelective(SaiheSkuRelate record);

    List<SaiheSkuRelate> select(Page<SaiheSkuRelate> record);

    long count(Page<SaiheSkuRelate> record);
}

