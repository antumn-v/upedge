package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class OrderTracking{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 运输ID
	 */
    private String trackingCode;
	/**
	 * 物流商单号
	 */
    private String logisticsOrderNo;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 订单ID
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String userId;
	/**
	 * 
	 */
    private String shippingMethodName;
	/**
	 * 0:上传失败 1:admin上传成功 2:admin更新成功 8:app上传成功 9:app更新成功
	 */
    private Long appOrderId;
	/**
	 * shopify物流上传状态
	 */
    private Integer state;
	/**
	 * 订单来源
	 */
    private String source;
	/**
	 * 赛盒运输id
	 */
    private Integer transportId;

}
