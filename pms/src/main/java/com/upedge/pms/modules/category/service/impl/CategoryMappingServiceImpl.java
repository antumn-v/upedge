package com.upedge.pms.modules.category.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.category.dao.CategoryMappingDao;
import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.pms.modules.category.service.CategoryMappingService;


@Service
public class CategoryMappingServiceImpl implements CategoryMappingService {

    @Autowired
    private CategoryMappingDao categoryMappingDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long aliCnCategoryId) {
        CategoryMapping record = new CategoryMapping();
        record.setAliCnCategoryId(aliCnCategoryId);
        return categoryMappingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CategoryMapping record) {
        return categoryMappingDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CategoryMapping record) {
        return categoryMappingDao.insert(record);
    }

    @Override
    public CategoryMapping selectByAliCateCode(String aliCnCategoryId) {
        return null;
    }

    /**
     *
     */
    public CategoryMapping selectByPrimaryKey(Long aliCnCategoryId){
        CategoryMapping record = new CategoryMapping();
        record.setAliCnCategoryId(aliCnCategoryId);
        return categoryMappingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CategoryMapping record) {
        return categoryMappingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CategoryMapping record) {
        return categoryMappingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CategoryMapping> select(Page<CategoryMapping> record){
        record.initFromNum();
        return categoryMappingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CategoryMapping> record){
        return categoryMappingDao.count(record);
    }

}