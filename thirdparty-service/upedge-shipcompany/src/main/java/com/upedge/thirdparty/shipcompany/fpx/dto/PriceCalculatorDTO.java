package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class PriceCalculatorDTO {
    @JSONField(name = "service_code")
    private String serviceCode;
    @JSONField(name = "product_code")
    private String productCode;
    @JSONField(name = "quotation_code")
    private String quotationCode;
    @JSONField(name = "total_amount")
    private Double totalAmount;
    @JSONField(name = "currency")
    private String currency;
    @JSONField(name = "fees")
    private List<FeesDTO> fees;
    @JSONField(name = "timely")
    private String timely;

    @NoArgsConstructor
    @Data
    public static class FeesDTO {
        @JSONField(name = "fee_code")
        private String feeCode;
        @JSONField(name = "amount")
        private BigDecimal amount;
        @JSONField(name = "currency")
        private String currency;
    }
}
