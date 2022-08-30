package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class YanwenExpressDto {

    @JSONField(name = "Userid")
    private String userid;
    @JSONField(name = "Channel")
    private String channel;
    @JSONField(name = "Epcode")
    private String epcode;
    @JSONField(name = "ReferenceNo")
    private String referenceNo;
    @JSONField(name = "UserOrderNumber")
    private String userOrderNumber;
    @JSONField(name = "SendDate")
    private String sendDate;
    @JSONField(name = "Quantity")
    private String quantity;
    @JSONField(name = "Insure")
    private String insure;
    @JSONField(name = "MerchantCsName")
    private String merchantCsName;
    @JSONField(name = "ProductLink")
    private String productLink;
    @JSONField(name = "Memo")
    private String memo;
    @JSONField(name = "DateOfReceipt")
    private String dateOfReceipt;
    @JSONField(name = "MRP")
    private String mrp;
    @JSONField(name = "ExpiryDate")
    private String expiryDate;
    @JSONField(name = "Receiver")
    private YanwenReceiverDTO receiver;
    @JSONField(name = "Sender")
    private SenderDTO sender;
    @JSONField(name = "GoodsName")
    private YanwenGoodsNameDTO goodsName;

    @NoArgsConstructor
    @Data
    public static class YanwenReceiverDTO {
        @JSONField(name = "Userid")
        private String userid;
        @JSONField(name = "Name")
        private String name;
        @JSONField(name = "Phone")
        private String phone;
        @JSONField(name = "Email")
        private String email;
        @JSONField(name = "Company")
        private String company;
        @JSONField(name = "Country")
        private String country;
        @JSONField(name = "Postcode")
        private String postcode;
        @JSONField(name = "State")
        private String state;
        @JSONField(name = "City")
        private String city;
        @JSONField(name = "District")
        private String district;
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
    }

    @NoArgsConstructor
    @Data
    public static class SenderDTO {
        @JSONField(name = "TaxNumber")
        private String taxNumber;
    }

    @NoArgsConstructor
    @Data
    public static class YanwenGoodsNameDTO {
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
        @JSONField(name = "HsCode")
        private String hsCode;
        @JSONField(name = "BatteryStatus")
        private String batteryStatus;
        @JSONField(name = "MoreGoodsName")
        private String moreGoodsName;
        @JSONField(name = "ProductBrand")
        private String productBrand;
        @JSONField(name = "ProductSize")
        private String productSize;
        @JSONField(name = "ProductColor")
        private String productColor;
        @JSONField(name = "ProductMaterial")
        private String productMaterial;
    }
}
