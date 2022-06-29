package com.upedge.oms.modules.vat.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.vat.request.VatRuleAddRequest;
import com.upedge.oms.modules.vat.request.VatRuleAssignCustomerRequest;
import com.upedge.oms.modules.vat.request.VatRuleListRequest;
import com.upedge.oms.modules.vat.request.VatRuleUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleAddResponse;
import com.upedge.oms.modules.vat.response.VatRuleListResponse;
import com.upedge.oms.modules.vat.response.VatRuleUpdateResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface VatRuleService{

    List<VatRule> selectAllAreaVatRule();

    BaseResponse assignCustomer(VatRuleAssignCustomerRequest request,Session session);

    BigDecimal getOrderVatAmount(BigDecimal productAmount, BigDecimal shipPrice, Long areaId,Long customerId);

    VatRule selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(VatRule record);

    int updateByPrimaryKeySelective(VatRule record);

    int insert(VatRule record);

    int insertSelective(VatRule record);

    List<VatRule> select(Page<VatRule> record);

    long count(Page<VatRule> record);

    VatRuleAddResponse addVatRule(VatRuleAddRequest request, Session session);

    VatRuleUpdateResponse update(Long id, VatRuleUpdateRequest request, Session session);

    VatRuleListResponse adminList(VatRuleListRequest request);
}

