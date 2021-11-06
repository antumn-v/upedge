package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.vo.PushRelateVo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class PushRelateRequest {
    @NotNull
    private Long customerProductId;
    @NotNull
    private Long adminProductId;
    @Size(min = 1)
    private List<PushRelateVo> pushRelateVoList;


}
