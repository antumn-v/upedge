package com.upedge.pms.modules.category.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.category.entity.AliCnCategory;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class AliCnCategoryAddRequest{

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

    public AliCnCategory toAliCnCategory(){
        AliCnCategory aliCnCategory=new AliCnCategory();
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
