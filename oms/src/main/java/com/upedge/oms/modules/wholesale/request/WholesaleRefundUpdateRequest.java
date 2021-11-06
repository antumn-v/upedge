package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WholesaleRefundUpdateRequest{

    /**
     * 申请退款的理由
     */
    private String reason;
    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 驳回理由
     */
    private String rejectInfo;
    /**
     * 
     */
    private String managerCode;
    /**
     * 
     */
    private BigDecimal refundAmount;
    /**
     * 
     */
    private BigDecimal refundVatAmount;
    /**
     * 
     */
    private BigDecimal refundProductAmount;
    /**
     * 
     */
    private BigDecimal refundShippingPrice;
    /**
     * 0=审核中，1=已退款，2=已驳回
     */
    private Integer state;
    /**
     * 0:app,1:admin
     */
    private Integer source;
    /**
     * 退款订单发货状态 0:未发货 1:已发货
     */
    private Integer trackingState;
    /**
     * 赛盒订单渠道
     */
    private Integer orderSourceId;
    /**
     * 备注
     */
    private String remark;

    public WholesaleRefund toWholesaleRefund(Long id){
        WholesaleRefund wholesaleRefund=new WholesaleRefund();
        wholesaleRefund.setId(id);
        wholesaleRefund.setReason(reason);
        wholesaleRefund.setOrderId(orderId);
        wholesaleRefund.setCustomerId(customerId);
        wholesaleRefund.setCreateTime(createTime);
        wholesaleRefund.setUpdateTime(updateTime);
        wholesaleRefund.setRejectInfo(rejectInfo);
        wholesaleRefund.setManagerCode(managerCode);
        wholesaleRefund.setRefundAmount(refundAmount);
        wholesaleRefund.setRefundVatAmount(refundVatAmount);
        wholesaleRefund.setRefundProductAmount(refundProductAmount);
        wholesaleRefund.setRefundShippingPrice(refundShippingPrice);
        wholesaleRefund.setState(state);
        wholesaleRefund.setSource(source);
        wholesaleRefund.setTrackingState(trackingState);
        wholesaleRefund.setOrderSourceId(orderSourceId);
        wholesaleRefund.setRemark(remark);
        return wholesaleRefund;
    }

}
