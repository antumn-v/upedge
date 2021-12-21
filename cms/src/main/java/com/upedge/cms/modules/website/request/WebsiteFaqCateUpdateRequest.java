package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqCateUpdateRequest{

    private Long id;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private Integer sort;

    public WebsiteFaqCate toWebsiteFaqCate(Long id){
        WebsiteFaqCate websiteFaqCate=new WebsiteFaqCate();
        websiteFaqCate.setId(id);
        websiteFaqCate.setName(name);
        websiteFaqCate.setSort(sort);
        websiteFaqCate.setCreateTime(new Date());
        return websiteFaqCate;
    }

}
