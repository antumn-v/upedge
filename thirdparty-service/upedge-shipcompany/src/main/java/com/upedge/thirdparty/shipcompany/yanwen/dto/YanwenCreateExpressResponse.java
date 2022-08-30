package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class YanwenCreateExpressResponse {


    @JSONField(name = "CallSuccess")
    private String callSuccess;
    @JSONField(name = "Response")
    private ResponseDTO response;
    @JSONField(name = "CreatedExpress")
    private CreatedExpressDTO createdExpress;

    @NoArgsConstructor
    @Data
    public static class ResponseDTO {
        @JSONField(name = "Userid")
        private String userid;
        @JSONField(name = "Operation")
        private String operation;
        @JSONField(name = "Success")
        private String success;
        @JSONField(name = "Reason")
        private String reason;
        @JSONField(name = "ReasonMessage")
        private String reasonMessage;
        @JSONField(name = "Epcode")
        private String epcode;
    }

    @NoArgsConstructor
    @Data
    public static class CreatedExpressDTO {
        @JSONField(name = "UserOrderNumber")
        private String userOrderNumber;
        @JSONField(name = "DateOfReceipt")
        private String dateOfReceipt;
        @JSONField(name = "Epcode")
        private String epcode;
        @JSONField(name = "ReferenceNo")
        private String referenceNo;
        @JSONField(name = "Userid")
        private String userid;
        @JSONField(name = "ChannelType")
        private ChannelTypeDTO channelType;
        @JSONField(name = "Channel")
        private String channel;
        @JSONField(name = "Package")
        private String packageX;
        @JSONField(name = "SendDate")
        private String sendDate;
        @JSONField(name = "Quantity")
        private String quantity;
        @JSONField(name = "Receiver")
        private ReceiverDTO receiver;
        @JSONField(name = "Sender")
        private SenderDTO sender;
        @JSONField(name = "GoodsName")
        private GoodsNameDTO goodsName;
        @JSONField(name = "CustomDeclarationCollection")
        private CustomDeclarationCollectionDTO customDeclarationCollection;
        @JSONField(name = "YanwenNumber")
        private String yanwenNumber;
        @JSONField(name = "PackageNo")
        private String packageNo;
        @JSONField(name = "Insure")
        private String insure;
        @JSONField(name = "Memo")
        private String memo;
        @JSONField(name = "TrackingStatus")
        private String trackingStatus;
        @JSONField(name = "IsPrint")
        private String isPrint;
        @JSONField(name = "CreateDate")
        private String createDate;
        @JSONField(name = "MerchantCsName")
        private String merchantCsName;
        @JSONField(name = "ProductLink")
        private String productLink;
        @JSONField(name = "UndeliveryOption")
        private String undeliveryOption;
        @JSONField(name = "IsPostPlatform")
        private String isPostPlatform;
        @JSONField(name = "MRP")
        private String mrp;
        @JSONField(name = "ExpiryDate")
        private String expiryDate;
        @JSONField(name = "BatteryStatus")
        private String batteryStatus;
        @JSONField(name = "IsStatus")
        private String isStatus;

        @NoArgsConstructor
        @Data
        public static class ChannelTypeDTO {
            @JSONField(name = "Id")
            private String id;
            @JSONField(name = "Name")
            private String name;
            @JSONField(name = "Status")
            private String status;
            @JSONField(name = "NameEn")
            private String nameEn;
            @JSONField(name = "LimitStatus")
            private String limitStatus;
        }

        @NoArgsConstructor
        @Data
        public static class ReceiverDTO {
            @JSONField(name = "Userid")
            private String userid;
            @JSONField(name = "Name")
            private String name;
            @JSONField(name = "Phone")
            private String phone;
            @JSONField(name = "Mobile")
            private String mobile;
            @JSONField(name = "Email")
            private String email;
            @JSONField(name = "Company")
            private String company;
            @JSONField(name = "Country")
            private String country;
            @JSONField(name = "CountryType")
            private CountryTypeDTO countryType;
            @JSONField(name = "Postcode")
            private String postcode;
            @JSONField(name = "State")
            private String state;
            @JSONField(name = "City")
            private String city;
            @JSONField(name = "CityCode")
            private String cityCode;
            @JSONField(name = "Address1")
            private String address1;
            @JSONField(name = "Address2")
            private String address2;
            @JSONField(name = "NationalId")
            private String nationalId;
            @JSONField(name = "NationalIdFullName")
            private String nationalIdFullName;
            @JSONField(name = "NationalIdIssueDate")
            private String nationalIdIssueDate;
            @JSONField(name = "NationalIdExpireDate")
            private String nationalIdExpireDate;
            @JSONField(name = "District")
            private String district;

            @NoArgsConstructor
            @Data
            public static class CountryTypeDTO {
                @JSONField(name = "Id")
                private String id;
                @JSONField(name = "RegionCh")
                private String regionCh;
                @JSONField(name = "RegionEn")
                private String regionEn;
                @JSONField(name = "RegionCode")
                private String regionCode;
            }
        }

        @NoArgsConstructor
        @Data
        public static class SenderDTO {
            @JSONField(name = "TaxNumber")
            private String taxNumber;
        }

        @NoArgsConstructor
        @Data
        public static class GoodsNameDTO {
            @JSONField(name = "Id")
            private String id;
            @JSONField(name = "Userid")
            private String userid;
            @JSONField(name = "NameCh")
            private String nameCh;
            @JSONField(name = "NameEn")
            private String nameEn;
            @JSONField(name = "Weight")
            private String weight;
            @JSONField(name = "DeclaredValue")
            private String declaredValue;
            @JSONField(name = "DeclaredCurrency")
            private String declaredCurrency;
            @JSONField(name = "MoreGoodsName")
            private String moreGoodsName;
            @JSONField(name = "HsCode")
            private String hsCode;
            @JSONField(name = "ProductBrand")
            private String productBrand;
            @JSONField(name = "ProductSize")
            private String productSize;
            @JSONField(name = "ProductColor")
            private String productColor;
            @JSONField(name = "ProductMaterial")
            private String productMaterial;
        }

        @NoArgsConstructor
        @Data
        public static class CustomDeclarationCollectionDTO {
            @JSONField(name = "CustomDeclarationType")
            private CustomDeclarationTypeDTO customDeclarationType;

            @NoArgsConstructor
            @Data
            public static class CustomDeclarationTypeDTO {
                @JSONField(name = "Id")
                private String id;
                @JSONField(name = "Userid")
                private String userid;
                @JSONField(name = "NameCh")
                private String nameCh;
                @JSONField(name = "NameEn")
                private String nameEn;
                @JSONField(name = "Weight")
                private String weight;
                @JSONField(name = "DeclaredValue")
                private String declaredValue;
                @JSONField(name = "DeclaredCurrency")
                private String declaredCurrency;
                @JSONField(name = "MoreGoodsName")
                private String moreGoodsName;
                @JSONField(name = "HsCode")
                private String hsCode;
                @JSONField(name = "Quantity")
                private String quantity;
            }
        }
    }
}
