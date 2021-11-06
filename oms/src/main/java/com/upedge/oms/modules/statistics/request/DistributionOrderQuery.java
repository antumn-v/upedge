package com.upedge.oms.modules.statistics.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 数据报表   销售统计  qurey
 */
@Data
public class DistributionOrderQuery {

    /**
     * 时间    yyyy-MM
     */
    @NotNull(message = "月份不能为空")
    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    private Date queryDate;

    /**
     * 订单来源
     */
    @NotNull(message = "订单来源id不能为空")
    private Long orderSourceId;


    @NotBlank(message = "订单来源name不能为空")
    private String orderSourceName;

}
