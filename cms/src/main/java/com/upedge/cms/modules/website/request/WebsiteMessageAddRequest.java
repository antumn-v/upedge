package com.upedge.cms.modules.website.request;

import com.upedge.common.base.Page;
import com.upedge.cms.modules.website.entity.WebsiteMessage;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteMessageAddRequest{

    /**
    * 
    */
    private String name;
    /**
    * 
    */
    private String email;
    /**
    * 
    */
    private String whatsapp;
    /**
    * 
    */
    private String facebook;
    /**
    * 
    */
    private String skype;
    /**
    * 
    */
    private String message;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long userId;
    /**
    * 
    */
    private String remark;
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

    public WebsiteMessage toWebsiteMessage(){
        WebsiteMessage websiteMessage=new WebsiteMessage();
        websiteMessage.setName(name);
        websiteMessage.setEmail(email);
        websiteMessage.setWhatsapp(whatsapp);
        websiteMessage.setFacebook(facebook);
        websiteMessage.setSkype(skype);
        websiteMessage.setMessage(message);
        websiteMessage.setCreateTime(createTime);
        websiteMessage.setCustomerId(customerId);
        websiteMessage.setUserId(userId);
        websiteMessage.setRemark(remark);
        websiteMessage.setRemoteAddr(remoteAddr);
        websiteMessage.setDeviceName(deviceName);
        websiteMessage.setState(state);
        return websiteMessage;
    }

}
