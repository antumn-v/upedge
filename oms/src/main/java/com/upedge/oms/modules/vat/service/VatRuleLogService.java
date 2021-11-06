package com.upedge.oms.modules.vat.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.entity.VatRuleLog;

import java.util.List;

/**
 * @author author
 */
public interface VatRuleLogService{

    VatRuleLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(VatRuleLog record);

    int updateByPrimaryKeySelective(VatRuleLog record);

    int insert(VatRuleLog record);

    int insertSelective(VatRuleLog record);

    List<VatRuleLog> select(Page<VatRuleLog> record);

    long count(Page<VatRuleLog> record);
}

