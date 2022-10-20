package com.upedge.pms.modules.product.vo;


import com.upedge.pms.modules.product.entity.ProductAttribute;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.entity.ProductVariant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddProductVo {

    /**
     * 商品标题
     */
    @NotBlank(message = "productTitle")
    private String productTitle;
    /**
     * 商品主图
     */
    @NotBlank(message = "productImage")
    private String productImage;
    /**
     * 运输模板id
     */
    private Long shippingId;
    /**
     * 商品类目code
     */
    private Long cateCode;
    /**
     * 0:普通商品 1:定制包装
     */
    private Integer cateType;

    @Size(min = 1,message = "upedgeVariantList")
    private List<ProductVariant> productVariantList=new ArrayList<>();
    @Size(min = 1,message = "upedgeImgList")
    private List<ProductImg> productImgList=new ArrayList<>();
    private ProductInfo productInfo=new ProductInfo();
    private ProductAttribute productAttribute=new ProductAttribute();

}
