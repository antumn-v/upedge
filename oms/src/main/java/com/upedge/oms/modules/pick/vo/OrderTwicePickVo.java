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

        private String barcode;

        private Set<Long> orderIds = new HashSet<>();
    }
}
