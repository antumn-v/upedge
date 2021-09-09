package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductImg;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductImgUpdateRequest{

    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品图片
     */
    private String imageUrl;
    /**
     * 1:启用/0:禁用
     */
    private Integer state;
    /**
     * 图片排序
     */
    private Integer imageSeq;

    public ProductImg toProductImg(Long id){
        ProductImg productImg=new ProductImg();
        productImg.setId(id);
        productImg.setProductId(productId);
        productImg.setImageUrl(imageUrl);
        productImg.setState(state);
        productImg.setImageSeq(imageSeq);
        return productImg;
    }

}
