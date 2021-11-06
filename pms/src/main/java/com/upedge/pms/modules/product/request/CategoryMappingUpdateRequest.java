package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.category.entity.CategoryMapping;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CategoryMappingUpdateRequest{

    private Long aliCnCategoryId;
    /**
     * 
     */
    private String aliCnCategoryName;
    /**
     * 
     */
    private String aliCateCode;
    /**
     * 
     */
    private Long categoryId;
    /**
     * 
     */
    private String categoryName;
    /**
     * 
     */
    private Date updateTime;

    public CategoryMapping toCategoryMapping(Long id){
        CategoryMapping categoryMapping=new CategoryMapping();
        categoryMapping.setAliCnCategoryId(aliCnCategoryId);
        categoryMapping.setAliCnCategoryName(aliCnCategoryName);
        categoryMapping.setAliCateCode(aliCateCode);
        categoryMapping.setCategoryId(categoryId);
        categoryMapping.setCategoryName(categoryName);
        categoryMapping.setUpdateTime(updateTime);
        return categoryMapping;
    }

}
