package com.upedge.oms.modules.statistics.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.statistics.entity.InvoiceExportRequest;

import java.util.List;

/**
 * @author Ï¦Îí
 */
public interface InvoiceExportRequestDao{

    InvoiceExportRequest selectByPrimaryKey(InvoiceExportRequest record);

    int deleteByPrimaryKey(InvoiceExportRequest record);

    int updateByPrimaryKey(InvoiceExportRequest record);

    int updateByPrimaryKeySelective(InvoiceExportRequest record);

    int insert(InvoiceExportRequest record);

    int insertSelective(InvoiceExportRequest record);

    int insertByBatch(List<InvoiceExportRequest> list);

    List<InvoiceExportRequest> select(Page<InvoiceExportRequest> record);

    long count(Page<InvoiceExportRequest> record);

}
