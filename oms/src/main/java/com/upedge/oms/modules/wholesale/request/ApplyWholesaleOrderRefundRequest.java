package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApplyWholesaleOrderRefundRequest {
    @NotNull
    private Long orderId;
    @NotBlank
    private String refundReason;
    @NotNull
    BigDecimal shippingPrice;
    @NotNull
    BigDecimal vatAmount;

    String remark;

    List<WholesaleRefundItem> refundItemList=new ArrayList<>();


}
