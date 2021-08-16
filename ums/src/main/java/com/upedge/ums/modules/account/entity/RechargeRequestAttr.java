package com.upedge.ums.modules.account.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class RechargeRequestAttr{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long rechargeRequestId;
	/**
	 * 
	 */
    private String attrName;
	/**
	 * 
	 */
    private String attrValue;

}
