package com.upedge.sms.modules.wholesale.request;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WholesaleOrderAddressAddRequest{

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

    public WholesaleOrderAddress toWholesaleOrderAddress(){
        WholesaleOrderAddress wholesaleOrderAddress=new WholesaleOrderAddress();
        wholesaleOrderAddress.setFirstName(firstName);
        wholesaleOrderAddress.setLastName(lastName);
        wholesaleOrderAddress.setEmail(email);
        wholesaleOrderAddress.setPhone(phone);
        wholesaleOrderAddress.setCountry(country);
        wholesaleOrderAddress.setProvince(province);
        wholesaleOrderAddress.setCity(city);
        wholesaleOrderAddress.setAddress1(address1);
        wholesaleOrderAddress.setAddress2(address2);
        wholesaleOrderAddress.setZip(zip);
        wholesaleOrderAddress.setCountryCode(countryCode);
        wholesaleOrderAddress.setProvinceCode(provinceCode);
        wholesaleOrderAddress.setOrderId(orderId);
        return wholesaleOrderAddress;
    }

}
