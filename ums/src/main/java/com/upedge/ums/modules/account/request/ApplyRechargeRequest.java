package com.upedge.ums.modules.account.request;

import com.upedge.common.model.account.AccountPayAmount;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import lombok.Data;

import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class ApplyRechargeRequest extends AccountPayAmount {



    public RechargeRequestLog toRechargeRequest(Session session){
        RechargeRequestLog log = new RechargeRequestLog();
        log.setAccountId(session.getAccountId());
        log.setRequestUserId(session.getId());
        log.setCustomerId(session.getCustomerId());
        log.setAmount(getAmount());
        log.setCustomerMoney(getAmount());
        log.setStatus(0);
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());
        log.setAccountPaymethodId(getAccountPaymethodId());
        log.setRemarks(getRemarks());
        return log;
    }

//    public List<RechargeRequestAttr> toRechargeRequestAttrs(int requestId){
//        List<RechargeRequestAttr> attrList = new ArrayList<>();
//
//        for (Map.Entry<String,String> entry:attrs.entrySet()){
//            RechargeRequestAttr attr = new RechargeRequestAttr();
//            attr.setRechargeRequestId(requestId);
//            attr.setAttrName(entry.getKey());
//            attr.setAttrValue(entry.getValue());
//            attrList.add(attr);
//        }
//        return attrList;
//    }
}
