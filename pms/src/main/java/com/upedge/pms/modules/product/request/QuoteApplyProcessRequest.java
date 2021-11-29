package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.quote.dto.QuoteApplyProcessItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QuoteApplyProcessRequest {

    List<QuoteApplyProcessItem> items;


}
