package com.upedge.oms.modules.wholesale.service;

import com.upedge.oms.modules.wholesale.entity.WholesaleShipReviewRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface WholesaleShipReviewRecordService{

    WholesaleShipReviewRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleShipReviewRecord record);

    int updateByPrimaryKeySelective(WholesaleShipReviewRecord record);

    int insert(WholesaleShipReviewRecord record);

    int insertSelective(WholesaleShipReviewRecord record);

    List<WholesaleShipReviewRecord> select(Page<WholesaleShipReviewRecord> record);

    long count(Page<WholesaleShipReviewRecord> record);
}

