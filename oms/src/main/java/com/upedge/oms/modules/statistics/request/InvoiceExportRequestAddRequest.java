package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import java.util.Date;

/**
 * @author Ï¦Îí
 */
@Data
public class InvoiceExportRequestAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private String rangeBegin;
    /**
    * 
    */
    private String rangeEnd;
    /**
    * 
    */
    private String excelName;
    /**
    * 
    */
    private String excelUrl;
    /**
    * 
    */
    private Integer state;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public InvoiceExportRequest toInvoiceExportRequest(){
        InvoiceExportRequest invoiceExportRequest=new InvoiceExportRequest();
        invoiceExportRequest.setRangeBegin(rangeBegin);
        invoiceExportRequest.setRangeEnd(rangeEnd);
        invoiceExportRequest.setExcelName(excelName);
        invoiceExportRequest.setExcelUrl(excelUrl);
        invoiceExportRequest.setState(state);
        invoiceExportRequest.setCreateTime(createTime);
        invoiceExportRequest.setUpdateTime(updateTime);
        return invoiceExportRequest;
    }

}
