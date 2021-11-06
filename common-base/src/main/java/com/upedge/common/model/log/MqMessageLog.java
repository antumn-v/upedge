package com.upedge.common.model.log;

import lombok.Data;
import org.apache.rocketmq.common.message.Message;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class MqMessageLog {
	/**
	 *
	 */
    private Integer id;
	/**
	 *
	 */
	@NotNull
    private String msgKey;
	/**
	 *
	 */
    private String tags;
	/**
	 *
	 */
	@NotNull
    private String topic;
	/**
	 *
	 */
    private String body;
	/**
	 *
	 */
    private Integer delay;
	/**
	 *
	 */
    private String transactionId;
	/**
	 * 消息是否发送成功
	 */
    private Integer isSendSuccess;
	/**
	 *
	 */
    private Integer isConsumeSuccess;
	/**
	 * 消费次数
	 */
    private Integer consumeCount;
	/**
	 * 发送时间
	 */
    private Date sendTime;
	/**
	 * 最后一次消费时间
	 */
    private Date consumeTime;


	public static MqMessageLog toMqMessageLog(Message message){
		MqMessageLog messageLog = new MqMessageLog();
		messageLog.setBody(message.getBody().toString());
		messageLog.setTags(message.getTags());
		messageLog.setTopic(message.getTopic());
		messageLog.setMsgKey(message.getKeys());
		messageLog.setTransactionId(message.getTransactionId());
		messageLog.setDelay(message.getDelayTimeLevel());
		return messageLog;
	}

	public static MqMessageLog toMqMessageLog(Message message, String body){
		MqMessageLog messageLog = new MqMessageLog();
		messageLog.setTags(message.getTags());
		messageLog.setTopic(message.getTopic());
		messageLog.setMsgKey(message.getKeys());
		messageLog.setTransactionId(message.getTransactionId());
		messageLog.setDelay(message.getDelayTimeLevel());
		messageLog.setIsConsumeSuccess(0);
		messageLog.setSendTime(new Date());
		messageLog.setConsumeCount(0);
		messageLog.setBody(null);
		return messageLog;
	}

}
