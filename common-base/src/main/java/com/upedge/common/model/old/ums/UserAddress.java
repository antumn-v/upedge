package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserAddress{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private String firstName;
	/**
	 * 
	 */
    private String lastName;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private String phone;
	/**
	 * 
	 */
    private String country;
	/**
	 * 
	 */
    private String province;
	/**
	 * 
	 */
    private String city;
	/**
	 * 
	 */
    private String address1;
	/**
	 * 
	 */
    private String address2;
	/**
	 * 
	 */
    private String zip;
	/**
	 * 
	 */
    private String countryCode;
	/**
	 * 
	 */
    private String provinceCode;
	/**
	 * 
	 */
    private Integer isDefault;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
