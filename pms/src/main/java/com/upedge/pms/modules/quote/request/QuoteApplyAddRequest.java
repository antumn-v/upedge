package com.upedge.pms.modules.quote.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.entity.QuoteApply;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class QuoteApplyAddRequest{

    /**
    * 
    */
    private Long applyUserId;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long handleUserId;
    /**
    * 
    */
    private Long relateOrderId;
    /**
    * 0=待认领，1=处理中，2=已完成
    */
    private Integer quoteState;
    /**
    * 0=未报价，1=全部报价，2=部分报价
    */
    private Integer quoteType;
    /**
    * 备注
    */
    private String remark;
    /**
    * 
    */
    private String feedback;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public QuoteApply toQuoteApply(){
        QuoteApply quoteApply=new QuoteApply();
        quoteApply.setApplyUserId(applyUserId);
        quoteApply.setCustomerId(customerId);
        quoteApply.setHandleUserId(handleUserId);
        quoteApply.setRelateOrderId(relateOrderId);
        quoteApply.setQuoteState(quoteState);
        quoteApply.setQuoteType(quoteType);
        quoteApply.setRemark(remark);
        quoteApply.setFeedback(feedback);
        quoteApply.setCreateTime(createTime);
        quoteApply.setUpdateTime(updateTime);
        return quoteApply;
    }

}
