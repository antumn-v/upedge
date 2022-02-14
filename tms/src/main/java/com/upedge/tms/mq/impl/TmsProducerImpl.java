package com.upedge.tms.mq.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.utils.IdGenerate;
import com.upedge.thirdparty.fpx.config.FpxConfig;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.constants.MethodEnum;
import com.upedge.thirdparty.fpx.dto.ShipPriceCalculator;
import com.upedge.thirdparty.fpx.model.AffterentParam;
import com.upedge.thirdparty.fpx.utils.ApiHttpClientUtils;
import com.upedge.tms.mq.TmsProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class TmsProducerImpl implements TmsProducerService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * umsFeignClient
     */
    @Autowired
    private UmsFeignClient umsFeignClient;

    /**
     * RocketMQ生产者发送消息默认使用
     */
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Override
    public boolean sendMessage(List<Long> shipUnitIds) {
        return send(shipUnitIds);
    }

    private boolean send(List<Long> shipUnitIds) {
        log.info("运输单元修改id为：{}",shipUnitIds.toString());
        boolean b = false;
        Message message = new Message(RocketMqConfig.TOPIC_SHIP_UNIT_UPDATE,"shipUnit",IdGenerate.nextId().toString() ,JSON.toJSONBytes(shipUnitIds));
        if (message == null){
            return b;
        }
        log.debug("运输单元发送消息，Message:{},tag:{},数据:{}",  JSON.toJSON(message));
        message.setDelayTimeLevel(1);
        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message,"");
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())) {
            try {
                status = defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("运输单元发送消息，Message:{},tag:{},数据:{}",message.getKeys());
            } finally {
                i += 1;
            }
        }
        if (status.equals(SendStatus.SEND_OK.name())) {
            b = true;
            messageLog.setIsSendSuccess(1);
            log.warn("运输单元发送消息，key:{},运输单元发送成功", message.getKeys());
        } else {
            messageLog.setIsSendSuccess(0);
            log.warn("运输单元发送消息，key:{}", message.getKeys());
        }
        umsFeignClient.saveMqLog(messageLog);
        return b;
    }

    public static void main(String[] args) {
        AffterentParam param = new AffterentParam();
        param.setAppKey(FpxConfig.API_KEY);
        param.setAppSecret(FpxConfig.API_SECRET);
        param.setVersion(FpxConfig.VERSION);
        param.setMethod(MethodEnum.price_calculator.getMethod());
        param.setFormat("json");
        param.setLanguage("cn");
        param.setAccessToken("");

        List<String> codes = new ArrayList<>();
        codes.add("S004");
        codes.add("S001");
        codes.add("S003");
        codes.add("S006");
        codes.add("F009");
        codes.add("S007");
        codes.add("S098");
        codes.add("S153");
        codes.add("F191");
        codes.add("F192");
        codes.add("F001");
        codes.add("F137");
        codes.add("F190");

        ShipPriceCalculator.DestinationDTO destinationDTO = new ShipPriceCalculator.DestinationDTO();
        destinationDTO.setCountry("US");

        ShipPriceCalculator priceCalculator = new ShipPriceCalculator();
        priceCalculator.setHeight("55.335");
        priceCalculator.setLength("25.335");
        priceCalculator.setWidth("15.335");
        priceCalculator.setWeight("25");
        priceCalculator.setService_code("FB4");
        priceCalculator.setProduct_codes(codes);
        priceCalculator.setWarehouse_code("USLAXA");
        priceCalculator.setBilling_time(System.currentTimeMillis());
        priceCalculator.setDestination(destinationDTO);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(priceCalculator));

        System.out.println(ApiHttpClientUtils.apiJsongPost(param,jsonObject, AmbientEnum.FORMAT_ADDRESS));
    }
}
