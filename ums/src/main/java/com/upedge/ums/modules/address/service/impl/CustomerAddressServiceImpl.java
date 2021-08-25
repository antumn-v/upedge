package com.upedge.ums.modules.address.service.impl;

import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.address.dao.CustomerAddressDao;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.ums.modules.address.service.CustomerAddressService;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        CustomerAddress record = new CustomerAddress();
        record.setId(id);
        return customerAddressDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insert(CustomerAddress record) {
        return insertSelective(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insertSelective(CustomerAddress record) {
        Session session = UserUtil.getSession(redisTemplate);
        Page page = new Page();
        page.setPageSize(-1);
        Long count = customerAddressDao.count(page);
        if (count == 10){
            return 0;
        }
        if (count == null || count == 0){
            record.setIsDefault(true);
        }
        int i = customerAddressDao.insert(record);
        if (i == 1){
            if (record.getIsDefault()){
                customerAddressDao.cancelOtherDefaultAddress(record.getId(),session.getCustomerId());
            }
        }
        return i;
    }

    /**
     *
     */
    public CustomerAddress selectByPrimaryKey(Long id){
        CustomerAddress record = new CustomerAddress();
        record.setId(id);
        return customerAddressDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerAddress record) {
        return customerAddressDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerAddress record) {
        return customerAddressDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerAddress> select(Page<CustomerAddress> record){
        record.initFromNum();
        return customerAddressDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerAddress> record){
        return customerAddressDao.count(record);
    }

}