package com.upedge.pms.modules.product.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.store.StoreType;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.dao.*;
import com.upedge.pms.modules.product.dto.StoreProductDto;
import com.upedge.pms.modules.product.entity.*;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.response.StoreProductListResponse;
import com.upedge.pms.modules.product.service.ProductAttributeService;
import com.upedge.pms.modules.product.service.ProductInfoService;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.shopify.moudles.product.controller.ShopifyProductApi;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyProduct;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyVariant;
import com.upedge.thirdparty.shoplazza.moudles.product.entity.ShoplazzaProduct;
import com.upedge.thirdparty.shoplazza.moudles.product.entity.ShoplazzaVariant;
import com.upedge.thirdparty.woocommerce.moudles.product.controller.WoocommerceProductApi;
import com.upedge.thirdparty.woocommerce.moudles.product.entity.WoocProduct;
import com.upedge.thirdparty.woocommerce.moudles.product.entity.WoocVariant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 海桐
 */
@Slf4j
@Service
public class StoreProductServiceImpl implements StoreProductService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ImportProductAttributeDao importProductAttributeDao;

    @Autowired
    ImportProductVariantDao importProductVariantDao;

    @Autowired
    StoreProductAttributeDao storeProductAttributeDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;

    @Autowired
    ProductService productService;

    @Autowired
    ProductAttributeService productAttributeService;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ProductVariantDao productVariantDao;

    @Autowired
    ProductVariantAttrDao productVariantAttrDao;


    @Override
    public List<StoreProductRelateVo> selectStoreVariantRelateDetail(Long storeProductId) {
        return storeProductVariantDao.selectStoreVariantRelateDetail(storeProductId);
    }

    @Transactional
    @Override
    public BaseResponse toNormalProduct(Long id, Session session) {
        StoreProductAttribute storeProductAttribute = storeProductAttributeDao.selectByPrimaryKey(id);
        if (null == storeProductAttribute) {
            return BaseResponse.failed("店铺产品不存在");
        }
        if (storeProductAttribute.getTransformState() == 1) {
            return BaseResponse.failed("同一产品不能重复转换");
        }
        List<StoreProductVariant> storeProductVariants = storeProductVariantDao.listUseVariantProductId(id);
        List<ProductVariant> productVariants = new ArrayList<>();
        List<ProductVariantAttr> productVariantAttrs = new ArrayList<>();
        Long newProductId = null;
        Product product = productService.selectByOriginalId(id.toString());
        if (null == product) {
            newProductId = IdGenerate.nextId();
            product = storeProductAttribute.toProduct(session);
            product.setId(newProductId);
            for (StoreProductVariant storeProductVariant : storeProductVariants) {
                Long variantId = IdGenerate.nextId();
                ProductVariant productVariant = storeProductVariant.toProductVariant(newProductId, variantId);
                productVariants.add(productVariant);
                ProductVariantAttr productVariantAttr = new ProductVariantAttr();
                productVariantAttr.setVariantId(variantId);
                productVariantAttr.setProductId(newProductId);
                productVariantAttr.setVariantAttrCname("属性");
                productVariantAttr.setVariantAttrEname("option");
                productVariantAttr.setOriginalAttrCvalue(storeProductVariant.getTitle());
                productVariantAttr.setVariantAttrEvalue(storeProductVariant.getTitle());
                productVariantAttr.setVariantAttrCvalue(storeProductVariant.getTitle());
                productVariantAttrs.add(productVariantAttr);
            }
            productService.insert(product);
            productVariantDao.insertByBatch(productVariants);
            productVariantAttrDao.insertByBatch(productVariantAttrs);
            storeProductAttributeDao.updateTransformStateById(id, 1);

            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setProductId(newProductId);
            productAttribute.setWarehouseCode(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID);
            productAttributeService.insert(productAttribute);

            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(newProductId);
            productInfoService.insert(productInfo);

            return BaseResponse.success();
        }
        storeProductAttributeDao.updateTransformStateById(id, 1);
        return BaseResponse.failed("同一产品不能重复转换");
    }

    @Override
    public List<StoreProductVariantVo> selectVariantByPlatId(PlatIdSelectStoreVariantRequest request) {
        Long storeId = request.getStoreId();

        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + storeId);
        if (null == storeVo) {
            StoreSearchRequest storeSearchRequest = new StoreSearchRequest();
            storeSearchRequest.setId(storeId);
        }
        List<StoreProductVariantVo> variantVoList = new ArrayList<>();

        List<StoreProductVariantVo> variantVos = request.getVariantVos();
        if (ListUtils.isNotEmpty(variantVos)){
            for (StoreProductVariantVo variantVo : variantVos) {
                StoreProductVariantVo variant = storeProductVariantDao.selectByPlatVariantId(storeId, variantVo.getPlatVariantId(), variantVo.getPlatProductId());
                if (null == variant) {
                    JSONObject jsonObject = null;
                    if (storeVo.getStoreType() == StoreType.SHOPIFY) {
                        jsonObject = ShopifyProductApi.getProduct(variantVo.getPlatProductId(), storeVo.getApiToken(), storeVo.getStoreName());
                        if (null == jsonObject) {
                            break;
                        }
                        ShopifyProduct shopifyProduct = jsonObject.getJSONObject("product").toJavaObject(ShopifyProduct.class);
                        saveShopifyProduct(shopifyProduct, storeVo);
                    } else if (storeVo.getStoreType() == StoreType.WOOCOMMERCE) {
                        jsonObject = WoocommerceProductApi.getProduct(storeVo.getApiToken(), storeVo.getStoreUrl(), variantVo.getPlatProductId());
                        if (null == jsonObject) {
                            break;
                        }
                        WoocProduct woocProduct = jsonObject.toJavaObject(WoocProduct.class);
                        saveWoocProduct(woocProduct, storeVo);
                    }
                    variant = storeProductVariantDao.selectByPlatVariantId(storeId, variantVo.getPlatVariantId(), variantVo.getPlatProductId());
                }
                variantVoList.add(variant);
            }
        }
        if (null != request.getStoreVariantId()){
            StoreProductVariant storeProductVariant = storeProductVariantDao.selectByPrimaryKey(request.getStoreVariantId());
            StoreProductVariantVo storeProductVariantVo = new StoreProductVariantVo();
            BeanUtils.copyProperties(storeProductVariant,storeProductVariantVo);
            variantVoList.add(storeProductVariantVo);
        }
        if (ListUtils.isNotEmpty(request.getStoreVariantIds())){
            List<StoreProductVariant> storeProductVariants = storeProductVariantDao.selectByIds(request.getStoreVariantIds());
            for (StoreProductVariant storeProductVariant : storeProductVariants) {
                StoreProductVariantVo storeProductVariantVo = new StoreProductVariantVo();
                BeanUtils.copyProperties(storeProductVariant,storeProductVariantVo);
                variantVoList.add(storeProductVariantVo);
            }
        }
        return variantVoList;
    }

    @Override
    public StoreProductListResponse storeProductList(StoreProductListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()) {
            request.setT(new StoreProductDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        List<StoreProductAttribute> attributes = storeProductAttributeDao.selectStoreProduct(request);
        Long total = storeProductAttributeDao.countStoreProduct(request);
        request.setTotal(total);
        return new StoreProductListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, attributes, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveWoocProduct(WoocProduct product, StoreVo storeVo) {

        String platProductId = product.getId();

        ImportProductAttribute importAttribute = importProductAttributeDao.selectByPlatId(storeVo.getId(), platProductId);

        StoreProductAttribute attribute = saveWoocProduct(product, storeVo, platProductId, importAttribute);

        Long storeProductId = attribute.getId();

        List<String> platVariantIds = product.getVariations();

        List<String> variantPlatIds = storeProductVariantDao.selectPlatVariantIdByProductId(storeProductId);

        if (null != platVariantIds && 0 < platVariantIds.size()) {
            JSONArray array = WoocommerceProductApi.getProductAllVariants(storeVo.getApiToken(), storeVo.getStoreUrl(), platProductId);
            List<WoocVariant> variants = array.toJavaList(WoocVariant.class);

            List<StoreProductVariant> variantInsert = new ArrayList<>();
            List<StoreProductVariant> variantUpdate = new ArrayList<>();
            Date date = new Date();
            if (null == variantPlatIds || 0 == variantPlatIds.size()) {
                /**
                 * 第一次插入变体
                 */
                variants.forEach(woocVariant -> {
                    StoreProductVariant variant = woocVariantToVariant(woocVariant, platProductId, storeProductId, date);
                    Long storeVariantId = IdGenerate.nextId();
                    variant.setId(storeVariantId);
                    variantInsert.add(variant);
                });
            } else {
                /**
                 * 插入或更新变体
                 */
                platVariantIds.add(product.getId());
                variants.forEach(woocVariant -> {
                    StoreProductVariant variant = woocVariantToVariant(woocVariant, platProductId, storeProductId, date);
                    if (variantPlatIds.contains(woocVariant.getId())) {
                        variantUpdate.add(variant);
                    } else {
                        Long storeVariantId = IdGenerate.nextId();
                        variant.setId(storeVariantId);
                        variantInsert.add(variant);
                    }
                });
            }

            if (0 < variantInsert.size()) {
                storeProductVariantDao.insertByBatch(variantInsert);
            }

            if (0 < variantUpdate.size()) {
                storeProductVariantDao.updateByBatch(variantUpdate);
            }
            storeProductVariantDao.markStoreVariantAsRemovedByPlatId(storeProductId, platVariantIds);
        } else {
            /**
             * 无变体
             */
            platVariantIds = new ArrayList<>();
            platVariantIds.add(platProductId);
            storeProductVariantDao.markStoreVariantAsRemovedByPlatId(storeProductId, platVariantIds);
        }
        saveProductRelate(attribute, importAttribute);
        return attribute.getId();
    }


    /**
     * 保存更新shopify产品
     *
     * @param product
     * @param storeVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveShopifyProduct(ShopifyProduct product, StoreVo storeVo) {
        String platProductId = product.getId();
        String key = RedisKey.STRING_STORE_PLAT_PRODUCT + storeVo.getId() + ":" + platProductId;
        boolean b = RedisUtil.lock(redisTemplate, key, 5L, 5 * 1000L);
        if (!b) {
            return null;
        }
        ImportProductAttribute importAttribute = importProductAttributeDao.selectByPlatId(storeVo.getId(), platProductId);
        StoreProductAttribute attribute = saveShopifyProductAttribute(product, storeVo, importAttribute);
        Long storeProductId = attribute.getId();
        String mainImage = null;
        if (product.getImage() != null) {
            mainImage = product.getImage().getSrc();
        }
        //已保存的店铺变体
        List<String> variantPlatIds = storeProductVariantDao.selectPlatVariantIdByProductId(storeProductId);
        List<ShopifyVariant> variants = product.getVariants();
        List<ShopifyImage> images = product.getImages();
        HashMap<String, String> imageMap = new HashMap<>();
        if (ListUtils.isNotEmpty(images)) {
            images.forEach(image -> {
                imageMap.put(image.getId(), image.getSrc());
            });
        }
        List<StoreProductVariant> insertVariants = new ArrayList<>();
        Date date = new Date();
        TreeSet<BigDecimal> variantPrices = new TreeSet<>();
        List<String> platVariantIds = new ArrayList<>();
        List<StoreProductVariant> updateVariants = new ArrayList<>();
        List<String> newPlatVariantIds = new ArrayList<>();
        for (ShopifyVariant variant : variants) {
            newPlatVariantIds.add(variant.getId());
            variantPrices.add(variant.getPrice());
            StoreProductVariant storeVariant = shopifyVariantToStoreVariant(variant, date, storeProductId, platProductId);
            if (null != variant.getImage_id()) {
                storeVariant.setImage(imageMap.get(variant.getImage_id()));
            }
            //没有图片的变体用主图图片
            if (StringUtils.isBlank(storeVariant.getImage())) {
                storeVariant.setImage(mainImage);
            }
            if (variantPlatIds.contains(variant.getId())) {
                updateVariants.add(storeVariant);
            } else {
                Long storeVariantId = IdGenerate.nextId();
                storeVariant.setId(storeVariantId);
                storeVariant.setSplitType(0);
                insertVariants.add(storeVariant);
            }
            platVariantIds.add(variant.getId());
        }

        if (insertVariants.size() > 0) {
            storeProductVariantDao.insertByBatch(insertVariants);
        }
//        if (updateVariants.size() > 0) {
//            storeProductVariantDao.updateByBatch(updateVariants);
//        }
        platVariantIds.removeAll(newPlatVariantIds);
        if(ListUtils.isNotEmpty(platVariantIds)){
            storeProductVariantDao.markStoreVariantAsRemovedByPlatId(storeProductId, platVariantIds);
        }


        if (importAttribute != null) {
            storeProductVariantDao.updateAdminVariantIdByImportId(importAttribute.getId(), storeProductId);
        }
//        attribute = new StoreProductAttribute();
//        attribute.setId(storeProductId);
//        if (variantPrices.size() > 1) {
//            variantPrices.descendingSet();
//            attribute.setPrice(variantPrices.first() + "~" + variantPrices.last());
//        } else {
//            attribute.setPrice(variantPrices.last() + "");
//        }
//        storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
        RedisUtil.unLock(redisTemplate, key);
        return attribute.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveShoplazzaProduct(ShoplazzaProduct product, StoreVo storeVo) {
        String platProductId = product.getId();
        String key = RedisKey.STRING_STORE_PLAT_PRODUCT + storeVo.getId() + ":" + platProductId;
        boolean b = RedisUtil.lock(redisTemplate, key, 5L, 20 * 1000L);
        if (!b) {
            return null;
        }
        ImportProductAttribute importAttribute = importProductAttributeDao.selectByPlatId(storeVo.getId(), platProductId);
        StoreProductAttribute attribute = saveShoplazzaProductAttribute(product, storeVo, importAttribute);
        Long storeProductId = attribute.getId();
        //已保存的店铺变体
        List<String> variantPlatIds = storeProductVariantDao.selectPlatVariantIdByProductId(storeProductId);
        List<ShoplazzaVariant> variants = product.getVariants();
        List<StoreProductVariant> insertVariants = new ArrayList<>();
        Date date = new Date();
        TreeSet<BigDecimal> variantPrices = new TreeSet<>();
        if (null == variantPlatIds || 0 == variantPlatIds.size()) {
            variants.forEach(variant -> {
                Long storeVariantId = IdGenerate.nextId();
                StoreProductVariant storeVariant = shoplazzaVariantToStoreVariant(variant, date, storeProductId, platProductId);
                variantPrices.add(variant.getPrice());
                storeVariant.setId(storeVariantId);
                insertVariants.add(storeVariant);
            });
            storeProductVariantDao.insertByBatch(insertVariants);
        } else {
            List<String> platVariantIds = new ArrayList<>();
            List<StoreProductVariant> updateVariants = new ArrayList<>();
            variants.forEach(variant -> {
                variantPrices.add(variant.getPrice());
                if (variantPlatIds.contains(variant.getId())) {
                    StoreProductVariant storeVariant = shoplazzaVariantToStoreVariant(variant, date, storeProductId, platProductId);
                    updateVariants.add(storeVariant);
                } else {
                    Long storeVariantId = IdGenerate.nextId();
                    StoreProductVariant storeVariant = shoplazzaVariantToStoreVariant(variant, date, storeProductId, platProductId);
                    storeVariant.setId(storeVariantId);
                    insertVariants.add(storeVariant);
                }
                platVariantIds.add(variant.getId());
            });
            if (insertVariants.size() > 0) {
                storeProductVariantDao.insertByBatch(insertVariants);
            }
            if (updateVariants.size() > 0) {
                storeProductVariantDao.updateByBatch(updateVariants);
            }
            storeProductVariantDao.markStoreVariantAsRemovedByPlatId(storeProductId, platVariantIds);
        }
        saveProductRelate(attribute, importAttribute);
        attribute = new StoreProductAttribute();
        attribute.setId(storeProductId);
        if (variantPrices.size() > 1) {
            variantPrices.descendingSet();
            attribute.setPrice(variantPrices.first() + "~" + variantPrices.last());
        } else {
            attribute.setPrice(variantPrices.last() + "");
        }
        storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
//        RedisUtil.unLock(redisTemplate, key);
        return attribute.getId();
    }

    @Override
    public void storeProductDeleteByStore(String platProductId, Long storeId) {
        StoreProductAttribute storeProductAttribute = storeProductAttributeDao.selectStoreProductByPlatId(storeId, platProductId);
        if (null == storeProductAttribute) {
            return;
        }
        storeProductAttribute.setState(0);
        storeProductAttribute.setUpdateAt(new Date());
        storeProductAttributeDao.updateByPrimaryKey(storeProductAttribute);
    }

    @Override
    public BaseResponse storeProductListCount(StoreProductListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        if (null == request.getT()) {
            request.setT(new StoreProductDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            List<Long> orgIds = session.getOrgIds();
            if (ListUtils.isEmpty(orgIds)) {
                request.setTotal(0L);
                return BaseResponse.success(0, request);
            }
            request.getT().setOrgIds(orgIds);
        }
        Long total = storeProductAttributeDao.countStoreProduct(request);

        request.setTotal(total);

        return BaseResponse.success(total, request);
    }

    public StoreProductAttribute saveShoplazzaProductAttribute(ShoplazzaProduct product, StoreVo storeVo, ImportProductAttribute importAttribute) {
        String platProductId = product.getId();
        StoreProductAttribute attribute = storeProductAttributeDao.selectStoreProductByPlatId(storeVo.getId(), platProductId);

        if (null == attribute) {
            attribute = new StoreProductAttribute();
            attribute.setId(IdGenerate.nextId());

            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }
            if (null != product.getImage()) {
                attribute.setImage(product.getImage().getSrc());
            }
            attribute.setUpdateAt(product.getUpdated_at());
            attribute.setTitle(product.getTitle());
            attribute.setHandle("https://" + storeVo.getStoreUrl() + "/products/" + product.getHandle());
            attribute.setCustomerId(storeVo.getCustomerId());
            attribute.setVendor(product.getVendor());
            attribute.setRelateState(0);
            attribute.setPlatProductId(platProductId);
            attribute.setStoreId(storeVo.getId());
            attribute.setOrgId(storeVo.getOrgId());
            attribute.setOrgPath(storeVo.getOrgPath());
            attribute.setCreateAt(product.getCreated_at());
            attribute.setState(0);
            attribute.setStoreName(storeVo.getStoreName());
            attribute.setImportTime(new Date());
            storeProductAttributeDao.insert(attribute);
        } else {

            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }
            if (null != product.getImage()) {
                attribute.setImage(product.getImage().getSrc());
            }
            attribute.setUpdateAt(product.getUpdated_at());
            attribute.setTitle(product.getTitle());
            attribute.setHandle("https://" + storeVo.getStoreUrl() + "/products/" + product.getHandle());
            attribute.setVendor(product.getVendor());
            storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
        }
        return attribute;
    }

    public StoreProductAttribute saveShopifyProductAttribute(ShopifyProduct product, StoreVo storeVo, ImportProductAttribute importAttribute) {
        String platProductId = product.getId();
        StoreProductAttribute attribute = storeProductAttributeDao.selectStoreProductByPlatId(storeVo.getId(), platProductId);
        if (null == attribute) {
            attribute = new StoreProductAttribute();
            attribute.setId(IdGenerate.nextId());
            attribute.setManagerCode((String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(storeVo.getCustomerId())));
            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }
            if (null != product.getImage()) {
                attribute.setImage(product.getImage().getSrc());
            }
            attribute.setUpdateAt(product.getUpdated_at());
            attribute.setTitle(product.getTitle());
            attribute.setHandle("https://" + storeVo.getStoreUrl() + "/products/" + product.getHandle());
            attribute.setCustomerId(storeVo.getCustomerId());
            attribute.setVendor(product.getVendor());
            attribute.setRelateState(0);
            attribute.setPlatProductId(platProductId);
            attribute.setStoreId(storeVo.getId());
            attribute.setOrgId(storeVo.getOrgId());
            attribute.setOrgPath(storeVo.getOrgPath());
            attribute.setCreateAt(product.getCreated_at());
            attribute.setState(1);
            attribute.setTransformState(0);
            attribute.setStoreName(storeVo.getStoreName());
            attribute.setImportTime(new Date());
            storeProductAttributeDao.insert(attribute);
        } else {
            attribute.setManagerCode((String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(storeVo.getCustomerId())));
            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }
            if (null != product.getImage()) {
                attribute.setImage(product.getImage().getSrc());
            }
            attribute.setUpdateAt(product.getUpdated_at());
            attribute.setTitle(product.getTitle());
            attribute.setHandle("https://" + storeVo.getStoreUrl() + "/products/" + product.getHandle());
            attribute.setVendor(product.getVendor());
            storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
        }
        return attribute;
    }

    public StoreProductVariant shopifyVariantToStoreVariant(ShopifyVariant variant, Date importTime, Long storeProductId, String platProductId) {
        StoreProductVariant storeVariant = new StoreProductVariant();
        storeVariant.setImportTime(importTime);
        storeVariant.setProductId(storeProductId);
        storeVariant.setPlatProductId(platProductId);
        storeVariant.setPlatVariantId(variant.getId());
        storeVariant.setPrice(variant.getPrice());
        storeVariant.setState(1);
        storeVariant.setTitle(variant.getTitle());
        storeVariant.setSku(variant.getSku());
        storeVariant.setParentVariantId(0L);
        return storeVariant;
    }

    public StoreProductVariant shoplazzaVariantToStoreVariant(ShoplazzaVariant variant, Date importTime, Long storeProductId, String platProductId) {
        StoreProductVariant storeVariant = new StoreProductVariant();
        storeVariant.setImportTime(importTime);
        storeVariant.setProductId(storeProductId);
        storeVariant.setPlatProductId(platProductId);
        storeVariant.setPlatVariantId(variant.getId());
        storeVariant.setPrice(variant.getPrice());
        storeVariant.setState(1);
        storeVariant.setTitle(variant.getTitle());
        storeVariant.setSku(variant.getSku());
        if (null != variant.getImage() && null != variant.getImage().getSrc()) {
            storeVariant.setImage(variant.getImage().getSrc());
        }
        return storeVariant;
    }


    public StoreProductAttribute saveWoocProduct(WoocProduct product, StoreVo storeVo, String platProductId, ImportProductAttribute importAttribute) {

        StoreProductAttribute attribute = storeProductAttributeDao.selectStoreProductByPlatId(storeVo.getId(), platProductId);

        if (null == attribute) {
            attribute = new StoreProductAttribute();
            attribute.setId(IdGenerate.nextId());

            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }

            if (null != product.getImages() && 0 < product.getImages().size()) {
                attribute.setImage(product.getImages().get(0).getSrc());
            }
            attribute.setUpdateAt(product.getDate_modified());
            attribute.setTitle(product.getName());
            attribute.setHandle(product.getPermalink());
            attribute.setPrice(product.getPrice());
            attribute.setCustomerId(storeVo.getCustomerId());
            attribute.setVendor(storeVo.getStoreName());
            attribute.setRelateState(0);
            attribute.setPlatProductId(platProductId);
            attribute.setStoreId(storeVo.getId());
            attribute.setOrgId(storeVo.getOrgId());
            attribute.setOrgPath(storeVo.getOrgPath());
            attribute.setCreateAt(product.getDate_created());
            attribute.setState(0);
            attribute.setStoreName(storeVo.getStoreName());
            attribute.setImportTime(new Date());
            storeProductAttributeDao.insert(attribute);

            StoreProductVariant variant = woocProductToVariant(attribute);
            variant.setId(IdGenerate.nextId());
            variant.setSku(product.getSku());
            variant.setImportTime(attribute.getImportTime());
            storeProductVariantDao.insert(variant);

        } else {
            if (null == importAttribute) {
                attribute.setSource(2);
            } else {
                attribute.setSource(importAttribute.getSource());
                attribute.setAdminProductId(importAttribute.getSourceProductId());
            }

            if (null != product.getImages() && 0 < product.getImages().size()) {
                attribute.setImage(product.getImages().get(0).getSrc());
            }
            attribute.setUpdateAt(product.getDate_modified());
            attribute.setTitle(product.getName());
            attribute.setHandle(product.getPermalink());
            attribute.setPrice(product.getPrice());
            storeProductAttributeDao.updateByPrimaryKeySelective(attribute);

            StoreProductVariant variant = woocProductToVariant(attribute);
            variant.setSku(product.getSku());
            storeProductVariantDao.updateByPlatVariantId(variant);

        }
        return attribute;
    }

    public StoreProductVariant woocProductToVariant(StoreProductAttribute product) {
        StoreProductVariant variant = new StoreProductVariant();
        variant.setPlatVariantId(product.getPlatProductId());
        variant.setPlatProductId(product.getPlatProductId());
        variant.setTitle(product.getTitle());
        variant.setImage(product.getImage());
        variant.setPrice(new BigDecimal(product.getPrice()));
        variant.setProductId(product.getId());
        variant.setState(1);
        return variant;
    }

    public StoreProductVariant woocVariantToVariant(WoocVariant woocVariant,
                                                    String platProductId,
                                                    Long storeProductId,
                                                    Date importTime) {
        StoreProductVariant variant = new StoreProductVariant();
        if (null != woocVariant.getImage()) {
            variant.setImage(woocVariant.getImage().getSrc());
        }
        StringBuffer title = new StringBuffer();
        woocVariant.getAttributes().forEach(attr -> {
            title.append("/").append(attr.getOption());
        });
        variant.setTitle(title.toString().replaceFirst("/", ""));
        variant.setSku(woocVariant.getSku());
        variant.setPrice(woocVariant.getPrice());
        variant.setPlatProductId(platProductId);
        variant.setPlatVariantId(woocVariant.getId());
        variant.setProductId(storeProductId);
        variant.setImportTime(importTime);
        variant.setState(1);

        return variant;
    }


    public void saveProductRelate(StoreProductAttribute attribute, ImportProductAttribute importProductAttribute) {

//        if (0 != attribute.getSource()) {
//            return;
//        }
//
//        if (null == importProductAttribute) {
//            return;
//        }
//
//        Long storeProductId = attribute.getId();
//
//        List<Long> relateVariantIds = productRelateDao.selectStoreVariantIdByStoreProductId(storeProductId);
//
//        Date date = new Date();
//        List<ProductRelate> relates = null;
//        if (null == relateVariantIds || 0 == relateVariantIds.size()) {
//            relates = storeProductVariantDao.selectRelateDetailByProductId(storeProductId, importProductAttribute.getId(), null);
//        } else {
//            List<Long> storeVariantIds = storeProductVariantDao.selectIdByProductId(storeProductId);
//            storeVariantIds.removeAll(relateVariantIds);
//            if (0 == storeVariantIds.size()) {
//                return;
//            }
//            relates = storeProductVariantDao.selectRelateDetailByProductId(storeProductId, importProductAttribute.getId(), storeVariantIds);
//        }
//        attribute = new StoreProductAttribute();
//        attribute.setId(storeProductId);
//        if (null == relates || 0 == relates.size()) {
//            attribute.setRelateState(0);
//            storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
//            return;
//        }
//        relates.forEach(productRelate -> {
//            productRelate.setAdminProductId(importProductAttribute.getSourceProductId());
//            productRelate.setCreateTime(date);
//        });
//        productRelateDao.insertByBatch(relates);
//
//        List<StoreProductVariant> variants = storeProductVariantDao.selectUnRelateVariantByProduct(storeProductId);
//        if (null == variants || 0 == variants.size()) {
//            attribute.setRelateState(1);
//        } else {
//            attribute.setRelateState(2);
//        }
//        storeProductAttributeDao.updateByPrimaryKeySelective(attribute);
    }
}
