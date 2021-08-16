package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RechargeRequestAttrService{

    RechargeRequestAttr selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(RechargeRequestAttr record);

    int updateByPrimaryKeySelective(RechargeRequestAttr record);

    int insert(RechargeRequestAttr record);

    int insertSelective(RechargeRequestAttr record);

    List<RechargeRequestAttr> select(Page<RechargeRequestAttr> record);

    long count(Page<RechargeRequestAttr> record);
}

