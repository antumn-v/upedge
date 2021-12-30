package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleShipReviewRecord;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleShipReviewRecordDao{

    WholesaleShipReviewRecord selectByPrimaryKey(WholesaleShipReviewRecord record);

    int deleteByPrimaryKey(WholesaleShipReviewRecord record);

    int updateByPrimaryKey(WholesaleShipReviewRecord record);

    int updateByPrimaryKeySelective(WholesaleShipReviewRecord record);

    int insert(WholesaleShipReviewRecord record);

    int insertSelective(WholesaleShipReviewRecord record);

    int insertByBatch(List<WholesaleShipReviewRecord> list);

    List<WholesaleShipReviewRecord> select(Page<WholesaleShipReviewRecord> record);

    long count(Page<WholesaleShipReviewRecord> record);

}
