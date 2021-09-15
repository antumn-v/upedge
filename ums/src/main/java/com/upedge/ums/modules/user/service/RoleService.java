package com.upedge.ums.modules.user.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.RoleVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.request.RoleAddRequest;
import com.upedge.ums.modules.user.request.RoleUpdateRequest;

import javax.management.relation.RoleInfo;
import java.util.List;

/**
 * @author gx
 */
public interface RoleService{



    RoleVo selectRoleInfo(Long roleId);

    BaseResponse addRole(RoleAddRequest request, Session session);

    BaseResponse updateRole(RoleUpdateRequest request,Long id);

    Role selectRoleByUser(Long userId);

    Role selectApplicationDefaultRole(Long applicationId);

    Role selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Role record);

    int updateByPrimaryKeySelective(Role record);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> select(Page<Role> record);

    long count(Page<Role> record);
}

