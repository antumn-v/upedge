package com.upedge.common.model.access.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiwu
 */
@Data
public class AccessHistoryVo {

	/**
	 * 
	 */
    private Long id;
	/**
	 * 访问者ip
	 */
    private String ip;
	/**
	 * 访问者所在国家
	 */
    private String adressCountries;
	/**
	 * 访问渠道类型
	 */
    private String accessPathType;
	/**
	 * 访问渠道网址
	 */
    private String accessPathIp;
	/**
	 * 访问设备类型
	 */
    private String deviceType;
	/**
	 * 访问时间
	 */
    private Date accessTime;
	/**
	 * 访问类型（枚举）
	 */
    private Integer accessType;

	/**
	 * 是否成功 0 失败  1 成功
	 */
	private Integer successOf;
	/**
	 * 支付订单的金额
	 */
    private BigDecimal amount;

    private Long customerId;

}
