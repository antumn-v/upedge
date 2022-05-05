package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteFaqCateUpdateRequest{

    @NotNull
    private Long id;
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
        websiteFaqCate.setId(id);
        websiteFaqCate.setName(name);
        websiteFaqCate.setSort(sort);
        websiteFaqCate.setCreateTime(createTime);
        websiteFaqCate.setState(state);
        return websiteFaqCate;
    }

}
