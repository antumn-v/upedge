package com.upedge.oms.modules.vat.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.request.VatRuleItemRemoveRequest;
import com.upedge.oms.modules.vat.request.VatRuleItemUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleItemListResponse;
import com.upedge.oms.modules.vat.response.VatRuleItemRemoveResponse;
import com.upedge.oms.modules.vat.response.VatRuleItemUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface VatRuleItemService{

    VatRuleItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(VatRuleItem record);

    int updateByPrimaryKeySelective(VatRuleItem record);

    int insert(VatRuleItem record);

    int insertSelective(VatRuleItem record);

    List<VatRuleItem> select(Page<VatRuleItem> record);

    long count(Page<VatRuleItem> record);

    VatRuleItemListResponse areaSelect(Long ruleId);

    VatRuleItemRemoveResponse removeItem(VatRuleItemRemoveRequest request, Session session);

    VatRuleItemUpdateResponse updateRuleItems(VatRuleItemUpdateRequest request, Session session);
}

