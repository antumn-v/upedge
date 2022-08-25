package com.upedge.thirdparty.shipcompany.cne.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CneCreateOrderRequest extends CneRequestBase {


    @JSONField(name = "RecList")
    private List<RecListDTO> recList = new ArrayList<>();

    @NoArgsConstructor
    @Data
    public static class RecListDTO {
        @JsonProperty("cEmsKind")
        private String cEmsKind;
        @JsonProperty("nItemType")
        private Integer nItemType = 1;
        @JsonProperty("cAddrFrom")
        private String cAddrFrom = "OTHER";
        @JsonProperty("iItem")
        private Integer iItem = 1;
        @JsonProperty("cRNo")
        private String cRNo;
        @JsonProperty("cDes")
        private String cDes;
        @JsonProperty("cReceiver")
        private String cReceiver;
        @JsonProperty("cRUnit")
        private String cRUnit;
        @JsonProperty("cRAddr")
        private String cRAddr;
        @JsonProperty("cRCity")
        private String cRCity;
        @JsonProperty("cRPostcode")
        private String cRPostcode;
        @JsonProperty("cRProvince")
        private String cRProvince;
        @JsonProperty("cRCountry")
        private String cRCountry;
        @JsonProperty("cRPhone")
        private String cRPhone;
        @JsonProperty("cRSms")
        private String cRSms;
        @JsonProperty("cREMail")
        private String cREMail;
        @JsonProperty("cRTaxCode")
        private String cRTaxCode;
        @JsonProperty("fWeight")
        private Double fWeight;
        @JsonProperty("iLong")
        private Object iLong;
        @JsonProperty("iWidth")
        private Object iWidth;
        @JsonProperty("iHeight")
        private Object iHeight;
        @JsonProperty("cMark")
        private String cMark;
        @JsonProperty("tradeTime")
        private String tradeTime;
        @JsonProperty("cMemo")
        private String cMemo;
        @JsonProperty("cReserve")
        private String cReserve;
        @JsonProperty("vatCode")
        private String vatCode;
        @JsonProperty("iossCode")
        private String iossCode;
        @JsonProperty("returnAddress")
        private ReturnAddressDTO returnAddress;
        @JsonProperty("labelContent")
        private LabelContentDTO labelContent;
        @JSONField(name = "GoodsList")
        private List<CneGoodsListDTO> goodsList;

        @NoArgsConstructor
        @Data
        public static class ReturnAddressDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("addr")
            private String addr;
            @JsonProperty("city")
            private String city;
            @JsonProperty("postcode")
            private String postcode;
            @JsonProperty("province")
            private String province;
            @JsonProperty("country")
            private String country;
        }

        @NoArgsConstructor
        @Data
        public static class LabelContentDTO {
            @JsonProperty("fileType")
            private String fileType;
            @JsonProperty("labelType")
            private String labelType;
            @JsonProperty("pickList")
            private String pickList;
        }

        @NoArgsConstructor
        @Data
        public static class CneGoodsListDTO {
            @JsonProperty("cxGoods")
            private String cxGoods;
            @JsonProperty("cxGoodsA")
            private String cxGoodsA;
            @JsonProperty("ixQuantity")
            private Integer ixQuantity;
            @JsonProperty("fxPrice")
            private BigDecimal fxPrice;
            @JsonProperty("cxMoney")
            private String cxMoney = "USD";
        }
    }
}
