package com.upedge.tms.modules.ship.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class AdminWarehouse{

	/**
	 * 仓库id
	 */
    private Integer id;
	/**
	 * 
	 */
    private String warehouseName;
	/**
	 * 仓库类型
	 */
    private Integer warehouseType;
	/**
	 * 
	 */
    private String warehouseEname;
	/**
	 * 状态 1:可用 0:禁用
	 */
    private Integer state;

}
