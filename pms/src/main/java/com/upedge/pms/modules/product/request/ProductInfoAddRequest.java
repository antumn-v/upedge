package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ProductInfo;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductInfoAddRequest{

    /**
    * 商品描述
    */
    private String productDesc;
    /**
    * 商品id
    */
    private Long productId;
    /**
    * 
    */
    private String cnDesc;

    public ProductInfo toProductInfo(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductDesc(productDesc);
        productInfo.setProductId(productId);
        productInfo.setCnDesc(cnDesc);
        return productInfo;
    }

}
