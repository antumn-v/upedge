package com.upedge.pms.modules.supplier.request;

import com.upedge.pms.modules.supplier.entity.Supplier;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class SupplierUpdateRequest{

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
    private Date createTime;
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
    private String categoryName;

    public Supplier toSupplier(Integer id){
        Supplier supplier=new Supplier();
        supplier.setId(id);
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
