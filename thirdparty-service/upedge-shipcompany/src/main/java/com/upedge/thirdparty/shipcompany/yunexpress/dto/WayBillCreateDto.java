package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WayBillCreateDto {

    /**
     * 订单ID
     */
    @JsonProperty("CustomerOrderNumber")
    private String customerOrderNumber;
    /**
     * 运输方式代码
     */
    @JsonProperty("ShippingMethodCode")
    private String shippingMethodCode;
    /**
     * 包裹号
     */
    @JsonProperty("TrackingNumber")
    private Object trackingNumber;
    @JsonProperty("TransactionNumber")
    private Object transactionNumber;
    @JsonProperty("IossCode")
    private String iossCode;
    @JsonProperty("BrazilianCode")
    private Object brazilianCode;
    @JsonProperty("Height")
    private Integer height;
    @JsonProperty("Length")
    private Integer length;
    @JsonProperty("Width")
    private Integer width;
    /**
     * 包裹数量
     */
    @JsonProperty("PackageCount")
    private Integer packageCount;
    @JsonProperty("Weight")
    private Integer weight;
    @JsonProperty("ApplicationType")
    private Integer applicationType;
    @JsonProperty("ReturnOption")
    private Integer returnOption;
    @JsonProperty("TariffPrepay")
    private Integer tariffPrepay;
    @JsonProperty("InsuranceOption")
    private Integer insuranceOption;
    @JsonProperty("SourceCode")
    private String sourceCode;
    @JsonProperty("Receiver")
    private ReceiverDTO receiver;
    @JsonProperty("Sender")
    private SenderDTO sender;
    @JsonProperty("OrderExtra")
    private List<OrderExtraDTO> orderExtra;
    @JsonProperty("Parcels")
    private List<ParcelsDTO> parcels;
    @JsonProperty("ChildOrders")
    private List<ChildOrdersDTO> childOrders;

    @NoArgsConstructor
    @Data
    public static class ReceiverDTO {
        @JsonProperty("CountryCode")
        private String countryCode;
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("LastName")
        private String lastName;
        @JsonProperty("Company")
        private String company;
        @JsonProperty("Street")
        private String street;
        @JsonProperty("City")
        private String city;
        @JsonProperty("State")
        private String state;
        @JsonProperty("Zip")
        private String zip;
        @JsonProperty("Phone")
        private String phone;
        @JsonProperty("HouseNumber")
        private String houseNumber;
        @JsonProperty("Email")
        private String email;
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JsonProperty("CountryCode")
        private String countryCode;
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("LastName")
        private String lastName;
        @JsonProperty("Company")
        private String company;
        @JsonProperty("Street")
        private String street;
        @JsonProperty("City")
        private String city;
        @JsonProperty("State")
        private String state;
        @JsonProperty("Zip")
        private String zip;
        @JsonProperty("Phone")
        private String phone;
        @JsonProperty("HouseNumber")
        private String houseNumber;
    }

    @NoArgsConstructor
    @Data
    public static class OrderExtraDTO {
        @JsonProperty("ExtraCode")
        private String extraCode;
        @JsonProperty("ExtraName")
        private String extraName;
    }

    @NoArgsConstructor
    @Data
    public static class ParcelsDTO {
        /**
         * 报关英文名
         */
        @JsonProperty("EName")
        private String eName;
        /**
         * 报关中文名
         */
        @JsonProperty("CName")
        private String cName;
        @JsonProperty("HSCode")
        private Object hSCode;
        @JsonProperty("Quantity")
        private Integer quantity;
        @JsonProperty("SKU")
        private String sku;
        @JsonProperty("UnitPrice")
        private Integer unitPrice;
        @JsonProperty("UnitWeight")
        private Integer unitWeight;
        @JsonProperty("Remark")
        private String remark;
        @JsonProperty("InvoiceRemark")
        private String invoiceRemark;
        @JsonProperty("CurrencyCode")
        private Object currencyCode;
    }

    @NoArgsConstructor
    @Data
    public static class ChildOrdersDTO {
        @JsonProperty("BoxNumber")
        private String boxNumber;
        @JsonProperty("Length")
        private Integer length;
        @JsonProperty("Width")
        private Integer width;
        @JsonProperty("Height")
        private Integer height;
        @JsonProperty("BoxWeight")
        private Integer boxWeight;
        @JsonProperty("ChildDetails")
        private List<ChildDetailsDTO> childDetails;

        @NoArgsConstructor
        @Data
        public static class ChildDetailsDTO {
            @JsonProperty("Sku")
            private String sku;
            @JsonProperty("Quantity")
            private Integer quantity;
        }
    }
}
