package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerPackageStatisticsVo;
import com.upedge.oms.modules.pack.dao.PackageDailyCountDao;
import com.upedge.oms.modules.pack.entity.PackageDailyCount;
import com.upedge.oms.modules.pack.request.PackageDailyCountRequest;
import com.upedge.oms.modules.pack.service.PackageDailyCountService;
import com.upedge.oms.modules.pack.vo.ManagerPackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageDailyCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PackageDailyCountServiceImpl implements PackageDailyCountService {

    @Autowired
    private PackageDailyCountDao packageDailyCountDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        PackageDailyCount record = new PackageDailyCount();
        record.setId(id);
        return packageDailyCountDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PackageDailyCount record) {
        return packageDailyCountDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PackageDailyCount record) {
        return packageDailyCountDao.insert(record);
    }

    @Override
    public List<ManagerPackageCountVo> selectManagerPackageCount(PackageDailyCountRequest request) {
        return packageDailyCountDao.selectManagerPackageCount(request);
    }

    @Override
    public List<PackageDailyCountVo> selectPackageDailyCount(PackageDailyCountRequest request) {
        return packageDailyCountDao.selectPackageDailyCount(request);
    }

    @Override
    public List<ManagerPackageStatisticsVo> selectManagerPackageStatistics(ManagerPackageStatisticsRequest request) {
        return packageDailyCountDao.selectManagerPackageStatistics(request);
    }

    /**
     *
     */
    public PackageDailyCount selectByPrimaryKey(Integer id){
        PackageDailyCount record = new PackageDailyCount();
        record.setId(id);
        return packageDailyCountDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PackageDailyCount record) {
        return packageDailyCountDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PackageDailyCount record) {
        return packageDailyCountDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PackageDailyCount> select(Page<PackageDailyCount> record){
        record.initFromNum();
        return packageDailyCountDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PackageDailyCount> record){
        return packageDailyCountDao.count(record);
    }

}