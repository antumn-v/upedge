package com.upedge.thirdparty.shopify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WebhookCustomerRedact {


    @JsonProperty("shop_id")
    private String shopId;
    @JsonProperty("shop_domain")
    private String shopDomain;
    @JsonProperty("customer")
    private CustomerDTO customer;
    @JsonProperty("orders_to_redact")
    private List<String> ordersToRedact;

    @NoArgsConstructor
    @Data
    public static class CustomerDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("email")
        private String email;
        @JsonProperty("phone")
        private String phone;
    }
}
