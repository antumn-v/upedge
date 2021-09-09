package com.upedge.ums.modules.application.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class TPermission{

	/**
	 * 
	 */
    private Long id;

    private Long menuId;
	/**
	 * 
	 */
    private Long parentId;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private Integer type;
	/**
	 * 
	 */
    private String description;

}
