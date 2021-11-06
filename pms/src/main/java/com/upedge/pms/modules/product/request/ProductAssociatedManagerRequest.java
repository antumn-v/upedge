package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 超级管理员将产品分配给客户经理
 */
@Data
public class ProductAssociatedManagerRequest {

    /**
     * 客户经理code
     */
    @NotNull
    private String managerCode;

    /**
     * 产品id List
     */
    @Size(min = 1)
    private List<Long> productIds;
}
