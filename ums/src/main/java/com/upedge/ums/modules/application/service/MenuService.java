package com.upedge.ums.modules.application.service;

import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.MenuVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.application.request.MenuTreeRequest;

import java.util.List;

/**
 * @author gx
 */
public interface MenuService{

    void addMenus(List<Menu> menus,Session session);

    List<MenuVo> menuTree(Session session, MenuTreeRequest request) throws CustomerException;

    Menu selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Menu record);

    int updateByPrimaryKeySelective(Menu record);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> select(Page<Menu> record);

    long count(Page<Menu> record);
}

