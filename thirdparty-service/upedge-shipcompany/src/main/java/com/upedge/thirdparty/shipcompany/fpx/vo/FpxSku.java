package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
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


    @JSONField(name = "sku_id")
    private String skuId;
    @JSONField(name = "sku_code")
    private String skuCode;
    @JSONField(name = "product_code")
    private String productCode;
    @JSONField(name = "sku_name")
    private String skuName;
    @JSONField(name = "specification")
    private String specification;
    @JSONField(name = "uom")
    private String uom;
    @JSONField(name = "pcs")
    private Integer pcs;
    @JSONField(name = "single_sku_code")
    private String singleSkuCode;
    @JSONField(name = "wrapping")
    private String wrapping;
    @JSONField(name = "appearance")
    private String appearance;
    @JSONField(name = "characteristic")
    private List<String> characteristic;
    @JSONField(name = "weight")
    private Double weight;
    @JSONField(name = "length")
    private Double length;
    @JSONField(name = "width")
    private Double width;
    @JSONField(name = "height")
    private Double height;
    @JSONField(name = "logistics_package")
    private String logisticsPackage;
    @JSONField(name = "package_material")
    private String packageMaterial;
    @JSONField(name = "sn_rule_code")
    private String snRuleCode;
    @JSONField(name = "expired_date")
    private String expiredDate;
    @JSONField(name = "sales_link")
    private String salesLink;
    @JSONField(name = "include_batch")
    private String includeBatch;
    @JSONField(name = "include_battery")
    private String includeBattery;
    @JSONField(name = "battery_config")
    private String batteryConfig;
    @JSONField(name = "battery_type")
    private String batteryType;
    @JSONField(name = "battery_power")
    private String batteryPower;
    @JSONField(name = "battery_number")
    private Integer batteryNumber;
    @JSONField(name = "battery_resource")
    private String batteryResource;
    @JSONField(name = "picture_url")
    private List<String> pictureUrl;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "customer_code")
    private String customerCode;
    @JSONField(name = "sku_status")
    private String skuStatus;
    @JSONField(name = "chinese_name")
    private String chineseName;
    @JSONField(name = "is_brand")
    private String isBrand;
    @JSONField(name = "brand_name")
    private String brandName;
    @JSONField(name = "origin_country")
    private String originCountry;
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
        @JSONField(name = "service_type")
        private String serviceType;
        @JSONField(name = "transport_way")
        private String transportWay;
    }
}
