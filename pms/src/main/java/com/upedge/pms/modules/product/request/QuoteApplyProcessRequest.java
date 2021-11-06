package com.upedge.pms.modules.product.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QuoteApplyProcessRequest {

    List<QuoteApplyProcessItem> items;

    @Data
    public class QuoteApplyProcessItem {
        Long quoteApplyItemId;

        String variantSku;

        BigDecimal price;
    }
}
