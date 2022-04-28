package com.upedge.sms.modules.photography.vo;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductPhotographyOrderVo extends ProductPhotographyOrder {

    private List<ProductPhotographyOrderItem> orderItems;

    List<String> referenceImages;

    public void initReferenceImages(){
        referenceImages = new ArrayList<>();
        if (super.getReferenceImage() == null){
            return;
        }
        String referenceImage = super.getReferenceImage();
        if (referenceImage.contains(",")){
            String[] s = referenceImage.split(",");
            for (String s1 : s) {
                referenceImages.add(s1);
            }
        }else {
            referenceImages.add(referenceImage);
        }
    }
}
