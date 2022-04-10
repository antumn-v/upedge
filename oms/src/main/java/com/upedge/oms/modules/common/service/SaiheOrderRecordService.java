package com.upedge.oms.modules.common.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;

import java.util.List;

/**
 * @author gx
 */
public interface SaiheOrderRecordService{

    List<SaiheOrderRecord> selectTwiceUploadOrder();

    SaiheOrderRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(SaiheOrderRecord record);

    int updateByPrimaryKeySelective(SaiheOrderRecord record);

    int insert(SaiheOrderRecord record);

    int insertSelective(SaiheOrderRecord record);

    List<SaiheOrderRecord> select(Page<SaiheOrderRecord> record);

    long count(Page<SaiheOrderRecord> record);
}

