package com.upedge.oms.mq;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderCreatePackageCustomer {

    DefaultMQPushConsumer consumer;

    @Autowired
    OrderPackageService orderPackageService;

    public OrderCreatePackageCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("create_package");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_ORDER_CREATE_PACKAGE, "*");
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
                    List<Long> orderIds = JSONObject.parseArray(new String(message.getBody()),Long.class);
                    for (Long orderId : orderIds) {
                        orderPackageService.createPackage(orderId);
                    }
                }
            } catch (Exception e) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println(RocketMqConfig.TOPIC_ORDER_CREATE_PACKAGE + "-->消费者 启动成功=======");
    }
}
