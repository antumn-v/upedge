package com.upedge.thirdparty.fpx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class FpxGetLabelDto {

    @JsonProperty("request_no")
    private List<String> requestNo = new ArrayList<>();
    @JsonProperty("logistics_product_code")
    private String logisticsProductCode;
    @JsonProperty("label_size")
    private String labelSize = "label_80x90";
    @JsonProperty("is_print_time")
    private String isPrintTime = "N";
    @JsonProperty("is_print_buyer_id")
    private String isPrintBuyerId = "N";
    @JsonProperty("is_print_pick_info")
    private String isPrintPickInfo = "N";
    @JsonProperty("is_print_customer_weight")
    private String isPrintCustomerWeight = "N";
    @JsonProperty("is_print_pick_barcode")
    private String isPrintPickBarcode = "N";
    @JsonProperty("create_package_label")
    private String createPackageLabel = "N";
}
