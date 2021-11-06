package com.upedge.oms.modules.common.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;

import java.util.List;

/**
 * @author gx
 */
public interface SaiheOrderRecordDao{

    SaiheOrderRecord selectByClientOrderCode(String clientOrderCode);

    SaiheOrderRecord selectByPrimaryKey(SaiheOrderRecord record);

    int deleteByPrimaryKey(SaiheOrderRecord record);

    int updateByPrimaryKey(SaiheOrderRecord record);

    int updateByPrimaryKeySelective(SaiheOrderRecord record);

    int insert(SaiheOrderRecord record);

    int insertSelective(SaiheOrderRecord record);

    int insertByBatch(List<SaiheOrderRecord> list);

    List<SaiheOrderRecord> select(Page<SaiheOrderRecord> record);

    long count(Page<SaiheOrderRecord> record);

}
