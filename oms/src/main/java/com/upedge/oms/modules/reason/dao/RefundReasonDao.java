package com.upedge.oms.modules.reason.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.reason.entity.RefundReason;

import java.util.List;

/**
 * @author author
 */
public interface RefundReasonDao{

    RefundReason selectByPrimaryKey(RefundReason record);

    int deleteByPrimaryKey(RefundReason record);

    int updateByPrimaryKey(RefundReason record);

    int updateByPrimaryKeySelective(RefundReason record);

    int insert(RefundReason record);

    int insertSelective(RefundReason record);

    int insertByBatch(List<RefundReason> list);

    List<RefundReason> select(Page<RefundReason> record);

    long count(Page<RefundReason> record);

    List<RefundReason> all();
}
