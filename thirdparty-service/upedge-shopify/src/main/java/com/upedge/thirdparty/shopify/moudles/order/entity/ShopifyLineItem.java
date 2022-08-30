package com.upedge.thirdparty.shopify.moudles.order.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ShopifyLineItem {


    @JSONField(name = "id")
    private String id;
    @JSONField(name = "admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JSONField(name = "fulfillable_quantity")
    private Integer fulfillable_quantity;
    @JSONField(name = "fulfillment_service")
    private String fulfillmentService;
    @JSONField(name = "fulfillment_status")
    private String fulfillmentStatus;
    @JSONField(name = "gift_card")
    private Boolean giftCard;
    @JSONField(name = "grams")
    private Integer grams;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "origin_location")
    private OriginLocationDTO originLocation;
    @JSONField(name = "price")
    private BigDecimal price;
    @JSONField(name = "price_set")
    private PriceSetDTO priceSet;
    @JSONField(name = "product_exists")
    private Boolean productExists;
    @JSONField(name = "product_id")
    private String product_id;
    @JSONField(name = "quantity")
    private Integer quantity;
    @JSONField(name = "requires_shipping")
    private Boolean requiresShipping;
    @JSONField(name = "sku")
    private String sku;
    @JSONField(name = "taxable")
    private Boolean taxable;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "total_discount")
    private String totalDiscount;
    @JSONField(name = "total_discount_set")
    private TotalDiscountSetDTO totalDiscountSet;
    @JSONField(name = "variant_id")
    private String variant_id;
    @JSONField(name = "variant_inventory_management")
    private String variantInventoryManagement;
    @JSONField(name = "variant_title")
    private String variant_title;
    @JSONField(name = "vendor")
    private String vendor;


    @NoArgsConstructor
    @Data
    public static class OriginLocationDTO {
        @JSONField(name = "id")
        private Long id;
        @JSONField(name = "country_code")
        private String countryCode;
        @JSONField(name = "province_code")
        private String provinceCode;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "address1")
        private String address1;
        @JSONField(name = "address2")
        private String address2;
        @JSONField(name = "city")
        private String city;
        @JSONField(name = "zip")
        private String zip;
    }

    @NoArgsConstructor
    @Data
    public static class PriceSetDTO {
        @JSONField(name = "shop_money")
        private ShopMoneyDTO shopMoney;
        @JSONField(name = "presentment_money")
        private PresentmentMoneyDTO presentmentMoney;

        @NoArgsConstructor
        @Data
        public static class ShopMoneyDTO {
            @JSONField(name = "amount")
            private String amount;
            @JSONField(name = "currency_code")
            private String currencyCode;
        }

        @NoArgsConstructor
        @Data
        public static class PresentmentMoneyDTO {
            @JSONField(name = "amount")
            private String amount;
            @JSONField(name = "currency_code")
            private String currencyCode;
        }
    }

    @NoArgsConstructor
    @Data
    public static class TotalDiscountSetDTO {
        @JSONField(name = "shop_money")
        private ShopMoneyDTO shopMoney;
        @JSONField(name = "presentment_money")
        private PresentmentMoneyDTO presentmentMoney;

        @NoArgsConstructor
        @Data
        public static class ShopMoneyDTO {
            @JSONField(name = "amount")
            private String amount;
            @JSONField(name = "currency_code")
            private String currencyCode;
        }

        @NoArgsConstructor
        @Data
        public static class PresentmentMoneyDTO {
            @JSONField(name = "amount")
            private String amount;
            @JSONField(name = "currency_code")
            private String currencyCode;
        }
    }
}
