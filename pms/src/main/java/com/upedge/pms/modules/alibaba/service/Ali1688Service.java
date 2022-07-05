package com.upedge.pms.modules.alibaba.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.utils.GetImgUrlList;
import com.upedge.common.utils.OkHttpRequest;
import com.upedge.pms.modules.alibaba.entity.listPushed.AlibabaCrossSyncProductListPushedParam;
import com.upedge.pms.modules.alibaba.entity.listPushed.AlibabaCrossSyncProductListPushedResult;
import com.upedge.pms.modules.alibaba.entity.listPushed.CommonResult;
import com.upedge.pms.modules.alibaba.entity.product.*;
import com.upedge.pms.modules.alibaba.entity.supplier.AlibabaAccountAgentCrossBasicParam;
import com.upedge.pms.modules.alibaba.entity.supplier.AlibabaAccountAgentCrossBasicResult;
import com.upedge.pms.modules.alibaba.entity.supplier.SimpleAccountInfo;
import com.upedge.pms.modules.alibaba.entity.translate.TranslateResult;
import com.upedge.thirdparty.ali1688.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.*;

public class Ali1688Service {


    public static AlibabaApiVo refreshAccessToken(AlibabaApiVo alibabaApiVo) {
        String url = "https://gw.open.1688.com/openapi/param2/1/system.oauth2/getToken/" + alibabaApiVo.getApiKey() + "?grant_type=refresh_token&client_id=" + alibabaApiVo.getApiKey() + "&client_secret=" + alibabaApiVo.getApiSecret() + "&refresh_token=" + alibabaApiVo.getRefreshToken();
        String body = OkHttpRequest.commonRequest(url, HttpMethod.GET, null);
        if (StringUtils.isNotBlank(body)) {
            long now = System.currentTimeMillis();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String accessToken = jsonObject.getString("access_token");
            long expireTime = jsonObject.getLong("expires_in") * 1000;
            alibabaApiVo.setAccessToken(accessToken);
            alibabaApiVo.setAccessTokenCreateTime(now);
            alibabaApiVo.setAccessTokenExpireTime(now + expireTime - 1000L);
            return alibabaApiVo;

        }
        return null;
    }


    /**
     * 获取1688产品
     */
    public static AlibabaProductVo getProduct(String alibabaProductId, AlibabaApiVo alibabaApiVo) {
        AlibabaProductVo alibabaProductVo = new AlibabaProductVo();
        long s = System.currentTimeMillis();
        ProductInfo productInfo = getAlibabaProductDetail(alibabaProductId, alibabaApiVo);
        long e = System.currentTimeMillis();
        if (productInfo == null) {
            return null;
        }
        alibabaProductVo.setProductSku(alibabaProductId);
        alibabaProductVo.setOriginalTitle(productInfo.getSubject());
        alibabaProductVo.setProductTitle(productInfo.getSubject());

        String price = "";
        //参考价格，返回价格区间，可能为空
        String referencePrice = productInfo.getReferencePrice();
        if (!StringUtils.isBlank(referencePrice)) {
            if (referencePrice.contains("~")) {
                price = String.valueOf(referencePrice.split("~")[1]);
            } else {
                price = String.valueOf(referencePrice);
            }
        }

        ProductAttributeVo productAttributeVo = new ProductAttributeVo();
        productAttributeVo.setAliCnCategoryId(productInfo.getCategoryID());
        productAttributeVo.setScore(productInfo.getQualityLevel());

        List<ProductAttribute> attrList = productInfo.getAttributes();
        for (ProductAttribute productAttribute : attrList) {
            if (productAttribute.getAttributeName().contains("货号")) {
                if (StringUtils.isNumeric(productAttribute.getValue())) {
                    productAttributeVo.setItemNo(productAttribute.getValue());
                } else {
                    productAttributeVo.setItemNo("");
                }
            }
        }

        ProductInfoVo productInfoVo = productInfo.toProductInfoVo();
        //产品描述
        alibabaProductVo.setProductInfoVo(productInfoVo);
        //提取描述图片
        List<String> descImgList = GetImgUrlList.listImgUrlList(productInfo.getDescription());

        //产品重量
        Double weight = 0.0;
        //图片
        Map<String, Integer> imgMap = new HashMap<String, Integer>();

        List<ProductImgVo> productImgVoList = new ArrayList<>();
        ProductImageInfo imageInfo = productInfo.getImage();
        if (imageInfo != null && imageInfo.getImages() != null && imageInfo.getImages().size() > 0) {
            //产品主图
            String productImage = "https://cbu01.alicdn.com/" + imageInfo.getImages().get(0);
            alibabaProductVo.setProductImage(productImage);
            for (String img : imageInfo.getImages()) {
                if (!imgMap.containsKey(img)) {
                    imgMap.put("https://cbu01.alicdn.com/" + img, imgMap.size() + 1);
                }
            }
        }

        List<ProductVariantVo> productVariantVoList = new ArrayList<>();
        Set<String> attrSet = new HashSet<>();
        for (ProductSKUInfo skuInfos : productInfo.getSkuInfos()) {
            //变体
            ProductVariantVo productVariantVo = new ProductVariantVo();
            List<ProductVariantAttrVo> productVariantAttrVoList = new ArrayList<>();
            List<Attribute> attributeList = skuInfos.getAttributes();
            ProductShippingInfo shippingInfo = productInfo.getShippingInfo();
            if (shippingInfo != null) {
                if (shippingInfo.getUnitWeight() != null) {
                    productVariantVo.setWeight(new BigDecimal(shippingInfo.getUnitWeight()).multiply(new BigDecimal("1000")));
                    productVariantVo.setVolumeWeight(productVariantVo.getWeight());
                }
                if (shippingInfo.getVolume() != null) {
                    productVariantVo.setVolumeWeight(new BigDecimal(shippingInfo.getVolume()));
                }
            }
            productVariantVo.setOriginalVariantId(skuInfos.getSkuId());
            productVariantVo.setVariantSku(skuInfos.getSkuId());
            productVariantVo.setVariantPrice(skuInfos.getPrice() == null ? new BigDecimal(price) : new BigDecimal(skuInfos.getPrice()));
            productVariantVo.setInventory(Integer.toUnsignedLong(skuInfos.getAmountOnSale()));
            productVariantVo.setSpecId(skuInfos.getSpecId());
            productVariantVo.setState(1);
            if (productVariantVo.getInventory() <= 0
                    || null == productVariantVo.getWeight()
                    || null == productVariantVo.getVolumeWeight()
                    || productVariantVo.getVolumeWeight().compareTo(BigDecimal.ZERO) <= 0
                    || productVariantVo.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
                productVariantVo.setState(0);
            }
            for (int i = 0; i < attributeList.size(); i++) {
                Attribute attribute = attributeList.get(i);
                ProductVariantAttrVo productVariantAttrVo = new ProductVariantAttrVo();
                productVariantAttrVo.setVariantAttrCname(attribute.getAttributeDisplayName());
                productVariantAttrVo.setVariantAttrEname("");
                productVariantAttrVo.setOriginalAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrEvalue("");
                productVariantAttrVo.setSeq(i);
                productVariantAttrVoList.add(productVariantAttrVo);
                if (StringUtils.isNotBlank(attribute.getSkuImageUrl())) {
                    productVariantVo.setVariantImage("https://cbu01.alicdn.com/" + attribute.getSkuImageUrl());
                    imgMap.put("https://cbu01.alicdn.com/" + attribute.getSkuImageUrl(), imgMap.size() + 1);
                }

            }
            productVariantVo.setVariantAttrVoList(productVariantAttrVoList);
            productVariantVoList.add(productVariantVo);

        }
        //没有变体，添加默认变体
        if (productInfo.getSkuInfos() == null || productInfo.getSkuInfos().size() == 0) {
            //变体
            ProductVariantVo productVariantVo = new ProductVariantVo();
            productVariantVo.setOriginalVariantId(alibabaProductId);
            productVariantVo.setVariantSku(alibabaProductId);
            productVariantVo.setVariantPrice(price == null ? new BigDecimal(price) : new BigDecimal(price));
            productVariantVo.setInventory(productInfo.getSaleInfo().getAmountOnSale() == null ? 0 : productInfo.getSaleInfo().getAmountOnSale().longValue());
            productVariantVo.setWeight(new BigDecimal(weight));
            productVariantVo.setVolumeWeight(new BigDecimal(weight));
            productVariantVo.setState(1);
            if (productVariantVo.getInventory() <= 0
                    || productVariantVo.getVolumeWeight().compareTo(BigDecimal.ZERO) <= 0
                    || productVariantVo.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
                productVariantVo.setState(0);
            }
            List<ProductVariantAttrVo> productVariantAttrVoList = new ArrayList<>();
            ProductVariantAttrVo productVariantAttrVo = new ProductVariantAttrVo();
            productVariantAttrVo.setVariantAttrCname("默认");
            productVariantAttrVo.setVariantAttrEname("default");
            productVariantAttrVo.setOriginalAttrCvalue("默认");
            productVariantAttrVo.setVariantAttrCvalue("default");
            productVariantAttrVo.setVariantAttrEvalue("default");
            productVariantAttrVo.setSeq(1);
            productVariantAttrVoList.add(productVariantAttrVo);
            productVariantVo.setVariantImage(alibabaProductVo.getProductImage());
            productVariantVo.setVariantAttrVoList(productVariantAttrVoList);
            productVariantVoList.add(productVariantVo);
        }
        //产品变体列表
        alibabaProductVo.setProductVariantVoList(productVariantVoList);

        //产品图片
        for (Map.Entry<String, Integer> image : imgMap.entrySet()) {
            if (StringUtils.isBlank(image.getKey())) {
                continue;
            }
            ProductImgVo productImgVo = new ProductImgVo();
            productImgVo.setImageUrl(image.getKey());
            productImgVo.setImageSeq(image.getValue());
            productImgVo.setState(1);
            productImgVoList.add(productImgVo);
        }

        //添加描述图片
        for (String img : descImgList) {
            if (StringUtils.isBlank(img) || imgMap.containsKey(img)) {
                continue;
            }
            Integer seq = imgMap.size() + 1;
            imgMap.put(img, seq);
            ProductImgVo productImgVo = new ProductImgVo();
            productImgVo.setImageUrl(img);
            productImgVo.setImageSeq(seq);
            productImgVo.setState(0);
            productImgVoList.add(productImgVo);
        }

        //产品图片列表
        alibabaProductVo.setProductImgVoList(productImgVoList);

        //获取供应商信息
        SimpleAccountInfo simpleAccountInfo = getSimpleAccountInfo(productInfo.getSupplierLoginId(), alibabaApiVo);
        //供应商信息
        SupplierVo supplierVo = simpleAccountInfo.toSupplierVo();
        alibabaProductVo.setSupplierVo(supplierVo);

        productAttributeVo.setAliCnCategoryName(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryCname(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryEname(simpleAccountInfo.getCategoryName());
        alibabaProductVo.setProductAttributeVo(productAttributeVo);

        //翻译 标题&报关英文名
        List<String> translateAttr = new ArrayList<>(attrSet);
        translateAttr.add(productInfo.getSubject());
        translateAttr.add(simpleAccountInfo.getCategoryName());
        TranslateResult translateResult = TranslateService.translate(translateAttr);
        if (translateResult != null && translateResult.getResult() != null) {
            //报关英文名
            String enCategoryName = translateResult.getResult().get(simpleAccountInfo.getCategoryName());
            if (!StringUtils.isBlank(enCategoryName)) {
                productAttributeVo.setEntryEname(enCategoryName);
            }
            //标题
            if (translateResult != null && translateResult.getResult() != null) {
                String enTitle = translateResult.getResult().get(productInfo.getSubject());
                if (!StringUtils.isBlank(enTitle)) {
                    alibabaProductVo.setProductTitle(enTitle);
                }

            }
        }
        //翻译产品描述
        List<String> descAttr = new ArrayList<>();
        descAttr.add(productInfoVo.getCnDesc());
        TranslateResult descTranslateResult = TranslateService.translate(descAttr);
        String enDesc = descTranslateResult.getResult().get(productInfoVo.getCnDesc());
        if (StringUtils.isNotBlank(enDesc)) {
            productInfoVo.setProductDesc(enDesc);
        } else {
            productInfoVo.setProductDesc(productInfoVo.getCnDesc());
        }
        alibabaProductVo.setProductInfoVo(productInfoVo);
        //翻译产品属性
        List<String> variantAttr = new ArrayList<>();
        Map<String,String> result=new HashMap<>();
        List<ProductVariantVo> productVariantVos = alibabaProductVo.getProductVariantVoList();
        int i = 1;
        for (ProductVariantVo productVariantVo : productVariantVos) {
            List<ProductVariantAttrVo> variantAttrVoList = productVariantVo.getVariantAttrVoList();
            for (ProductVariantAttrVo productVariantAttrVo : variantAttrVoList) {
                variantAttr.add(productVariantAttrVo.getVariantAttrCname());
                variantAttr.add(productVariantAttrVo.getVariantAttrCvalue());
            }
            i++;
            if (i / 10 == 1){
                i = 1;
                TranslateResult attrResult = TranslateService.translate(variantAttr);
                if (attrResult.getResult() != null){
                    result.putAll(attrResult.getResult());
                }
                variantAttr = new ArrayList<>();
            }
        }
        TranslateResult attrResult = TranslateService.translate(variantAttr);
        if (attrResult.getResult() != null){
            result.putAll(attrResult.getResult());
        }
        alibabaProductVo.getProductVariantVoList().forEach(variant -> {
            List<ProductVariantAttrVo> variantAttrVoList = variant.getVariantAttrVoList();
            for (ProductVariantAttrVo attr : variantAttrVoList) {
                String cname = attr.getVariantAttrCname();
                String cvalue = attr.getOriginalAttrCvalue();
                if (!StringUtils.isBlank(cname) &&
                        !StringUtils.isBlank(result.get(cname))) {
                    attr.setVariantAttrEname(result.get(cname));
                }
                if (!StringUtils.isBlank(cvalue) &&
                        !StringUtils.isBlank(result.get(cvalue))) {
                    attr.setVariantAttrEvalue(result.get(cvalue));
                }
            }
        });
        return alibabaProductVo;
    }

    /**
     * 通过1688产品id获取产品信息
     */
    private static ProductInfo getAlibabaProductDetail(String alibabaProductId, AlibabaApiVo alibabaApiVo) {
        if (StringUtils.isBlank(alibabaProductId) || !StringUtils.isNumeric(alibabaProductId)) {
            return null;
        }
        Long[] productIdList = new Long[1];
        productIdList[0] = Long.parseLong(alibabaProductId);
        //加入铺货列表
        syncProductListPushed(productIdList, alibabaApiVo);

        //获取产品信息
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaCrossProductInfoParam param = new AlibabaCrossProductInfoParam();

        param.setProductId(Long.parseLong(alibabaProductId));

        SDKResult<AlibabaCrossProductInfoResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());

        ProductInfo productInfo = sdkResult.getResult().getProductInfo();
        return productInfo;

    }

    /**
     * 根据供应商登录id获取供应商信息
     */
    private static SimpleAccountInfo getSimpleAccountInfo(String loginId, AlibabaApiVo alibabaApiVo) {
        if (StringUtils.isBlank(loginId)) {
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaAccountAgentCrossBasicParam param = new AlibabaAccountAgentCrossBasicParam();

        param.setLoginId(loginId);

        SDKResult<AlibabaAccountAgentCrossBasicResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());

        SimpleAccountInfo accountInfo = sdkResult.getResult().getResult();
        return accountInfo;

    }

    /**
     * 加入产品铺货列表
     */
    private static CommonResult syncProductListPushed(Long[] productIdList, AlibabaApiVo alibabaApiVo) {
        if (productIdList == null || productIdList.length == 0) {
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaCrossSyncProductListPushedParam param = new AlibabaCrossSyncProductListPushedParam();

        param.setProductIdList(productIdList);

        SDKResult<AlibabaCrossSyncProductListPushedResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());
        CommonResult result = sdkResult.getResult().getResult();
        return result;

    }


}
