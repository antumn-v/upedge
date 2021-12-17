package com.upedge.oms.modules.common.service;

import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.mq.ChangeManagerVo;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import org.apache.rocketmq.common.message.Message;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 夕雾
 * 2021-4-29
 */
public interface OrderCommonService {

    /**
     * 订单导入赛盒前saiheOrder的处理和是否满足上传条件的判断
     */
    SaiheOrderRecord importOrderToSaihe(SaiheOrder saiheOrder);

    /**
     * 发送mq消息
     */
    boolean sendMqMessage(Message message);

    boolean sendMqMessage(List<Message> messages);

    /**
     * 该订单是否在赛盒已经上传了，不用再回传
     * orderCode在数据库中未有，保存信息
     * @return
     */
    Boolean checkAndSaveOrderCodeFromSaihe(String orderId, Integer orderType);


    /**
     * 上传订单信息实体封装
     * @param saiheOrder
     * @return
     */
    Boolean upLoadOrderToSaiHe(SaiheOrder saiheOrder, Integer orderType);

    /**
     * 刷新赛盒发货状态
     * @param id
     * @param orderType
     * @return
     */
    boolean synRefundTrackingState(String id, Integer orderType) throws CustomerException;


    /**
     *
     * @param id
     * @param orderType
     * @return
     * @throws CustomerException
     */
    boolean getTrackingFromSaihe(Long id, Integer orderType) ;


    /**
     * 修改订单模块业务数据的客户经理Code
     * @param changeManagerVos
     * @return
     */
    void updateOmsManagerCodeByChanagerManager(List<ChangeManagerVo> changeManagerVos) throws ExecutionException, InterruptedException;


}
