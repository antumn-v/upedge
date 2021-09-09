package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
	 */
    private Integer optType;
	/**
	 * 
	 */
    private String oldInfo;
	/**
	 * 
	 */
    private String newInfo;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String sku;
	/**
	 * 
	 */
    private String adminUser;
	/**
	 * 
	 */
    private Date createTime;

}
