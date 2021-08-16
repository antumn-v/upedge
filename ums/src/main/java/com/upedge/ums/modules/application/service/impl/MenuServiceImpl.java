package com.upedge.ums.modules.application.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.application.dao.MenuDao;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.application.service.MenuService;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Menu record = new Menu();
        record.setId(id);
        return menuDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Menu record) {
        return menuDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Menu record) {
        return menuDao.insert(record);
    }

    /**
     *
     */
    public Menu selectByPrimaryKey(Long id){
        Menu record = new Menu();
        record.setId(id);
        return menuDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Menu record) {
        return menuDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Menu record) {
        return menuDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Menu> select(Page<Menu> record){
        record.initFromNum();
        return menuDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Menu> record){
        return menuDao.count(record);
    }

}