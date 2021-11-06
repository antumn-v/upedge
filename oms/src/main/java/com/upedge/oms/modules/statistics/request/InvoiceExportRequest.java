package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import java.util.Date;

@Data
public class InvoiceExportRequest {
    private Integer id;

    private Long userId;

    private String rangeBegin;

    private String rangeEnd;

    private String excelUrl;

    private String absolutePath;

    private String excelName;

    private Integer state;

    private Date createTime;

    private Date updateTime;


}