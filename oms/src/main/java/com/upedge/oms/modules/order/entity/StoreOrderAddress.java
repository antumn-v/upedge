package com.upedge.oms.modules.order.entity;

import com.upedge.thirdparty.shopify.moudles.order.entity.ShippingAddressBean;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder.*;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderAddress;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author author
 */
@Data
public class StoreOrderAddress{

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
    private String note;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private String platOrderId;
	/**
	 * 
	 */
    private String company;

	public StoreOrderAddress(ShippingAddressBean addressBean) {
		this.address1 = addressBean.getAddress1();
		this.address2 = addressBean.getAddress2();
		this.city = addressBean.getCity();
		this.country = addressBean.getCountry();
		this.province = addressBean.getProvince();
		this.firstName = addressBean.getFirst_name();
		this.lastName = addressBean.getLast_name();
		this.phone = addressBean.getPhone();
		this.zip = addressBean.getZip();
		this.company = addressBean.getCompany();
		this.countryCode = addressBean.getCountry_code();
		this.provinceCode = addressBean.getProvince_code();
		if (StringUtils.isNotBlank(this.province)){
			this.province = addressBean.getCity();
		}
	}

	public StoreOrderAddress(ShoplazzaShippingAddress addressBean) {
		this.address1 = addressBean.getAddress1();
		this.address2 = addressBean.getAddress2();
		this.city = addressBean.getCity();
		this.country = addressBean.getCountry();
		this.province = addressBean.getProvince();
		this.firstName = addressBean.getFirst_name();
		this.lastName = addressBean.getLast_name();
		this.phone = addressBean.getPhone();
		this.zip = addressBean.getZip();
		this.company = addressBean.getCompany();
		this.countryCode = addressBean.getCountry_code();
		this.provinceCode = addressBean.getProvince_code();
	}

	public StoreOrderAddress(WoocommerceOrderAddress addressBean) {
		this.address1 = addressBean.getAddress_1();
		this.address2 = addressBean.getAddress_2();
		this.city = addressBean.getCity();
		this.country = addressBean.getCountry();
		this.province = addressBean.getState();
		this.firstName = addressBean.getFirst_name();
		this.lastName = addressBean.getLast_name();
		this.zip = addressBean.getPostcode();
		this.company = addressBean.getCompany();
		this.countryCode = addressBean.getCountry();
		this.provinceCode = addressBean.getState();

	}

	public StoreOrderAddress() {
	}
}
