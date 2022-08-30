package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class FpxGetLabelDto {

    @JSONField(name = "request_no")
    private List<String> requestNo = new ArrayList<>();
    @JSONField(name = "logistics_product_code")
    private String logisticsProductCode;
    @JSONField(name = "label_size")
    private String labelSize = "label_80x90";
    @JSONField(name = "is_print_time")
    private String isPrintTime = "N";
    @JSONField(name = "is_print_buyer_id")
    private String isPrintBuyerId = "N";
    @JSONField(name = "is_print_pick_info")
    private String isPrintPickInfo = "N";
    @JSONField(name = "is_print_customer_weight")
    private String isPrintCustomerWeight = "N";
    @JSONField(name = "is_print_pick_barcode")
    private String isPrintPickBarcode = "N";
    @JSONField(name = "create_package_label")
    private String createPackageLabel = "N";
}
