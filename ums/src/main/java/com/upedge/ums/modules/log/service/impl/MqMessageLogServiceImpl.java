package com.upedge.ums.modules.log.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.modules.log.dao.MqMessageLogDao;
import com.upedge.ums.modules.log.service.MqMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MqMessageLogServiceImpl implements MqMessageLogService {

    @Autowired
    private MqMessageLogDao mqMessageLogDao;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        MqMessageLog record = new MqMessageLog();
        record.setId(id);
        return mqMessageLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(MqMessageLog record) {
        return mqMessageLogDao.insert(record);
    }

    @Override
    public int insertByBatch(List<MqMessageLog> mqMessageLogs) {
        if (ListUtils.isEmpty(mqMessageLogs)){
            return 0;
        }
        return mqMessageLogDao.insertByBatch(mqMessageLogs);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(MqMessageLog record) {
        return mqMessageLogDao.insert(record);
    }

    @Override
    public MqMessageLog selectByMsgKey(String key) {
        return mqMessageLogDao.selectByMsgKey(key);
    }

    @Override
    public MqMessageLog selectByTopicAndKey(MqMessageLog mqMessageLog) {
        return mqMessageLogDao.selectByTopicAndKey(mqMessageLog);
    }

    /**
     *
     */
    public MqMessageLog selectByPrimaryKey(Integer id){
        MqMessageLog record = new MqMessageLog();
        record.setId(id);
        return mqMessageLogDao.selectByPrimaryKey(record);
    }

    @Override
    public BaseResponse sendMessage(Message message) {
        MqMessageLog messageLog = mqMessageLogDao.selectByMsgKey(message.getKeys());
        if(null != messageLog){
            return BaseResponse.failed("repeated request");
        }
        messageLog = MqMessageLog.toMqMessageLog(message,new String(message.getBody()));
        String key = message.getKeys();
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())){
            try {
                status =  defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn(message.getTopic() + "发送消息，key:{},交易信息发送失败,失败次数:{}",key,i);
            }finally {
                i += 1;
            }
        }
        if(status.equals(SendStatus.SEND_OK.name())){
            messageLog.setIsSendSuccess(1);
            mqMessageLogDao.insert(messageLog);
            log.warn(message.getTopic() + "发送消息，key:{},交易信息发送成功",key);
            return BaseResponse.success();
        }else {
            messageLog.setIsSendSuccess(0);
            mqMessageLogDao.insert(messageLog);
            log.warn(message.getTopic() + "发送消息，key:{}",key);
            return BaseResponse.failed();
        }
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(MqMessageLog record) {
        return mqMessageLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(MqMessageLog record) {
        return mqMessageLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<MqMessageLog> select(Page<MqMessageLog> record){
        record.initFromNum();
        return mqMessageLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<MqMessageLog> record){
        return mqMessageLogDao.count(record);
    }

}