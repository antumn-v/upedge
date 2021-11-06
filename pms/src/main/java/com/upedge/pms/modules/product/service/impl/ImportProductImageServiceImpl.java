package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.ImportProductImageDao;
import com.upedge.pms.modules.product.entity.ImportProductImage;
import com.upedge.pms.modules.product.service.ImportProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ImportProductImageServiceImpl implements ImportProductImageService {

    @Autowired
    private ImportProductImageDao importProductImageDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ImportProductImage record = new ImportProductImage();
        record.setId(id);
        return importProductImageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportProductImage record) {
        return importProductImageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportProductImage record) {
        return importProductImageDao.insert(record);
    }

    /**
     *
     */
    public ImportProductImage selectByPrimaryKey(Long id){
        ImportProductImage record = new ImportProductImage();
        record.setId(id);
        return importProductImageDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImportProductImage record) {
        return importProductImageDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImportProductImage record) {
        return importProductImageDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportProductImage> select(Page<ImportProductImage> record){
        record.initFromNum();
        return importProductImageDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportProductImage> record){
        return importProductImageDao.count(record);
    }

}