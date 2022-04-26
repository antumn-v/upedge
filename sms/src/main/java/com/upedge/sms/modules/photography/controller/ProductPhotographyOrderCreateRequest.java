package com.upedge.sms.modules.photography.controller;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductPhotographyOrderCreateRequest {

    @NotNull
    private String serviceTitle;

    /**
     * 参考链接
     */
    private String referenceLink;

    private String referenceImageBase64;

    private Integer photographyType = 0;

    @Size(min = 1)
    private List<ProductPhotographyOrderItem> items;
}
