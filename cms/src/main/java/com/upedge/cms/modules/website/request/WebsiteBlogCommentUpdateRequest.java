package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogComment;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogCommentUpdateRequest{

    /**
     * 
     */
    private Long blogId;
    /**
     * 
     */
    private Long appUserId;
    /**
     * 
     */
    private String comment;
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
    private Integer state;
    /**
     * 
     */
    private Integer followNum;

    public WebsiteBlogComment toWebsiteBlogComment(Long id){
        WebsiteBlogComment websiteBlogComment=new WebsiteBlogComment();
        websiteBlogComment.setId(id);
        websiteBlogComment.setBlogId(blogId);
        websiteBlogComment.setAppUserId(appUserId);
        websiteBlogComment.setComment(comment);
        websiteBlogComment.setCreateTime(createTime);
        websiteBlogComment.setUpdateTime(updateTime);
        websiteBlogComment.setState(state);
        websiteBlogComment.setFollowNum(followNum);
        return websiteBlogComment;
    }

}
