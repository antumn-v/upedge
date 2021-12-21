package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteRemark;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class WebsiteRemarkUpdateRequest{

    @NotNull
    private Long id;
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
    @NotBlank
    private String msg;
    /**
     * 
     */
    @NotNull
    private Integer index;

    public WebsiteRemark toWebsiteRemark(Long id){
        WebsiteRemark websiteRemark=new WebsiteRemark();
        websiteRemark.setId(id);
        websiteRemark.setSort(sort);
        websiteRemark.setType(type);
        websiteRemark.setMsg(msg);
        websiteRemark.setIndex(index);
        return websiteRemark;
    }

}
