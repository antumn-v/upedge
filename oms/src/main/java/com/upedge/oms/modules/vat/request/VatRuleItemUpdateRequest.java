package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.vo.VatAreaVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author author
 */
@Data
public class VatRuleItemUpdateRequest{

    /**
     * 
     */
    @NotNull
    private Long ruleId;
    /**
     * 
     */
    @NotNull
    @Size(min = 1)
    private List<VatAreaVo> vatAreaVos;

}
