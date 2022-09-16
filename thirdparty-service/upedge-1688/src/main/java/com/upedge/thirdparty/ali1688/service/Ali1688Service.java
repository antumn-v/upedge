package com.upedge.thirdparty.ali1688.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.alibaba.logistics.param.AlibabaTradeGetLogisticsTraceInfoBuyerViewParam;
import com.alibaba.logistics.param.AlibabaTradeGetLogisticsTraceInfoBuyerViewResult;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.alibaba.trade.param.*;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.utils.GetImgUrlList;
import com.upedge.common.utils.OkHttpRequest;
import com.upedge.thirdparty.ali1688.entity.listPushed.AlibabaCrossSyncProductListPushedParam;
import com.upedge.thirdparty.ali1688.entity.listPushed.AlibabaCrossSyncProductListPushedResult;
import com.upedge.thirdparty.ali1688.entity.listPushed.CommonResult;
import com.upedge.thirdparty.ali1688.entity.product.AlibabaCrossProductInfoParam;
import com.upedge.thirdparty.ali1688.entity.product.AlibabaCrossProductInfoResult;
import com.upedge.thirdparty.ali1688.entity.product.Attribute;
import com.upedge.thirdparty.ali1688.entity.product.ProductInfo;
import com.upedge.thirdparty.ali1688.entity.supplier.AlibabaAccountAgentCrossBasicParam;
import com.upedge.thirdparty.ali1688.entity.supplier.AlibabaAccountAgentCrossBasicResult;
import com.upedge.thirdparty.ali1688.entity.supplier.SimpleAccountInfo;
import com.upedge.thirdparty.ali1688.entity.translate.TranslateResult;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.*;

public class Ali1688Service {


    public static AlibabaApiVo refreshAccessToken(AlibabaApiVo alibabaApiVo){
        String url = "https://gw.open.1688.com/openapi/param2/1/system.oauth2/getToken/"+alibabaApiVo.getApiKey()+"?grant_type=refresh_token&client_id="+alibabaApiVo.getApiKey()+"&client_secret="+alibabaApiVo.getApiSecret()+"&refresh_token=" + alibabaApiVo.getRefreshToken();
        String body = OkHttpRequest.commonRequest(url, HttpMethod.GET,null);
        if (StringUtils.isNotBlank(body)){
            long now = System.currentTimeMillis();
            JSONObject jsonObject = JSONObject.parseObject(body);{
                String accessToken = jsonObject.getString("access_token");
                long expireTime = jsonObject.getLong("expires_in") * 1000;
                alibabaApiVo.setAccessToken(accessToken);
                alibabaApiVo.setAccessTokenCreateTime(now);
                alibabaApiVo.setAccessTokenExpireTime(now + expireTime - 1000L);
                return alibabaApiVo;
            }
        }
        return null;
    }


    /**
     * 获取1688产品
     */
    public static AlibabaProductVo getProduct(String alibabaProductId,AlibabaApiVo alibabaApiVo){
        AlibabaProductVo alibabaProductVo=new AlibabaProductVo();
        long s=System.currentTimeMillis();
        com.upedge.thirdparty.ali1688.entity.product.ProductInfo productInfo=getAlibabaProductDetail(alibabaProductId,alibabaApiVo);
        long e=System.currentTimeMillis();
        if(productInfo==null){
            return null;
        }
        alibabaProductVo.setProductSku(alibabaProductId);
        alibabaProductVo.setOriginalTitle(productInfo.getSubject());
        alibabaProductVo.setProductTitle(productInfo.getSubject());

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

        com.upedge.thirdparty.ali1688.vo.ProductAttributeVo productAttributeVo=new com.upedge.thirdparty.ali1688.vo.ProductAttributeVo();
        productAttributeVo.setAliCnCategoryId(productInfo.getCategoryID());
        productAttributeVo.setScore(productInfo.getQualityLevel());

        List<com.upedge.thirdparty.ali1688.entity.product.ProductAttribute> attrList=productInfo.getAttributes();
        for(com.upedge.thirdparty.ali1688.entity.product.ProductAttribute productAttribute:attrList){
            if(productAttribute.getAttributeName().contains("货号")){
              if(StringUtils.isNumeric(productAttribute.getValue())){
                productAttributeVo.setItemNo(productAttribute.getValue());
              }else {
                productAttributeVo.setItemNo("");
              }
            }
        }

        com.upedge.thirdparty.ali1688.vo.ProductInfoVo productInfoVo=productInfo.toProductInfoVo();
        //产品描述
        alibabaProductVo.setProductInfoVo(productInfoVo);
        //提取描述图片
        List<String> descImgList= GetImgUrlList.listImgUrlList(productInfo.getDescription());

        //产品重量
        Double weight=0.0;
        //图片
        Map<String,Integer> imgMap=new HashMap<String,Integer>();

        List<com.upedge.thirdparty.ali1688.vo.ProductImgVo> productImgVoList=new ArrayList<>();
        com.upedge.thirdparty.ali1688.entity.product.ProductImageInfo imageInfo=productInfo.getImage();
        if(imageInfo!=null&&imageInfo.getImages()!=null&&imageInfo.getImages().size()>0){
            //产品主图
            String productImage="https://cbu01.alicdn.com/"+imageInfo.getImages().get(0);
            alibabaProductVo.setProductImage(productImage);
            for (String img : imageInfo.getImages()) {
                if(!imgMap.containsKey(img)) {
                    imgMap.put("https://cbu01.alicdn.com/"+img, imgMap.size() + 1);
                }
            }
        }

        List<com.upedge.thirdparty.ali1688.vo.ProductVariantVo> productVariantVoList=new ArrayList<>();
        Set<String> attrSet=new HashSet<>();
        for(com.upedge.thirdparty.ali1688.entity.product.ProductSKUInfo skuInfos:productInfo.getSkuInfos()){
            //变体
            com.upedge.thirdparty.ali1688.vo.ProductVariantVo productVariantVo=new com.upedge.thirdparty.ali1688.vo.ProductVariantVo();
            List<com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo> productVariantAttrVoList=new ArrayList<>();
            List<Attribute> attributeList=skuInfos.getAttributes();
            com.upedge.thirdparty.ali1688.entity.product.ProductShippingInfo shippingInfo=productInfo.getShippingInfo();
            if(shippingInfo!=null){
                if (shippingInfo.getUnitWeight() != null){
                    productVariantVo.setWeight(new BigDecimal(shippingInfo.getUnitWeight()).multiply(new BigDecimal("1000")));
                    productVariantVo.setVolumeWeight(productVariantVo.getWeight());
                }
                if (shippingInfo.getVolume() != null){
                    productVariantVo.setVolumeWeight(new BigDecimal(shippingInfo.getVolume()));
                }
            }
            productVariantVo.setOriginalVariantId(skuInfos.getSkuId());
            productVariantVo.setVariantSku(skuInfos.getSkuId());
            productVariantVo.setVariantPrice(skuInfos.getPrice()==null?new BigDecimal(price):new BigDecimal(skuInfos.getPrice()));
            productVariantVo.setInventory(Integer.toUnsignedLong(skuInfos.getAmountOnSale()));

            productVariantVo.setState(1);
            if(productVariantVo.getInventory()<=0
                    || null == productVariantVo.getWeight()
                    || null == productVariantVo.getVolumeWeight()
                    ||productVariantVo.getVolumeWeight().compareTo(BigDecimal.ZERO)<=0
                    ||productVariantVo.getWeight().compareTo(BigDecimal.ZERO)<=0){
                productVariantVo.setState(0);
            }
            for(int i=0;i<attributeList.size();i++){
                Attribute attribute=attributeList.get(i);
                com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo productVariantAttrVo=new com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo();
                productVariantAttrVo.setVariantAttrCname(attribute.getAttributeDisplayName());
                productVariantAttrVo.setVariantAttrEname("");
                productVariantAttrVo.setOriginalAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrCvalue(attribute.getAttributeValue());
                productVariantAttrVo.setVariantAttrEvalue("");
                productVariantAttrVo.setSeq(i);
                productVariantAttrVoList.add(productVariantAttrVo);
                if(StringUtils.isNotBlank(attribute.getSkuImageUrl())) {
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
            com.upedge.thirdparty.ali1688.vo.ProductVariantVo productVariantVo=new com.upedge.thirdparty.ali1688.vo.ProductVariantVo();
            productVariantVo.setOriginalVariantId(alibabaProductId);
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
            List<com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo> productVariantAttrVoList=new ArrayList<>();
            com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo productVariantAttrVo=new com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo();
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
        for(Map.Entry<String,Integer> image:imgMap.entrySet()){
            if(StringUtils.isBlank(image.getKey())){
                continue;
            }
            com.upedge.thirdparty.ali1688.vo.ProductImgVo productImgVo=new com.upedge.thirdparty.ali1688.vo.ProductImgVo();
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
            com.upedge.thirdparty.ali1688.vo.ProductImgVo productImgVo=new com.upedge.thirdparty.ali1688.vo.ProductImgVo();
            productImgVo.setImageUrl(img);
            productImgVo.setImageSeq(seq);
            productImgVo.setState(0);
            productImgVoList.add(productImgVo);
        }

        //产品图片列表
        alibabaProductVo.setProductImgVoList(productImgVoList);

        //获取供应商信息
        SimpleAccountInfo simpleAccountInfo=getSimpleAccountInfo(productInfo.getSupplierLoginId(),alibabaApiVo);
        //供应商信息
        com.upedge.thirdparty.ali1688.vo.SupplierVo supplierVo=simpleAccountInfo.toSupplierVo();
        alibabaProductVo.setSupplierVo(supplierVo);

        productAttributeVo.setAliCnCategoryName(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryCname(simpleAccountInfo.getCategoryName());
        productAttributeVo.setEntryEname(simpleAccountInfo.getCategoryName());
        alibabaProductVo.setProductAttributeVo(productAttributeVo);

        //翻译 标题&报关英文名&变体属性
        List<String> translateAttr=new ArrayList<>(attrSet);
        translateAttr.add(productInfo.getSubject());
        translateAttr.add(simpleAccountInfo.getCategoryName());
        TranslateResult translateResult= com.upedge.thirdparty.ali1688.config.service.TranslateService.translate(translateAttr);

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
                    alibabaProductVo.setProductTitle(enTitle);
                }

            }
            List<String> descAttr = new ArrayList<>();
            descAttr.add(productInfoVo.getCnDesc());
            TranslateResult  descTranslateResult= com.upedge.thirdparty.ali1688.config.service.TranslateService.translate(descAttr);
            String enDesc = descTranslateResult.getResult().get(productInfoVo.getCnDesc());
            if (StringUtils.isNotBlank(enDesc)){
                productInfoVo.setProductDesc(enDesc);
            }else {
                productInfoVo.setProductDesc(productInfoVo.getCnDesc());
            }
            alibabaProductVo.setProductInfoVo(productInfoVo);
            alibabaProductVo.getProductVariantVoList().forEach(variant -> {
                List<com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo> variantAttrVoList=variant.getVariantAttrVoList();
                for(com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo attr:variantAttrVoList){
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

        return alibabaProductVo;
    }

    /**
     * 通过1688产品id获取产品信息
     */
    private static ProductInfo getAlibabaProductDetail(String alibabaProductId, AlibabaApiVo alibabaApiVo){
        if(StringUtils.isBlank(alibabaProductId)||!StringUtils.isNumeric(alibabaProductId)){
            return null;
        }
        Long[] productIdList=new Long[1];
        productIdList[0]=Long.parseLong(alibabaProductId);
        //加入铺货列表
        syncProductListPushed(productIdList,alibabaApiVo);

        //获取产品信息
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaCrossProductInfoParam param = new AlibabaCrossProductInfoParam();

        param.setProductId(Long.parseLong(alibabaProductId));

        SDKResult<AlibabaCrossProductInfoResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());

        ProductInfo productInfo=sdkResult.getResult().getProductInfo();
        return productInfo;

    }

    /**
     * 根据供应商登录id获取供应商信息
     */
    private static SimpleAccountInfo getSimpleAccountInfo(String loginId,AlibabaApiVo alibabaApiVo){
        if(StringUtils.isBlank(loginId)){
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaAccountAgentCrossBasicParam param = new AlibabaAccountAgentCrossBasicParam();

        param.setLoginId(loginId);

        SDKResult<AlibabaAccountAgentCrossBasicResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());

        SimpleAccountInfo accountInfo=sdkResult.getResult().getResult();
        return accountInfo;

    }

    /**
     * 加入产品铺货列表
     */
    private static CommonResult syncProductListPushed(Long[] productIdList,AlibabaApiVo alibabaApiVo){
        if(productIdList==null||productIdList.length==0){
            return null;
        }
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaCrossSyncProductListPushedParam param = new AlibabaCrossSyncProductListPushedParam();

        param.setProductIdList(productIdList);

        SDKResult<AlibabaCrossSyncProductListPushedResult> sdkResult =
                apiExecutor.execute(param, alibabaApiVo.getAccessToken());
        CommonResult result=sdkResult.getResult().getResult();
        return result;

    }

    public static List<AlibabaCreateOrderPreviewResultModel> createOrderPreview(List<AlibabaTradeFastCargo> alibabaTradeFastCargos,AlibabaApiVo alibabaApiVo) throws CustomerException {
        ApiExecutor apiExecutor = new ApiExecutor(alibabaApiVo.getApiKey(), alibabaApiVo.getApiSecret());

        AlibabaTradeFastAddress addressParam = new AlibabaTradeFastAddress();
        addressParam.setAddressId(0L);
        addressParam.setFullName("辰戎贸易");
        addressParam.setMobile("13751135729");
        addressParam.setProvinceText("浙江省");
        addressParam.setCityText("金华市");
        addressParam.setAreaText("义乌市");
        addressParam.setTownText("后宅街道");
        addressParam.setAddress("遗安二区42幢1单元三楼");
        addressParam.setPhone("13751135729");

        AlibabaCreateOrderPreviewParam createOrderPreviewParam = new AlibabaCreateOrderPreviewParam();
        createOrderPreviewParam.setAddressParam(addressParam);
        createOrderPreviewParam.setCargoParamList(alibabaTradeFastCargos.toArray(new AlibabaTradeFastCargo[alibabaTradeFastCargos.size()]));
        createOrderPreviewParam.setFlow("general");

        SDKResult<AlibabaCreateOrderPreviewResult> sdkResult =
                apiExecutor.execute(createOrderPreviewParam, alibabaApiVo.getAccessToken());
        if(sdkResult.getErrorMessage() != null){
            throw new CustomerException(sdkResult.getErrorMessage());
        }
        AlibabaCreateOrderPreviewResult result=sdkResult.getResult();
        if (result.getErrorMsg() != null){
            throw new CustomerException(result.getErrorMsg());
        }
        return Arrays.asList(result.getOrderPreviewResuslt());
    }

    public static AlibabaTradeFastResult createOrder(List<AlibabaTradeFastCargo> alibabaTradeFastCargos,AlibabaApiVo alibabaApiVo,String message) throws CustomerException {
        ApiExecutor apiExecutor = new ApiExecutor("7715698", "DUE2C3KM9s");

        AlibabaTradeFastAddress addressParam = new AlibabaTradeFastAddress();
        addressParam.setAddressId(0L);
        addressParam.setFullName("辰戎贸易");
        addressParam.setMobile("13751135729");
        addressParam.setProvinceText("浙江省");
        addressParam.setCityText("金华市");
        addressParam.setAreaText("义乌市");
        addressParam.setTownText("后宅街道");
        addressParam.setAddress("遗安二区42幢1单元三楼");
        addressParam.setPhone("13751135729");

        AlibabaTradeFastCreateOrderParam createOrderPreviewParam = new AlibabaTradeFastCreateOrderParam();
        createOrderPreviewParam.setAddressParam(addressParam);
        createOrderPreviewParam.setCargoParamList(alibabaTradeFastCargos.toArray(new AlibabaTradeFastCargo[alibabaTradeFastCargos.size()]));
        createOrderPreviewParam.setFlow("general");
        createOrderPreviewParam.setMessage(message);

        SDKResult<AlibabaTradeFastCreateOrderResult> sdkResult =
                apiExecutor.execute(createOrderPreviewParam, "e000f7e2-1353-4324-ac1e-b69b8fe80dcd");
        if(sdkResult.getErrorMessage() != null){
            throw new CustomerException(sdkResult.getErrorMessage());
        }
        AlibabaTradeFastCreateOrderResult result=sdkResult.getResult();
        return result.getResult();
    }


    public static AlibabaOpenplatformTradeModelTradeInfo orderDetail(Long orderId,AlibabaApiVo alibabaApiVo) throws CustomerException {
        ApiExecutor apiExecutor = new ApiExecutor("7715698", "DUE2C3KM9s");
        AlibabaTradeGetBuyerViewParam alibabaTradeGetBuyerViewParam = new AlibabaTradeGetBuyerViewParam();
        alibabaTradeGetBuyerViewParam.setOrderId(orderId);
        alibabaTradeGetBuyerViewParam.setWebSite("1688");

        SDKResult<AlibabaTradeGetBuyerViewResult> sdkResult =
                apiExecutor.execute(alibabaTradeGetBuyerViewParam, "e000f7e2-1353-4324-ac1e-b69b8fe80dcd");
        if(sdkResult.getErrorMessage() != null){
            throw new CustomerException(sdkResult.getErrorMessage());
        }
        AlibabaTradeGetBuyerViewResult result=sdkResult.getResult();
        return result.getResult();
    }


    public static List<AlibabaLogisticsOpenPlatformLogisticsTrace> orderShipDetail(Long orderId, AlibabaApiVo alibabaApiVo) throws CustomerException {
        ApiExecutor apiExecutor = new ApiExecutor("7715698", "DUE2C3KM9s");
        AlibabaTradeGetLogisticsTraceInfoBuyerViewParam param = new AlibabaTradeGetLogisticsTraceInfoBuyerViewParam();
        param.setOrderId(orderId);
        param.setWebSite("1688");

        SDKResult<AlibabaTradeGetLogisticsTraceInfoBuyerViewResult> sdkResult =
                apiExecutor.execute(param, "e000f7e2-1353-4324-ac1e-b69b8fe80dcd");
        if(sdkResult.getErrorMessage() != null){
            throw new CustomerException(sdkResult.getErrorMessage());
        }
        AlibabaTradeGetLogisticsTraceInfoBuyerViewResult result=sdkResult.getResult();
        return Arrays.asList(result.getLogisticsTrace());
    }


    public static void main(String[] args) {
        List<AlibabaLogisticsOpenPlatformLogisticsTrace> alibabaLogisticsOpenPlatformLogisticsTraces = null;
        try {
            alibabaLogisticsOpenPlatformLogisticsTraces = orderShipDetail(2770474212376530454L,null);
        } catch (CustomerException e) {
            e.printStackTrace();
        }
        System.out.println(alibabaLogisticsOpenPlatformLogisticsTraces);

//        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
//        try {
//            alibabaOpenplatformTradeModelTradeInfo = orderDetail(1622473212155530454L,null);
//        } catch (CustomerException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(alibabaOpenplatformTradeModelTradeInfo);
    }
}
