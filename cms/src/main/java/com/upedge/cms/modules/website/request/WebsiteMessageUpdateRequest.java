package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class WebsiteMessageUpdateRequest{
    @NotNull
    private Long id;
    /**
     * 
     */
    @NotBlank
    private String remark;


    public WebsiteMessage toWebsiteMessage(){
        WebsiteMessage websiteMessage=new WebsiteMessage();
        websiteMessage.setId(id);
        websiteMessage.setRemark(remark);
        return websiteMessage;
    }

}
