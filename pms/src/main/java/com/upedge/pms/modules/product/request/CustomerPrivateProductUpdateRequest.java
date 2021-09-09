package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerPrivateProductUpdateRequest{

    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 用户ID
     */
    private Long customerId;

    public CustomerPrivateProduct toCustomerPrivateProduct(Long id){
        CustomerPrivateProduct customerPrivateProduct=new CustomerPrivateProduct();
        customerPrivateProduct.setId(id);
        customerPrivateProduct.setProductId(productId);
        customerPrivateProduct.setCustomerId(customerId);
        return customerPrivateProduct;
    }

}
