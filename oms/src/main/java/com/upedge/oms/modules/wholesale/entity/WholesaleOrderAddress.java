package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class WholesaleOrderAddress{

	/**
	 * 
	 */
    private Long id;
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
    private Long orderId;

}
