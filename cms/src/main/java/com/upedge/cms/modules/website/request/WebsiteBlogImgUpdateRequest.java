package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogImg;
import lombok.Data;

/**
 * @author author
 */
@Data
public class WebsiteBlogImgUpdateRequest{

    /**
     * 
     */
    private String img;
    /**
     * 
     */
    private Long blogId;
    /**
     * 
     */
    private Integer state;

    public WebsiteBlogImg toWebsiteBlogImg(Long id){
        WebsiteBlogImg websiteBlogImg=new WebsiteBlogImg();
        websiteBlogImg.setId(id);
        websiteBlogImg.setImg(img);
        websiteBlogImg.setBlogId(blogId);
        websiteBlogImg.setState(state);
        return websiteBlogImg;
    }

}
