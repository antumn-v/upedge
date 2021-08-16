package com.upedge.ums.modules.application.service;

import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface MenuService{

    Menu selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Menu record);

    int updateByPrimaryKeySelective(Menu record);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> select(Page<Menu> record);

    long count(Page<Menu> record);
}

