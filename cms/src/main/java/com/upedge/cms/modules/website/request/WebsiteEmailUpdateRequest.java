package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteEmail;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteEmailUpdateRequest{

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

    public WebsiteEmail toWebsiteEmail(Long id){
        WebsiteEmail websiteEmail=new WebsiteEmail();
        websiteEmail.setId(id);
        websiteEmail.setEmail(email);
        websiteEmail.setCreateTime(createTime);
        websiteEmail.setRemoteAddr(remoteAddr);
        websiteEmail.setDeviceName(deviceName);
        websiteEmail.setState(state);
        return websiteEmail;
    }

}
