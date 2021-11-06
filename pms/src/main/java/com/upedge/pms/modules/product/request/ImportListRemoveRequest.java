package com.upedge.pms.modules.product.request;

import lombok.Data;

import java.util.List;

@Data
public class ImportListRemoveRequest {

    Long id;

    Long productId;

    List<Long> ids;

}
