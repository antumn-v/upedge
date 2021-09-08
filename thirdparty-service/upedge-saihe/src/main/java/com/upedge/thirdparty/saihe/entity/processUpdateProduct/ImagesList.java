package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cjq on 2019/3/29.
 */
public class ImagesList {

    List<ApiImportProductImage> ImagesList;//产品图片(最多9张)

    @XmlElement(name="ApiImportProductImage")
    public List<ApiImportProductImage> getImagesList() {
        return ImagesList;
    }

    public void setImagesList(List<ApiImportProductImage> imagesList) {
        ImagesList = imagesList;
    }
}
