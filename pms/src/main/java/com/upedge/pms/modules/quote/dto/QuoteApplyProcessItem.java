package com.upedge.pms.modules.quote.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class QuoteApplyProcessItem {

    Long quoteApplyItemId;

    String variantSku;

    BigDecimal price;

    boolean canQuote;
}
