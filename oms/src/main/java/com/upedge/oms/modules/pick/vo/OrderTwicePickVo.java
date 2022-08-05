package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderTwicePickVo {

    private List<OrderPickInfoVo> orderPickInfoVos;


    private List<VariantOrderId> variantOrderIds;

    @Data
    public static class VariantOrderId{
        private Long variantId;

        private String variantSku;

        private String barcode;

        private List<Long> orderIds = new ArrayList<>();
    }
}
