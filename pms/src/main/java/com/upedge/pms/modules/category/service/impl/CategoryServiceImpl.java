package com.upedge.pms.modules.category.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.category.dao.CategoryDao;
import com.upedge.pms.modules.category.entity.Category;
import com.upedge.pms.modules.category.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Category record = new Category();
        record.setId(id);
        return categoryDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Category record) {
        return categoryDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Category record) {
        return categoryDao.insert(record);
    }

    /**
     *
     */
    @Override
    public Category selectByPrimaryKey(Long id){
        Category record = new Category();
        record.setId(id);
        return categoryDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Category record) {
        return categoryDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Category record) {
        return categoryDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Category> select(Page<Category> record){
        record.initFromNum();
        return categoryDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Category> record){
        return categoryDao.count(record);
    }

}