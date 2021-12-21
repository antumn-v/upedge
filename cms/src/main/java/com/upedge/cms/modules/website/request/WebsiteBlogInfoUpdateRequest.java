package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogInfoUpdateRequest{

    @NotNull
    private Long id;
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

    public WebsiteBlogInfo toWebsiteBlogInfo(Long id,String adminUser){
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setId(id);
        websiteBlogInfo.setTitle(title);
        websiteBlogInfo.setUrlSuf(urlSuf);
        websiteBlogInfo.setSeoImg(seoImg);
        websiteBlogInfo.setImg(img);
        websiteBlogInfo.setShortInfo(shortInfo);
        websiteBlogInfo.setContent(content);
        websiteBlogInfo.setAdminUser(adminUser);
        websiteBlogInfo.setUpdateTime(new Date());
        websiteBlogInfo.setKeywords(keywords);
        websiteBlogInfo.setDescription(description);
        return websiteBlogInfo;
    }

}
