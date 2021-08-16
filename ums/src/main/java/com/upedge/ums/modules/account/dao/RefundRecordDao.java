package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RefundRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
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

}
