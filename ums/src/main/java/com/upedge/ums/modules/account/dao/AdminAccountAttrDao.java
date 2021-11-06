package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccountAttr;
import com.upedge.ums.modules.account.vo.AdminAccountAttrVo;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountAttrDao{

    AdminAccountAttr selectByPrimaryKey(AdminAccountAttr record);

    int deleteByPrimaryKey(AdminAccountAttr record);

    int updateByPrimaryKey(AdminAccountAttr record);

    int updateByPrimaryKeySelective(AdminAccountAttr record);

    int insert(AdminAccountAttr record);

    int insertSelective(AdminAccountAttr record);

    int insertByBatch(List<AdminAccountAttr> list);

    List<AdminAccountAttr> select(Page<AdminAccountAttr> record);

    long count(Page<AdminAccountAttr> record);

    List<AdminAccountAttrVo> listAttrsByAccountId(Long accountId, Integer accountType);

    void saveByBatch(List<AdminAccountAttr> attrs);
}
