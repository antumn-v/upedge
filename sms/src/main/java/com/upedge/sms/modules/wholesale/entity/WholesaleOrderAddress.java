package com.upedge.sms.modules.wholesale.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
