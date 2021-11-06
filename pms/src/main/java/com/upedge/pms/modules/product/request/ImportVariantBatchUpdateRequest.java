package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class ImportVariantBatchUpdateRequest {

    @NotNull(message = "productId 为空")
    private Long productId;

    /**
     * 0=setNewValue
     * 1=multiplyBy
     */
    @NotNull(message = "type 为空")
    private Integer type;

    /**
     * price或者compare_price
     */
    @NotBlank(message = "field 为空")
    private String field;

    @NotNull(message = "value 为空")
    private BigDecimal value;

    private Integer state;
}
