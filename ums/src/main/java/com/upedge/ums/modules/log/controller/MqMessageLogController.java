package com.upedge.ums.modules.log.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.ums.modules.log.service.MqMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
@Slf4j
@RestController
@RequestMapping("/mq")
public class MqMessageLogController {
    @Autowired
    private MqMessageLogService mqMessageLogService;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @PostMapping("/log/select")
    public BaseResponse selectMqLog(@RequestBody @Valid MqMessageLog log){
        MqMessageLog mqMessageLog = mqMessageLogService.selectByTopicAndKey(log);

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,mqMessageLog);
    }

    @PostMapping("/log/save")
    public BaseResponse saveMqLog(@RequestBody @Valid MqMessageLog log){
        MqMessageLog mqMessageLog = mqMessageLogService.selectByTopicAndKey(log);
        if(null == mqMessageLog){
            mqMessageLogService.insert(log);
        }
        return BaseResponse.success();
    }

    @PostMapping("/log/update")
    public BaseResponse updateMqLog(@RequestBody @Valid MqMessageLog log){
        MqMessageLog mqMessageLog = mqMessageLogService.selectByTopicAndKey(log);
        if(null == mqMessageLog){
            mqMessageLogService.insert(log);
        }else {
            mqMessageLogService.updateByPrimaryKeySelective(log);
        }
        return BaseResponse.success();
    }

    @PostMapping("/message/send")
    public BaseResponse sendMessage(@RequestBody Message message){

        return mqMessageLogService.sendMessage(message);
    }
}
