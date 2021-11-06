package com.upedge.pms.modules.product.request;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class ImportVariantUpdateStateRequest {

    @NonNull
    private Long productId;

    @NotNull
    private Integer state;

    @NotNull
    private List<Long> variantIds;

    public ImportVariantUpdateStateRequest() {
    }
}
