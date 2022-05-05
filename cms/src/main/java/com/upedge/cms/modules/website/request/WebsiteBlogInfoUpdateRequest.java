package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteBlogInfoUpdateRequest{

    @NotNull
    private Long id;

    /**
     * 
     */
    private String title;
    /**
     * 
     */
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
    private String shortInfo;
    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private Integer viewNum;
    /**
     * 
     */
    private Integer followNum;
    /**
     * 
     */
    private String keywords;
    /**
     * 
     */
    private String description;
    /**
     * 
     */
    private Integer state;

    public WebsiteBlogInfo toWebsiteBlogInfo(Long userId){
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setId(id);
        websiteBlogInfo.setTitle(title);
        websiteBlogInfo.setUrlSuf(urlSuf);
        websiteBlogInfo.setSeoImg(seoImg);
        websiteBlogInfo.setImg(img);
        websiteBlogInfo.setShortInfo(shortInfo);
        websiteBlogInfo.setContent(content);
        websiteBlogInfo.setUserId(userId);
        websiteBlogInfo.setCreateTime(createTime);
        websiteBlogInfo.setUpdateTime(updateTime);
        websiteBlogInfo.setViewNum(viewNum);
        websiteBlogInfo.setFollowNum(followNum);
        websiteBlogInfo.setKeywords(keywords);
        websiteBlogInfo.setDescription(description);
        websiteBlogInfo.setState(state);
        return websiteBlogInfo;
    }

}
