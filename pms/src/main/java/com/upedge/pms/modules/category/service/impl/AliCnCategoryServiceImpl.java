package com.upedge.pms.modules.category.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.category.dao.AliCnCategoryDao;
import com.upedge.pms.modules.category.entity.AliCnCategory;
import com.upedge.pms.modules.category.service.AliCnCategoryService;


@Service
public class AliCnCategoryServiceImpl implements AliCnCategoryService {

    @Autowired
    private AliCnCategoryDao aliCnCategoryDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        AliCnCategory record = new AliCnCategory();
        record.setId(id);
        return aliCnCategoryDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AliCnCategory record) {
        return aliCnCategoryDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AliCnCategory record) {
        return aliCnCategoryDao.insert(record);
    }

    /**
     *
     */
    public AliCnCategory selectByPrimaryKey(Integer id){
        AliCnCategory record = new AliCnCategory();
        record.setId(id);
        return aliCnCategoryDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AliCnCategory record) {
        return aliCnCategoryDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AliCnCategory record) {
        return aliCnCategoryDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AliCnCategory> select(Page<AliCnCategory> record){
        record.initFromNum();
        return aliCnCategoryDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AliCnCategory> record){
        return aliCnCategoryDao.count(record);
    }

}