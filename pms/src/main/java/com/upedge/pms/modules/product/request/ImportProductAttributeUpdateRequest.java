package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ImportProductAttributeUpdateRequest{


    /**
     * 商品名称
     */
    private String title;
    /**
     * 产品类别
     */
    private String type;
    /**
     * 产品在product选项卡展示的图片
     */
    private String image;
    /**
     * 产品状态（已上架，未上架）
     */
    private Integer status;
    /**
     * 产品标签
     */
    private String tags;
    /**
     * 
     */
    private Long collectId;
    /**
     * 
     */
    private Date updateTime;


    public ImportProductAttribute toImportProductAttribute(Long id){
        ImportProductAttribute importProductAttribute=new ImportProductAttribute();
        importProductAttribute.setId(id);
        importProductAttribute.setTitle(title);
        importProductAttribute.setType(type);
        importProductAttribute.setImage(image);
        importProductAttribute.setState(status);
        importProductAttribute.setTags(tags);
        importProductAttribute.setCollectId(collectId);
        importProductAttribute.setUpdateTime(new Date());
        return importProductAttribute;
    }

}
