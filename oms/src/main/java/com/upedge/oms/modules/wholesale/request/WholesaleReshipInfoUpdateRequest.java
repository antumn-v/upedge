package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;
import lombok.Data;

/**
 * @author author
 */
@Data
public class WholesaleReshipInfoUpdateRequest{

    /**
     * 
     */
    private Long originalOrderId;
    /**
     * 
     */
    private String reason;
    /**
     * 补发次数
     */
    private Integer reshipTimes;

    public WholesaleReshipInfo toWholesaleReshipInfo(Long orderId){
        WholesaleReshipInfo wholesaleReshipInfo=new WholesaleReshipInfo();
        wholesaleReshipInfo.setOrderId(orderId);
        wholesaleReshipInfo.setOriginalOrderId(originalOrderId);
        wholesaleReshipInfo.setReason(reason);
        wholesaleReshipInfo.setReshipTimes(reshipTimes);
        return wholesaleReshipInfo;
    }

}
