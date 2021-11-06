package com.upedge.pms.modules.product.request;


import com.upedge.pms.modules.category.entity.AliCnCategory;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class AliCnCategoryUpdateRequest{

    /**
     * 
     */
    private String cateCode;
    /**
     * 
     */
    private String cateName;
    /**
     * 
     */
    private String parentCode;
    /**
     * 
     */
    private String parentName;
    /**
     * 
     */
    private String pathName;
    /**
     * 
     */
    private String pathCode;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Long shippingAttributeId;

    public AliCnCategory toAliCnCategory(Integer id){
        AliCnCategory aliCnCategory=new AliCnCategory();
        aliCnCategory.setId(id);
        aliCnCategory.setCateCode(cateCode);
        aliCnCategory.setCateName(cateName);
        aliCnCategory.setParentCode(parentCode);
        aliCnCategory.setParentName(parentName);
        aliCnCategory.setPathName(pathName);
        aliCnCategory.setPathCode(pathCode);
        aliCnCategory.setCreateTime(createTime);
        return aliCnCategory;
    }

}
