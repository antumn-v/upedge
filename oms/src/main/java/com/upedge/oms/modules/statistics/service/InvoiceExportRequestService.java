package com.upedge.oms.modules.statistics.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.statistics.entity.InvoiceExportRequest;

import java.util.List;

/**
 * @author Ï¦Îí
 */
public interface InvoiceExportRequestService{

    InvoiceExportRequest selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(InvoiceExportRequest record);

    int updateByPrimaryKeySelective(InvoiceExportRequest record);

    int insert(InvoiceExportRequest record);

    int insertSelective(InvoiceExportRequest record);

    List<InvoiceExportRequest> select(Page<InvoiceExportRequest> record);

    long count(Page<InvoiceExportRequest> record);
}

