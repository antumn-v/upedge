package com.upedge.ums.modules.application.dao;

import com.upedge.ums.modules.application.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface MenuDao{

    Menu selectByPrimaryKey(Menu record);

    int deleteByPrimaryKey(Menu record);

    int updateByPrimaryKey(Menu record);

    int updateByPrimaryKeySelective(Menu record);

    int insert(Menu record);

    int insertSelective(Menu record);

    int insertByBatch(List<Menu> list);

    List<Menu> select(Page<Menu> record);

    long count(Page<Menu> record);

}
