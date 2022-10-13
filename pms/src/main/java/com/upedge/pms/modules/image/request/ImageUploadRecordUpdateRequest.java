package com.upedge.pms.modules.image.request;

import com.upedge.pms.modules.image.entity.ImageUploadRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ImageUploadRecordUpdateRequest{

    /**
     * 
     */
    private String oldImage;
    /**
     * 
     */
    private String newImage;
    /**
     * 0=店铺  1=1688
     */
    private Integer imageSource;
    /**
     * 
     */
    private String imageSourceId;
    /**
     * 
     */
    private Date createTime;

    public ImageUploadRecord toImageUploadRecord(Integer id){
        ImageUploadRecord imageUploadRecord=new ImageUploadRecord();
        imageUploadRecord.setId(id);
        imageUploadRecord.setOldImage(oldImage);
        imageUploadRecord.setNewImage(newImage);
        imageUploadRecord.setImageSource(imageSource);
        imageUploadRecord.setImageSourceId(imageSourceId);
        imageUploadRecord.setCreateTime(createTime);
        return imageUploadRecord;
    }

}
