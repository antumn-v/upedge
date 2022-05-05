package com.upedge.cms.modules.website.request;

import com.upedge.common.base.Page;
import com.upedge.cms.modules.website.entity.WebsiteRemark;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteRemarkAddRequest{

    /**
    * 
    */
    private Integer sort;
    /**
    * 0:客户 1:客户经理
    */
    private Integer type;
    /**
    * 
    */
    private String msg;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private String image;
    /**
    * 
    */
    private Integer index;
    /**
    * 
    */
    private Integer state;

    public WebsiteRemark toWebsiteRemark(){
        WebsiteRemark websiteRemark=new WebsiteRemark();
        websiteRemark.setSort(sort);
        websiteRemark.setType(type);
        websiteRemark.setMsg(msg);
        websiteRemark.setCreateTime(createTime);
        websiteRemark.setImage(image);
        websiteRemark.setIndex(index);
        websiteRemark.setState(state);
        return websiteRemark;
    }

}
