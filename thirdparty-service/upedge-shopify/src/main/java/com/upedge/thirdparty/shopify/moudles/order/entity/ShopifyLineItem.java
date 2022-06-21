package com.upedge.thirdparty.shopify.moudles.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ShopifyLineItem {


    @JsonProperty("id")
    private String id;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("fulfillable_quantity")
    private Integer fulfillable_quantity;
    @JsonProperty("fulfillment_service")
    private String fulfillmentService;
    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;
    @JsonProperty("gift_card")
    private Boolean giftCard;
    @JsonProperty("grams")
    private Integer grams;
    @JsonProperty("name")
    private String name;
    @JsonProperty("origin_location")
    private OriginLocationDTO originLocation;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("price_set")
    private PriceSetDTO priceSet;
    @JsonProperty("product_exists")
    private Boolean productExists;
    @JsonProperty("product_id")
    private String product_id;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("requires_shipping")
    private Boolean requiresShipping;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("taxable")
    private Boolean taxable;
    @JsonProperty("title")
    private String title;
    @JsonProperty("total_discount")
    private String totalDiscount;
    @JsonProperty("total_discount_set")
    private TotalDiscountSetDTO totalDiscountSet;
    @JsonProperty("variant_id")
    private String variant_id;
    @JsonProperty("variant_inventory_management")
    private String variantInventoryManagement;
    @JsonProperty("variant_title")
    private String variant_title;
    @JsonProperty("vendor")
    private String vendor;


    @NoArgsConstructor
    @Data
    public static class OriginLocationDTO {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("country_code")
        private String countryCode;
        @JsonProperty("province_code")
        private String provinceCode;
        @JsonProperty("name")
        private String name;
        @JsonProperty("address1")
        private String address1;
        @JsonProperty("address2")
        private String address2;
        @JsonProperty("city")
        private String city;
        @JsonProperty("zip")
        private String zip;
    }

    @NoArgsConstructor
    @Data
    public static class PriceSetDTO {
        @JsonProperty("shop_money")
        private ShopMoneyDTO shopMoney;
        @JsonProperty("presentment_money")
        private PresentmentMoneyDTO presentmentMoney;

        @NoArgsConstructor
        @Data
        public static class ShopMoneyDTO {
            @JsonProperty("amount")
            private String amount;
            @JsonProperty("currency_code")
            private String currencyCode;
        }

        @NoArgsConstructor
        @Data
        public static class PresentmentMoneyDTO {
            @JsonProperty("amount")
            private String amount;
            @JsonProperty("currency_code")
            private String currencyCode;
        }
    }

    @NoArgsConstructor
    @Data
    public static class TotalDiscountSetDTO {
        @JsonProperty("shop_money")
        private ShopMoneyDTO shopMoney;
        @JsonProperty("presentment_money")
        private PresentmentMoneyDTO presentmentMoney;

        @NoArgsConstructor
        @Data
        public static class ShopMoneyDTO {
            @JsonProperty("amount")
            private String amount;
            @JsonProperty("currency_code")
            private String currencyCode;
        }

        @NoArgsConstructor
        @Data
        public static class PresentmentMoneyDTO {
            @JsonProperty("amount")
            private String amount;
            @JsonProperty("currency_code")
            private String currencyCode;
        }
    }
}
