package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RefundRecord;
import com.upedge.ums.modules.account.vo.RecordVo;

import java.util.List;

/**
 * @author author
 */
public interface RefundRecordDao{

    RefundRecord selectByPrimaryKey(RefundRecord record);

    int deleteByPrimaryKey(RefundRecord record);

    int updateByPrimaryKey(RefundRecord record);

    int updateByPrimaryKeySelective(RefundRecord record);

    int insert(RefundRecord record);

    int insertSelective(RefundRecord record);

    int insertByBatch(List<RefundRecord> list);

    List<RefundRecord> select(Page<RefundRecord> record);

    long count(Page<RefundRecord> record);

    List<RecordVo> listRefundRecordByOrderId(Long orderId);
}
