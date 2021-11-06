package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SplitNormalOrderRequest {

    @Size(min = 2)
    List<OrderSplitModule> orderSplitModules;

    public static class OrderSplitModule{

        List<ItemQuantity> itemQuantities;

        public List<ItemQuantity> getItemQuantities() {
            return itemQuantities;
        }

        public void setItemQuantities(List<ItemQuantity> itemQuantities) {
            this.itemQuantities = itemQuantities;
        }

        public static class ItemQuantity{
            Long itemId;
            Integer quantity;

            public Long getItemId() {
                return itemId;
            }

            public void setItemId(Long itemId) {
                this.itemId = itemId;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }
        }

    }


}
