package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogFollow;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteBlogFollowUpdateRequest{

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
    private Integer state;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private String userName;

    public WebsiteBlogFollow toWebsiteBlogFollow(Long id){
        WebsiteBlogFollow websiteBlogFollow=new WebsiteBlogFollow();
        websiteBlogFollow.setId(id);
        websiteBlogFollow.setBlogId(blogId);
        websiteBlogFollow.setCustomerId(customerId);
        websiteBlogFollow.setUserId(userId);
        websiteBlogFollow.setState(state);
        websiteBlogFollow.setUpdateTime(updateTime);
        websiteBlogFollow.setUserName(userName);
        return websiteBlogFollow;
    }

}
