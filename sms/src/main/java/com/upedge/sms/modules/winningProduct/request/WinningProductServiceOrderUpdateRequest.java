package com.upedge.sms.modules.winningProduct.request;

import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WinningProductServiceOrderUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Integer serviceOption;
    /**
     * 
     */
    private String category;
    /**
     * 
     */
    private String description;
    /**
     * 
     */
    private String productFileLink;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private Date payTime;
    /**
     * 
     */
    private Long paymentId;
    /**
     * 
     */
    private Integer payState;
    /**
     * 
     */
    private Integer refundState;
    /**
     * 
     */
    private Integer orderState;

    public WinningProductServiceOrder toWinningProductServiceOrder(Long id){
        WinningProductServiceOrder winningProductServiceOrder=new WinningProductServiceOrder();
        winningProductServiceOrder.setId(id);
        winningProductServiceOrder.setCustomerId(customerId);
        winningProductServiceOrder.setServiceOption(serviceOption);
        winningProductServiceOrder.setCategory(category);
        winningProductServiceOrder.setDescription(description);
        winningProductServiceOrder.setProductFileLink(productFileLink);
        winningProductServiceOrder.setCreateTime(createTime);
        winningProductServiceOrder.setUpdateTime(updateTime);
        winningProductServiceOrder.setPayTime(payTime);
        winningProductServiceOrder.setPaymentId(paymentId);
        winningProductServiceOrder.setPayState(payState);
        winningProductServiceOrder.setRefundState(refundState);
        winningProductServiceOrder.setOrderState(orderState);
        return winningProductServiceOrder;
    }

}
