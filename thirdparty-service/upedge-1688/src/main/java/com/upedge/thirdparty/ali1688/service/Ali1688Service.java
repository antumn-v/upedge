package com.upedge.thirdparty.ali1688.service;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;

import com.upedge.common.utils.GetImgUrlList;
import com.upedge.thirdparty.ali1688.entity.listPushed.AlibabaCrossSyncProductListPushedParam;
import com.upedge.thirdparty.ali1688.entity.listPushed.AlibabaCrossSyncProductListPushedResult;

import com.upedge.thirdparty.ali1688.entity.product.*;
import com.upedge.thirdparty.ali1688.entity.supplier.AlibabaAccountAgentCrossBasicParam;
import com.upedge.thirdparty.ali1688.entity.supplier.SimpleAccountInfo;
import com.upedge.thirdparty.ali1688.entity.translate.TranslateResult;

import com.upedge.thirdparty.ali1688.config.AlibabaConfig;
import com.upedge.thirdparty.ali1688.entity.listPushed.CommonResult;
import com.upedge.thirdparty.ali1688.entity.supplier.AlibabaAccountAgentCrossBasicResult;
import com.upedge.thirdparty.ali1688.vo.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jiaqi on 2020/6/15.
 */
public class Ali1688Service {

    /**
     * 获取1688产品
     */
    public static ProductVo getProduct(String alibabaProductId){
        ProductVo productVo=new ProductVo();
        long s=System.currentTimeMillis();
        ProductInfo productInfo=getAlibabaProductDetail(alibabaProductId);
        long e=System.currentTimeMillis();
        System.out.println("1688获取:"+(e-s));;
        if(productInfo==null){
            return null;
        }
        productVo.setProductSku(alibabaProductId);
        productVo.setOriginalTitle(productInfo.getSubject());
        productVo.setProductTitle(productInfo.getSubject());

        String price="";
        //参考价格，返回价格区间，可能为空
        String referencePrice=productInfo.getReferencePrice();
        if(!StringUtils.isBlank(referencePrice)){
            if(referencePrice.contains("~")) {
                price =String.valueOf(referencePrice.split("~")[1]);
            }else {
                price =String.valueOf(referencePrice);
            }
        }

        ProductAttributeVo productAttributeVo=new ProductAttributeVo();
        productAttributeVo.setAliCnCategoryId(productInfo.getCategoryID());
        productAttributeVo.setScore(productInfo.getQualityLevel());

        List<ProductAttribute> attrList=productInfo.getAttributes();
        for(ProductAttribute productAttribute:attrList){
            if(productAttribute.getAttributeName().contains("货号")){
              if(StringUtils.isNumeric(productAttribute.getValue())){
                productAttributeVo.setItemNo(productAttribute.getValue());
              }else {
                productAttributeVo.setItemNo("");
              }
            }
        }

        ProductInfoVo productInfoVo=productInfo.toProductInfoVo();
        //产品描述
        productVo.setProductInfoVo(productInfoVo);
        //提取描述图片
        List<String> descImgList= GetImgUrlList.listImgUrlList(productInfo.getDescription());

        //产品重量
        Double weight=0.0;
        ProductShippingInfo shippingInfo=productInfo.getShippingInfo();
        if(shippingInfo!=null&&shippingInfo.getUnitWeight()!=null){
            weight=shippingInfo.getUnitWeight();
        }

        //图片
        Map<String,Integer> imgMap=new HashMap<String,Integer>();

        List<ProductImgVo> productImgVoList=new ArrayList<>();
        ProductImageInfo imageInfo=productInfo.getImage();
        if(imageInfo!=null&&imageInfo.getImages()!=null&&imageInfo.getImages().size()>0){
            //产品主图
            String productImage="https://cbu01.alicdn.com/"+imageInfo.getImages().get(0);
            productVo.setProductImage(productImage);
            for (String img : imageInfo.getImages()) {
                if(!imgMap.containsKey(img)) {
                    imgMap.put("https://cbu01.alicdn.com/"+img, imgMap.size() + 1);
                }
            }
        }

        List<ProductVariantVo> productVariantVoList=new ArrayList<>();
        Set<String> attrSet=new HashSet<>();
        for(ProductSKUInfo skuInfos:productInfo.getSkuInfos()){
            //变体
            ProductVariantVo productVariantVo=new ProductVariantVo();
            List<ProductVariantAttrVo> productVariantAttrVoList=new ArrayList<>();
            List<Attribute> attributeList=skuInfos.getAttributes();

            productVariantVo.setOriginalVariantId(Long.parseLong(skuInfos.getSkuId()));
            productVariantVo.setVariantSku(skuInfos.getSkuId());
            productVariantVo.setVariantPrice(skuInfos.getPrice()==null?new BigDecimal(price):new BigDecimal(skuInfos.getPrice()));
            productVariantVo.setInventory(Integer.toUnsignedLong(skuInfos.getAmountOnSale()));
            productVariantVo.setWeight(new BigDecimal(weight));
            productVariantVo.setVolumeWeight(new BigDecimal(weight));
            productVariantVo.setState(1);
            if(productVariantVo.getInventory()<=0
                    ||productVariantVo.getVolumeWeight().compareTo(BigDecimal.ZERO)<=0
                    ||productVariantVo.getWeight().compareTo(BigDecimal.ZERO)<=0){
                productVariantVo.setState(0);
            }
            for(int i=0;i<attributeList.size();i++){
                Attribute attribute=attributeList.get(i);
                ProductVariantAttrVo productVariantAttrVo=new ProductVariantAttrVo();
                productVariantAttrVo.setVariantAttrCname(attribute.getAttributeDisplayName());
                productVariantAttrVo.setVariantAttrEname("");
                productVariantAttrVo.setOriginalAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrEvalue("");
                productVariantAttrVo.setSeq(i);
                productVariantAttrVoList.add(productVariantAttrVo);
                if(!StringUtils.isBlank(attribute.getSkuImageUrl())) {
                    productVariantVo.setVariantImage("https://cbu01.alicdn.com/" + attribute.getSkuImageUrl());
                    imgMap.put("https://cbu01.alicdn.com/"+attribute.getSkuImageUrl(), imgMap.size() + 1);
                }
                attrSet.add(attribute.getAttributeDisplayName());
                attrSet.add(attribute.getAttributeValue());
            }
            productVariantVo.setVariantAttrVoList(productVariantAttrVoList);
            productVariantVoList.add(productVariantVo);

        }
        //没有变体，添加默认变体
        if(productInfo.getSkuInfos()==null||productInfo.getSkuInfos().size()==0){
            //变体
            ProductVariantVo productVariantVo=new ProductVariantVo();
            productVariantVo.setOriginalVariantId(Long.parseLong(alibabaProductId));
            productVariantVo.setVariantSku(alibabaProductId);
            productVariantVo.setVariantPrice(price==null?new BigDecimal(price):new BigDecimal(price));
            productVariantVo.setInventory(productInfo.getSaleInfo().getAmountOnSale()==null?0:productInfo.getSaleInfo().getAmountOnSale().longValue());
            productVariantVo.setWeight(new BigDecimal(weight));
            productVariantVo.setVolumeWeight(new BigDecimal(weight));
            productVariantVo.setState(1);
            if(productVariantVo.getInventory()<=0
                    ||productVariantVo.getVolumeWeight().compareTo(BigDecimal.ZERO)<=0
                    ||productVariantVo.getWeight().compareTo(BigDecimal.ZERO)<=0){
                productVariantVo.setState(0);
            }
            List<ProductVariantAttrVo> productVariantAttrVoList=new ArrayList<>();
            ProductVariantAttrVo productVariantAttrVo=new ProductVariantAttrVo();
            productVariantAttrVo.setVariantAttrCname("默认");
            productVariantAttrVo.setVariantAttrEname("default");
            productVariantAttrVo.setOriginalAttrCvalue("默认");
            productVariantAttrVo.setVariantAttrCvalue("default");
            productVariantAttrVo.setVariantAttrEvalue("default");
            productVariantAttrVo.setSeq(1);
            productVariantAttrVoList.add(productVariantAttrVo);
            productVariantVo.setVariantImage(productVo.getProductImage());
            productVariantVo.setVariantAttrVoList(productVariantAttrVoList);
            productVariantVoList.add(productVariantVo);
        }
        //产品变体列表
        productVo.setProductVariantVoList(productVariantVoList);

        //产品图片
        for(Map.Entry<String,Integer> image:imgMap.entrySet()){
            if(StringUtils.isBlank(image.getKey())){
                continue;
            }
            ProductImgVo productImgVo=new ProductImgVo();
            productImgVo.setImageUrl(image.getKey());
            productImgVo.setImageSeq(image.getValue());
            productImgVo.setState(1);
            productImgVoList.add(productImgVo);
        }

        //添加描述图片
        for(String img:descImgList) {
            if(StringUtils.isBlank(img)||imgMap.containsKey(img)) {
                continue;
            }
            Integer seq=imgMap.size() + 1;
            imgMap.put(img, seq);
            ProductImgVo productImgVo=new ProductImgVo();
            productImgVo.setImageUrl(img);
            productImgVo.setImageSeq(seq);
            productImgVo.setState(0);
            productImgVoList.add(productImgVo);
        }

        //产品图片列表
        productVo.setProductImgVoList(productImgVoList);

        //获取供应商信息
        SimpleAccountInfo simpleAccountInfo=getSimpleAccountInfo(productInfo.getSupplierLoginId());
        //供应商信息
        SupplierVo supplierVo=simpleAccountInfo.toSupplierVo();
        productVo.setSupplierVo(supplierVo);

        productAttributeVo.setAliCnCategoryName(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryCname(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryEname(simpleAccountInfo.getCategoryName());
        productVo.setProductAttributeVo(productAttributeVo);

        //翻译 标题&报关英文名&变体属性
        List<String> translateAttr=new ArrayList<>(attrSet);
        translateAttr.add(productInfo.getSubject());
        translateAttr.add(simpleAccountInfo.getCategoryName());
        long start=System.currentTimeMillis();
        TranslateResult translateResult= TranslateService.translate(translateAttr);
        long end=System.currentTimeMillis();
        System.out.println("产品翻译时间:"+(end-start));
        if(translateResult!=null&&translateResult.getResult()!=null) {
            //报关英文名
            String enCategoryName=translateResult.getResult().get(simpleAccountInfo.getCategoryName());
            if(!StringUtils.isBlank(enCategoryName)){
                productAttributeVo.setEntryEname(enCategoryName);
            }
            //标题
            if(translateResult!=null&&translateResult.getResult()!=null){
                String enTitle=translateResult.getResult().get(productInfo.getSubject());
                if(!StringUtils.isBlank(enTitle)){
                    productVo.setProductTitle(enTitle);
                }
            }
            productVo.getProductVariantVoList().forEach(variant -> {
                List<ProductVariantAttrVo> variantAttrVoList=variant.getVariantAttrVoList();
                for(ProductVariantAttrVo attr:variantAttrVoList){
                    String cname = attr.getVariantAttrCname();
                    String cvalue = attr.getOriginalAttrCvalue();
                    if (!StringUtils.isBlank(cname) &&
                            !StringUtils.isBlank(translateResult.getResult().get(cname))) {
                        attr.setVariantAttrEname(translateResult.getResult().get(cname));
                    }
                    if (!StringUtils.isBlank(cvalue) &&
                            !StringUtils.isBlank(translateResult.getResult().get(cvalue))) {
                        attr.setVariantAttrEvalue(translateResult.getResult().get(cvalue));
                    }
                }
            });
        }

        return productVo;
    }

    /**
     * 通过1688产品id获取产品信息
     */
    private static ProductInfo getAlibabaProductDetail(String alibabaProductId){
        if(StringUtils.isBlank(alibabaProductId)||!StringUtils.isNumeric(alibabaProductId)){
            return null;
        }
        Long[] productIdList=new Long[1];
        productIdList[0]=Long.parseLong(alibabaProductId);
        //加入铺货列表
        syncProductListPushed(productIdList);

        //获取产品信息
        ApiExecutor apiExecutor = new ApiExecutor(AlibabaConfig.API_KEY, AlibabaConfig.API_SECRET);

        AlibabaCrossProductInfoParam param = new AlibabaCrossProductInfoParam();

        param.setProductId(Long.parseLong(alibabaProductId));

        SDKResult<AlibabaCrossProductInfoResult> sdkResult =
                apiExecutor.execute(param, AlibabaConfig.ACCESS_TOKEN);

        ProductInfo productInfo=sdkResult.getResult().getProductInfo();
        return productInfo;

    }

    /**
     * 根据供应商登录id获取供应商信息
     */
    private static SimpleAccountInfo getSimpleAccountInfo(String loginId){
        if(StringUtils.isBlank(loginId)){
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(AlibabaConfig.API_KEY, AlibabaConfig.API_SECRET);

        AlibabaAccountAgentCrossBasicParam param = new AlibabaAccountAgentCrossBasicParam();

        param.setLoginId(loginId);

        SDKResult<AlibabaAccountAgentCrossBasicResult> sdkResult =
                apiExecutor.execute(param, AlibabaConfig.ACCESS_TOKEN);

        SimpleAccountInfo accountInfo=sdkResult.getResult().getResult();
        return accountInfo;

    }

    /**
     * 加入产品铺货列表
     */
    private static CommonResult syncProductListPushed(Long[] productIdList){
        if(productIdList==null||productIdList.length==0){
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(AlibabaConfig.API_KEY, AlibabaConfig.API_SECRET);

        AlibabaCrossSyncProductListPushedParam param = new AlibabaCrossSyncProductListPushedParam();

        param.setProductIdList(productIdList);

        SDKResult<AlibabaCrossSyncProductListPushedResult> sdkResult =
                apiExecutor.execute(param, AlibabaConfig.ACCESS_TOKEN);
        CommonResult result=sdkResult.getResult().getResult();
        return result;

    }



}
