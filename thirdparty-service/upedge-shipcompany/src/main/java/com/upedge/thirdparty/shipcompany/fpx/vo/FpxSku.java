package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * sku
 */
@NoArgsConstructor
@Data
public class FpxSku {


    @JsonProperty("sku_id")
    private String skuId;
    @JsonProperty("sku_code")
    private String skuCode;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("sku_name")
    private String skuName;
    @JsonProperty("specification")
    private String specification;
    @JsonProperty("uom")
    private String uom;
    @JsonProperty("pcs")
    private Integer pcs;
    @JsonProperty("single_sku_code")
    private String singleSkuCode;
    @JsonProperty("wrapping")
    private String wrapping;
    @JsonProperty("appearance")
    private String appearance;
    @JsonProperty("characteristic")
    private List<String> characteristic;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("length")
    private Double length;
    @JsonProperty("width")
    private Double width;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("logistics_package")
    private String logisticsPackage;
    @JsonProperty("package_material")
    private String packageMaterial;
    @JsonProperty("sn_rule_code")
    private String snRuleCode;
    @JsonProperty("expired_date")
    private String expiredDate;
    @JsonProperty("sales_link")
    private String salesLink;
    @JsonProperty("include_batch")
    private String includeBatch;
    @JsonProperty("include_battery")
    private String includeBattery;
    @JsonProperty("battery_config")
    private String batteryConfig;
    @JsonProperty("battery_type")
    private String batteryType;
    @JsonProperty("battery_power")
    private String batteryPower;
    @JsonProperty("battery_number")
    private Integer batteryNumber;
    @JsonProperty("battery_resource")
    private String batteryResource;
    @JsonProperty("picture_url")
    private List<String> pictureUrl;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("customer_code")
    private String customerCode;
    @JsonProperty("sku_status")
    private String skuStatus;
    @JsonProperty("chinese_name")
    private String chineseName;
    @JsonProperty("is_brand")
    private String isBrand;
    @JsonProperty("brand_name")
    private String brandName;
    @JsonProperty("origin_country")
    private String originCountry;
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
        @JsonProperty("service_type")
        private String serviceType;
        @JsonProperty("transport_way")
        private String transportWay;
    }
}
