package com.upedge.oms.modules.vat.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.entity.VatRule;

import java.util.List;

/**
 * @author author
 */
public interface VatRuleDao{

    List<VatRule> selectAllAreaVatRule();

    VatRule selectVatRuleByAreaId(Long areaId);

    VatRule selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(VatRule record);

    int updateByPrimaryKey(VatRule record);

    int updateByPrimaryKeySelective(VatRule record);

    int insert(VatRule record);

    int insertSelective(VatRule record);

    int insertByBatch(List<VatRule> list);

    List<VatRule> select(Page<VatRule> record);

    long count(Page<VatRule> record);
}
