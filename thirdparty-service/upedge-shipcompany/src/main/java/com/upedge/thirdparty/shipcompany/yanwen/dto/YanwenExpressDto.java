package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class YanwenExpressDto   {


    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("orderSource")
    private String orderSource;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("dateOfReceipt")
    private String dateOfReceipt;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("receiverInfo")
    private YanwenReceiverInfoDTO receiverInfo = new YanwenReceiverInfoDTO();
    @JsonProperty("parcelInfo")
    private YanwenParcelInfoDTO parcelInfo = new YanwenParcelInfoDTO();
    @JsonProperty("senderInfo")
    private YanwenSenderInfoDTO senderInfo;

    @NoArgsConstructor
    @Data
    public static class YanwenReceiverInfoDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("email")
        private String email;
        @JsonProperty("company")
        private String company;
        @JsonProperty("country")
        private String country;
        @JsonProperty("state")
        private String state;
        @JsonProperty("city")
        private String city;
        @JsonProperty("zipCode")
        private String zipCode;
        @JsonProperty("houseNumber")
        private String houseNumber;
        @JsonProperty("address")
        private String address;
        @JsonProperty("taxNumber")
        private String taxNumber;
    }

    @NoArgsConstructor
    @Data
    public static class YanwenParcelInfoDTO {
        @JsonProperty("productList")
        private List<YanwenProductListDTO> productList = new ArrayList<>();
        @JsonProperty("hasBattery")
        private Integer hasBattery = 0;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("totalPrice")
        private BigDecimal totalPrice;
        @JsonProperty("totalQuantity")
        private Integer totalQuantity;
        @JsonProperty("totalWeight")
        private Integer totalWeight;
        @JsonProperty("height")
        private String height;
        @JsonProperty("width")
        private String width;
        @JsonProperty("length")
        private String length;
        @JsonProperty("ioss")
        private String ioss;

        @NoArgsConstructor
        @Data
        public static class YanwenProductListDTO {
            @JsonProperty("goodsNameCh")
            private String goodsNameCh;
            @JsonProperty("goodsNameEn")
            private String goodsNameEn;
            @JsonProperty("price")
            private BigDecimal price;
            @JsonProperty("quantity")
            private Integer quantity;
            @JsonProperty("weight")
            private Integer weight;
            @JsonProperty("hscode")
            private String hscode;
            @JsonProperty("url")
            private String url;
            @JsonProperty("material")
            private String material;
        }
    }

    @NoArgsConstructor
    @Data
    public static class YanwenSenderInfoDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("company")
        private String company;
        @JsonProperty("email")
        private String email;
        @JsonProperty("country")
        private String country;
        @JsonProperty("state")
        private String state;
        @JsonProperty("city")
        private String city;
        @JsonProperty("zipCode")
        private String zipCode;
        @JsonProperty("houseNumber")
        private String houseNumber;
        @JsonProperty("address")
        private String address;
        @JsonProperty("taxNumber")
        private String taxNumber;
    }
}
