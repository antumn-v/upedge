package com.upedge.ums.modules.log.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.log.MqMessageLog;

import java.util.List;

/**
 * @author author
 */
public interface MqMessageLogDao{

    MqMessageLog selectByMsgKey(String key);

    MqMessageLog selectByTopicAndKey(MqMessageLog mqMessageLog);

    MqMessageLog selectByPrimaryKey(MqMessageLog record);

    int deleteByPrimaryKey(MqMessageLog record);

    int updateByPrimaryKey(MqMessageLog record);

    int updateByPrimaryKeySelective(MqMessageLog record);

    int insert(MqMessageLog record);

    int insertSelective(MqMessageLog record);

    int insertByBatch(List<MqMessageLog> list);

    List<MqMessageLog> select(Page<MqMessageLog> record);

    long count(Page<MqMessageLog> record);

}
