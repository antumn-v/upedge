package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteCommentFollow;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteCommentFollowAddRequest{

    /**
    * 
    */
    private Long commentId;
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

    public WebsiteCommentFollow toWebsiteCommentFollow(){
        WebsiteCommentFollow websiteCommentFollow=new WebsiteCommentFollow();
        websiteCommentFollow.setCommentId(commentId);
        websiteCommentFollow.setAppUserId(appUserId);
        websiteCommentFollow.setState(state);
        websiteCommentFollow.setUpdateTime(updateTime);
        return websiteCommentFollow;
    }

}
