package com.upedge.cms.modules.website.request;

import com.upedge.common.base.Page;
import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WebsiteFaqCateAddRequest{

    /**
    * 
    */
    private String name;
    /**
    * 
    */
    private Integer sort;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Integer state;

    public WebsiteFaqCate toWebsiteFaqCate(){
        WebsiteFaqCate websiteFaqCate=new WebsiteFaqCate();
        websiteFaqCate.setName(name);
        websiteFaqCate.setSort(sort);
        websiteFaqCate.setCreateTime(createTime);
        websiteFaqCate.setState(state);
        return websiteFaqCate;
    }

}
