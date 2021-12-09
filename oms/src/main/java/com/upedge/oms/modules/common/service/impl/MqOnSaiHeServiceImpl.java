package com.upedge.oms.modules.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.constant.OrderType;

import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.common.service.MqOnSaiheService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将支付成功的订单id推送到mq
 * <p>
 * mq 获取之后上传到赛盒
 */
@Slf4j
@Service
public class MqOnSaiHeServiceImpl implements MqOnSaiheService {


    /**
     * 普通订单
     */
    @Autowired
    private OrderService orderService;

    /**
     * 备库订单
     */
    @Autowired
    private StockOrderService stockOrderService;


    /**
     * 批发订单
     */
    @Autowired
    private WholesaleOrderService  wholesaleOrderService;


    /**
     * RocketMQ生产者发送消息默认使用
     */
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    UmsFeignClient umsFeignClient;


    /**
     * 支付订单处理成mq需要的message
     * @param paymentId
     * @param orderType
     */
    @Override
    public void uploadPaymentIdOnMq(Long paymentId, Integer orderType) {

        List<Long> longs = new ArrayList<>();
        if (orderType.equals(OrderType.NORMAL)){
            List<Order> orders = orderService.selectOrderByPaymentId(paymentId);
            longs = orders.stream().map(e -> e.getId()).collect(Collectors.toList());
        }
        if (orderType.equals(OrderType.STOCK)){
            List<StockOrder> orders = stockOrderService.selectStockOrderByPaymentId(paymentId);
            longs = orders.stream().map(e -> e.getId()).collect(Collectors.toList());
        }
        if (orderType.equals(OrderType.WHOLESALE)){
            List<WholesaleOrder> orders = wholesaleOrderService.selectWholesaleOrderByPaymentId(paymentId);
            longs = orders.stream().map(e -> e.getId()).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(longs)){
            log.info("RocketMQ生产者发送消息订单为空===>订单上传赛盒，orderType:{},paymentId:{}",orderType,paymentId);
        }
        Message message = new Message(RocketMqConfig.TOPIC_ORDER_UPLOAD_SAIHE, orderType.toString(), IdGenerate.nextId().toString(), JSON.toJSONBytes(longs));

        uploadPaymentIdOnMq(message);
    }

    /**
     * mq发送message
     * @param message
     */
    private void uploadPaymentIdOnMq(Message message) {
         if( message == null){
             return;
         }
        message.setDelayTimeLevel(1);
        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message,"");

        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())) {
            try {
                status = defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("订单上传赛盒发送消息，key:{},交易信息发送失败,失败次数:{}",message.getKeys());
            } finally {
                i += 1;
            }
        }
        if (status.equals(SendStatus.SEND_OK.name())) {
            messageLog.setIsSendSuccess(1);
            log.warn("订单上传赛盒发送消息，key:{},发送成功", message.getKeys());
        } else {
            messageLog.setIsSendSuccess(0);
            log.warn("订单上传赛盒发送消息，key:{}", message.getKeys());
        }
        umsFeignClient.saveMqLog(messageLog);
    }


}
