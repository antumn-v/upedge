package com.upedge.pms.modules.product.request;


import lombok.Data;

/**
 * @author gx
 */
@Data
public class ManagerProductUpdateRequest{

    /**
     * 产品id
     */
    private Long productId;

    String managerCode;



}
