package com.upedge.ums.modules.manager.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;
import com.upedge.ums.modules.manager.request.ManagerInfoUpdateRequest;

import java.util.List;

/**
 * @author gx
 */
public interface ManagerInfoService{

    ManagerInfo selectByInviteCode(String inviteCode);

    List<ManagerInfoVo> selectManagerDetail();

    BaseResponse create(ManagerInfoAddRequest request, Session session);

    ManagerInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKeySelective(ManagerInfoUpdateRequest request);

    int insert(ManagerInfo record);

    int insertSelective(ManagerInfo record);

    List<ManagerInfo> select(Page<ManagerInfo> record);

    long count(Page<ManagerInfo> record);
}

