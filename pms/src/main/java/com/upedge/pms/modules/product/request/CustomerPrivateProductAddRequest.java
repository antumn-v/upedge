package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerPrivateProductAddRequest{

    /**
    * 产品ID
    */
    private Long productId;
    /**
    * 用户ID
    */
    private Long customerId;

    public CustomerPrivateProduct toCustomerPrivateProduct(){
        CustomerPrivateProduct customerPrivateProduct=new CustomerPrivateProduct();
        customerPrivateProduct.setProductId(productId);
        customerPrivateProduct.setCustomerId(customerId);
        return customerPrivateProduct;
    }

}
