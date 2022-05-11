package com.upedge.ums.mq;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.ums.async.StoreAsync;
import com.upedge.ums.modules.log.service.MqMessageLogService;
import com.upedge.ums.modules.store.entity.Store;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class GetStoreDataCustomer {

    DefaultMQPushConsumer consumer ;

    @Autowired
    MqMessageLogService mqMessageLogService;

    @Autowired
    StoreAsync storeAsync;

    @Autowired
    RedisTemplate redisTemplate;

    public GetStoreDataCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("get_store_data");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_GET_STORE_DATA, "*");
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            try {
                for (Message message : msgs) {
                    if(null == message.getBody()){
                        log.warn("消息内容有误：{}",message);
                        continue;
                    }
                    boolean b = RedisUtil.lock(redisTemplate,message.getKeys(),10L,60 * 1000L);
                    if (!b){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    MqMessageLog mqMessageLog = mqMessageLogService.selectByMsgKey(message.getKeys());
                    if(mqMessageLog != null && mqMessageLog.getIsConsumeSuccess() == 1){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    Store store = JSONObject.parseObject(new String(message.getBody())).toJavaObject(Store.class);
                    storeAsync.getStoreData(store);

                    if(null == mqMessageLog){
                        mqMessageLog = MqMessageLog.toMqMessageLog(message,String.valueOf(System.currentTimeMillis()));
                        mqMessageLog.setConsumeCount(1);
                        mqMessageLog.setIsSendSuccess(0);
                        mqMessageLogService.insert(mqMessageLog);
                    }else if(mqMessageLog.getIsConsumeSuccess() == 0) {
                        mqMessageLog.setConsumeCount(mqMessageLog.getConsumeCount() + 1);
                    }
                    mqMessageLog.setIsConsumeSuccess(1);
                    mqMessageLog.setConsumeTime(new Date());
                    mqMessageLogService.updateByPrimaryKeySelective(mqMessageLog);
                    log.warn("Consumer-获取消息-主题topic为={}, key={}", message.getTopic(), message.getKeys());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println(RocketMqConfig.TOPIC_GET_STORE_DATA + "消费者 启动成功=======");
    }

}
