package com.upedge.thirdparty.shopify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WebhookDateRequest {


    @JsonProperty("shop_id")
    private String shopId;
    @JsonProperty("shop_domain")
    private String shopDomain;
    @JsonProperty("orders_requested")
    private List<String> ordersRequested;
    @JsonProperty("customer")
    private CustomerDTO customer;
    @JsonProperty("data_request")
    private DataRequestDTO dataRequest;

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

    @NoArgsConstructor
    @Data
    public static class DataRequestDTO {
        @JsonProperty("id")
        private String id;
    }
}
