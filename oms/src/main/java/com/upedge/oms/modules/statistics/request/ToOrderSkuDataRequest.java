package com.upedge.oms.modules.statistics.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.statistics.vo.ToOrderSkuVo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ToOrderSkuDataRequest extends Page<ToOrderSkuVo> {

    @NotNull
    private Long customerId;

}
