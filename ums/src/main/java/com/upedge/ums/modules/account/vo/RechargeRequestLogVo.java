package com.upedge.ums.modules.account.vo;

import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RechargeRequestLogVo {

    private Long id;

    private Long accountId;

    private Integer accountPaymethodId;

    private Long customerId;

    private String loginName;

    private String username;

    private String email;

    private Long adminUserId;

    //到账金额 默认=客户申请金额
    private BigDecimal amount;

    private BigDecimal benefits = BigDecimal.ZERO;

    //客户申请金额
    private BigDecimal customerMoney;

    private BigDecimal repaymentAmount;

    private String remarks;

    private String transferFlow;

    private Integer status;

    private Integer rechargeType;

    private Date createTime;

    private Date updateTime;

    private BigDecimal fixFee;

    private Map<String, String> attrs=new HashMap<>();

    /**
     * 手续费
     * @return
     */
    public BigDecimal getFixFee() {
        return customerMoney.subtract(amount);
    }

    public void setAttrs(List<RechargeRequestAttr> attrList) {
        Map<String, String> attrs=new HashMap<>();
        //0:电汇 1:paypal充值 2:payoneer申请,3:payoneer充值
        switch (rechargeType){
            case 0:
                attrs.put("image","");
                attrs.put("receivingAccount","");
                if(status==2){
                    attrs.put("rejectReason","");
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        attrList.forEach(rechargeRequestAttr -> {
            attrs.put(rechargeRequestAttr.getAttrName(),rechargeRequestAttr.getAttrValue());
        });
        this.attrs = attrs;
    }
}
