package com.upedge.thirdparty.shipcompany.fpx.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CreateFpxInboundRequest {

    @JSONField(name = "business_type")
    private String businessType = "D";
    @JSONField(name = "customer_code ")
    private String customerCode = FpxConfig.CUSTOMER_CODE;
    @JSONField(name = "ref_no")
    private String refNo;
    @JSONField(name = "is_pickup")
    private String isPickup = "N";
    @JSONField(name = "from_warehouse_code")
    private String fromWarehouseCode;
    @JSONField(name = "to_warehouse_code")
    private String toWarehouseCode;
    @JSONField(name = "transport_type")
    private String transportType = "E";
    @JSONField(name = "tracking_no")
    private String trackingNo;
    @JSONField(name = "license_plate")
    private String licensePlate;
    @JSONField(name = "supplier_address")
    private SupplierAddressDTO supplierAddress = new SupplierAddressDTO();
    @JSONField(name = "logistics_service_info")
    private LogisticsServiceInfoDTO logisticsServiceInfo = new LogisticsServiceInfoDTO();
    @JSONField(name = "insure_services")
    private String insureServices;
    @JSONField(name = "ocustoms_service")
    private String ocustomsService = "D3";
    @JSONField(name = "icustoms_service")
    private String icustomsService = "D1";
    @JSONField(name = "currency")
    private String currency;
    @JSONField(name = "insure_value")
    private String insureValue;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "print_box_no")
    private String printBoxNo = "Y";
    @JSONField(name = "print_box_type")
    private String printBoxType = "Z";
    @JSONField(name = "iconsignment_sku")
    private List<IconsignmentSkuDTO> iconsignmentSku;

    @NoArgsConstructor
    @Data
    public static class SupplierAddressDTO {
        @JSONField(name = "country")
        private String country = "CN";
        @JSONField(name = "state")
        private String state = "浙江省";
        @JSONField(name = "city")
        private String city = "杭州市";
        @JSONField(name = "district")
        private String district;
        @JSONField(name = "post_code")
        private String postCode;
        @JSONField(name = "street")
        private String street;
        @JSONField(name = "house_number")
        private String houseNumber;
        @JSONField(name = "company")
        private String company;
        @JSONField(name = "contack_name")
        private String contackName;
        @JSONField(name = "phone")
        private String phone;
        @JSONField(name = "pickup_time")
        private String pickupTime;
        @JSONField(name = "plan_arrive_time")
        private String planArriveTime;
    }

    @NoArgsConstructor
    @Data
    public static class LogisticsServiceInfoDTO {
        @JSONField(name = "logistics_product_code")
        private String logisticsProductCode;
        @JSONField(name = "signature_service")
        private String signatureService = "N";
    }

    @NoArgsConstructor
    @Data
    public static class IconsignmentSkuDTO {
        @JSONField(name = "box_no")
        private String boxNo;
        @JSONField(name = "box_barcode")
        private String boxBarcode;
        @JSONField(name = "sku_code")
        private String skuCode;
        @JSONField(name = "plan_qty")
        private Integer planQty;
        @JSONField(name = "batch_no")
        private String batchNo;
    }
}
