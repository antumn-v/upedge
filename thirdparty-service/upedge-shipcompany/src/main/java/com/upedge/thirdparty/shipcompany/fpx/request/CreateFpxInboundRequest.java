package com.upedge.thirdparty.shipcompany.fpx.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CreateFpxInboundRequest {

    @JsonProperty("business_type")
    private String businessType = "D";
    @JsonProperty("customer_code ")
    private String customerCode = FpxConfig.CUSTOMER_CODE;
    @JsonProperty("ref_no")
    private String refNo;
    @JsonProperty("is_pickup")
    private String isPickup = "N";
    @JsonProperty("from_warehouse_code")
    private String fromWarehouseCode;
    @JsonProperty("to_warehouse_code")
    private String toWarehouseCode;
    @JsonProperty("transport_type")
    private String transportType = "E";
    @JsonProperty("tracking_no")
    private String trackingNo;
    @JsonProperty("license_plate")
    private String licensePlate;
    @JsonProperty("supplier_address")
    private SupplierAddressDTO supplierAddress = new SupplierAddressDTO();
    @JsonProperty("logistics_service_info")
    private LogisticsServiceInfoDTO logisticsServiceInfo = new LogisticsServiceInfoDTO();
    @JsonProperty("insure_services")
    private String insureServices;
    @JsonProperty("ocustoms_service")
    private String ocustomsService = "D3";
    @JsonProperty("icustoms_service")
    private String icustomsService = "D1";
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("insure_value")
    private String insureValue;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("print_box_no")
    private String printBoxNo = "Y";
    @JsonProperty("print_box_type")
    private String printBoxType = "Z";
    @JsonProperty("iconsignment_sku")
    private List<IconsignmentSkuDTO> iconsignmentSku;

    @NoArgsConstructor
    @Data
    public static class SupplierAddressDTO {
        @JsonProperty("country")
        private String country = "CN";
        @JsonProperty("state")
        private String state = "浙江省";
        @JsonProperty("city")
        private String city = "杭州市";
        @JsonProperty("district")
        private String district;
        @JsonProperty("post_code")
        private String postCode;
        @JsonProperty("street")
        private String street;
        @JsonProperty("house_number")
        private String houseNumber;
        @JsonProperty("company")
        private String company;
        @JsonProperty("contack_name")
        private String contackName;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("pickup_time")
        private String pickupTime;
        @JsonProperty("plan_arrive_time")
        private String planArriveTime;
    }

    @NoArgsConstructor
    @Data
    public static class LogisticsServiceInfoDTO {
        @JsonProperty("logistics_product_code")
        private String logisticsProductCode;
        @JsonProperty("signature_service")
        private String signatureService = "N";
    }

    @NoArgsConstructor
    @Data
    public static class IconsignmentSkuDTO {
        @JsonProperty("box_no")
        private String boxNo;
        @JsonProperty("box_barcode")
        private String boxBarcode;
        @JsonProperty("sku_code")
        private String skuCode;
        @JsonProperty("plan_qty")
        private Integer planQty;
        @JsonProperty("batch_no")
        private String batchNo;
    }
}
