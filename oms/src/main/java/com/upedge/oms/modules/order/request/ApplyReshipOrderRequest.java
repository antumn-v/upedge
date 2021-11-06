package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.vo.ReshipOrderItemVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ApplyReshipOrderRequest {

    @NotNull
    private Long originalOrderId;//补发目标订单id

    @NotNull
    private Long shipMethodId;

    @NotBlank
    private String reason;//补发原因

    @Size(min = 1,message = "补发项为空!")
    List<ReshipOrderItemVo> items;

}
