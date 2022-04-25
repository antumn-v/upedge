package com.upedge.ums.modules.address.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.address.dao.CustomerAddressDao;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.ums.modules.address.request.CustomerAddressListRequest;
import com.upedge.ums.modules.address.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        if (record.getAddressType() == 0){
            List<CustomerAddress> customerAddresses = customerAddressDao.selectCustomerNormalAddress(session.getCustomerId());
            if (ListUtils.isEmpty(customerAddresses)){
                record.setIsDefault(true);
            }
        }else if (record.getAddressType() == 1){
            CustomerAddress address = selectCustomerBillingAddress(session.getCustomerId());
            if (address != null){
                record.setId(address.getId());
                return updateByPrimaryKey(record);
            }
        }else {
            return 0;
        }
        int i = customerAddressDao.insert(record);
        if (i == 1){
            if (record.getIsDefault()){
                customerAddressDao.cancelOtherDefaultAddress(record.getId(),session.getCustomerId());
            }
        }
        return i;
    }

    @Override
    public CustomerAddress selectCustomerBillingAddress(Long customerId) {
        CustomerAddressListRequest request = new CustomerAddressListRequest();
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(customerId);
        customerAddress.setAddressType(1);
        request.setT(customerAddress);
        List<CustomerAddress> results = select(request);
        if (ListUtils.isNotEmpty(results)){
            return results.get(0);
        }
        return null;
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