package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.PaypalPayment;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PaypalPaymentAddRequest{

    /**
    * 收款方交易号
    */
    private String saleId;
    /**
    * 
    */
    private String paymentId;
    /**
    * 
    */
    private String paymentMethod;
    /**
    * 
    */
    private String payerEmail;
    /**
    * 
    */
    private String payerName;
    /**
    * 
    */
    private String payerId;
    /**
    * 
    */
    private String state;
    /**
    * 
    */
    private String amount;
    /**
    * 
    */
    private String currency;
    /**
    * 
    */
    private String fixFee;
    /**
    * 
    */
    private String createTime;
    /**
    * 
    */
    private String updateTime;
    /**
    * 
    */
    private String payeeEmail;
    /**
    * 
    */
    private String merchantId;
    /**
    * 备注
    */
    private String remark;
    /**
    * 
    */
    private Integer accountPaymethodId;
    /**
    * 
    */
    private Long accountId;
    /**
    * 
    */
    private Integer requestId;
    /**
    * 0=充值 1=备库订单 2=普通订单 3=批发订单
    */
    private Integer orderType;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long userId;

    public PaypalPayment toPaypalPayment(){
        PaypalPayment paypalPayment=new PaypalPayment();
        paypalPayment.setSaleId(saleId);
        paypalPayment.setPaymentId(paymentId);
        paypalPayment.setPaymentMethod(paymentMethod);
        paypalPayment.setPayerEmail(payerEmail);
        paypalPayment.setPayerName(payerName);
        paypalPayment.setPayerId(payerId);
        paypalPayment.setState(state);
        paypalPayment.setAmount(amount);
        paypalPayment.setCurrency(currency);
        paypalPayment.setFixFee(fixFee);
        paypalPayment.setCreateTime(createTime);
        paypalPayment.setUpdateTime(updateTime);
        paypalPayment.setPayeeEmail(payeeEmail);
        paypalPayment.setMerchantId(merchantId);
        paypalPayment.setRemark(remark);
        paypalPayment.setAccountPaymethodId(accountPaymethodId);
        paypalPayment.setAccountId(accountId);
        paypalPayment.setRequestId(requestId);
        paypalPayment.setOrderType(orderType);
        paypalPayment.setCustomerId(customerId);
        paypalPayment.setUserId(userId);
        return paypalPayment;
    }

}
