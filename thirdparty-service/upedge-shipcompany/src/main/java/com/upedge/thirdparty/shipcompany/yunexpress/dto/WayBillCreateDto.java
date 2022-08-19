package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class WayBillCreateDto {

    /**
     * 订单ID
     */
    @JsonProperty("CustomerOrderNumber")
    private String CustomerOrderNumber;
    /**
     * 运输方式代码
     */
    @JsonProperty("ShippingMethodCode")
    private String ShippingMethodCode;
    /**
     * 包裹号
     */
    @JsonProperty("TrackingNumber")
    private String TrackingNumber;
    @JsonProperty("TransactionNumber")
    private String TransactionNumber;
    @JsonProperty("IossCode")
    private String IossCode;
    @JsonProperty("BrazilianCode")
    private String BrazilianCode;
    @JsonProperty("Height")
    private Integer Height = 1;
    @JsonProperty("Length")
    private Integer Length = 1;
    @JsonProperty("Width")
    private Integer Width = 1;
    /**
     * 包裹数量
     */
    @JsonProperty("PackageCount")
    private Integer PackageCount;
    @JsonProperty("Weight")
    private BigDecimal Weight;
    @JsonProperty("ApplicationType")
    private Integer ApplicationType;
    @JsonProperty("ReturnOption")
    private Integer ReturnOption;
    @JsonProperty("TariffPrepay")
    private Integer TariffPrepay;
    @JsonProperty("InsuranceOption")
    private Integer InsuranceOption;
    @JsonProperty("SourceCode")
    private String SourceCode;
    @JsonProperty("Receiver")
    private ReceiverDTO Receiver;
    @JsonProperty("Sender")
    private SenderDTO Sender = new SenderDTO();
    @JsonProperty("OrderExtra")
    private List<OrderExtraDTO> OrderExtra;
    @JsonProperty("Parcels")
    private List<ParcelsDTO> Parcels;
    @JsonProperty("ChildOrders")
    private List<ChildOrdersDTO> ChildOrders = new ArrayList<>();

    @NoArgsConstructor
    @Data
    public static class ReceiverDTO {
        @JsonProperty("CountryCode")
        private String CountryCode;
        @JsonProperty("FirstName")
        private String FirstName;
        @JsonProperty("LastName")
        private String LastName;
        @JsonProperty("Company")
        private String Company;
        @JsonProperty("Street")
        private String Street;
        @JsonProperty("City")
        private String City;
        @JsonProperty("State")
        private String State;
        @JsonProperty("Zip")
        private String Zip;
        @JsonProperty("Phone")
        private String Phone;
        @JsonProperty("HouseNumber")
        private String HouseNumber;
        @JsonProperty("Email")
        private String Email;
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JsonProperty("CountryCode")
        private String CountryCode = "CN";
        @JsonProperty("FirstName")
        private String FirstName = "郭";
        @JsonProperty("LastName")
        private String LastName = "xin";
        @JsonProperty("Company")
        private String Company = "upedge";
        @JsonProperty("Street")
        private String Street = "五常街道";
        @JsonProperty("City")
        private String City = "杭州市";
        @JsonProperty("State")
        private String State = "浙江省";
        @JsonProperty("Zip")
        private String Zip = "311100";
        @JsonProperty("Phone")
        private String Phone = "18166668888";
        @JsonProperty("HouseNumber")
        private String HouseNumber = "406";
    }

    @NoArgsConstructor
    @Data
    public static class OrderExtraDTO {
        @JsonProperty("ExtraCode")
        private String ExtraCode;
        @JsonProperty("ExtraName")
        private String ExtraName;
    }

    @NoArgsConstructor
    @Data
    public static class ParcelsDTO {
        /**
         * 报关英文名
         */
        @JsonProperty("EName")
        private String EName;
        /**
         * 报关中文名
         */
        @JsonProperty("CName")
        private String CName;
        @JsonProperty("HSCode")
        private Object HSCode;
        @JsonProperty("Quantity")
        private Integer Quantity;
        @JsonProperty("SKU")
        private String SKU;
        @JsonProperty("UnitPrice")
        private BigDecimal UnitPrice;
        @JsonProperty("UnitWeight")
        private BigDecimal UnitWeight;
        @JsonProperty("Remark")
        private String Remark;
        @JsonProperty("InvoiceRemark")
        private String InvoiceRemark;
        @JsonProperty("CurrencyCode")
        private String CurrencyCode = "USD";
    }

    @NoArgsConstructor
    @Data
    public static class ChildOrdersDTO {
        @JsonProperty("BoxNumber")
        private String BoxNumber;
        @JsonProperty("Length")
        private Integer Length = 1;
        @JsonProperty("Width")
        private Integer Width = 1;
        @JsonProperty("Height")
        private Integer Height = 1;
        @JsonProperty("BoxWeight")
        private BigDecimal BoxWeight;
        @JsonProperty("ChildDetails")
        private List<ChildDetailsDTO> ChildDetails;

        @NoArgsConstructor
        @Data
        public static class ChildDetailsDTO {
            @JsonProperty("Sku")
            private String Sku;
            @JsonProperty("Quantity")
            private Integer Quantity;
        }
    }
}
