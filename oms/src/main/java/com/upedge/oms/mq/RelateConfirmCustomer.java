package com.upedge.oms.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.product.RelateDetailVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderItemService;
import com.upedge.oms.modules.order.service.StoreOrderRelateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
//@Component
public class RelateConfirmCustomer {

    DefaultMQPushConsumer consumer;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    StoreOrderItemService storeOrderItemService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    StoreOrderRelateService storeOrderRelateService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public RelateConfirmCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("Product_Relate_Update");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_RELATE_CONFIRM, "*");
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
                    boolean b = RedisUtil.lock(redisTemplate,RocketMqConfig.TOPIC_RELATE_CONFIRM + key,5L,15 * 1000L);
                    if (!b){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    MqMessageLog mqMessageLog = new MqMessageLog();
                    mqMessageLog.setTopic(RocketMqConfig.TOPIC_RELATE_CONFIRM);
                    mqMessageLog.setMsgKey(key);
                    BaseResponse baseResponse = umsFeignClient.selectMqLog(mqMessageLog);
                    if (baseResponse.getCode() != ResultCode.SUCCESS_CODE) {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    mqMessageLog = JSONObject.parseObject(JSON.toJSONString(baseResponse.getData())).toJavaObject(MqMessageLog.class);
                    if (mqMessageLog != null && mqMessageLog.getIsConsumeSuccess() == 1) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    String body = new String(message.getBody());
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    List<RelateDetailVo> newRelates = jsonObject.getJSONArray("newRelate").toJavaList(RelateDetailVo.class);
                    JSONArray jsonArray = jsonObject.getJSONArray("oldRelate");
                    if (ListUtils.isNotEmpty(jsonArray)) {
                        List<RelateDetailVo> oldRelates = jsonArray.toJavaList(RelateDetailVo.class);
                        for (RelateDetailVo newRelate : newRelates) {
                            for (RelateDetailVo oldRelate : oldRelates) {
                                if (newRelate.getStoreVariantId().equals(oldRelate.getStoreVariantId())) {
                                    refreshOrderItemByRelate(oldRelate, newRelate);
                                    continue;
                                }
                            }
                        }
                    } else {
                        for (RelateDetailVo newRelate : newRelates) {
                            refreshOrderItemByRelate(null, newRelate);
                        }
                    }

                    if (null == mqMessageLog) {
                        mqMessageLog = MqMessageLog.toMqMessageLog(message, String.valueOf(System.currentTimeMillis()));
                        mqMessageLog.setConsumeCount(1);
                        mqMessageLog.setIsSendSuccess(0);
                        umsFeignClient.saveMqLog(mqMessageLog);
                    } else if (mqMessageLog.getIsConsumeSuccess() == 0) {
                        mqMessageLog.setConsumeCount(mqMessageLog.getConsumeCount() + 1);
                    }

                    mqMessageLog.setIsConsumeSuccess(1);
                    mqMessageLog.setConsumeTime(new Date());
                    umsFeignClient.updateMqLog(mqMessageLog);
                    RedisUtil.unLock(redisTemplate,RocketMqConfig.TOPIC_RELATE_CONFIRM + key);
                    log.warn("Consumer-获取消息-主题topic为={}, key={}", message.getTopic(), message.getKeys());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println(RocketMqConfig.TOPIC_RELATE_CONFIRM + "-->消费者 启动成功=======");
    }


    public void refreshOrderItemByRelate(RelateDetailVo oldRelate, RelateDetailVo newRelate) {
        Long storeVariantId = newRelate.getStoreVariantId();
        //已生成订单的店铺订单ID
        List<Long> createdStoreOrderIds = storeOrderItemService.selectStoreOrderIdByStoreVariantIdAndState(storeVariantId, 1);
        //新关联的产品数据
        List<RelateVariantVo> newVariantVos = newRelate.getRelateVariantVos();
        List<Long> unPaidOrderIds = new ArrayList<>();
        //已生成订单的店铺订单ID若不为空，则更新已生成的订单
        if (ListUtils.isNotEmpty(createdStoreOrderIds)) {
            //获取未支付的订单信息
            List<StoreOrderRelate> storeOrderRelates = storeOrderRelateService.selectUnPaidByStoreOrderId(createdStoreOrderIds);
            for (StoreOrderRelate relate : storeOrderRelates) {
                unPaidOrderIds.add(relate.getOrderId());
            }
            //本地关联或上次关联的产品如果是捆绑产品，则删除已生成的订单重新生成订单
            //普通订单直接更新产品信息
            List<RelateVariantVo> oldVariantVos = new ArrayList<>();
            if (ListUtils.isNotEmpty(unPaidOrderIds)) {
                if (null != oldRelate) {
                    oldVariantVos = oldRelate.getRelateVariantVos();
                }
                if (oldVariantVos.size() > 1 || newVariantVos.size() > 1) {
                    log.debug("产品关联生成订单，删除捆绑产品订单：{}", unPaidOrderIds);
                    try {
                        orderService.deleteOrderByIds(unPaidOrderIds);
                    } catch (CustomerException e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    RelateVariantVo relateVariantVo = newVariantVos.get(0);
                    orderItemService.updateAdminVariantByStoreVariantId(storeVariantId, relateVariantVo);
                }
            }
        }
        //查询包含未生成订单产品的店铺订单信息
        List<Long> unCreatedStoreOrderIds = storeOrderItemService.selectStoreOrderIdByStoreVariantIdAndState(storeVariantId, 0);
        for (Long storeOrderId : unCreatedStoreOrderIds) {
            List<StoreOrderRelate> storeOrderRelateList = storeOrderRelateService.selectByStoreOrderId(storeOrderId);
            if (ListUtils.isEmpty(storeOrderRelateList)) {
                orderService.createOrderByStoreOrder(storeOrderId);
            } else {
                orderItemService.addOrderItemFromStoreOrder(storeOrderRelateList.get(0).getOrderId(), storeOrderId, newRelate);
            }
        }
    }
}
