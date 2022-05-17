package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.request.ManagerAddCommissionRequest;
import com.upedge.common.utils.DateUtils;
import com.upedge.ums.modules.manager.dao.ManagerCommissionRecordDao;
import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;
import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import com.upedge.ums.modules.manager.service.ManagerCommissionRecordService;
import com.upedge.ums.modules.manager.service.ManagerMonthCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ManagerCommissionRecordServiceImpl implements ManagerCommissionRecordService {

    @Autowired
    private ManagerCommissionRecordDao ManagerCommissionRecordDao;

    @Autowired
    ManagerMonthCommissionService managerMonthCommissionService;

    @Autowired
    CustomerManagerService customerManagerService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ManagerCommissionRecord record = new ManagerCommissionRecord();
        record.setId(id);
        return ManagerCommissionRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ManagerCommissionRecord record) {
        return ManagerCommissionRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ManagerCommissionRecord record) {
        return ManagerCommissionRecordDao.insert(record);
    }

    @Transactional
    @Override
    public void addCommissionRecord(ManagerAddCommissionRequest request) {
        Long customerId = request.getCustomerId();
        ManagerInfoVo managerInfoVo = (ManagerInfoVo) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,customerId);
        if (null == managerInfoVo){
            return;
        }
        Long orderId = request.getOrderId();
        Long managerId = managerInfoVo.getId();
        ManagerCommissionRecord managerCommissionRecord = new ManagerCommissionRecord();
        managerCommissionRecord.setManagerId(managerId);
        managerCommissionRecord.setCommission(managerInfoVo.getPerCommission());
        managerCommissionRecord.setOrderId(orderId);
        managerCommissionRecord.setCustomerId(customerId);
        managerCommissionRecord.setCreateTime(new Date());
        insert(managerCommissionRecord);

        String month = DateUtils.getDate("yyyy-MM");

        ManagerMonthCommission managerMonthCommission = new ManagerMonthCommission();
        managerMonthCommission.setManagerId(managerId);
        managerMonthCommission.setTotalCommission(managerInfoVo.getPerCommission());
        managerMonthCommission.setDateMonth(month);
        managerMonthCommissionService.insert(managerMonthCommission);

    }

    /**
     *
     */
    public ManagerCommissionRecord selectByPrimaryKey(Long id){
        ManagerCommissionRecord record = new ManagerCommissionRecord();
        record.setId(id);
        return ManagerCommissionRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ManagerCommissionRecord record) {
        return ManagerCommissionRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ManagerCommissionRecord record) {
        return ManagerCommissionRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ManagerCommissionRecord> select(Page<ManagerCommissionRecord> record){
        record.initFromNum();
        return ManagerCommissionRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ManagerCommissionRecord> record){
        return ManagerCommissionRecordDao.count(record);
    }

}