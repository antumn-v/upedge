package com.upedge.ums.modules.account.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.entity.AdminCurrencyRate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class AdminCurrencyRateAddRequest{

    /**
    * 
    */
    @NotBlank
    private String currencyCode;
    /**
    * 
    */
    @NotBlank
    private String currencyName;
    /**
     * 货币对人民币的汇率
     */
    @NotNull
    private BigDecimal cnyRate;
    /**
     * 货币对美元的汇率
     */
    @NotNull
    private BigDecimal usdRate;
    /**
    * 收款账号
    */
    private Long accountId;

    public AdminCurrencyRate toAdminCurrencyRate(String adminUser){
        AdminCurrencyRate adminCurrencyRate=new AdminCurrencyRate();
        adminCurrencyRate.setId(IdGenerate.nextId());
        adminCurrencyRate.setCurrencyCode(currencyCode);
        adminCurrencyRate.setCurrencyName(currencyName);
        adminCurrencyRate.setCnyRate(cnyRate);
        adminCurrencyRate.setUsdRate(usdRate);
        adminCurrencyRate.setAdminUser(adminUser);
        adminCurrencyRate.setAccountId(accountId);
        return adminCurrencyRate;
    }

}
