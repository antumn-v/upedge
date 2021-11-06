package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppPandaOrder{

	/**
	 * 拆分订单id
	 */
    private Long id;
	/**
	 * 
	 */
    private Long appOrderId;
	/**
	 * 提成
	 */
    private BigDecimal percentage;
	/**
	 * 美元汇率
	 */
    private BigDecimal usdRate;
	/**
	 * 支付时间
	 */
    private Date payTime;
	/**
	 * app用户id
	 */
    private Long appUserId;
	/**
	 * -1取消订单 0初始 1已付款 2退款中 3已退款 4部分退款
	 */
    private Integer appOrderState;
	/**
	 * 后台处理人
	 */
    private String adminUserId;
	/**
	 * 发货状态 0未发货1已发货 2已完成
	 */
    private Integer adminOrderState;
	/**
	 * 支付方式

	 */
    private String payMethod;
	/**
	 * 原商品总金额  ￥
	 */
    private BigDecimal originalProductsAmount;
	/**
	 * 应付订单商品总金额  $
	 */
    private BigDecimal payProductsAmount;
	/**
	 * 抵扣金额
	 */
    private BigDecimal dischargeAmount;
	/**
	 * 
	 */
    private BigDecimal paypalFee;
	/**
	 * 运费 $
	 */
    private BigDecimal shippingPrice;
	/**
	 * 
	 */
    private BigDecimal vatAmount;
	/**
	 * 支付金额 $
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 运输方式
	 */
    private Long shippingMethodId;
	/**
	 * 
	 */
    private Long shippingTemplateId;
	/**
	 * 
	 */
    private Double totalWeight;
	/**
	 * 
	 */
    private String fromAddr;
	/**
	 * 
	 */
    private Long addressId;
	/**
	 * 
	 */
    private Long toAreaId;
	/**
	 * 账户支付流水id
	 */
    private Long accountFlowId;
	/**
	 * 
	 */
    private Long trackingTime;
	/**
	 * 订单对应的店铺Id
	 */
    private Long appStoreId;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 
	 */
    private String saiheOrderCode;
	/**
	 * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
	 */
    private Integer orderType;
	/**
	 *   正常 = 0,待审查 = 1,作废 = 2
	 */
    private Integer orderStatus;
	/**
	 * 
	 */
    private Long originalPandaId;
	/**
	 * 补发原因
	 */
    private String trackingAgainReason;
	/**
	 * 
	 */
    private String note;
	/**
	 * 
	 */
    private Long shipRuleId;

}
