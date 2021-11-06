package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleExcelRecord;
import com.upedge.oms.modules.wholesale.vo.WholesaleNameNumber;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleExcelRecordDao{

    List<WholesaleNameNumber> selectNameNumbersByCustomer(Long customerId);

    WholesaleExcelRecord selectByPrimaryKey(WholesaleExcelRecord record);

    int deleteByPrimaryKey(WholesaleExcelRecord record);

    int updateByPrimaryKey(WholesaleExcelRecord record);

    int updateByPrimaryKeySelective(WholesaleExcelRecord record);

    int insert(WholesaleExcelRecord record);

    int insertSelective(WholesaleExcelRecord record);

    int insertByBatch(List<WholesaleExcelRecord> list);

    List<WholesaleExcelRecord> select(Page<WholesaleExcelRecord> record);

    long count(Page<WholesaleExcelRecord> record);

}
