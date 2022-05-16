package com.upedge.ums.modules.manager.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class CustomerManager{

	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 用户经理code
	 */
    private Long managerId;

}
