package com.upedge.sms.modules.winningProduct.request;

import com.upedge.common.constant.OrderConstant;
import com.upedge.common.utils.IdGenerate;
import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class WinningProductServiceOrderAddRequest{

    @NotNull
    private String serviceTitle;

    @NotNull
    private Integer serviceOption;

    @NotNull
    private BigDecimal payAmount;
    /**
    * 
    */
    @NotNull
    private String category;
    /**
    * 
    */
    @NotNull
    private String description;

    private String remark;


    public WinningProductServiceOrder toWinningProductServiceOrder(){
        Date date = new Date();
        WinningProductServiceOrder winningProductServiceOrder=new WinningProductServiceOrder();
        winningProductServiceOrder.setServiceOption(serviceOption);
        winningProductServiceOrder.setCategory(category);
        winningProductServiceOrder.setDescription(description);
        winningProductServiceOrder.setPayAmount(payAmount);
        winningProductServiceOrder.setCreateTime(date);
        winningProductServiceOrder.setUpdateTime(date);
        winningProductServiceOrder.setPayTime(date);
        winningProductServiceOrder.setPaymentId(IdGenerate.nextId());
        winningProductServiceOrder.setPayState(OrderConstant.PAY_STATE_PAID);
        winningProductServiceOrder.setRefundState(0);
        winningProductServiceOrder.setOrderState(0);
        return winningProductServiceOrder;
    }

}
