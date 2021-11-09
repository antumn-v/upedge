package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ImportPublishedRecord{

	/**
	 * 
	 */
    private Long importProductId;
	/**
	 * 
	 */
    private Long stroreId;
	/**
	 * 
	 */
    private String platProductId;
	/**
	 * 
	 */
    private Date publishTime;

}
