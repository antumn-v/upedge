package com.upedge.pms.modules.product.request;


import com.upedge.pms.modules.category.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class CategoryAddRequest{

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
    private BigDecimal treeSort;
            
    public Category toCategory(){
        Category category=new Category();
        category.setCateCode(cateCode);
        category.setCateName(cateName);
        category.setParentCode(parentCode);
        category.setParentName(parentName);
        category.setPathName(pathName);
        category.setPathCode(pathCode);
        category.setCreateTime(createTime);
        category.setTreeSort(treeSort);
        return category;
    }

}
