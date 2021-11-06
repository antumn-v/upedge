package com.upedge.ums.modules.account.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.entity.AdminCredit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AdminCreditApplyRequest{

    /**
    * 申请原因
    */
    @NotBlank
    private String reason;
    /**
    * 客户id
    */
    @NotNull
    private Long customerId;
    /**
    * 申请额度上限
    */
    @NotNull
    private BigDecimal applyCredit;

    public AdminCredit toAdminCredit(String applyUserId){
        AdminCredit adminCredit=new AdminCredit();
        adminCredit.setId(IdGenerate.nextId());
        adminCredit.setReason(reason);
        adminCredit.setCustomerId(customerId);
        adminCredit.setCreateTime(new Date());
        adminCredit.setUpdateTime(new Date());
        adminCredit.setState(0);
        adminCredit.setApplyUserId(applyUserId);
        adminCredit.setApplyCredit(applyCredit);
        return adminCredit;
    }

}
