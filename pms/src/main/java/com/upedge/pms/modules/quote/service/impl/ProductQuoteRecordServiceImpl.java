package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.quote.dao.ProductQuoteRecordDao;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.pms.modules.quote.service.ProductQuoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductQuoteRecordServiceImpl implements ProductQuoteRecordService {

    @Autowired
    private ProductQuoteRecordDao productQuoteRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        ProductQuoteRecord record = new ProductQuoteRecord();
        record.setId(id);
        return productQuoteRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductQuoteRecord record) {
        return productQuoteRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductQuoteRecord record) {
        return productQuoteRecordDao.insert(record);
    }

    /**
     *
     */
    public ProductQuoteRecord selectByPrimaryKey(Integer id){
        ProductQuoteRecord record = new ProductQuoteRecord();
        record.setId(id);
        return productQuoteRecordDao.selectByPrimaryKey(record);
    }

    @Override
    public int insertByBatch(List<ProductQuoteRecord> productQuoteRecords) {
        if (ListUtils.isEmpty(productQuoteRecords)){
            return 0;
        }
        return productQuoteRecordDao.insertByBatch(productQuoteRecords);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductQuoteRecord record) {
        return productQuoteRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductQuoteRecord record) {
        return productQuoteRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductQuoteRecord> select(Page<ProductQuoteRecord> record){
        record.initFromNum();
        return productQuoteRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductQuoteRecord> record){
        return productQuoteRecordDao.count(record);
    }

}