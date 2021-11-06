package com.upedge.oms.modules.fulfillment.vo;

import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyFulfillment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntityObject {

    private ShopifyFulfillment fulfillment;

    private List<ShopifyFulfillment> fulfillments=new ArrayList<>();

}
