package com.upedge.cms.modules.website.request;

import com.upedge.common.base.Page;
import com.upedge.cms.modules.website.entity.WebsiteCommentFollow;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
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
    private Long customerId;
    /**
    * 
    */
    private Long userId;
    /**
    * 
    */
    private Integer state;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private String userName;

    public WebsiteCommentFollow toWebsiteCommentFollow(){
        WebsiteCommentFollow websiteCommentFollow=new WebsiteCommentFollow();
        websiteCommentFollow.setCommentId(commentId);
        websiteCommentFollow.setCustomerId(customerId);
        websiteCommentFollow.setUserId(userId);
        websiteCommentFollow.setState(state);
        websiteCommentFollow.setUpdateTime(updateTime);
        websiteCommentFollow.setUserName(userName);
        return websiteCommentFollow;
    }

}
