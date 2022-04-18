package com.upedge.thirdparty.fpx.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CreateFpxSkuRequest {


    @JsonProperty("sku_code")
    private String skuCode;
    @JsonProperty("sku_name")
    private String skuName;
    @JsonProperty("chinese_name")
    private String chineseName;
    @JsonProperty("sales_link")
    private String salesLink;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("length")
    private String length;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("is_brand")
    private String isBrand;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("origin_country")
    private String originCountry = "CN";
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("single_sku_code")
    private String singleSkuCode;
    @JsonProperty("pcs")
    private String pcs;
    @JsonProperty("logistics_package")
    private String logisticsPackage;
    @JsonProperty("specification")
    private String specification;
    @JsonProperty("include_battery")
    private String includeBattery;
    @JsonProperty("battery_resource")
    private String batteryResource;
    @JsonProperty("expired_date")
    private String expiredDate;
    @JsonProperty("include_batch")
    private String includeBatch;
    @JsonProperty("sn_rule_code")
    private String snRuleCode;
    @JsonProperty("picture_url")
    private List<String> pictureUrl;
    @JsonProperty("declare_country_list")
    private List<DeclareCountryListDTO> declareCountryList;

    @NoArgsConstructor
    @Data
    public static class DeclareCountryListDTO {
        @JsonProperty("export_country")
        private String exportCountry;
        @JsonProperty("export_port")
        private String exportPort;
        @JsonProperty("export_declare")
        private String exportDeclare;
        @JsonProperty("declare_country_list")
        private String declareCountryList;
        @JsonProperty("export_declare_value")
        private String exportDeclareValue;
        @JsonProperty("export_currency")
        private String exportCurrency;
        @JsonProperty("country")
        private String country;
        @JsonProperty("import_port")
        private String importPort;
        @JsonProperty("import_declare")
        private String importDeclare;
        @JsonProperty("hs_code")
        private String hsCode;
        @JsonProperty("declare_value")
        private String declareValue;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("certificate_list")
        private List<CertificateListDTO> certificateList;

        @NoArgsConstructor
        @Data
        public static class CertificateListDTO {
            @JsonProperty("certificate_type")
            private String certificateType;
            @JsonProperty("certificate_url")
            private String certificateUrl;
            @JsonProperty("certificate_no")
            private String certificateNo;
            @JsonProperty("briefs")
            private String briefs;
            @JsonProperty("end_date")
            private String endDate;
        }
    }
}
