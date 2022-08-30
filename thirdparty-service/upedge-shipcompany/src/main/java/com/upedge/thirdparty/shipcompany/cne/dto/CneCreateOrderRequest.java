package com.upedge.thirdparty.shipcompany.cne.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
        @JSONField(name = "cEmsKind")
        private String cEmsKind;
        @JSONField(name = "nItemType")
        private Integer nItemType = 1;
        @JSONField(name = "cAddrFrom")
        private String cAddrFrom = "OTHER";
        @JSONField(name = "iItem")
        private Integer iItem = 1;
        @JSONField(name = "cRNo")
        private String cRNo;
        @JSONField(name = "cDes")
        private String cDes;
        @JSONField(name = "cReceiver")
        private String cReceiver;
        @JSONField(name = "cRUnit")
        private String cRUnit;
        @JSONField(name = "cRAddr")
        private String cRAddr;
        @JSONField(name = "cRCity")
        private String cRCity;
        @JSONField(name = "cRPostcode")
        private String cRPostcode;
        @JSONField(name = "cRProvince")
        private String cRProvince;
        @JSONField(name = "cRCountry")
        private String cRCountry;
        @JSONField(name = "cRPhone")
        private String cRPhone;
        @JSONField(name = "cRSms")
        private String cRSms;
        @JSONField(name = "cREMail")
        private String cREMail;
        @JSONField(name = "cRTaxCode")
        private String cRTaxCode;
        @JSONField(name = "fWeight")
        private Double fWeight;
        @JSONField(name = "iLong")
        private Object iLong;
        @JSONField(name = "iWidth")
        private Object iWidth;
        @JSONField(name = "iHeight")
        private Object iHeight;
        @JSONField(name = "cMark")
        private String cMark;
        @JSONField(name = "tradeTime")
        private String tradeTime;
        @JSONField(name = "cMemo")
        private String cMemo;
        @JSONField(name = "cReserve")
        private String cReserve;
        @JSONField(name = "vatCode")
        private String vatCode;
        @JSONField(name = "iossCode")
        private String iossCode;
        @JSONField(name = "returnAddress")
        private ReturnAddressDTO returnAddress;
        @JSONField(name = "labelContent")
        private LabelContentDTO labelContent;
        @JSONField(name = "GoodsList")
        private List<CneGoodsListDTO> goodsList;

        @NoArgsConstructor
        @Data
        public static class ReturnAddressDTO {
            @JSONField(name = "name")
            private String name;
            @JSONField(name = "addr")
            private String addr;
            @JSONField(name = "city")
            private String city;
            @JSONField(name = "postcode")
            private String postcode;
            @JSONField(name = "province")
            private String province;
            @JSONField(name = "country")
            private String country;
        }

        @NoArgsConstructor
        @Data
        public static class LabelContentDTO {
            @JSONField(name = "fileType")
            private String fileType;
            @JSONField(name = "labelType")
            private String labelType;
            @JSONField(name = "pickList")
            private String pickList;
        }

        @NoArgsConstructor
        @Data
        public static class CneGoodsListDTO {
            @JSONField(name = "cxGoods")
            private String cxGoods;
            @JSONField(name = "cxGoodsA")
            private String cxGoodsA;
            @JSONField(name = "ixQuantity")
            private Integer ixQuantity;
            @JSONField(name = "fxPrice")
            private BigDecimal fxPrice;
            @JSONField(name = "cxMoney")
            private String cxMoney = "USD";
        }
    }
}
