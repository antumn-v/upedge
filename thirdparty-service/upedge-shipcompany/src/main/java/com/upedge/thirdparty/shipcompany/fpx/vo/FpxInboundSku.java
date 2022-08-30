package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 入库委托sku
 */
@NoArgsConstructor
@Data
public class FpxInboundSku {

    @JSONField(name = "box_no")
    private String boxNo;
    @JSONField(name = "box_barcode")
    private String boxBarcode;
    @JSONField(name = "sku_id")
    private String skuId;
    @JSONField(name = "sku_code")
    private String skuCode;
    @JSONField(name = "product_code")
    private String productCode;
    @JSONField(name = "logistics_package")
    private String logisticsPackage;
    @JSONField(name = "plan_qty")
    private Integer planQty;
    @JSONField(name = "received_qty")
    private Integer receivedQty;
    @JSONField(name = "exception_qty")
    private Integer exceptionQty;
    @JSONField(name = "batch_no")
    private String batchNo;
    @JSONField(name = "weight")
    private String weight;
    @JSONField(name = "volume")
    private String volume;
    @JSONField(name = "length")
    private String length;
    @JSONField(name = "width")
    private String width;
    @JSONField(name = "height")
    private String height;
}
