package com.upedge.thirdparty.fpx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class PriceCalculatorDTO {
    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("quotation_code")
    private String quotationCode;
    @JsonProperty("total_amount")
    private Double totalAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("fees")
    private List<FeesDTO> fees;
    @JsonProperty("timely")
    private String timely;

    @NoArgsConstructor
    @Data
    public static class FeesDTO {
        @JsonProperty("fee_code")
        private String feeCode;
        @JsonProperty("amount")
        private BigDecimal amount;
        @JsonProperty("currency")
        private String currency;
    }
}
