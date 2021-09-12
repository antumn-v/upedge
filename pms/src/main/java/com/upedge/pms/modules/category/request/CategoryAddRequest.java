package com.upedge.pms.modules.category.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.category.entity.Category;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
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
