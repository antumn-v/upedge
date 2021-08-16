package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface RechargeRequestAttrDao{

    RechargeRequestAttr selectByPrimaryKey(RechargeRequestAttr record);

    int deleteByPrimaryKey(RechargeRequestAttr record);

    int updateByPrimaryKey(RechargeRequestAttr record);

    int updateByPrimaryKeySelective(RechargeRequestAttr record);

    int insert(RechargeRequestAttr record);

    int insertSelective(RechargeRequestAttr record);

    int insertByBatch(List<RechargeRequestAttr> list);

    List<RechargeRequestAttr> select(Page<RechargeRequestAttr> record);

    long count(Page<RechargeRequestAttr> record);

}
