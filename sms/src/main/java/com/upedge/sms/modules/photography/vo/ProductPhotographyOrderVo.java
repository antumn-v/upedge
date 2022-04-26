package com.upedge.sms.modules.photography.vo;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class ProductPhotographyOrderVo extends ProductPhotographyOrder {

    private List<ProductPhotographyOrderItem> orderItems;
}
