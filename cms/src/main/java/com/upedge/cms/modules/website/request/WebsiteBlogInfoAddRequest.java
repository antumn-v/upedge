package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import com.upedge.common.model.user.vo.Session;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteBlogInfoAddRequest{

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
    private Long customerId;
    /**
    * 
    */
    private Long userId;
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
    private String keywords;
    /**
    * 
    */
    private String description;
    /**
    * 
    */
    private Integer state;

    public WebsiteBlogInfo toWebsiteBlogInfo(Session session){
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setTitle(title);
        websiteBlogInfo.setUrlSuf(urlSuf);
        websiteBlogInfo.setSeoImg(seoImg);
        websiteBlogInfo.setImg(img);
        websiteBlogInfo.setShortInfo(shortInfo);
        websiteBlogInfo.setContent(content);
        websiteBlogInfo.setCustomerId(session.getCustomerId());
        websiteBlogInfo.setUserId(session.getId());
        websiteBlogInfo.setCreateTime(new Date());
        websiteBlogInfo.setUpdateTime(new Date());
        websiteBlogInfo.setViewNum(0);
        websiteBlogInfo.setFollowNum(0);
        websiteBlogInfo.setKeywords(keywords);
        websiteBlogInfo.setDescription(description);
        websiteBlogInfo.setState(state);
        return websiteBlogInfo;
    }

}
