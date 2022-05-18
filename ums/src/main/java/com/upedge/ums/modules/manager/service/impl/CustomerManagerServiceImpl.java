package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.ums.modules.manager.dao.CustomerManagerDao;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerManagerServiceImpl implements CustomerManagerService {

    @Autowired
    private CustomerManagerDao customerManagerDao;

    @Autowired
    ManagerInfoService managerInfoService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerManager record = new CustomerManager();
        record.setCustomerId(customerId);
        return customerManagerDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerManager record) {
        return customerManagerDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerManager record) {
        return customerManagerDao.insert(record);
    }

    @Override
    public BaseResponse inviteCodeBindCustomer(String managerInviteToken, Long customerId) {
        if (StringUtils.isBlank(managerInviteToken)
        || null == customerId){
            return BaseResponse.failed();
        }
        ManagerInfo managerInfo = managerInfoService.selectByInviteCode(managerInviteToken);
        if (null == managerInfo){
            return BaseResponse.failed();
        }
        CustomerManager customerManager = new CustomerManager();
        customerManager.setCustomerId(customerId);
        customerManager.setManagerId(managerInfo.getId());
        insert(customerManager);

        redisTemplate.opsForHash().put(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,customerId,managerInfo.getId());
        return BaseResponse.success();
    }

    /**
     *
     */
    public CustomerManager selectByPrimaryKey(Long customerId){
        CustomerManager record = new CustomerManager();
        record.setCustomerId(customerId);
        return customerManagerDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerManager record) {
        return customerManagerDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerManager record) {
        return customerManagerDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerManager> select(Page<CustomerManager> record){
        record.initFromNum();
        return customerManagerDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerManager> record){
        return customerManagerDao.count(record);
    }

}