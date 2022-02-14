package com.upedge.pms.modules.alibaba.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.alibaba.dao.AlibabaApiDao;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AlibabaApiServiceImpl implements AlibabaApiService {

    @Autowired
    private AlibabaApiDao alibabaApiDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String apiKey) {
        AlibabaApi record = new AlibabaApi();
        record.setApiKey(apiKey);
        return alibabaApiDao.deleteByPrimaryKey(record);
    }

    @Override
    public AlibabaApi selectAlibabaApi() {
        return alibabaApiDao.selectAlibabaApi();
    }

    @Override
    public AlibabaApi selectUnExpireApi(long timestamp) {
        return alibabaApiDao.selectUnExpireApi(timestamp);
    }

    /**
     *
     */
    public AlibabaApi selectByPrimaryKey(String apiKey){
        AlibabaApi record = new AlibabaApi();
        record.setApiKey(apiKey);
        return alibabaApiDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AlibabaApi record) {
        return alibabaApiDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AlibabaApi record) {
        return alibabaApiDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AlibabaApi> select(Page<AlibabaApi> record){
        record.initFromNum();
        return alibabaApiDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AlibabaApi> record){
        return alibabaApiDao.count(record);
    }

}