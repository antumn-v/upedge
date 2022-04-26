package com.upedge.sms.modules.photography.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.photography.dao.ProductPhotographyOrderItemDao;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductPhotographyOrderItemServiceImpl implements ProductPhotographyOrderItemService {

    @Autowired
    private ProductPhotographyOrderItemDao productPhotographyOrderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductPhotographyOrderItem record = new ProductPhotographyOrderItem();
        record.setId(id);
        return productPhotographyOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductPhotographyOrderItem record) {
        return productPhotographyOrderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<ProductPhotographyOrderItem> productPhotographyOrderItems) {
        if (ListUtils.isNotEmpty(productPhotographyOrderItems)){
            return productPhotographyOrderItemDao.insertByBatch(productPhotographyOrderItems);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductPhotographyOrderItem record) {
        return productPhotographyOrderItemDao.insert(record);
    }

    @Override
    public List<ProductPhotographyOrderItem> selectByOrderId(Long orderId) {
        return productPhotographyOrderItemDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public ProductPhotographyOrderItem selectByPrimaryKey(Long id){
        ProductPhotographyOrderItem record = new ProductPhotographyOrderItem();
        record.setId(id);
        return productPhotographyOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductPhotographyOrderItem record) {
        return productPhotographyOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductPhotographyOrderItem record) {
        return productPhotographyOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductPhotographyOrderItem> select(Page<ProductPhotographyOrderItem> record){
        record.initFromNum();
        return productPhotographyOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductPhotographyOrderItem> record){
        return productPhotographyOrderItemDao.count(record);
    }

}