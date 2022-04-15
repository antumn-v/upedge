package com.upedge.common.model.cart.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartSelectByIdsRequest {

    List<Long> ids;

    Integer cartType;

    Long customerId;
}
