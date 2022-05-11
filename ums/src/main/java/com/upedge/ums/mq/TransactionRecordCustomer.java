package com.upedge.ums.mq;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.account.service.PaypalService;
import com.upedge.ums.modules.log.service.MqMessageLogService;
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
public class TransactionRecordCustomer {

    @Autowired
    AccountService accountService;

    @Autowired
    PaypalService paypalService;

    @Autowired
    MqMessageLogService mqMessageLogService;


    DefaultMQPushConsumer consumer ;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    OmsFeignClient omsFeignClient;

    public TransactionRecordCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("transaction_record");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_SAVE_ORDER_TRANSACTION, "*");
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
                    String key = message.getKeys();
                    MqMessageLog mqMessageLog = mqMessageLogService.selectByMsgKey(key);

                    if(mqMessageLog != null && mqMessageLog.getIsConsumeSuccess() == 1){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    boolean flag = RedisUtil.lock(redisTemplate,key,2L,12 * 1000L);
                    if(!flag){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    if(null == mqMessageLog){
                        mqMessageLog = MqMessageLog.toMqMessageLog(message,"");
                        mqMessageLog.setConsumeCount(1);
                        mqMessageLog.setIsSendSuccess(0);
                        mqMessageLogService.insert(mqMessageLog);
                    }else if(mqMessageLog.getIsConsumeSuccess() == 0) {
                        mqMessageLog.setConsumeCount(mqMessageLog.getConsumeCount() + 1);
                    }
                    mqMessageLog.setConsumeTime(new Date());
                    PaymentDetail detail = null;
                    try {
                        detail = JSONObject.parseObject(message.getBody(),PaymentDetail.class);
                    } catch (Exception e) {
                        RedisUtil.unLock(redisTemplate,key);
                        mqMessageLog.setIsConsumeSuccess(0);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    boolean b = false;
                    switch (detail.getPayMethod()){
                        case 0:
                            b = accountService.saveTransactionDetails(detail);
                            break;
                        case 1:
                            b = paypalService.paypalPayOrders(detail);
                            break;
                        default:
                            break;
                    }

                    if(b){
                        mqMessageLog.setIsConsumeSuccess(1);
                        mqMessageLogService.updateByPrimaryKeySelective(mqMessageLog);
                        RedisUtil.unLock(redisTemplate,key);

//                        UplodaSaiheOnMqVo uplodaSaiheOnMq = new UplodaSaiheOnMqVo(detail.getPaymentId(), detail.getOrderType());
//
//                        omsFeignClient.uploadPaymentIdOnMq(uplodaSaiheOnMq);

                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }else {
                        RedisUtil.unLock(redisTemplate,key);
                    }
                    log.warn("Consumer-获取消息-主题topic为={}, key={},是否消费成功：{}", message.getTopic(), key,b);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        consumer.start();
        System.out.println("order_transaction 消费者 启动成功=======");
    }
}
