package com.upedge.ums.modules.manager.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;

import java.util.List;

/**
 * @author gx
 */
public interface ManagerInfoService{

    BaseResponse create(ManagerInfoAddRequest request, Session session);

    ManagerInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKeySelective(ManagerInfo record);

    int insert(ManagerInfo record);

    int insertSelective(ManagerInfo record);

    List<ManagerInfo> select(Page<ManagerInfo> record);

    long count(Page<ManagerInfo> record);
}

