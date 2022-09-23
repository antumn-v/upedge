package com.upedge.pms.mq.customer;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.model.pms.vo.VariantWarehouseStockModel;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class VariantWarehouseStockRefreshCustomer {

    DefaultMQPushConsumer consumer ;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;

    public VariantWarehouseStockRefreshCustomer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("variant_stock_refresh");
        consumer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(RocketMqConfig.TOPIC_VARIANT_WAREHOUSE_STOCK_REFRESH, "*");
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
                    String warehouseCode = message.getTags();
                    Set<Long> variantIds = JSONObject.parseObject(message.getBody(),Set.class);
                    for (Long variantId : variantIds) {
                        VariantWarehouseStock variantWarehouseStock = variantWarehouseStockService.selectByPrimaryKey(variantId,warehouseCode);
                        VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
                        BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
                        redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+warehouseCode,variantId.toString(),variantWarehouseStockModel);
                    }
                    log.warn("Consumer-获取消息-主题topic为={}, key={}", message.getTopic(), message.getKeys());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println(RocketMqConfig.TOPIC_VARIANT_WAREHOUSE_STOCK_REFRESH + "消费者 启动成功=======");
    }
}
