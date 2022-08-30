package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class FpxOrderCreateDto {


    @JSONField(name = "4px_tracking_no")
    private String fpxTrackingNo;
    /**
     * 订单ID
     */
    @JSONField(name = "ref_no")
    private String refNo;
    @JSONField(name = "business_type")
    private String businessType = "BDS";
    @JSONField(name = "duty_type")
    private String dutyType = "U";
    @JSONField(name = "cargo_type")
    private String cargoType = "5";
    /**
     * ioss
     */
    @JSONField(name = "vat_no")
    private String vatNo;
    @JSONField(name = "eori_no")
    private String eoriNo;
    /**
     * 客户名
     */
    @JSONField(name = "buyer_id")
    private String buyerId;
    @JSONField(name = "sales_platform")
    private String salesPlatform;
    @JSONField(name = "seller_id")
    private String sellerId;
    /**
     * 物流服务信息，需要运费方式关联的代码
     */
    @JSONField(name = "logistics_service_info")
    private LogisticsServiceInfoDTO logisticsServiceInfo = new LogisticsServiceInfoDTO();
    @JSONField(name = "label_barcode")
    private String labelBarcode;
    @JSONField(name = "return_info")
    private ReturnInfoDTO returnInfo = new ReturnInfoDTO();
    @JSONField(name = "parcel_list")
    private List<ParcelListDTO> parcelList;
    @JSONField(name = "is_insure")
    private String isInsure = "N";
    @JSONField(name = "insurance_info")
    private InsuranceInfoDTO insuranceInfo = new InsuranceInfoDTO();
    /**
     * 发件人信息（固定地址）
     */
    @JSONField(name = "sender")
    private SenderDTO sender = new SenderDTO();
    /**
     * 收件人信息（订单地址）
     */
    @JSONField(name = "recipient_info")
    private RecipientInfoDTO recipientInfo = new RecipientInfoDTO();
    @JSONField(name = "deliver_type_info")
    private DeliverTypeInfoDTO deliverTypeInfo = new DeliverTypeInfoDTO();
    @JSONField(name = "label_config_info")
    private LabelConfigInfoDTO labelConfigInfo;
    @JSONField(name = "deliver_to_recipient_info")
    private DeliverToRecipientInfo deliverToRecipientInfo = new DeliverToRecipientInfo();

    @NoArgsConstructor
    @Data
    public static class LogisticsServiceInfoDTO {
        @JSONField(name = "logistics_product_code")
        private String logisticsProductCode;
        @JSONField(name = "customs_service")
        private String customsService;
        @JSONField(name = "signature_service")
        private String signatureService;
        @JSONField(name = "value_added_services")
        private String valueAddedServices;
    }

    @NoArgsConstructor
    @Data
    public static class ReturnInfoDTO {
        @JSONField(name = "is_return_on_domestic")
        private String isReturnOnDomestic = "Y";
        @JSONField(name = "domestic_return_addr")
        private DomesticReturnAddrDTO domesticReturnAddr;
        @JSONField(name = "is_return_on_oversea")
        private String isReturnOnOversea = "U";
        @JSONField(name = "oversea_return_addr")
        private OverseaReturnAddrDTO overseaReturnAddr;

        @NoArgsConstructor
        @Data
        public static class DomesticReturnAddrDTO {
            @JSONField(name = "first_name")
            private String firstName;
            @JSONField(name = "last_name")
            private String lastName;
            @JSONField(name = "company")
            private String company;
            @JSONField(name = "phone")
            private String phone;
            @JSONField(name = "phone2")
            private String phone2;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "post_code")
            private String postCode;
            @JSONField(name = "country")
            private String country;
            @JSONField(name = "state")
            private String state;
            @JSONField(name = "city")
            private String city;
            @JSONField(name = "district")
            private String district;
            @JSONField(name = "street")
            private String street;
            @JSONField(name = "house_number")
            private String houseNumber;
        }

        @NoArgsConstructor
        @Data
        public static class OverseaReturnAddrDTO {
            @JSONField(name = "first_name")
            private String firstName;
            @JSONField(name = "last_name")
            private String lastName;
            @JSONField(name = "company")
            private String company;
            @JSONField(name = "phone")
            private String phone;
            @JSONField(name = "phone2")
            private String phone2;
            @JSONField(name = "email")
            private String email;
            @JSONField(name = "post_code")
            private String postCode;
            @JSONField(name = "country")
            private String country;
            @JSONField(name = "state")
            private String state;
            @JSONField(name = "city")
            private String city;
            @JSONField(name = "district")
            private String district;
            @JSONField(name = "street")
            private String street;
            @JSONField(name = "house_number")
            private String houseNumber;
        }
    }

    @NoArgsConstructor
    @Data
    public static class InsuranceInfoDTO {
        @JSONField(name = "insure_type")
        private String insureType;
        @JSONField(name = "insure_value")
        private Integer insureValue;
        @JSONField(name = "currency")
        private String currency;
        @JSONField(name = "insure_person")
        private String insurePerson;
        @JSONField(name = "certificate_type")
        private String certificateType;
        @JSONField(name = "certificate_no")
        private String certificateNo;
        @JSONField(name = "category_code")
        private String categoryCode;
        @JSONField(name = "insure_product_name")
        private String insureProductName;
        @JSONField(name = "package_qty")
        private String packageQty;
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JSONField(name = "first_name")
        private String firstName = "郭";
        @JSONField(name = "last_name")
        private String lastName = "xin";
        @JSONField(name = "company")
        private String company = "upedge";
        @JSONField(name = "phone")
        private String phone = "18166668888";
        @JSONField(name = "phone2")
        private String phone2;
        @JSONField(name = "email")
        private String email = "89127398621@qq.com";
        @JSONField(name = "post_code")
        private String postCode = "0";
        @JSONField(name = "country")
        private String country = "CN";
        @JSONField(name = "state")
        private String state = "浙江省";
        @JSONField(name = "city")
        private String city = "杭州市";
        @JSONField(name = "district")
        private String district = "余杭区";
        @JSONField(name = "street")
        private String street = "五常街道";
        @JSONField(name = "house_number")
        private String houseNumber = "406";
        @JSONField(name = "certificate_info")
        private CertificateInfoDTO certificateInfo;

        @NoArgsConstructor
        @Data
        public static class CertificateInfoDTO {
            @JSONField(name = "certificate_type")
            private String certificateType;
            @JSONField(name = "certificate_no")
            private String certificateNo;
            @JSONField(name = "id_front_url")
            private String idFrontUrl;
            @JSONField(name = "id_back_url")
            private String idBackUrl;
        }
    }

    @NoArgsConstructor
    @Data
    public static class RecipientInfoDTO {
        @JSONField(name = "first_name")
        private String firstName;
        @JSONField(name = "last_name")
        private String lastName;
        @JSONField(name = "company")
        private String company;
        @JSONField(name = "phone")
        private String phone;
        @JSONField(name = "phone2")
        private String phone2;
        @JSONField(name = "email")
        private String email;
        @JSONField(name = "post_code")
        private String postCode;
        @JSONField(name = "country")
        private String country;
        @JSONField(name = "state")
        private String state;
        @JSONField(name = "city")
        private String city;
        @JSONField(name = "district")
        private String district;
        @JSONField(name = "street")
        private String street;
        @JSONField(name = "house_number")
        private String houseNumber;
        @JSONField(name = "certificate_info")
        private CertificateInfoDTO certificateInfo;

        @NoArgsConstructor
        @Data
        public static class CertificateInfoDTO {
            @JSONField(name = "certificate_type")
            private String certificateType;
            @JSONField(name = "certificate_no")
            private String certificateNo;
            @JSONField(name = "id_front_url")
            private String idFrontUrl;
            @JSONField(name = "id_back_url")
            private String idBackUrl;
        }
    }

    @NoArgsConstructor
    @Data
    public static class DeliverToRecipientInfo{
        @JSONField(name = "deliver_type")
        private String deliverType = "HOME_DELIVERY";

        @JSONField(name = "station_code")
        private String stationCode;
    }

    @NoArgsConstructor
    @Data
    public static class DeliverTypeInfoDTO {
        @JSONField(name = "deliver_type")
        private String deliverType = "1";
        @JSONField(name = "warehouse_code")
        private String warehouseCode;
        @JSONField(name = "pick_up_info")
        private PickUpInfoDTO pickUpInfo;
        @JSONField(name = "express_to_4px_info")
        private ExpressTo4pxInfoDTO expressTo4pxInfo;
        @JSONField(name = "self_send_to_4px_info")
        private SelfSendTo4pxInfoDTO selfSendTo4pxInfo;

        @NoArgsConstructor
        @Data
        public static class PickUpInfoDTO {
            @JSONField(name = "expect_pick_up_earliest_time")
            private String expectPickUpEarliestTime;
            @JSONField(name = "expect_pick_up_latest_time")
            private String expectPickUpLatestTime;
            @JSONField(name = "pick_up_address_info")
            private PickUpAddressInfoDTO pickUpAddressInfo;

            @NoArgsConstructor
            @Data
            public static class PickUpAddressInfoDTO {
                @JSONField(name = "first_name")
                private String firstName;
                @JSONField(name = "last_name")
                private String lastName;
                @JSONField(name = "company")
                private String company;
                @JSONField(name = "phone")
                private String phone;
                @JSONField(name = "phone2")
                private String phone2;
                @JSONField(name = "email")
                private String email;
                @JSONField(name = "post_code")
                private String postCode;
                @JSONField(name = "country")
                private String country;
                @JSONField(name = "state")
                private String state;
                @JSONField(name = "city")
                private String city;
                @JSONField(name = "district")
                private String district;
                @JSONField(name = "street")
                private String street;
                @JSONField(name = "house_number")
                private String houseNumber;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ExpressTo4pxInfoDTO {
            @JSONField(name = "express_company")
            private String expressCompany;
            @JSONField(name = "tracking_no")
            private String trackingNo;
        }

        @NoArgsConstructor
        @Data
        public static class SelfSendTo4pxInfoDTO {
            @JSONField(name = "booking_earliest_time")
            private String bookingEarliestTime;
            @JSONField(name = "booking_latest_time")
            private String bookingLatestTime;
        }
    }

    @NoArgsConstructor
    @Data
    public static class LabelConfigInfoDTO {
        @JSONField(name = "label_size")
        private String labelSize;
        @JSONField(name = "response_label_format")
        private String responseLabelFormat;
        @JSONField(name = "create_logistics_label")
        private String createLogisticsLabel;
        @JSONField(name = "logistics_label_config")
        private LogisticsLabelConfigDTO logisticsLabelConfig;
        @JSONField(name = "create_package_label")
        private String createPackageLabel;

        @NoArgsConstructor
        @Data
        public static class LogisticsLabelConfigDTO {
            @JSONField(name = "is_print_time")
            private String isPrintTime;
            @JSONField(name = "is_print_buyer_id")
            private String isPrintBuyerId;
            @JSONField(name = "is_print_pick_info")
            private String isPrintPickInfo;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ParcelListDTO {
        @JSONField(name = "weight")
        private Integer weight;
        @JSONField(name = "length")
        private Integer length;
        @JSONField(name = "width")
        private Integer width;
        @JSONField(name = "height")
        private Integer height;
        @JSONField(name = "parcel_value")
        private Double parcelValue;
        @JSONField(name = "currency")
        private String currency;
        @JSONField(name = "include_battery")
        private String includeBattery;
        @JSONField(name = "battery_type")
        private String batteryType;
        @JSONField(name = "product_list")
        private List<ProductListDTO> productList;
        @JSONField(name = "declare_product_info")
        private List<DeclareProductInfoDTO> declareProductInfo;

        @NoArgsConstructor
        @Data
        public static class ProductListDTO {
            @JSONField(name = "sku_code")
            private String skuCode;
            @JSONField(name = "standard_product_barcode")
            private String standardProductBarcode;
            @JSONField(name = "product_name")
            private String productName;
            @JSONField(name = "product_description")
            private String productDescription;
            @JSONField(name = "product_unit_price")
            private Integer productUnitPrice;
            @JSONField(name = "currency")
            private String currency;
            @JSONField(name = "qty")
            private Integer qty;
        }

        @NoArgsConstructor
        @Data
        public static class DeclareProductInfoDTO {
            @JSONField(name = "declare_product_code")
            private String declareProductCode;
            @JSONField(name = "declare_product_name_cn")
            private String declareProductNameCn;
            @JSONField(name = "declare_product_name_en")
            private String declareProductNameEn;
            @JSONField(name = "uses")
            private String uses;
            @JSONField(name = "specification")
            private String specification;
            @JSONField(name = "component")
            private String component;
            @JSONField(name = "unit_net_weight")
            private Integer unitNetWeight;
            @JSONField(name = "unit_gross_weight")
            private Integer unitGrossWeight;
            @JSONField(name = "material")
            private String material;
            @JSONField(name = "declare_product_code_qty")
            private Integer declareProductCodeQty;
            @JSONField(name = "unit_declare_product")
            private String unitDeclareProduct;
            @JSONField(name = "origin_country")
            private String originCountry;
            @JSONField(name = "country_export")
            private String countryExport;
            @JSONField(name = "country_import")
            private String countryImport;
            @JSONField(name = "hscode_export")
            private String hscodeExport;
            @JSONField(name = "hscode_import")
            private String hscodeImport;
            @JSONField(name = "declare_unit_price_export")
            private BigDecimal declareUnitPriceExport;
            @JSONField(name = "currency_export")
            private String currencyExport = "USD";
            @JSONField(name = "declare_unit_price_import")
            private BigDecimal declareUnitPriceImport;
            @JSONField(name = "currency_import")
            private String currencyImport = "USD";
            @JSONField(name = "brand_export")
            private String brandExport = "none";
            @JSONField(name = "brand_import")
            private String brandImport = "none";
            @JSONField(name = "sales_url")
            private String salesUrl;
            @JSONField(name = "package_remarks")
            private String packageRemarks;
        }
    }
}
