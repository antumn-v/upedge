package com.upedge.pms.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.ship.request.ShipMethodPriceRequest;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.*;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.request.AppVariantShipsRequest;
import com.upedge.pms.modules.product.request.MarketPlaceListRequest;
import com.upedge.pms.modules.product.request.PrivateProductListRequest;
import com.upedge.pms.modules.product.response.AppVariantShipsResponse;
import com.upedge.pms.modules.product.response.MarketPlaceListResponse;
import com.upedge.pms.modules.product.response.ProductImgListResponse;
import com.upedge.pms.modules.product.service.AppProductService;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.vo.AppProductVariantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 海桐
 */
@Service
public class AppProductServiceImpl implements AppProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductVariantDao productVariantDao;

    @Autowired
    AppProductVariantDao appProductVariantDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    ProductImgDao productImgDao;

    @Autowired
    ImportProductAttributeDao importProductAttributeDao;

    @Autowired
    ProductService productService;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public MarketPlaceListResponse marketPlaceList(MarketPlaceListRequest request, Session session) {
//        List<AppProductVo> productVos = productDao.selectMarketPlace(request);
//
//        if (ListUtils.isNotEmpty(productVos)) {
//            List<String> sourceProductIds = importProductAttributeDao.selectImportedSourceProductIds(session.getCustomerId(), productVos);
//            boolean b = ListUtils.isNotEmpty(sourceProductIds);
//            productVos.forEach(appProductVo -> {
//                appProductVo.initPriceRange();
//                if (b) {
//                    if (sourceProductIds.contains(String.valueOf(appProductVo.getId()))) {
//                        appProductVo.setImportState(1);
//                    } else {
//                        appProductVo.setImportState(0);
//                    }
//                }else {
//                    appProductVo.setImportState(0);
//                }
//            });
//        }
//        Long total = productDao.countMarketPlace(request);
//        request.setTotal(total);
//        return new MarketPlaceListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, productVos, request);
        return null;
    }

    @Override
    public MarketPlaceListResponse customerPrivateProduct(PrivateProductListRequest request) {
//        List<AppProductVo> productVos = productDao.selectCustomerPrivateProduct(request);
//        if (ListUtils.isNotEmpty(productVos)) {
//            List<String> sourceProductIds = importProductAttributeDao.selectImportedSourceProductIds(request.getCustomerId(), productVos);
//            boolean b = ListUtils.isNotEmpty(sourceProductIds);
//            productVos.forEach(appProductVo -> {
//                appProductVo.initPriceRange();
//                if (b) {
//                    if (sourceProductIds.contains(String.valueOf(appProductVo.getId()))) {
//                        appProductVo.setImportState(1);
//                    } else {
//                        appProductVo.setImportState(0);
//                    }
//                }else {
//                    appProductVo.setImportState(0);
//                }
//            });
//        }
//        Long total = productDao.countPrivateProduct(request);
//
//        request.setTotal(total);
//
//        return new MarketPlaceListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, productVos, request);
        return null;
    }

    @Override
    public List<AppProductVariantVo> productVariants(Long productId) {
        List<AppProductVariantVo> variantVos = appProductVariantDao.selectProductVariantsByProductId(productId);
        return variantVos;
    }

    @Override
    public ProductInfo selectProductInfo(Long productId) {
        return null;
    }

    @Override
    public ProductImgListResponse selectProductImages(Long productId) {
        List<ProductImg> productImgs = productImgDao.selectByProductId(productId);
        return new ProductImgListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,productImgs,null);
    }

    @Override
    public AppVariantShipsResponse variantShips(AppVariantShipsRequest request, Session session) {

        ProductVariant variant = new ProductVariant();
        variant.setId(request.getVariantId());
        variant = productVariantDao.selectByPrimaryKey(variant);
        if (null == variant) {
            return new AppVariantShipsResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>());
        }

        Product product = productDao.selectByPrimaryKey(variant.getProductId());

        String key = RedisKey.SHIPPING_TEMPLATED_METHODS + product.getShippingId();
        Set<Object> objects = redisTemplate.opsForSet().members(key);
        Set<Long> methodIds = new HashSet<>();
        if (ListUtils.isNotEmpty(objects)) {
            objects.forEach(o -> {
                methodIds.add((Long) o);
            });
        }
        ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
        searchRequest.getTemplateIds().add(product.getShippingId());
        searchRequest.setToAreaId(request.getToAreaId());
        searchRequest.setMethodIds(methodIds);
        searchRequest.setWeight(variant.getWeight().multiply(request.getQuantity()));
        searchRequest.setVolumeWeight(variant.getVolumeWeight().multiply(request.getQuantity()));

        ShipMethodSearchResponse searchResponse = tmsFeignClient.shipSearch(searchRequest);
        if (null == searchResponse
        || null == searchResponse.getData()){
            return new AppVariantShipsResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>());
        }
        List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(searchResponse.getData())).toJavaList(ShipDetail.class);
        for (ShipDetail shipDetail : shipDetails) {
            shipDetail.setPrice(shipDetail.getPrice().add(shipDetail.getServiceFee()));
        }
        return new AppVariantShipsResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shipDetails);
    }

    @Override
    public BaseResponse productVariantsShipList(ProductVariantShipsRequest request) {
        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        Set<Long> productIds = new HashSet<>();
        List<Long> shipTemplateIds = null;
        List<VariantDetail> variants = request.getVariantDetails();
        for (VariantDetail variant : variants) {
            weight = weight.add(variant.getWeight());
            volume = volume.add(variant.getVolume());
            productIds.add(variant.getProductId());
        }
        shipTemplateIds = productDao.selectShippingIdByIds(productIds);
        if (ListUtils.isEmpty(shipTemplateIds)) {
            return BaseResponse.failed();
        }

        Collection<String> strings = new ArrayList<>();
        for (int i = 0; i < shipTemplateIds.size(); i++) {
            strings.add(RedisKey.SHIPPING_TEMPLATED_METHODS + shipTemplateIds.get(i));
        }

        Set<Object> shipMethodIds = redisTemplate.opsForSet().union(strings);
        if (ListUtils.isEmpty(shipMethodIds)) {
            return BaseResponse.failed();
        }
        Set<Long> methodIds = new HashSet<>();
        shipMethodIds.forEach(shipMethodId -> {
            methodIds.add((Long) shipMethodId);
        });

        ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
        searchRequest.setTemplateIds(shipTemplateIds);
        searchRequest.setToAreaId(request.getAreaId());
        searchRequest.setWeight(weight);
        searchRequest.setVolumeWeight(volume);
        searchRequest.setMethodIds(methodIds);
        if (null != request.getShipMethodId()) {
            ShipMethodPriceRequest shipMethodPriceRequest = new ShipMethodPriceRequest();
            BeanUtils.copyProperties(searchRequest, shipMethodPriceRequest);
            shipMethodPriceRequest.setShipMethodId(request.getShipMethodId());
            return tmsFeignClient.shipMethodPrice(shipMethodPriceRequest);
        } else {
            ShipMethodSearchResponse searchResponse = tmsFeignClient.shipSearch(searchRequest);

            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, searchResponse.getData(), request);
        }

    }
}
