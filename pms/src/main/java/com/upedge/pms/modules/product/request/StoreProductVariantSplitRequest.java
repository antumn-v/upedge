package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.dto.StoreProductVariantSplitVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StoreProductVariantSplitRequest {

    @NotNull
    Long storeVariantId;

    @Size(min = 1,message = "子变体不能为空")
    List<StoreProductVariantSplitVo> splitVos;
}
