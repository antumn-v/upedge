package com.upedge.ums.modules.account.entity.payoneer;

import com.upedge.common.model.account.payoneer.DebitResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author author
 */
@Data
public class PayoneerPayment {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long accountId;

    private String payoneerAccountId;
    /**
     *
     */
    private String paymentId;
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private Integer accountPaymethodId;
    /**
     *
     */
    private String commitId;
    /**
     *
     */
    private Long applicationId;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private String statusDescription;
    /**
     *
     */
    private Double fee;
    /**
     *
     */
    private Double chargedAmount;
    /**
     *
     */
    private Double targetAmount;
    /**
     *
     */
    private String createdAt;
    /**
     *
     */
    private String lastStatus;
    /**
     *
     */
    private String clientReferenceId;
    /**
     *
     */
    private Double requestAmount;
    /**
     *
     */
    private String requestCurrency;
    /**
     *
     */
    private String sourceCurrency;
    /**
     *
     */
    private String rate;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;


    private Long customerId;

    public PayoneerPayment() {
    }

    public PayoneerPayment(DebitResponse debit) {
		this.id = Long.parseLong(debit.getClient_reference_id());
		this.targetAmount = debit.getAmounts().getTarget().getAmount();
		this.chargedAmount = debit.getAmounts().getCharged().getAmount();
		this.requestAmount = debit.getAmounts().getTarget().getAmount();
		this.clientReferenceId = debit.getClient_reference_id();
		this.commitId = debit.getCommit_id();
		this.createdAt = debit.getCreated_at();
		this.fee = 0D;
		if(null != debit.getFees() && debit.getFees().size() > 0){
			List<DebitResponse.FeesBean> fees = debit.getFees();
			for (DebitResponse.FeesBean fee: fees) {
				this.fee += fee.getAmount();
			}
		}
		this.lastStatus = debit.getLast_status();
		this.paymentId = debit.getPayment_id();
		if(debit.getFx() != null){
			this.requestCurrency = debit.getFx().getTarget_currency();
			this.sourceCurrency = debit.getFx().getSource_currency();
			this.rate = debit.getFx().getRate();
		}else {
			this.requestCurrency = "USD";
			this.sourceCurrency = "USD";
			this.rate = "1";
		}
		this.status = debit.getStatus();
		this.statusDescription = debit.getStatus_description();
	}

}
