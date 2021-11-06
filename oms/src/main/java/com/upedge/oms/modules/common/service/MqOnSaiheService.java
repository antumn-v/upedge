package com.upedge.oms.modules.common.service;

/**
 *  将支付成功的订单id推送到mq
 *
 *  mq 获取之后上传到赛盒
 */
public interface MqOnSaiheService {


    /**
     * 上传paymentId到mq  --+>  消费者获取上传赛盒
     * @param paymentId
     * @param orderType
     */
    void uploadPaymentIdOnMq(Long paymentId, Integer orderType);

}
