package com.upedge.oms.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.oms.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerIossUpdateCustomer {


    DefaultMQPushConsumer consumer;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    OrderService orderService;

    public void CustomerIossUpdateCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("customer_ioss_update");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_CHANGE_OMS_MANAGER_CODE, "*");
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            try {
                for (Message message : msgs) {
                    if (null == message.getBody()) {
                        log.warn("消息内容有误：{}", message);
                        continue;
                    }
                    String key = message.getKeys();
                    MqMessageLog mqMessageLog = new MqMessageLog();
                    mqMessageLog.setTopic(RocketMqConfig.TOPIC_CHANGE_OMS_MANAGER_CODE);
                    mqMessageLog.setMsgKey(key);
                    BaseResponse baseResponse = umsFeignClient.selectMqLog(mqMessageLog);
                    if (baseResponse.getCode() != ResultCode.SUCCESS_CODE || baseResponse.getData() == null) {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    mqMessageLog = JSONObject.parseObject(JSON.toJSONString(baseResponse.getData())).toJavaObject(MqMessageLog.class);
                    if (mqMessageLog != null && mqMessageLog.getIsConsumeSuccess() == 1) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    String body = new String(message.getBody());
                    CustomerIossVo customerIossVo = JSONObject.parseObject(body).toJavaObject(CustomerIossVo.class);
                    switch (message.getKeys()){
                        case "add":
                            if (StringUtils.isNotBlank(customerIossVo.getIossNum())){
                                orderService.initVatAmountByCustomerId(customerIossVo.getCustomerId());
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();;
    }
}
