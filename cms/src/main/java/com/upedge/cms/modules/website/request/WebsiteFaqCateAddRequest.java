package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqCateAddRequest{

    /**
    * 
    */
    @NotBlank
    private String name;
    /**
    * 
    */
    @NotNull
    private Integer sort;

    public WebsiteFaqCate toWebsiteFaqCate(){
        WebsiteFaqCate websiteFaqCate=new WebsiteFaqCate();
        websiteFaqCate.setName(name);
        websiteFaqCate.setSort(sort);
        websiteFaqCate.setCreateTime(new Date());
        websiteFaqCate.setState(1);
        return websiteFaqCate;
    }

}
