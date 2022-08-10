package com.upedge.thirdparty.fpx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class FpxOrderCreateDto {


    @JsonProperty("4px_tracking_no")
    private String fpxTrackingNo;
    /**
     * 订单ID
     */
    @JsonProperty("ref_no")
    private String refNo;
    @JsonProperty("business_type")
    private String businessType = "BDS";
    @JsonProperty("duty_type")
    private String dutyType = "U";
    @JsonProperty("cargo_type")
    private String cargoType = "5";
    /**
     * ioss
     */
    @JsonProperty("vat_no")
    private String vatNo;
    @JsonProperty("eori_no")
    private String eoriNo;
    /**
     * 客户名
     */
    @JsonProperty("buyer_id")
    private String buyerId;
    @JsonProperty("sales_platform")
    private String salesPlatform;
    @JsonProperty("seller_id")
    private String sellerId;
    /**
     * 物流服务信息，需要运费方式关联的代码
     */
    @JsonProperty("logistics_service_info")
    private LogisticsServiceInfoDTO logisticsServiceInfo = new LogisticsServiceInfoDTO();
    @JsonProperty("label_barcode")
    private String labelBarcode;
    @JsonProperty("return_info")
    private ReturnInfoDTO returnInfo = new ReturnInfoDTO();
    @JsonProperty("parcel_list")
    private List<ParcelListDTO> parcelList;
    @JsonProperty("is_insure")
    private String isInsure = "N";
    @JsonProperty("insurance_info")
    private InsuranceInfoDTO insuranceInfo = new InsuranceInfoDTO();
    /**
     * 发件人信息（固定地址）
     */
    @JsonProperty("sender")
    private SenderDTO sender = new SenderDTO();
    /**
     * 收件人信息（订单地址）
     */
    @JsonProperty("recipient_info")
    private RecipientInfoDTO recipientInfo = new RecipientInfoDTO();
    @JsonProperty("deliver_type_info")
    private DeliverTypeInfoDTO deliverTypeInfo = new DeliverTypeInfoDTO();
    @JsonProperty("label_config_info")
    private LabelConfigInfoDTO labelConfigInfo;
    @JsonProperty("deliver_to_recipient_info")
    private DeliverToRecipientInfo deliverToRecipientInfo = new DeliverToRecipientInfo();

    @NoArgsConstructor
    @Data
    public static class LogisticsServiceInfoDTO {
        @JsonProperty("logistics_product_code")
        private String logisticsProductCode;
        @JsonProperty("customs_service")
        private String customsService;
        @JsonProperty("signature_service")
        private String signatureService;
        @JsonProperty("value_added_services")
        private String valueAddedServices;
    }

    @NoArgsConstructor
    @Data
    public static class ReturnInfoDTO {
        @JsonProperty("is_return_on_domestic")
        private String isReturnOnDomestic = "Y";
        @JsonProperty("domestic_return_addr")
        private DomesticReturnAddrDTO domesticReturnAddr;
        @JsonProperty("is_return_on_oversea")
        private String isReturnOnOversea = "U";
        @JsonProperty("oversea_return_addr")
        private OverseaReturnAddrDTO overseaReturnAddr;

        @NoArgsConstructor
        @Data
        public static class DomesticReturnAddrDTO {
            @JsonProperty("first_name")
            private String firstName;
            @JsonProperty("last_name")
            private String lastName;
            @JsonProperty("company")
            private String company;
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("phone2")
            private String phone2;
            @JsonProperty("email")
            private String email;
            @JsonProperty("post_code")
            private String postCode;
            @JsonProperty("country")
            private String country;
            @JsonProperty("state")
            private String state;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;
            @JsonProperty("street")
            private String street;
            @JsonProperty("house_number")
            private String houseNumber;
        }

        @NoArgsConstructor
        @Data
        public static class OverseaReturnAddrDTO {
            @JsonProperty("first_name")
            private String firstName;
            @JsonProperty("last_name")
            private String lastName;
            @JsonProperty("company")
            private String company;
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("phone2")
            private String phone2;
            @JsonProperty("email")
            private String email;
            @JsonProperty("post_code")
            private String postCode;
            @JsonProperty("country")
            private String country;
            @JsonProperty("state")
            private String state;
            @JsonProperty("city")
            private String city;
            @JsonProperty("district")
            private String district;
            @JsonProperty("street")
            private String street;
            @JsonProperty("house_number")
            private String houseNumber;
        }
    }

    @NoArgsConstructor
    @Data
    public static class InsuranceInfoDTO {
        @JsonProperty("insure_type")
        private String insureType;
        @JsonProperty("insure_value")
        private Integer insureValue;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("insure_person")
        private String insurePerson;
        @JsonProperty("certificate_type")
        private String certificateType;
        @JsonProperty("certificate_no")
        private String certificateNo;
        @JsonProperty("category_code")
        private String categoryCode;
        @JsonProperty("insure_product_name")
        private String insureProductName;
        @JsonProperty("package_qty")
        private String packageQty;
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JsonProperty("first_name")
        private String firstName = "郭";
        @JsonProperty("last_name")
        private String lastName = "xin";
        @JsonProperty("company")
        private String company = "upedge";
        @JsonProperty("phone")
        private String phone = "18166668888";
        @JsonProperty("phone2")
        private String phone2;
        @JsonProperty("email")
        private String email = "89127398621@qq.com";
        @JsonProperty("post_code")
        private String postCode = "0";
        @JsonProperty("country")
        private String country = "CN";
        @JsonProperty("state")
        private String state = "浙江省";
        @JsonProperty("city")
        private String city = "杭州市";
        @JsonProperty("district")
        private String district = "余杭区";
        @JsonProperty("street")
        private String street = "五常街道";
        @JsonProperty("house_number")
        private String houseNumber = "406";
        @JsonProperty("certificate_info")
        private CertificateInfoDTO certificateInfo;

        @NoArgsConstructor
        @Data
        public static class CertificateInfoDTO {
            @JsonProperty("certificate_type")
            private String certificateType;
            @JsonProperty("certificate_no")
            private String certificateNo;
            @JsonProperty("id_front_url")
            private String idFrontUrl;
            @JsonProperty("id_back_url")
            private String idBackUrl;
        }
    }

    @NoArgsConstructor
    @Data
    public static class RecipientInfoDTO {
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("company")
        private String company;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("phone2")
        private String phone2;
        @JsonProperty("email")
        private String email;
        @JsonProperty("post_code")
        private String postCode;
        @JsonProperty("country")
        private String country;
        @JsonProperty("state")
        private String state;
        @JsonProperty("city")
        private String city;
        @JsonProperty("district")
        private String district;
        @JsonProperty("street")
        private String street;
        @JsonProperty("house_number")
        private String houseNumber;
        @JsonProperty("certificate_info")
        private CertificateInfoDTO certificateInfo;

        @NoArgsConstructor
        @Data
        public static class CertificateInfoDTO {
            @JsonProperty("certificate_type")
            private String certificateType;
            @JsonProperty("certificate_no")
            private String certificateNo;
            @JsonProperty("id_front_url")
            private String idFrontUrl;
            @JsonProperty("id_back_url")
            private String idBackUrl;
        }
    }

    @NoArgsConstructor
    @Data
    public static class DeliverToRecipientInfo{
        @JsonProperty("deliver_type")
        private String deliverType = "HOME_DELIVERY";

        @JsonProperty("station_code")
        private String stationCode;
    }

    @NoArgsConstructor
    @Data
    public static class DeliverTypeInfoDTO {
        @JsonProperty("deliver_type")
        private String deliverType = "1";
        @JsonProperty("warehouse_code")
        private String warehouseCode;
        @JsonProperty("pick_up_info")
        private PickUpInfoDTO pickUpInfo;
        @JsonProperty("express_to_4px_info")
        private ExpressTo4pxInfoDTO expressTo4pxInfo;
        @JsonProperty("self_send_to_4px_info")
        private SelfSendTo4pxInfoDTO selfSendTo4pxInfo;

        @NoArgsConstructor
        @Data
        public static class PickUpInfoDTO {
            @JsonProperty("expect_pick_up_earliest_time")
            private String expectPickUpEarliestTime;
            @JsonProperty("expect_pick_up_latest_time")
            private String expectPickUpLatestTime;
            @JsonProperty("pick_up_address_info")
            private PickUpAddressInfoDTO pickUpAddressInfo;

            @NoArgsConstructor
            @Data
            public static class PickUpAddressInfoDTO {
                @JsonProperty("first_name")
                private String firstName;
                @JsonProperty("last_name")
                private String lastName;
                @JsonProperty("company")
                private String company;
                @JsonProperty("phone")
                private String phone;
                @JsonProperty("phone2")
                private String phone2;
                @JsonProperty("email")
                private String email;
                @JsonProperty("post_code")
                private String postCode;
                @JsonProperty("country")
                private String country;
                @JsonProperty("state")
                private String state;
                @JsonProperty("city")
                private String city;
                @JsonProperty("district")
                private String district;
                @JsonProperty("street")
                private String street;
                @JsonProperty("house_number")
                private String houseNumber;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ExpressTo4pxInfoDTO {
            @JsonProperty("express_company")
            private String expressCompany;
            @JsonProperty("tracking_no")
            private String trackingNo;
        }

        @NoArgsConstructor
        @Data
        public static class SelfSendTo4pxInfoDTO {
            @JsonProperty("booking_earliest_time")
            private String bookingEarliestTime;
            @JsonProperty("booking_latest_time")
            private String bookingLatestTime;
        }
    }

    @NoArgsConstructor
    @Data
    public static class LabelConfigInfoDTO {
        @JsonProperty("label_size")
        private String labelSize;
        @JsonProperty("response_label_format")
        private String responseLabelFormat;
        @JsonProperty("create_logistics_label")
        private String createLogisticsLabel;
        @JsonProperty("logistics_label_config")
        private LogisticsLabelConfigDTO logisticsLabelConfig;
        @JsonProperty("create_package_label")
        private String createPackageLabel;

        @NoArgsConstructor
        @Data
        public static class LogisticsLabelConfigDTO {
            @JsonProperty("is_print_time")
            private String isPrintTime;
            @JsonProperty("is_print_buyer_id")
            private String isPrintBuyerId;
            @JsonProperty("is_print_pick_info")
            private String isPrintPickInfo;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ParcelListDTO {
        @JsonProperty("weight")
        private Integer weight;
        @JsonProperty("length")
        private Integer length;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("parcel_value")
        private Double parcelValue;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("include_battery")
        private String includeBattery;
        @JsonProperty("battery_type")
        private String batteryType;
        @JsonProperty("product_list")
        private List<ProductListDTO> productList;
        @JsonProperty("declare_product_info")
        private List<DeclareProductInfoDTO> declareProductInfo;

        @NoArgsConstructor
        @Data
        public static class ProductListDTO {
            @JsonProperty("sku_code")
            private String skuCode;
            @JsonProperty("standard_product_barcode")
            private String standardProductBarcode;
            @JsonProperty("product_name")
            private String productName;
            @JsonProperty("product_description")
            private String productDescription;
            @JsonProperty("product_unit_price")
            private Integer productUnitPrice;
            @JsonProperty("currency")
            private String currency;
            @JsonProperty("qty")
            private Integer qty;
        }

        @NoArgsConstructor
        @Data
        public static class DeclareProductInfoDTO {
            @JsonProperty("declare_product_code")
            private String declareProductCode;
            @JsonProperty("declare_product_name_cn")
            private String declareProductNameCn;
            @JsonProperty("declare_product_name_en")
            private String declareProductNameEn;
            @JsonProperty("uses")
            private String uses;
            @JsonProperty("specification")
            private String specification;
            @JsonProperty("component")
            private String component;
            @JsonProperty("unit_net_weight")
            private Integer unitNetWeight;
            @JsonProperty("unit_gross_weight")
            private Integer unitGrossWeight;
            @JsonProperty("material")
            private String material;
            @JsonProperty("declare_product_code_qty")
            private Integer declareProductCodeQty;
            @JsonProperty("unit_declare_product")
            private String unitDeclareProduct;
            @JsonProperty("origin_country")
            private String originCountry;
            @JsonProperty("country_export")
            private String countryExport;
            @JsonProperty("country_import")
            private String countryImport;
            @JsonProperty("hscode_export")
            private String hscodeExport;
            @JsonProperty("hscode_import")
            private String hscodeImport;
            @JsonProperty("declare_unit_price_export")
            private BigDecimal declareUnitPriceExport;
            @JsonProperty("currency_export")
            private String currencyExport = "USD";
            @JsonProperty("declare_unit_price_import")
            private BigDecimal declareUnitPriceImport;
            @JsonProperty("currency_import")
            private String currencyImport = "USD";
            @JsonProperty("brand_export")
            private String brandExport = "none";
            @JsonProperty("brand_import")
            private String brandImport = "none";
            @JsonProperty("sales_url")
            private String salesUrl;
            @JsonProperty("package_remarks")
            private String packageRemarks;
        }
    }
}
