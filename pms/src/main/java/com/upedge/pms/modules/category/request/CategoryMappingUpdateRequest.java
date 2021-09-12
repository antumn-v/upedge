package com.upedge.pms.modules.category.request;

import com.upedge.pms.modules.category.entity.CategoryMapping;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CategoryMappingUpdateRequest{

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
//        categoryMapping.setAliCnCategoryId(aliCnCategoryId);
        categoryMapping.setAliCnCategoryName(aliCnCategoryName);
        categoryMapping.setAliCateCode(aliCateCode);
        categoryMapping.setCategoryId(categoryId);
        categoryMapping.setCategoryName(categoryName);
        categoryMapping.setUpdateTime(updateTime);
        return categoryMapping;
    }

}
