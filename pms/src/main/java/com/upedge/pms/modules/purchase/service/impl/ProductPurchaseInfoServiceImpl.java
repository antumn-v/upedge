package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.UrlUtils;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.purchase.dao.ProductPurchaseInfoDao;
import com.upedge.pms.modules.purchase.dto.OfferInventoryChangeListDTO;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ProductPurchaseInfoServiceImpl implements ProductPurchaseInfoService {

    @Autowired
    private ProductPurchaseInfoDao productPurchaseInfoDao;

    @Autowired
    ProductService productService;



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

    @Override
    public void syncPurchaseInventory(List<OfferInventoryChangeListDTO> offerInventoryChangeListDTOS) {
        for (OfferInventoryChangeListDTO offerInventoryChangeListDTO : offerInventoryChangeListDTOS) {
            productPurchaseInfoDao.updateInventory(offerInventoryChangeListDTO.getSkuId(),offerInventoryChangeListDTO.getOfferId(),offerInventoryChangeListDTO.getSkuOnSale());
        }
    }

    @Override
    public List<ProductPurchaseInfo> selectByPurchaseSkus(Set<String> purchaseSkus) {
        if (purchaseSkus == null || purchaseSkus.size() == 0){
            return new ArrayList<>();
        }
        return productPurchaseInfoDao.selectByPurchaseSkus(purchaseSkus);
    }

    @Override
    public List<ProductPurchaseInfo> selectByPurchaseLink(String purchaseLink) {
        if (StringUtils.isBlank(purchaseLink)){
            return new ArrayList<>();
        }
        return productPurchaseInfoDao.selectByPurchaseLink(purchaseLink);
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
        record.setPageSize(-1);
        ProductPurchaseInfo productPurchaseInfo = record.getT();
        if (productPurchaseInfo != null
        && productPurchaseInfo.getPurchaseLink() != null){
            String id = UrlUtils.getNameByUrl(productPurchaseInfo.getPurchaseLink());
            productService.importFrom1688Url(id,0L);
            productPurchaseInfo.setPurchaseLink(id);
            record.setT(productPurchaseInfo);
        }
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