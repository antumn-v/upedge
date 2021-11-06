package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class StoreShoplazzaId{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private String shoplazzaPlatId;
	/**
	 * 0=订单，1=订单变体，2=产品，3=产品变体
	 */
    private Integer dataType;
	/**
	 * 
	 */
    private Long appId;

}
