package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class PackageUsdRateUpdateRequest{

    @NotNull
    private String monthDate;
    /**
    * 
    */
    @NotNull
    private BigDecimal usdRate;

}
