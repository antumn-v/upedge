package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteRemark;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteRemarkAddRequest{

    /**
    * 
    */
    @NotNull
    private Integer sort;
    /**
    * 
    */
    @NotNull
    private Integer type;
    /**
    * 
    */
    @NotNull
    private String msg;
    /**
    * 
    */
    private String image;
    /**
    * 
    */
    @NotNull
    private Integer index;

    public WebsiteRemark toWebsiteRemark(){
        WebsiteRemark websiteRemark=new WebsiteRemark();
        websiteRemark.setSort(sort);
        websiteRemark.setType(type);
        websiteRemark.setMsg(msg);
        websiteRemark.setCreateTime(new Date());
        //0:客户 1:客户经理
        if(type==0) {
          websiteRemark.setImage(image);
        }
        websiteRemark.setIndex(index);
        websiteRemark.setState(1);
        return websiteRemark;
    }

}
