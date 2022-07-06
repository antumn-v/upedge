package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.ProductPurchaseInfoDao;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductPurchaseInfoServiceImpl implements ProductPurchaseInfoService {

    @Autowired
    private ProductPurchaseInfoDao productPurchaseInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String purchaseSku) {
        ProductPurchaseInfo record = new ProductPurchaseInfo();
        record.setPurchaseSku(purchaseSku);
        return productPurchaseInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductPurchaseInfo record) {
        return productPurchaseInfoDao.insert(record);
    }

    @Override
    public int insertByBatch(List<ProductPurchaseInfo> records) {
        if (ListUtils.isNotEmpty(records)){
            return productPurchaseInfoDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductPurchaseInfo record) {
        return productPurchaseInfoDao.insert(record);
    }

    /**
     *
     */
    public ProductPurchaseInfo selectByPrimaryKey(String purchaseSku){
        ProductPurchaseInfo record = new ProductPurchaseInfo();
        record.setPurchaseSku(purchaseSku);
        return productPurchaseInfoDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductPurchaseInfo record) {
        return productPurchaseInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductPurchaseInfo record) {
        return productPurchaseInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductPurchaseInfo> select(Page<ProductPurchaseInfo> record){
        record.initFromNum();
        return productPurchaseInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductPurchaseInfo> record){
        return productPurchaseInfoDao.count(record);
    }

}