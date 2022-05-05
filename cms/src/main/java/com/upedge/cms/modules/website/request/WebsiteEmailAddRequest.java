package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteEmail;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteEmailAddRequest{

    /**
    * 
    */
    private String email;
    /**
    * 
    */
    private Date createTime;
    /**
    * 操作IP地址
    */
    private String remoteAddr;
    /**
    * 设备名称/操作系统
    */
    private String deviceName;
    /**
    * 
    */
    private Integer state;

    public WebsiteEmail toWebsiteEmail(){
        WebsiteEmail websiteEmail=new WebsiteEmail();
        websiteEmail.setEmail(email);
        websiteEmail.setCreateTime(createTime);
        websiteEmail.setRemoteAddr(remoteAddr);
        websiteEmail.setDeviceName(deviceName);
        websiteEmail.setState(state);
        return websiteEmail;
    }

}
