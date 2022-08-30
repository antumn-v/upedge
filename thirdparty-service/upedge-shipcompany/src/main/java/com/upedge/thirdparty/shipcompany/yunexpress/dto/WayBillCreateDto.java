package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "CustomerOrderNumber")
    private String CustomerOrderNumber;
    /**
     * 运输方式代码
     */
    @JSONField(name = "ShippingMethodCode")
    private String ShippingMethodCode;
    /**
     * 包裹号
     */
    @JSONField(name = "TrackingNumber")
    private String TrackingNumber;
    @JSONField(name = "TransactionNumber")
    private String TransactionNumber;
    @JSONField(name = "IossCode")
    private String IossCode;
    @JSONField(name = "BrazilianCode")
    private String BrazilianCode;
    @JSONField(name = "Height")
    private Integer Height = 1;
    @JSONField(name = "Length")
    private Integer Length = 1;
    @JSONField(name = "Width")
    private Integer Width = 1;
    /**
     * 包裹数量
     */
    @JSONField(name = "PackageCount")
    private Integer PackageCount;
    @JSONField(name = "Weight")
    private BigDecimal Weight;
    @JSONField(name = "ApplicationType")
    private Integer ApplicationType;
    @JSONField(name = "ReturnOption")
    private Integer ReturnOption;
    @JSONField(name = "TariffPrepay")
    private Integer TariffPrepay;
    @JSONField(name = "InsuranceOption")
    private Integer InsuranceOption;
    @JSONField(name = "SourceCode")
    private String SourceCode;
    @JSONField(name = "Receiver")
    private ReceiverDTO Receiver;
    @JSONField(name = "Sender")
    private SenderDTO Sender = new SenderDTO();
    @JSONField(name = "OrderExtra")
    private List<OrderExtraDTO> OrderExtra;
    @JSONField(name = "Parcels")
    private List<ParcelsDTO> Parcels;
    @JSONField(name = "ChildOrders")
    private List<ChildOrdersDTO> ChildOrders = new ArrayList<>();

    @NoArgsConstructor
    @Data
    public static class ReceiverDTO {
        @JSONField(name = "CountryCode")
        private String CountryCode;
        @JSONField(name = "FirstName")
        private String FirstName;
        @JSONField(name = "LastName")
        private String LastName;
        @JSONField(name = "Company")
        private String Company;
        @JSONField(name = "Street")
        private String Street;
        @JSONField(name = "City")
        private String City;
        @JSONField(name = "State")
        private String State;
        @JSONField(name = "Zip")
        private String Zip;
        @JSONField(name = "Phone")
        private String Phone;
        @JSONField(name = "HouseNumber")
        private String HouseNumber;
        @JSONField(name = "Email")
        private String Email;
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JSONField(name = "CountryCode")
        private String CountryCode = "CN";
        @JSONField(name = "FirstName")
        private String FirstName = "郭";
        @JSONField(name = "LastName")
        private String LastName = "xin";
        @JSONField(name = "Company")
        private String Company = "upedge";
        @JSONField(name = "Street")
        private String Street = "五常街道";
        @JSONField(name = "City")
        private String City = "杭州市";
        @JSONField(name = "State")
        private String State = "浙江省";
        @JSONField(name = "Zip")
        private String Zip = "311100";
        @JSONField(name = "Phone")
        private String Phone = "18166668888";
        @JSONField(name = "HouseNumber")
        private String HouseNumber = "406";
    }

    @NoArgsConstructor
    @Data
    public static class OrderExtraDTO {
        @JSONField(name = "ExtraCode")
        private String ExtraCode;
        @JSONField(name = "ExtraName")
        private String ExtraName;
    }

    @NoArgsConstructor
    @Data
    public static class ParcelsDTO {
        /**
         * 报关英文名
         */
        @JSONField(name = "EName")
        private String EName;
        /**
         * 报关中文名
         */
        @JSONField(name = "CName")
        private String CName;
        @JSONField(name = "HSCode")
        private Object HSCode;
        @JSONField(name = "Quantity")
        private Integer Quantity;
        @JSONField(name = "SKU")
        private String SKU;
        @JSONField(name = "UnitPrice")
        private BigDecimal UnitPrice;
        @JSONField(name = "UnitWeight")
        private BigDecimal UnitWeight;
        @JSONField(name = "Remark")
        private String Remark;
        @JSONField(name = "InvoiceRemark")
        private String InvoiceRemark;
        @JSONField(name = "CurrencyCode")
        private String CurrencyCode = "USD";
    }

    @NoArgsConstructor
    @Data
    public static class ChildOrdersDTO {
        @JSONField(name = "BoxNumber")
        private String BoxNumber;
        @JSONField(name = "Length")
        private Integer Length = 1;
        @JSONField(name = "Width")
        private Integer Width = 1;
        @JSONField(name = "Height")
        private Integer Height = 1;
        @JSONField(name = "BoxWeight")
        private BigDecimal BoxWeight;
        @JSONField(name = "ChildDetails")
        private List<ChildDetailsDTO> ChildDetails;

        @NoArgsConstructor
        @Data
        public static class ChildDetailsDTO {
            @JSONField(name = "Sku")
            private String Sku;
            @JSONField(name = "Quantity")
            private Integer Quantity;
        }
    }
}
