package com.upedge.thirdparty.shipcompany.fpx.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CreateFpxSkuRequest {


    @JSONField(name = "sku_code")
    private String skuCode;
    @JSONField(name = "sku_name")
    private String skuName;
    @JSONField(name = "chinese_name")
    private String chineseName;
    @JSONField(name = "sales_link")
    private String salesLink;
    @JSONField(name = "weight")
    private String weight;
    @JSONField(name = "length")
    private String length;
    @JSONField(name = "width")
    private String width;
    @JSONField(name = "height")
    private String height;
    @JSONField(name = "is_brand")
    private String isBrand;
    @JSONField(name = "brand_name")
    private String brandName;
    @JSONField(name = "origin_country")
    private String originCountry = "CN";
    @JSONField(name = "uom")
    private String uom;
    @JSONField(name = "single_sku_code")
    private String singleSkuCode;
    @JSONField(name = "pcs")
    private String pcs;
    @JSONField(name = "logistics_package")
    private String logisticsPackage;
    @JSONField(name = "specification")
    private String specification;
    @JSONField(name = "include_battery")
    private String includeBattery;
    @JSONField(name = "battery_resource")
    private String batteryResource;
    @JSONField(name = "expired_date")
    private String expiredDate;
    @JSONField(name = "include_batch")
    private String includeBatch;
    @JSONField(name = "sn_rule_code")
    private String snRuleCode;
    @JSONField(name = "picture_url")
    private List<String> pictureUrl;
    @JSONField(name = "declare_country_list")
    private List<DeclareCountryListDTO> declareCountryList;

    @NoArgsConstructor
    @Data
    public static class DeclareCountryListDTO {
        @JSONField(name = "export_country")
        private String exportCountry;
        @JSONField(name = "export_port")
        private String exportPort;
        @JSONField(name = "export_declare")
        private String exportDeclare;
        @JSONField(name = "declare_country_list")
        private String declareCountryList;
        @JSONField(name = "export_declare_value")
        private String exportDeclareValue;
        @JSONField(name = "export_currency")
        private String exportCurrency;
        @JSONField(name = "country")
        private String country;
        @JSONField(name = "import_port")
        private String importPort;
        @JSONField(name = "import_declare")
        private String importDeclare;
        @JSONField(name = "hs_code")
        private String hsCode;
        @JSONField(name = "declare_value")
        private String declareValue;
        @JSONField(name = "currency")
        private String currency;
        @JSONField(name = "certificate_list")
        private List<CertificateListDTO> certificateList;

        @NoArgsConstructor
        @Data
        public static class CertificateListDTO {
            @JSONField(name = "certificate_type")
            private String certificateType;
            @JSONField(name = "certificate_url")
            private String certificateUrl;
            @JSONField(name = "certificate_no")
            private String certificateNo;
            @JSONField(name = "briefs")
            private String briefs;
            @JSONField(name = "end_date")
            private String endDate;
        }
    }
}
