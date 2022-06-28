package com.upedge.oms.modules.vat.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.vo.VatAreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface VatRuleItemDao{

    List<VatRuleItem> selectByRuleId(Long ruleId);

    void deleteByCustomerIds(List<Long> customerIds);

    VatRuleItem selectByPrimaryKey(VatRuleItem record);

    int deleteByPrimaryKey(VatRuleItem record);

    int updateByPrimaryKey(VatRuleItem record);

    int updateByPrimaryKeySelective(VatRuleItem record);

    int insert(VatRuleItem record);

    int insertSelective(VatRuleItem record);

    int insertByBatch(List<VatRuleItem> list);

    List<VatRuleItem> select(Page<VatRuleItem> record);

    long count(Page<VatRuleItem> record);

    List<VatRuleItem> listVatRuleItemByRuleId(Long ruleId);

    List<VatAreaVo> areaIdList(Long ruleId);

    void removeArea(@Param("ruleId") Long ruleId, @Param("areaId") Long areaId);

    void updateRuleItems(List<VatRuleItem> newItems);

    int countAreaNumByRuleId(Long ruleId);
}
