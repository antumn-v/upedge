package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.ImportProductVariantDao;
import com.upedge.pms.modules.product.entity.ImportProductVariant;
import com.upedge.pms.modules.product.service.ImportProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ImportProductVariantServiceImpl implements ImportProductVariantService {

    @Autowired
    private ImportProductVariantDao importProductVariantDao;



    /**
     *
     */
    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        ImportProductVariant record = new ImportProductVariant();
        record.setId(id);
        return importProductVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insert(ImportProductVariant record) {
        return importProductVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insertSelective(ImportProductVariant record) {
        return importProductVariantDao.insert(record);
    }

    /**
     *
     */
    public ImportProductVariant selectByPrimaryKey(Long id){
        ImportProductVariant record = new ImportProductVariant();
        record.setId(id);
        return importProductVariantDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(ImportProductVariant record) {
        
//        ImportProductVariant variant = new ImportProductVariant();
//        variant.setId(record.getProductId());
//        if(null != record.getSku()){
//            variant.setSku(record.getSku());
//            List<ImportProductVariant> variants = importProductVariantDao.selectByEntity(variant);
//            if(null != variants){
//                record.setSku(null);
//            }
//        }
//        if(null != record.getState()){
//            Integer state = record.getState();
//            ImportProductVariant variant = new ImportProductVariant();
//            variant.setProductId(record.getProductId());
//            variant.setState(state * -1);
//            Long count = importProductVariantDao.countByEntity(variant);
//        }
        return importProductVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    @Override
    public int updateByPrimaryKey(ImportProductVariant record) {
        return importProductVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportProductVariant> select(Page<ImportProductVariant> record){
        record.initFromNum();
        return importProductVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportProductVariant> record){
        return importProductVariantDao.count(record);
    }

}