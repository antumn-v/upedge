package com.upedge.oms.modules.reason.request;

import com.upedge.oms.modules.reason.entity.RefundReason;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class RefundReasonAddRequest{

    /**
    * 
    */
    @NotBlank
    private String reason;

    public RefundReason toRefundReason(){
        RefundReason refundReason=new RefundReason();
        refundReason.setReason(reason);
        refundReason.setState(1);
        refundReason.setCreateTime(new Date());
        refundReason.setUpdateTime(new Date());
        return refundReason;
    }

}
