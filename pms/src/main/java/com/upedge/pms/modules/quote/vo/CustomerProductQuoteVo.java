package com.upedge.pms.modules.quote.vo;

import com.upedge.common.model.product.VariantDetail;
import lombok.Data;

@Data
public class CustomerProductQuoteVo extends VariantDetail {

    private Long storeProductId;

    private Long storeVariantId;

    private Long quoteApplyId;
}
