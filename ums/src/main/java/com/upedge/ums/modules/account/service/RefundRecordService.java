package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.RefundRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RefundRecordService{

    RefundRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(RefundRecord record);

    int updateByPrimaryKeySelective(RefundRecord record);

    int insert(RefundRecord record);

    int insertSelective(RefundRecord record);

    List<RefundRecord> select(Page<RefundRecord> record);

    long count(Page<RefundRecord> record);
}

