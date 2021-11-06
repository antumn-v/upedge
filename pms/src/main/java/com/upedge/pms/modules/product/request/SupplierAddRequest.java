package com.upedge.pms.modules.product.request;


import com.upedge.pms.modules.supplier.entity.Supplier;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class SupplierAddRequest{

    /**
    *
    */
    private String loginId;
    /**
    *
    */
    private String supplierName;
    /**
    *
    */
    private String supplierLink;
    /**
    *
    */
    private Date createTime =new Date();
    /**
    *
    */
    private Date updateTime;
    /**
    *
    */
    private String companyName;
    /**
    *
    */
    private Integer isKuajingbao;
    /**
    *
    */
    private String categoryName;
            
    public Supplier toSupplier(){
        Supplier supplier=new Supplier();
        supplier.setLoginId(loginId);
        supplier.setSupplierName(supplierName);
        supplier.setSupplierLink(supplierLink);
        supplier.setCreateTime(createTime);
        supplier.setUpdateTime(updateTime);
        supplier.setCompanyName(companyName);

        supplier.setCategoryName(categoryName);
        return supplier;
    }

}
