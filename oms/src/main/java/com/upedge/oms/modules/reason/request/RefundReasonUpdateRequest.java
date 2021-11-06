package com.upedge.oms.modules.reason.request;

import com.upedge.oms.modules.reason.entity.RefundReason;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class RefundReasonUpdateRequest{

    /**
     * 
     */
    @NotBlank
    private String reason;

    @NotNull
    private Integer state;

    public RefundReason toRefundReason(Long id){
        RefundReason refundReason=new RefundReason();
        refundReason.setId(id);
        refundReason.setState(state);
        refundReason.setReason(reason);
        return refundReason;
    }

}
