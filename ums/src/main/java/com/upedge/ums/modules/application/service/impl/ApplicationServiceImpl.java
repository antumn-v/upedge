package com.upedge.ums.modules.application.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.application.dao.ApplicationDao;
import com.upedge.ums.modules.application.entity.Application;
import com.upedge.ums.modules.application.service.ApplicationService;


@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Application record = new Application();
        record.setId(id);
        return applicationDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Application record) {
        return applicationDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Application record) {
        return applicationDao.insert(record);
    }

    @Override
    public List<Application> allApplications() {
        Page<Application> applicationPage = new Page<>();
        applicationPage.setPageSize(-1);
        return null;
    }

    /**
     *
     */
    public Application selectByPrimaryKey(Long id){
        Application record = new Application();
        record.setId(id);
        return applicationDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Application record) {
        return applicationDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Application record) {
        return applicationDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Application> select(Page<Application> record){
        record.initFromNum();
        return applicationDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Application> record){
        return applicationDao.count(record);
    }

}