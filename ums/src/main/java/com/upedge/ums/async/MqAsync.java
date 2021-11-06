package com.upedge.ums.async;

import com.upedge.ums.modules.log.service.MqMessageLogService;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MqAsync {

    @Autowired
    MqMessageLogService mqMessageLogService;


    @Async
    public void sendMessage(Message message){
        mqMessageLogService.sendMessage(message);
    }
}
