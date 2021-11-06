package com.upedge.oms.modules.statistics.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.statistics.dao.InvoiceExportRequestDao;
import com.upedge.oms.modules.statistics.entity.InvoiceExportRequest;
import com.upedge.oms.modules.statistics.service.InvoiceExportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class InvoiceExportRequestServiceImpl implements InvoiceExportRequestService {

    @Autowired
    private InvoiceExportRequestDao invoiceExportRequestDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        InvoiceExportRequest record = new InvoiceExportRequest();
        record.setId(id);
        return invoiceExportRequestDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(InvoiceExportRequest record) {
        return invoiceExportRequestDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(InvoiceExportRequest record) {
        return invoiceExportRequestDao.insert(record);
    }

    /**
     *
     */
    public InvoiceExportRequest selectByPrimaryKey(Integer id){
        InvoiceExportRequest record = new InvoiceExportRequest();
        record.setId(id);
        return invoiceExportRequestDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(InvoiceExportRequest record) {
        return invoiceExportRequestDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(InvoiceExportRequest record) {
        return invoiceExportRequestDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<InvoiceExportRequest> select(Page<InvoiceExportRequest> record){
        record.initFromNum();
        return invoiceExportRequestDao.select(record);
    }

    /**
    *
    */
    public long count(Page<InvoiceExportRequest> record){
        return invoiceExportRequestDao.count(record);
    }

}