package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogInfoAddRequest{

    /**
    * 
    */
    @NotBlank
    private String title;
    /**
    * 
    */
    @NotBlank
    private String urlSuf;
    /**
    * 
    */
    private String seoImg;
    /**
    * 
    */
    private String img;
    /**
    * 
    */
    @NotBlank
    private String shortInfo;
    /**
    * 
    */
    @NotBlank
    private String content;
    /**
    * 
    */
    private String keywords;
    /**
    * 
    */
    private String description;

    public WebsiteBlogInfo toWebsiteBlogInfo(String adminUser){
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setTitle(title);
        websiteBlogInfo.setUrlSuf(urlSuf);
        websiteBlogInfo.setSeoImg(seoImg);
        websiteBlogInfo.setImg(img);
        websiteBlogInfo.setShortInfo(shortInfo);
        websiteBlogInfo.setContent(content);
        websiteBlogInfo.setAdminUser(adminUser);
        websiteBlogInfo.setCreateTime(new Date());
        websiteBlogInfo.setUpdateTime(new Date());
        websiteBlogInfo.setViewNum(0);
        websiteBlogInfo.setFollowNum(0);
        websiteBlogInfo.setKeywords(keywords);
        websiteBlogInfo.setDescription(description);
        websiteBlogInfo.setState(1);
        return websiteBlogInfo;
    }

}
