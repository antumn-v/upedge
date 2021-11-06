package com.upedge.tms.modules.ship.entity;

import com.upedge.common.utils.excel.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class ShippingUnit{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 运输方式id
	 */
    private Long methodId;
	/**
	 *
	 */
	@ExcelField(title="运输方式", align= ExcelField.Align.LEFT,sort = 10)
    private String methodName;
	/**
	 * 始发地id
	 */
	@ExcelField(title="发件地区/国家", align= ExcelField.Align.LEFT,sort = 20)
    private String fromAreaId;
	/**
	 * 目的地id
	 */
	@ExcelField(title="收件地区/国家", align= ExcelField.Align.LEFT,sort = 30)
    private String toAreaId;
	/**
	 * 区间开始重量
	 */
	@ExcelField(title="最小重量", align= ExcelField.Align.RIGHT,sort = 50)
    private BigDecimal startWeight;
	/**
	 * 区间结束重量
	 */
	@ExcelField(title="最大重量", align= ExcelField.Align.RIGHT,sort = 60)
    private BigDecimal endWeight;
	/**
	 * 固定费+挂号费
	 */
	@ExcelField(title="固定/挂号费", align= ExcelField.Align.RIGHT,sort = 70)
    private BigDecimal fixedFee;
	/**
	 * 各重费
	 */
	@ExcelField(title="克重费", align= ExcelField.Align.RIGHT,sort = 80,dataFormat="@")
    private BigDecimal weightCharge;
	/**
	 * 预计到达时间
	 */
	@ExcelField(title="预计达到天数（下限）", align= ExcelField.Align.RIGHT,sort =90)
    private Integer deliveryMinDay;
	/**
	 * 
	 */
	@ExcelField(title="预计达到天数（上限）", align= ExcelField.Align.RIGHT,sort = 100)
	private Integer deliveryMaxDay;
	/**
	 * 折扣
	 */
	@ExcelField(title="折扣", align= ExcelField.Align.RIGHT,sort = 40)
    private BigDecimal discount;
	/**
	 * 
	 */
	@ExcelField(title="备注", align= ExcelField.Align.LEFT,sort = 110)
    private String remarks;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Integer state;

}
