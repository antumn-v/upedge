package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AdminCurrencyRate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AdminCurrencyRateUpdateRequest{

    @NotNull
    private Long id;
    /**
     * 
     */
    @NotBlank
    private String currencyName;
    /**
     * 货币对人民币的汇率
     */
    private BigDecimal cnyRate;
    /**
     * 货币对美元的汇率
     */
    private BigDecimal usdRate;
    /**
     * 收款账号
     */
    private Long accountId;

    public AdminCurrencyRate toAdminCurrencyRate(String adminUser){
        AdminCurrencyRate adminCurrencyRate=new AdminCurrencyRate();
        adminCurrencyRate.setId(id);
        adminCurrencyRate.setCurrencyName(currencyName);
        adminCurrencyRate.setCnyRate(cnyRate);
        adminCurrencyRate.setUsdRate(usdRate);
        adminCurrencyRate.setUpdateTime(new Date());
        adminCurrencyRate.setAdminUser(adminUser);
        adminCurrencyRate.setAccountId(accountId);
        return adminCurrencyRate;
    }

}
