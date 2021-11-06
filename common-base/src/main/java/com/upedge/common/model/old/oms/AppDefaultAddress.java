package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppDefaultAddress{

	/**
	 * 
	 */
    private Long addrId;
	/**
	 * 
	 */
    private Long addressId;
	/**
	 * 
	 */
    private String addrEmail;
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
    private String addrPhone;
	/**
	 * 
	 */
    private String city;
	/**
	 * 
	 */
    private String zip;
	/**
	 * 
	 */
    private String province;
	/**
	 * 
	 */
    private String country;
	/**
	 * 
	 */
    private String addrCompany;
	/**
	 * 
	 */
    private String addrName;
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
    private String countryName;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

    Long orderId;

    Long storeOrderId;

}
