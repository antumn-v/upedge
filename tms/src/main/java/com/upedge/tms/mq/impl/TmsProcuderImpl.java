package com.upedge.tms.mq.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.utils.IdGenerate;
import com.upedge.tms.mq.TmsProcuderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class TmsProcuderImpl implements TmsProcuderService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * umsFeignClient
     */
    @Autowired
    private UmsFeignClient umsFeignClient;

    /**
     * RocketMQ生产者发送消息默认使用
     */
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Override
    public void sendMessage(List<Long> shipUnitIds) {
//        Future<?> submit =    threadPoolExecutor.submit(() -> {
//            send(shipUnitIds);
//        });
//        try {
//            submit.get();
//        } catch (Exception e) {
//            log.error("运输单元修改id为：{}",shipUnitIds.toString());
//            e.printStackTrace();
//        }
    }

    private void send(List<Long> shipUnitIds) {
        log.info("运输单元修改id为：{}",shipUnitIds.toString());
        Message message = new Message(RocketMqConfig.TOPIC_SHIP_UNIT_UPDATE,"shipUnit",IdGenerate.nextId().toString() ,JSON.toJSONBytes(shipUnitIds));
        if (message == null){
            return;
        }
        log.debug("运输单元发送消息，Message:{},tag:{},数据:{}",  JSON.toJSON(message));
        message.setDelayTimeLevel(1);
        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message,"");
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())) {
            try {
                status = defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("运输单元发送消息，Message:{},tag:{},数据:{}",message.getKeys());
            } finally {
                i += 1;
            }
        }
        if (status.equals(SendStatus.SEND_OK.name())) {
            messageLog.setIsSendSuccess(1);
            log.warn("运输单元发送消息，key:{},运输单元发送成功", message.getKeys());
        } else {
            messageLog.setIsSendSuccess(0);
            log.warn("运输单元发送消息，key:{}", message.getKeys());
        }
        umsFeignClient.saveMqLog(messageLog);
    }
}
