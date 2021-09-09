package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.utils.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductImgDao;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.service.ProductImgService;


@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    private ProductImgDao productImgDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductImg record = new ProductImg();
        record.setId(id);
        return productImgDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductImg record) {
        return productImgDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductImg record) {
        return productImgDao.insert(record);
    }

    /**
     *
     */
    public ProductImg selectByPrimaryKey(Long id){
        ProductImg record = new ProductImg();
        record.setId(id);
        return productImgDao.selectByPrimaryKey(record);
    }

    @Override
    public int insertByBatch(List<ProductImg> productImgs) {
        if (ListUtils.isEmpty(productImgs)){
            return 0;
        }
        return productImgDao.insertByBatch(productImgs);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductImg record) {
        return productImgDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductImg record) {
        return productImgDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductImg> select(Page<ProductImg> record){
        record.initFromNum();
        return productImgDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductImg> record){
        return productImgDao.count(record);
    }

}