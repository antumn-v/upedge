package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ImportPublishedRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ImportPublishedRecordDao{

    ImportPublishedRecord selectByPrimaryKey(ImportPublishedRecord record);

    int deleteByPrimaryKey(ImportPublishedRecord record);

    int updateByPrimaryKey(ImportPublishedRecord record);

    int updateByPrimaryKeySelective(ImportPublishedRecord record);

    int insert(ImportPublishedRecord record);

    int insertSelective(ImportPublishedRecord record);

    int insertByBatch(List<ImportPublishedRecord> list);

    List<ImportPublishedRecord> select(Page<ImportPublishedRecord> record);

    long count(Page<ImportPublishedRecord> record);

}
