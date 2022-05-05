package com.upedge.cms.modules.website.request;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import com.upedge.common.model.user.vo.Session;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class WebsiteFaqInfoAddRequest{

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
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long userId;
    /**
    * 
    */
    private Long cateId;
    /**
    * 
    */
    private Integer state;

    public WebsiteFaqInfo toWebsiteFaqInfo(Session session){
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setAskTitle(askTitle);
        websiteFaqInfo.setAnswerInfo(answerInfo);
        websiteFaqInfo.setCreateTime(createTime);
        websiteFaqInfo.setUpdateTime(updateTime);
        websiteFaqInfo.setCustomerId(session.getCustomerId());
        websiteFaqInfo.setUserId(session.getId());
        websiteFaqInfo.setCateId(cateId);
        websiteFaqInfo.setState(state);
        return websiteFaqInfo;
    }

}
