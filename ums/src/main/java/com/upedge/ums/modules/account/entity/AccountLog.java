package com.upedge.ums.modules.account.entity;

import com.upedge.common.enums.TransactionType;
import com.upedge.common.model.old.ums.AccountFlow;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AccountLog {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long accountId;


    private Long customerId;

    private Integer transactionType;

    private Integer orderType;

    private Integer payMethod;
    /**
     *
     */
    private Long transactionId;
    /**
     *
     */
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal rebate = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal credit = BigDecimal.ZERO;
    /**
     * 手续费
     */
    private BigDecimal fixFee;
    /**
     *
     */
    private Date createTime;

    /**
     * 开始时间 搜索使用  不存在数据库映射
     */
    private String shipDateStart;


    /**
     * 结束时间   搜索使用  不存在数据库映射
     */
    private String shipDateEnd;

    public AccountLog(AccountFlow accountFlow) {
        this.id = accountFlow.getId();
        this.createTime = accountFlow.getCreateTime();
        this.fixFee = accountFlow.getFixFee();
        this.balance = accountFlow.getAmount();
        this.rebate = accountFlow.getBenefits();
        this.credit = accountFlow.getCredit();
        this.transactionId = accountFlow.getRelateId();
        this.customerId = accountFlow.getAppUserId();
        TransactionType transactionType = null;
        switch (accountFlow.getType()){
            case 1:
                transactionType = TransactionType.TRANSFER_RECHARGE;
                break;
            case 2:
                transactionType = TransactionType.BALANCE_PAY_ORDER;
                break;
            case 3:
                transactionType = TransactionType.PAYPAL_PAY_ORDER;
                break;
            case 4:
                transactionType = TransactionType.ORDER_REFUND;
                break;
            case 5:
                transactionType = TransactionType.TRANSFER_RECHARGE_REPAYMENT;
                break;
            case 6:
                transactionType = TransactionType.PAYPAY_RECHARGE;
                break;
            case 7:
                transactionType = TransactionType.PAYPAY_RECHARGE_REPAYMENT;
                break;
            case 8:
                transactionType = TransactionType.INNER_TRANSFER_PAYEE;
                break;
            case 9:
                transactionType = TransactionType.INNER_TRANSFER_PAYER;
                break;
            case 10:
                transactionType = TransactionType.BALANCE_PAY_STOCK;
                break;
            case 11:
                transactionType = TransactionType.PAYPAL_PAY_STOCK;
                break;
            case 12:
                transactionType = TransactionType.BALANCE_WITHDRAW;
                break;
            case 13:
                transactionType = TransactionType.COMMISSION_WITHDRAW;
                break;
            case 14:
                transactionType = TransactionType.STOCK_ORDER_REFUND;
                break;
            case 15:
                transactionType = TransactionType.STOCK_ORDER_REFUND_REPAYMENT;
                break;
            case 16:
                transactionType = TransactionType.PAYONEER_RECHARGE;
                break;
            case 17:
                transactionType = TransactionType.PAYONEER_RECHARGE_REPAYMENT;
                break;
            case 18:
                transactionType = TransactionType.REBATE_REWARD;
                break;
            case 19:
                transactionType = TransactionType.BALANCE_PAY_WHOLESALE;
                break;
            case 20:
                transactionType = TransactionType.PAYPAL_PAY_WHOLESALE;
                break;
            case 21:
                transactionType = TransactionType.WHOLESALE_ORDER_REFUND;
                break;
            case 22:
                transactionType = TransactionType.WHOLESALE_ORDER_REFUND_REPAYMENT;
                break;
            case 23:
                transactionType = TransactionType.BALANCE_WITHDRAW_CANCEL;
                break;
            case 24:
                transactionType = TransactionType.INNER_TRANSFER_PAYEE_REPAYMENT;
                break;
            case 25:
                transactionType = TransactionType.PAYONEER_RECHARGE;
                break;
            case 26:
                transactionType = TransactionType.PAYONEER_RECHARGE_REPAYMENT;
                break;
        }
        if (transactionType != null){
            this.payMethod = transactionType.getPayMethod();
            this.orderType = transactionType.getOrderType();
            this.transactionType = transactionType.getTransactionType();
        }
    }

    public AccountLog() {
    }
}
