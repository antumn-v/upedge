package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ImportProductAttributeAddRequest{

                                    /**
             * 原商品ID
             */
            private Long adminProductId;
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
             * 商品上架店铺后的ID
             */
            private String platProductId;
                                /**
             * 
             */
            private Long collectId;
                                /**
             * 产品来源,0=app,1=ali
             */
            private Integer source;
                                /**
             * 原标题
             */
            private String originalTitle;
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
            private Long userId;
                                /**
             * 
             */
            private Long storeId;
            
    public ImportProductAttribute toImportProductAttribute(){
        ImportProductAttribute importProductAttribute=new ImportProductAttribute();
        importProductAttribute.setSourceProductId(adminProductId);
        importProductAttribute.setTitle(title);
        importProductAttribute.setType(type);
        importProductAttribute.setImage(image);
        importProductAttribute.setState(status);
        importProductAttribute.setTags(tags);
        importProductAttribute.setPlatProductId(platProductId);
        importProductAttribute.setCollectId(collectId);
        importProductAttribute.setSource(1);
        importProductAttribute.setOriginalTitle(originalTitle);
        importProductAttribute.setCreateTime(createTime);
        importProductAttribute.setUpdateTime(updateTime);
        importProductAttribute.setUserId(userId);
        importProductAttribute.setStoreId(storeId);
        return importProductAttribute;
    }

}
