package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteFaqInfoUpdateRequest{

    @NotNull
    private Long id;
    /**
     * 
     */
    private String askTitle;
    /**
     * 
     */
    private String answerInfo;
    /**
     * 
     */
    private Long cateId;
    /**
     * 
     */
    private Integer state;

    public WebsiteFaqInfo toWebsiteFaqInfo(){
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setId(id);
        websiteFaqInfo.setAskTitle(askTitle);
        websiteFaqInfo.setAnswerInfo(answerInfo);
        websiteFaqInfo.setUpdateTime(new Date());
        websiteFaqInfo.setCateId(cateId);
        websiteFaqInfo.setState(state);
        return websiteFaqInfo;
    }

}
