package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqInfoAddRequest{

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
    private Long cateId;
    /**
    * 
    */
    private Integer state;

    public WebsiteFaqInfo toWebsiteFaqInfo(String adminUser){
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setAskTitle(askTitle);
        websiteFaqInfo.setAnswerInfo(answerInfo);
        websiteFaqInfo.setCreateTime(new Date());
        websiteFaqInfo.setUpdateTime(new Date());
        websiteFaqInfo.setAdminUser(adminUser);
        websiteFaqInfo.setCateId(cateId);
        websiteFaqInfo.setState(state);
        return websiteFaqInfo;
    }

}
