package com.upedge.oms.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.oms.modules.cart.service.CartService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
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

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class VariantUpdateCusomer {

    @Autowired
    CartService cartService;

    @Autowired
    StockOrderService stockOrderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    WholesaleOrderItemService wholesaleOrderItemService;


    DefaultMQPushConsumer consumer ;

    @Autowired
    UmsFeignClient umsFeignClient;


    public VariantUpdateCusomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("variant");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_VARIANT_UPDATE, "*");
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

                    MqMessageLog mqMessageLog = new MqMessageLog();
                    mqMessageLog.setTopic(RocketMqConfig.TOPIC_VARIANT_UPDATE);
                    mqMessageLog.setMsgKey(message.getKeys());

                    BaseResponse baseResponse = umsFeignClient.selectMqLog(mqMessageLog);

                    if(baseResponse.getCode() != ResultCode.SUCCESS_CODE){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }

                    mqMessageLog = JSONObject.parseObject(JSON.toJSONString(baseResponse.getData())).toJavaObject(MqMessageLog.class);

                    if(mqMessageLog != null && mqMessageLog.getIsConsumeSuccess() == 1){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    String tag = message.getTags();

                    String body = new String(message.getBody());

                    List<VariantDetail> variantDetailList = JSONArray.parseArray(body,VariantDetail.class);

                    // 如果是shippingId 则只用跑一遍
                    if (StringUtils.equals("shippingId",tag)){
                        for (VariantDetail variantDetail: variantDetailList) {
                            if (null != variantDetail.getProductId() && null != variantDetail.getProductShippingId()) {
                                // 修改信息     删除shipMethodId 和   shipPrice
                                // 调用  orderInitShipDetail 并根据该方法仿写一份用作批发订单
                                orderItemService.updateAdminVariantByVariantId(variantDetail,tag);
//                                wholesaleOrderItemService.updateItemByVariantId(variantDetail,tag);
                                break;
                            }
                        }
                    }else {
                        for (VariantDetail variantDetail: variantDetailList) {
                            if(tag.equals("price")){
                                if (null == variantDetail.getUsdPrice()){
                                    continue;
                                }
                                cartService.updatePriceByVariantId(variantDetail);
                                stockOrderService.updatePriceByVariantId(variantDetail);
                            }
                            // 修改订单item信息  删除普通订单和批发订单得shipMethodId 和   shipPrice
                            //  调用  orderInitShipDetail 并根据该方法仿写一份用作批发订单

                            orderItemService.updateAdminVariantByVariantId(variantDetail,tag);
//                            wholesaleOrderItemService.updateItemByVariantId(variantDetail,tag);
                        }
                    }

                    if(null == mqMessageLog){
                        mqMessageLog = MqMessageLog.toMqMessageLog(message, String.valueOf(System.currentTimeMillis()));
                        mqMessageLog.setConsumeCount(1);
                        mqMessageLog.setIsSendSuccess(0);
                        umsFeignClient.saveMqLog(mqMessageLog);
                    }else if(mqMessageLog.getIsConsumeSuccess() == 0) {
                        mqMessageLog.setConsumeCount(mqMessageLog.getConsumeCount() + 1);
                    }
                    mqMessageLog.setIsConsumeSuccess(1);
                    mqMessageLog.setConsumeTime(new Date());
                    umsFeignClient.updateMqLog(mqMessageLog);
                    log.warn("Consumer-获取消息-主题topic为={}, key={}", message.getTopic(), message.getKeys());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println(RocketMqConfig.TOPIC_VARIANT_UPDATE + "-->消费者 启动成功=======");
    }


    void variantUpdateShippingId(List<VariantDetail> variantDetailList){

    }
}
