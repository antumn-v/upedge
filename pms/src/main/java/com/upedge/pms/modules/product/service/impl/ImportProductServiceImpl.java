package com.upedge.pms.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.dao.*;
import com.upedge.pms.modules.product.entity.*;
import com.upedge.pms.modules.product.request.ImportAddAppProductRequest;
import com.upedge.pms.modules.product.request.ImportListRemoveRequest;
import com.upedge.pms.modules.product.request.ImportVariantBatchUpdateRequest;
import com.upedge.pms.modules.product.request.ImportVariantUpdateStateRequest;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.AppProductVariantAttrVo;
import com.upedge.pms.modules.product.vo.AppProductVariantVo;
import com.upedge.pms.modules.product.vo.ImportVariantVo;
import com.upedge.thirdparty.shopify.moudles.inventory.api.ShopifyInventoryApi;
import com.upedge.thirdparty.shopify.moudles.inventory.entity.InventoryItem;
import com.upedge.thirdparty.shopify.moudles.product.controller.ShopifyProductApi;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyProduct;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyVariant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author 海桐
 */
@Slf4j
@Service
public class ImportProductServiceImpl implements ImportProductService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;



    @Autowired
    ProductService productService;

    @Autowired
    AppProductVariantDao appProductVariantDao;

    @Autowired
    ProductImgService productImgService;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ImportProductAttributeDao importProductAttributeDao;

    @Autowired
    ImportProductVariantAttrDao importProductVariantAttrDao;

    @Autowired
    ImportProductVariantDao importProductVariantDao;

    @Autowired
    ImportProductDescriptionDao importProductDescriptionDao;

    @Autowired
    ImportProductImageDao importProductImageDao;

    @Autowired
    ImportPriceRuleService importPriceRuleService;

    @Autowired
    AppProductService appProductService;

    @Autowired
    ImportPublishedRecordService importPublishedRecordService;


    @Override
    public ImportAeProductResponse importAeProduct(String url, Session session, String aeToken) {
        return null;
    }

    @Transactional
    @Override
    public ImportAddAppProductResponse addAppProduct(ImportAddAppProductRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        Long productId = Long.parseLong(request.getProductId());

        Product product = productService.selectByPrimaryKey(productId);

        ImportProductAttribute attribute = request.toImportAttr(product);
        attribute.setUserId(session.getId());
        attribute.setCustomerId(session.getCustomerId());
        importProductAttributeDao.insert(attribute);

        Long id = attribute.getId();

        List<AppProductVariantVo> variantVos = appProductVariantDao.selectProductVariantsByProductId(productId);

        List<ImportProductVariant> variants = new ArrayList<>();

        Map<BigDecimal, BigDecimal> priceMap = new HashMap<>();

        Map<BigDecimal, BigDecimal> comparePriceMap = new HashMap<>();

        List<ImportProductVariantAttr> variantAttrs = new ArrayList<>();

        variantVos.forEach(appProductVariantVo -> {
            Long variantId = IdGenerate.nextId();
            ImportProductVariant variant = appProductVariantVo.toImportVariant(variantId);
            BigDecimal cost = appProductVariantVo.getUsdPrice();
            variant.setCost(cost);
            if (priceMap.containsKey(cost)) {
                variant.setPrice(priceMap.get(cost));
                variant.setComparePrice(comparePriceMap.get(cost));
            } else {
//                BigDecimal price = importPriceRuleService.resetPrice(customerId, cost);
//                BigDecimal comparePrice = importPriceRuleService.resetComparePrice(customerId, cost);
                BigDecimal price = cost.multiply(new BigDecimal("2"));
                BigDecimal comparePrice = cost.multiply(new BigDecimal("2"));
                variant.setPrice(price);
                variant.setComparePrice(comparePrice);

                priceMap.put(cost, price);
                comparePriceMap.put(cost, comparePrice);
            }
            variant.setSourceVariantId(String.valueOf(appProductVariantVo.getId()));
            variant.setProductId(id);
            variants.add(variant);

            List<AppProductVariantAttrVo> variantAttrVos = appProductVariantVo.getAttrs();

            variantAttrVos.forEach(variantAttrVo -> {
                ImportProductVariantAttr attr = variantAttrVo.toImportVariantAttr(variantId);
                attr.setProductId(id);
                variantAttrs.add(attr);
            });
        });
        importProductVariantDao.insertByBatch(variants);
        importProductVariantAttrDao.insertByBatch(variantAttrs);


        List<ProductImg> imgs = productImgService.selectByProductId(productId);

        List<ImportProductImage> images = new ArrayList<>();

        imgs.forEach(img -> {
            ImportProductImage productImage = new ImportProductImage();
            productImage.setState(img.getState());
            productImage.setProductId(id);
            productImage.setSeq(img.getImageSeq());
            productImage.setUrl(img.getImageUrl());
            images.add(productImage);
        });
        importProductImageDao.insertByBatch(images);

        ProductInfo productInfo = productInfoService.selectByProductId(productId);

        ImportProductDescription description = new ImportProductDescription();
        description.setDescription(productInfo.getProductDesc());
        description.setProductId(id);
        importProductDescriptionDao.insert(description);

        return new ImportAddAppProductResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public ImportProductVariantListResponse selectProductVariants(Long productId) {

        List<ImportVariantVo> variantVos = importProductVariantDao.selectByProduct(productId);
        Map<BigDecimal, BigDecimal> map = new HashMap<>();
        for (ImportVariantVo variantVo : variantVos) {
            variantVo.setShipPrice(BigDecimal.ZERO);

        }


        return new ImportProductVariantListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, variantVos);
    }

    @Override
    public ImportProductDescriptionListResponse selectProductDescription(Long productId) {

        ImportProductDescription description = importProductDescriptionDao.selectByProductId(productId);

        return new ImportProductDescriptionListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, description);
    }

    @Override
    public ImportVariantBatchUpdateResponse batchUpdateVariantPrice(ImportVariantBatchUpdateRequest request) {
        importProductVariantDao.updateVariantPriceByProduct(request);
        return new ImportVariantBatchUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public VariantUpdateStateResponse updateVariantState(ImportVariantUpdateStateRequest request) {
        ImportProductAttribute attribute = importProductAttributeDao.selectByPrimaryKey(request.getProductId());
        if(attribute.getState() == 0 && ListUtils.isNotEmpty(request.getVariantIds())){
            importProductVariantDao.updateStateByIds(request.getVariantIds(),request.getState());
            return new VariantUpdateStateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        }

        return new VariantUpdateStateResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
    }

    @Transactional
    @Override
    public ImportProductAttributeDelResponse importProductRemove(ImportListRemoveRequest request) {
        ImportProductAttribute attribute = new ImportProductAttribute();
        attribute.setState(-1);
        if (request.getId() != null){
           attribute.setId(request.getId());
            importProductAttributeDao.updateByPrimaryKeySelective(attribute);
        }
        if (request.getProductId() != null){
            Session session = UserUtil.getSession(redisTemplate);
            importProductAttributeDao.updateStateByProductId(request.getProductId(),session.getCustomerId(),-1);
        }

        if (ListUtils.isNotEmpty(request.getIds())){
            importProductAttributeDao.updateStateByIds(request.getIds(),-1);
        }

        return new ImportProductAttributeDelResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Async
    @Transactional
    @Override
    public Boolean uploadProductToShopify(StoreVo storeVo, Long productId) {
        ImportProductAttribute attribute = importProductAttributeDao.selectByPrimaryKey(productId);
        if (2 != attribute.getState()) {
            return false;
        }
        List<ImportVariantVo> variantVos = importProductVariantDao.selectForUploadStore(productId);
        if(ListUtils.isEmpty(variantVos)){
            return false;
        }
        if (variantVos.size() > 100) {
            variantVos = variantVos.subList(0, 100);
        }
        if (variantVos.size() > 100) {
            variantVos = variantVos.subList(0, 100);
        }

        String storeUrl = storeVo.getStoreName();
        String token = storeVo.getApiToken();

        JSONObject jsonObject = new JSONObject();
        ImportProductDescription description = importProductDescriptionDao.selectByProductId(productId);
        ShopifyProduct product = new ShopifyProduct();
        product.setTitle(attribute.getTitle());
        product.setTags(attribute.getTags());
        product.setProduct_type(attribute.getType());
        product.setBody_html(description.getDescription());

        List<ShopifyVariant> variants = new ArrayList<>();
        Map<String, BigDecimal> skuCost = new HashMap<>();
        product.setOptions(importProductVariantAttrDao.selectForShopifyOption(productId));

        variantVos.forEach(vo -> {
            ShopifyVariant variant = new ShopifyVariant();
            variant.setCompare_at_price(vo.getComparePrice());
            variant.setPrice(vo.getPrice());
            variant.setSku(vo.getSku());
            variant.setWeight(vo.getWeight());
            variant.setInventory_quantity(vo.getInventory());
            skuCost.put(vo.getSku(),vo.getCost());
            List<ImportProductVariantAttr> attrs = vo.getAttrs();

            for (int j = 0; j < attrs.size(); j++) {
                if (0 == j) {
                    variant.setOption1(attrs.get(j).getAttrValue());
                }
                if (1 == j) {
                    variant.setOption2(attrs.get(j).getAttrValue());
                }
                if (2 == j) {
                    variant.setOption3(attrs.get(j).getAttrValue());
                    break;
                }
            }
            variants.add(variant);
        });

        product.setVariants(variants);

        jsonObject.put("product", JSON.toJSON(product));

        jsonObject = ShopifyProductApi.postProduct(jsonObject, token, storeVo.getStoreName());

        if (null != jsonObject) {
            product = jsonObject.getJSONObject("product").toJavaObject(ShopifyProduct.class);

            String platProductId = product.getId();

            List<ShopifyVariant> variantList = product.getVariants();
            List<List<ShopifyVariant>> lists = groupList(variantList,5);
            for (List<ShopifyVariant> list : lists) {
                importProductVariantDao.updatePlatIdBySku(list, productId);
            }

            LinkedList<ShopifyImage> images = importProductImageDao.selectForShopifyImage(productId);
            for (int i = 0; i < images.size(); i++) {
                if (attribute.getImage().equals(images.get(i).getSrc())) {
                    ShopifyImage image = images.get(i);
                    images.remove(i);
                    images.addFirst(image);
                    break;
                }
            }
            product = new ShopifyProduct();
            product.setId(platProductId);
            product.setImages(images);
            jsonObject.clear();
            jsonObject.put("product", JSON.toJSON(product));
            jsonObject = ShopifyProductApi.updateProduct(jsonObject, token, storeUrl, platProductId);
            attribute = new ImportProductAttribute();
            attribute.setId(productId);
            if (null != jsonObject) {
                attribute.setState(1);
                importProductAttributeDao.updateByPrimaryKeySelective(attribute);

                ImportPublishedRecord importPublishedRecord = new ImportPublishedRecord();
                importPublishedRecord.setImportProductId(productId);
                importPublishedRecord.setPlatProductId(platProductId);
                importPublishedRecord.setStroreId(storeVo.getId());
                importPublishedRecord.setPublishTime(new Date());
                importPublishedRecordService.insert(importPublishedRecord);

                variantList.forEach(shopifyVariant -> {
                    InventoryItem inventoryItem = new InventoryItem();
                    inventoryItem.setId(shopifyVariant.getInventory_item_id());
                    inventoryItem.setCost(skuCost.get(shopifyVariant.getSku()));
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("inventory_item",inventoryItem);
                    ShopifyInventoryApi.updateInventoryItem(storeUrl,token,shopifyVariant.getInventory_item_id(),objectMap);
                });

                return true;
            } else {
                ShopifyProductApi.deleteProduct(token, storeUrl, platProductId);
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean uploadProductToWoocommerce(StoreVo storeVo, Long productId) {
        return false;
    }


    public static List<List<ShopifyVariant>> groupList(List<ShopifyVariant> list, Integer toIndex) {
        List<List<ShopifyVariant>> listGroup = new ArrayList<List<ShopifyVariant>>();
        int listSize = list.size();
        //子集合的长度
        for (int i = 0; i < list.size(); i += toIndex) {
            if (i + toIndex > listSize) {
                toIndex = listSize - i;
            }
            List<ShopifyVariant> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }

}
