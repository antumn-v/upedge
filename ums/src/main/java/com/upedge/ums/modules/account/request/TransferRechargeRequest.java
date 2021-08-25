package com.upedge.ums.modules.account.request;

import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.FileUtil;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferRechargeRequest {

    String image;

    BigDecimal amount;

    String remarks;

    public RechargeRequestLog toRechargeRequestLog(Session session){
        RechargeRequestLog log = new RechargeRequestLog();
        log.setAccountId(session.getAccountId());
        log.setAmount(amount);
        log.setBenefits(BigDecimal.ZERO);
        log.setCreateTime(new Date());
        log.setCustomerMoney(amount);
        log.setCustomerId(session.getCustomerId());
        log.setRechargeType(RechargeRequestLog.TRANSFER_REQUEST_TYPE);
        log.setRemarks(this.remarks);
        log.setUpdateTime(new Date());
        log.setVoucher(FileUtil.uploadImage(image,"http://localhost:8611/image/","E:/developer/files/upedge/"));
        log.setStatus(RechargeRequestLog.PENDING);
        return log;
    }
}
