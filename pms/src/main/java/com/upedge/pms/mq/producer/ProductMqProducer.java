package com.upedge.pms.mq.producer;

import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMqProducer {

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Async
    public boolean sendMessage(Message message){
        String key = message.getKeys();

        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message,null);

        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())){
            try {
                status =  defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                i += 1;
            }
        }
        if(status.equals(SendStatus.SEND_OK.name())){
            messageLog.setIsSendSuccess(1);
            umsFeignClient.saveMqLog(messageLog);
            log.warn("topic:{},发送消息，key:{},发送成功,发送次数:{}",message.getTopic(),key,i);
            return true;
        }else {
            messageLog.setIsSendSuccess(0);
            umsFeignClient.saveMqLog(messageLog);
            log.warn("topic:{},发送消息，key:{},发送失败,发送次数:{}",message.getTopic(),key,i);
            return false;
        }
    }
}
