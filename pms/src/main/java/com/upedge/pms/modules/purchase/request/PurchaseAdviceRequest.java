package com.upedge.pms.modules.purchase.request;

import com.upedge.common.model.order.dto.OrderItemPurchaseAdviceDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PurchaseAdviceRequest extends OrderItemPurchaseAdviceDto {


    //1=正常采购  0=取消采购  -1=搁置
    @NotNull
    private Integer type;
}
