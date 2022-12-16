package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.pms.dto.VariantPurchaseInfoDto;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.UrlUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.pms.modules.alibaba.entity.product.ProductInfo;
import com.upedge.pms.modules.alibaba.entity.product.ProductSKUInfo;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.purchase.dao.ProductPurchaseInfoDao;
import com.upedge.pms.modules.purchase.dto.OfferInventoryChangeListDTO;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.thirdparty.ali1688.entity.product.ProductSaleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    RedisTemplate redisTemplate;



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
    public void refreshAlibabaProductInventory(String productLink) {
        String key = "key:1688product:refresh:inventory:" + productLink;
        boolean b = RedisUtil.lock(redisTemplate,key,0L,10 * 60 * 1000L);
        if (!b){
            return;
        }
        try {
            AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
            ProductInfo productInfo = Ali1688Service.getProductWithoutTranslate(productLink,alibabaApiVo);
            if (productInfo == null){
                return;
            }
            List<ProductSKUInfo> skuInfos = productInfo.getSkuInfos();
            if (ListUtils.isEmpty(skuInfos)){
                return;
            }
            ProductSaleInfo productSaleInfo = productInfo.getSaleInfo();
            for (ProductSKUInfo skuInfo : skuInfos) {
                productPurchaseInfoDao.updateInventory(skuInfo.getSkuId(),productLink,skuInfo.getAmountOnSale(),productSaleInfo.getMinOrderQuantity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<VariantPurchaseInfoDto> selectByVariantIds(List<Long> variantIds) {
        if (ListUtils.isEmpty(variantIds)){
            return new ArrayList<>();
        }
        return productPurchaseInfoDao.selectByVariantIds(variantIds);
    }

    @Override
    public void syncPurchaseInventory(List<OfferInventoryChangeListDTO> offerInventoryChangeListDTOS) {
        for (OfferInventoryChangeListDTO offerInventoryChangeListDTO : offerInventoryChangeListDTOS) {
            productPurchaseInfoDao.updateInventory(offerInventoryChangeListDTO.getSkuId(),offerInventoryChangeListDTO.getOfferId(),offerInventoryChangeListDTO.getSkuOnSale(),null);
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