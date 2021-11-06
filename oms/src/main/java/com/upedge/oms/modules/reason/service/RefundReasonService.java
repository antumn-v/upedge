package com.upedge.oms.modules.reason.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.reason.entity.RefundReason;
import com.upedge.oms.modules.reason.response.RefundReasonListResponse;

import java.util.List;

/**
 * @author author
 */
public interface RefundReasonService{

    RefundReason selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(RefundReason record);

    int updateByPrimaryKeySelective(RefundReason record);

    int insert(RefundReason record);

    int insertSelective(RefundReason record);

    List<RefundReason> select(Page<RefundReason> record);

    long count(Page<RefundReason> record);

    RefundReasonListResponse all();
}

