package com.upedge.oms.modules.statistics.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ExcelDostributionOrderQuery {


    @NotBlank(message = "开始时间不能为空")
    private String startDay;

    @NotBlank(message = "结束时间不能为空")
    private String endDay;

    /**
     * -1 代表全查
     */
    @NotNull(message = "订单来源id不能为空")
    private Long orderSourceId;

    @NotBlank(message = "订单来源name为空！")
    private String orderSourceName;
}
