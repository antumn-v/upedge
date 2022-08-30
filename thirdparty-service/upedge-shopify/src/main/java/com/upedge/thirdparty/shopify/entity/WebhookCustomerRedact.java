package com.upedge.thirdparty.shopify.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WebhookCustomerRedact {


    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_domain")
    private String shopDomain;
    @JSONField(name = "customer")
    private CustomerDTO customer;
    @JSONField(name = "orders_to_redact")
    private List<String> ordersToRedact;

    @NoArgsConstructor
    @Data
    public static class CustomerDTO {
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "email")
        private String email;
        @JSONField(name = "phone")
        private String phone;
    }
}
