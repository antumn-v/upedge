package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 海桐
 */
@Data
public class ImportAeProductRequest {

    @NotBlank
    private String url;

}
