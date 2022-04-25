package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderAddItemRequest {

    @NotNull
    private Long orderId;

    @Size(min = 1)
    private List<OrderItem> items;

//    @NoArgsConstructor
//    @Data
//    public class OrderAddItem{
//
//        private Long storeVariantId;
//
//        private String title;
//
//        private String sku;
//
//        private String image;
//
//        private Integer quantity;
//
//        public OrderItem toOrderItem(Long id){
//            OrderItem orderItem = new OrderItem();
//            orderItem.setId(id);
//            orderItem.setQuantity(quantity);
//            orderItem.setStoreVariantName(title);
//            orderItem.setStoreVariantSku(sku);
//            orderItem.setStoreVariantId(storeVariantId);
//            orderItem.setStoreProductTitle(title);
//            orderItem.setStoreVariantImage(image);
//            return orderItem;
//        }
//    }


}
