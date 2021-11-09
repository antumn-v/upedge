package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ImportPublishedRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ImportPublishedRecordService{

    ImportPublishedRecord selectByPrimaryKey(Long importProductId);

    int deleteByPrimaryKey(Long importProductId);

    int updateByPrimaryKey(ImportPublishedRecord record);

    int updateByPrimaryKeySelective(ImportPublishedRecord record);

    int insert(ImportPublishedRecord record);

    int insertSelective(ImportPublishedRecord record);

    List<ImportPublishedRecord> select(Page<ImportPublishedRecord> record);

    long count(Page<ImportPublishedRecord> record);
}

