package com.upedge.ums.modules.application.service;

import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.PermissionVo;
import com.upedge.ums.modules.application.entity.TPermission;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface TPermissionService{

    List<PermissionVo> selectByMenuId(Long menuId);

    List<PermissionVo> treePermission() throws CustomerException;

    TPermission selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(TPermission record);

    int updateByPrimaryKeySelective(TPermission record);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    List<TPermission> select(Page<TPermission> record);

    long count(Page<TPermission> record);
}

