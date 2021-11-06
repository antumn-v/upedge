package com.upedge.common.model.product.request;

import com.upedge.common.model.product.VariantDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariantShipsRequest {

    List<VariantDetail> variantDetails;

    Long areaId;

    Long customerId;

    Long shipMethodId;

}
