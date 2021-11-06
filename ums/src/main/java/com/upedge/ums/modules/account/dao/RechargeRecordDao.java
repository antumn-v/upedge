package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeRecord;
import com.upedge.ums.modules.account.vo.RecordVo;

import java.util.List;

/**
 * @author author
 */
public interface RechargeRecordDao{

    RechargeRecord selectByPrimaryKey(RechargeRecord record);

    int deleteByPrimaryKey(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    int insertByBatch(List<RechargeRecord> list);

    List<RechargeRecord> select(Page<RechargeRecord> record);

    long count(Page<RechargeRecord> record);

    /**
     * 获取关联的支付列表
     */
    List<RechargeRecord> listRechargeRecordRefundSeqByOrderId(Long orderId, Integer orderType);
    List<RecordVo> listRechargeRecordByOrderId(Long orderId);
}
