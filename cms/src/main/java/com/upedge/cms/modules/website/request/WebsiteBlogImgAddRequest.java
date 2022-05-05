package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteBlogImg;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteBlogImgAddRequest{

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

    public WebsiteBlogImg toWebsiteBlogImg(){
        WebsiteBlogImg websiteBlogImg=new WebsiteBlogImg();
        websiteBlogImg.setImg(img);
        websiteBlogImg.setBlogId(blogId);
        websiteBlogImg.setState(state);
        return websiteBlogImg;
    }

}
