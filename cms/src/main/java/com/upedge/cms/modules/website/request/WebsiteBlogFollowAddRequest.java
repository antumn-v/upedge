package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogFollow;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogFollowAddRequest{

    /**
    * 
    */
    private Long blogId;
    /**
    * 
    */
    private String appUserId;
    /**
    * 
    */
    private Integer state;
    /**
    * 
    */
    private Date updateTime;

    public WebsiteBlogFollow toWebsiteBlogFollow(){
        WebsiteBlogFollow websiteBlogFollow=new WebsiteBlogFollow();
        websiteBlogFollow.setBlogId(blogId);
        websiteBlogFollow.setAppUserId(appUserId);
        websiteBlogFollow.setState(state);
        websiteBlogFollow.setUpdateTime(updateTime);
        return websiteBlogFollow;
    }

}
