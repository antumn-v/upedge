package com.upedge.pms.modules.quote.vo;

import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuoteApplyVo {


    private Long id;
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

    List<QuoteApplyItem> quoteApplyItems;
}
