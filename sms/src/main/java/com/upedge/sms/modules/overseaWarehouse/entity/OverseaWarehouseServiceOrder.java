package com.upedge.sms.modules.overseaWarehouse.entity;

import com.upedge.common.model.oms.stock.StockOrderVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private BigDecimal productAmount;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 
	 */
    private Integer shipType;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Integer payState;
	/**
	 * 
	 */
    private Integer shipState;
	/**
	 * 
	 */
    private Integer refundState;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 
	 */
    private String warehouseCode;
	/**
	 * 
	 */
    private Long warehouseOrderId;
	/**
	 * 
	 */
    private Integer warehouseOrderState;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 
	 */
    private Date payTime;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long managerId;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	/**
	 * 
	 */
    private BigDecimal totalVolume;

	private String remark;

	public OverseaWarehouseServiceOrder() {

	}

	public void init(){
		this.payState = 0;
		this.refundState = 0;
		this.shipState = 0;
		this.createTime = new Date();
		this.updateTime = createTime;
	}

	public StockOrderVo toStockOrder(){
		StockOrderVo stockOrderVo = new StockOrderVo();
		stockOrderVo.setId(this.id);
		stockOrderVo.setAmount(this.payAmount);
		stockOrderVo.setPayState(this.payState);
		return stockOrderVo;
	}
}


