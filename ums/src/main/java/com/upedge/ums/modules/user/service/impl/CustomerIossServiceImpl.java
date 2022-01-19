package com.upedge.ums.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.ums.modules.log.service.MqMessageLogService;
import com.upedge.ums.modules.user.dao.CustomerIossDao;
import com.upedge.ums.modules.user.entity.CustomerIoss;
import com.upedge.ums.modules.user.service.CustomerIossService;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerIossServiceImpl implements CustomerIossService {

    @Autowired
    private CustomerIossDao customerIossDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MqMessageLogService mqMessageLogService;

    /**
     *
     */
    public int deleteByPrimaryKey(Long id) {
        CustomerIoss record = customerIossDao.selectByPrimaryKey(id);
        if (record != null){
            redisTemplate.delete(RedisKey.STRING_CUSTOMER_IOSS + record.getCustomerId());
        }
        return customerIossDao.deleteByPrimaryKey(id);
    }

    /**
     *
     */
    public int insert(CustomerIoss record) {
        int i = customerIossDao.insert(record);
        if (i == 1){
            CustomerIossVo customerIossVo = new CustomerIossVo();
            BeanUtils.copyProperties(record,customerIossVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_CUSTOMER_IOSS+record.getCustomerId(),customerIossVo);
            sendCustomerIossUpdateMessage(customerIossVo,"add");
        }
        return i;
    }

    /**
     *
     */
    public int insertSelective(CustomerIoss record) {
        int i = customerIossDao.insert(record);
        if (i == 1){
            CustomerIossVo customerIossVo = new CustomerIossVo();
            BeanUtils.copyProperties(record,customerIossVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_CUSTOMER_IOSS+record.getCustomerId(),customerIossVo);
            sendCustomerIossUpdateMessage(customerIossVo,"add");
        }
        return i;
    }

    @Override
    public CustomerIoss selectByCustomerId(Long customerId) {
        if (customerId == null){
            return null;
        }
        CustomerIossVo customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(RedisKey.STRING_CUSTOMER_IOSS + customerId);
        if (customerIossVo != null){
            CustomerIoss customerIoss = new CustomerIoss();
            BeanUtils.copyProperties(customerIossVo,customerIoss);
            return customerIoss;
        }
        return customerIossDao.selectByCustomerId(customerId);
    }

    /**
     *
     */
    public CustomerIoss selectByPrimaryKey(Long id){
        return customerIossDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    public int updateByPrimaryKeySelective(CustomerIoss record) {
        int i = customerIossDao.updateByPrimaryKeySelective(record);
        if (i == 1){
            record = selectByPrimaryKey(record.getId());
            CustomerIossVo customerIossVo = new CustomerIossVo();
            BeanUtils.copyProperties(record,customerIossVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_CUSTOMER_IOSS+record.getCustomerId(),customerIossVo);
        }

        return i;
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerIoss record) {
        CustomerIossVo customerIossVo = new CustomerIossVo();
        BeanUtils.copyProperties(record,customerIossVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_CUSTOMER_IOSS+record.getCustomerId(),customerIossVo);
        int i = customerIossDao.updateByPrimaryKey(record);
        return i;
    }

    /**
    *
    */
    public List<CustomerIoss> select(Page<CustomerIoss> record){
        record.initFromNum();
        return customerIossDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerIoss> record){
        return customerIossDao.count(record);
    }


    /**
     *
     * @param customerIossVo
     * @param type 1=新增 0=删除
     */
    public void sendCustomerIossUpdateMessage(CustomerIossVo customerIossVo,String type){

        Message message = new Message();
        message.setBody(JSONObject.toJSONBytes(customerIossVo));
        message.setKeys(UUID.randomUUID().toString());
        message.setTags(type);
        message.setTopic(RocketMqConfig.TOPIC_CUSTOMER_IOSS_UPDATE);
        BaseResponse response = mqMessageLogService.sendMessage(message);
    }

}