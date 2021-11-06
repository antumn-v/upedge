package com.upedge.ums.modules.log.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.log.MqMessageLog;
import org.apache.rocketmq.common.message.Message;

import java.util.List;

/**
 * @author author
 */
public interface MqMessageLogService{

    MqMessageLog selectByMsgKey(String key);

    MqMessageLog selectByTopicAndKey(MqMessageLog mqMessageLog);

    MqMessageLog selectByPrimaryKey(Integer id);

    BaseResponse sendMessage(Message message);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(MqMessageLog record);

    int updateByPrimaryKeySelective(MqMessageLog record);

    int insert(MqMessageLog record);

    int insertSelective(MqMessageLog record);

    List<MqMessageLog> select(Page<MqMessageLog> record);

    long count(Page<MqMessageLog> record);
}

