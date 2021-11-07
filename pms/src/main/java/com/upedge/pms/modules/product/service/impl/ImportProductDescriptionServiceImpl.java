package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.ImportProductDescriptionDao;
import com.upedge.pms.modules.product.entity.ImportProductDescription;
import com.upedge.pms.modules.product.service.ImportProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ImportProductDescriptionServiceImpl implements ImportProductDescriptionService {

    @Autowired
    private ImportProductDescriptionDao importProductDescriptionDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ImportProductDescription record = new ImportProductDescription();
        record.setId(id);
        return importProductDescriptionDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportProductDescription record) {
        return importProductDescriptionDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportProductDescription record) {
        return importProductDescriptionDao.insert(record);
    }

    @Override
    public ImportProductDescription selectByProductId(Long productId) {

        return importProductDescriptionDao.selectByProductId(productId);
    }

    /**
     *
     */
    public ImportProductDescription selectByPrimaryKey(Long id){
        ImportProductDescription record = new ImportProductDescription();
        record.setId(id);
        return importProductDescriptionDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImportProductDescription record) {
        return importProductDescriptionDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImportProductDescription record) {
        return importProductDescriptionDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportProductDescription> select(Page<ImportProductDescription> record){
        record.initFromNum();
        return importProductDescriptionDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportProductDescription> record){
        return importProductDescriptionDao.count(record);
    }

}