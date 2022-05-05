package com.upedge.cms.modules.website.request;

import com.upedge.common.base.Page;
import com.upedge.cms.modules.website.entity.WebsiteBlogComment;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteBlogCommentAddRequest{

    /**
    * 
    */
    private Long blogId;
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
    * 0:已禁用 1:已启用 2:已删除
    */
    private Integer state;
    /**
    * 
    */
    private Integer followNum;
    /**
    * 姓名
    */
    private String userName;

    public WebsiteBlogComment toWebsiteBlogComment(){
        WebsiteBlogComment websiteBlogComment=new WebsiteBlogComment();
        websiteBlogComment.setBlogId(blogId);
        websiteBlogComment.setCustomerId(customerId);
        websiteBlogComment.setUserId(userId);
        websiteBlogComment.setComment(comment);
        websiteBlogComment.setCreateTime(createTime);
        websiteBlogComment.setUpdateTime(updateTime);
        websiteBlogComment.setState(state);
        websiteBlogComment.setFollowNum(followNum);
        websiteBlogComment.setUserName(userName);
        return websiteBlogComment;
    }

}
