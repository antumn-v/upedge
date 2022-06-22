package com.upedge.sms.modules.photography.request;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductPhotographyOrderCreateRequest {

    @NotNull
    private String serviceTitle;

    private String description;

    /**
     * 参考链接
     */
    private String referenceLink;

    private String base64;

    private List<String> referenceImageBase64;

    private Integer photographyType = 0;

    private String remark;

    @Size(min = 1)
    private List<ProductPhotographyOrderItem> items;
}
