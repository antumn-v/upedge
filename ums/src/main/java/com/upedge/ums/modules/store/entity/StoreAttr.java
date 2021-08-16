package com.upedge.ums.modules.store.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class StoreAttr{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private String attrKey;
	/**
	 * 
	 */
    private String attrValue;

}
