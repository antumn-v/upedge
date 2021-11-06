package com.upedge.oms.modules.vat.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.entity.VatRuleLog;

import java.util.List;

/**
 * @author author
 */
public interface VatRuleLogDao{

    VatRuleLog selectByPrimaryKey(VatRuleLog record);

    int deleteByPrimaryKey(VatRuleLog record);

    int updateByPrimaryKey(VatRuleLog record);

    int updateByPrimaryKeySelective(VatRuleLog record);

    int insert(VatRuleLog record);

    int insertSelective(VatRuleLog record);

    int insertByBatch(List<VatRuleLog> list);

    List<VatRuleLog> select(Page<VatRuleLog> record);

    long count(Page<VatRuleLog> record);

}
