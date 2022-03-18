package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StoreProductVariantQuoteRequest {

    @NotNull(message = "拆分变体ID不能为空")
    Long storeVariantId;

    @NotBlank(message = "报价变体不能为空")
    String variantSku;

    @NotNull(message = "报价不能为空")
    BigDecimal quotePrice;

    @Min(value = 1,message = "报价比例最小为1")
    Integer quoteScale;

    @Min(value = 1,message = "最小数量为1")
    Integer quoteQuantity;
}
