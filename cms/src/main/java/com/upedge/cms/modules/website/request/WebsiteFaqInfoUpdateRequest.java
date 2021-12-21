package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqInfoUpdateRequest{

    @NotNull
    private Long id;
    /**
     * 
     */
    @NotBlank
    private String askTitle;
    /**
     * 
     */
    @NotBlank
    private String answerInfo;
    /**
     * 
     */
    private String adminUser;
    /**
     * 
     */
    @NotNull
    private Long cateId;

    public WebsiteFaqInfo toWebsiteFaqInfo(Long id,String adminUser){
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setId(id);
        websiteFaqInfo.setAskTitle(askTitle);
        websiteFaqInfo.setAnswerInfo(answerInfo);
        websiteFaqInfo.setUpdateTime(new Date());
        websiteFaqInfo.setAdminUser(adminUser);
        websiteFaqInfo.setCateId(cateId);
        return websiteFaqInfo;
    }

}
