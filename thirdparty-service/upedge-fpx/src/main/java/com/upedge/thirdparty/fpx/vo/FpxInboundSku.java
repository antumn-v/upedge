package com.upedge.thirdparty.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 入库委托sku
 */
@NoArgsConstructor
@Data
public class FpxInboundSku {

    @JsonProperty("box_no")
    private String boxNo;
    @JsonProperty("box_barcode")
    private String boxBarcode;
    @JsonProperty("sku_id")
    private String skuId;
    @JsonProperty("sku_code")
    private String skuCode;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("logistics_package")
    private String logisticsPackage;
    @JsonProperty("plan_qty")
    private Integer planQty;
    @JsonProperty("received_qty")
    private Integer receivedQty;
    @JsonProperty("exception_qty")
    private Integer exceptionQty;
    @JsonProperty("batch_no")
    private String batchNo;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("length")
    private String length;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
}
