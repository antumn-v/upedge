package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceProductVo {

//    @ExcelProperty(index = 0,value = "Title")
    private String title;

//    @ExcelProperty(index = 1,value = "Sku")
    private String sku;

//    @ExcelProperty(index = 2,value = "Variant")
    private String variantTitle;

//    @ExcelIgnore
    private String image;

//    @ExcelProperty(index = 3,value = "QTY")
    private Integer quantity;

//    @ExcelProperty(index = 4,value = "Unit Cost")
    private BigDecimal price;

//    @ExcelProperty(index = 5,value = "Total")
    private BigDecimal total;
}
