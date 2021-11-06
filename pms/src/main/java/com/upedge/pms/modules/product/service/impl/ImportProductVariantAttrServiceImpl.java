package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.ImportProductVariantAttrDao;
import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import com.upedge.pms.modules.product.service.ImportProductVariantAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ImportProductVariantAttrServiceImpl implements ImportProductVariantAttrService {

    @Autowired
    private ImportProductVariantAttrDao importProductVariantAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ImportProductVariantAttr record = new ImportProductVariantAttr();
        record.setId(id);
        return importProductVariantAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportProductVariantAttr record) {
        return importProductVariantAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportProductVariantAttr record) {
        return importProductVariantAttrDao.insert(record);
    }

    /**
     *
     */
    public ImportProductVariantAttr selectByPrimaryKey(Long id){
        ImportProductVariantAttr record = new ImportProductVariantAttr();
        record.setId(id);
        return importProductVariantAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImportProductVariantAttr record) {
        return importProductVariantAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImportProductVariantAttr record) {
        return importProductVariantAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportProductVariantAttr> select(Page<ImportProductVariantAttr> record){
        record.initFromNum();
        return importProductVariantAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportProductVariantAttr> record){
        return importProductVariantAttrDao.count(record);
    }

}