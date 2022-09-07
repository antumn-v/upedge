package com.upedge.pms.modules.product.request;

import lombok.Data;

import java.util.List;

@Data
public class ClaimQuoteApplyRequest {

    private Long quoteApplyId;


    private List<Long> quoteApplyIds;

}
