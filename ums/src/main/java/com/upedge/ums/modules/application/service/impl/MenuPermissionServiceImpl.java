package com.upedge.ums.modules.application.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.application.dao.MenuPermissionDao;
import com.upedge.ums.modules.application.entity.MenuPermission;
import com.upedge.ums.modules.application.service.MenuPermissionService;


@Service
public class MenuPermissionServiceImpl implements MenuPermissionService {

    @Autowired
    private MenuPermissionDao menuPermissionDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        MenuPermission record = new MenuPermission();
        record.setId(id);
        return menuPermissionDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(MenuPermission record) {
        return menuPermissionDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(MenuPermission record) {
        return menuPermissionDao.insert(record);
    }

    /**
     *
     */
    public MenuPermission selectByPrimaryKey(Long id){
        MenuPermission record = new MenuPermission();
        record.setId(id);
        return menuPermissionDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(MenuPermission record) {
        return menuPermissionDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(MenuPermission record) {
        return menuPermissionDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<MenuPermission> select(Page<MenuPermission> record){
        record.initFromNum();
        return menuPermissionDao.select(record);
    }

    /**
    *
    */
    public long count(Page<MenuPermission> record){
        return menuPermissionDao.count(record);
    }

}