package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderTwicePickVo {

    private List<OrderPickInfoVo> orderPickInfoVos;


    private List<VariantOrderId> variantOrderIds;

    @Data
    public static class VariantOrderId{
        private Long variantId;

        private String variantSku;

        private String variantName;

        private String variantImage;

        private String barcode;

        private Integer quantity;

        private Integer pickedQuantity;

        private Set<Long> orderIds = new HashSet<>();
    }
}
