package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.request.AppVariantShipsRequest;
import com.upedge.pms.modules.product.request.ImportProductAttributeListRequest;
import com.upedge.pms.modules.product.request.MarketPlaceListRequest;
import com.upedge.pms.modules.product.request.PrivateProductListRequest;
import com.upedge.pms.modules.product.response.AppVariantShipsResponse;
import com.upedge.pms.modules.product.response.MarketPlaceListResponse;
import com.upedge.pms.modules.product.response.ProductImgListResponse;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.AppProductVariantAttrVo;
import com.upedge.pms.modules.product.vo.AppProductVariantVo;
import com.upedge.pms.modules.product.vo.AppProductVo;
import com.upedge.pms.modules.product.vo.VariantAttributeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * market place
 * @author 海桐
 */
@RestController
@RequestMapping("/app/product")
public class AppProductController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    AppProductService appProductService;

    @Autowired
    ImportProductAttributeService importProductAttributeService;

    @Autowired
    ImportProductService  importProductService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVariantService productVariantService;

//    @Autowired
//    OmsFeignClient omsFeignClient;

    @PostMapping("/private")
    public MarketPlaceListResponse privateProductList(@RequestBody PrivateProductListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setCustomerId(session.getCustomerId());
        return appProductService.customerPrivateProduct(request);
    }

    @ApiOperation("Manager Products")
    @PostMapping("/marketPlace")
    public MarketPlaceListResponse marketPlace(@RequestBody MarketPlaceListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if(request.getT()==null){
            AppProductVo productVo = new AppProductVo();
            request.setT(productVo);
        }
        return appProductService.marketPlaceList(request,session);
    }

    @GetMapping("/{id}/detail")
    public BaseResponse productDetail(@PathVariable Long id){
        Product product =  productService.selectByPrimaryKey(id);
        AppProductVo appProductVo=  new AppProductVo();
        if (product != null){
            BeanUtils.copyProperties(product,appProductVo);
            appProductVo.initUsdPriceRange();
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,product);
    }

    @GetMapping("/{id}/variants")
    public BaseResponse productVariantList(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        List<AppProductVariantVo> variantVos = appProductService.productVariants(id);
        Map<String, Set<String>> attributeMap = new HashMap<>();
        for (AppProductVariantVo variantVo : variantVos) {
            List<AppProductVariantAttrVo> attrs = variantVo.getAttrs();
            attrs.forEach(appProductVariantAttrVo -> {
                if (!attributeMap.containsKey(appProductVariantAttrVo.getVariantAttrEname())){
                    attributeMap.put(appProductVariantAttrVo.getVariantAttrEname(),new HashSet<>());
                }
                attributeMap.get(appProductVariantAttrVo.getVariantAttrEname()).add(appProductVariantAttrVo.getVariantAttrEvalue());
            });
            if (variantVo.getUsdPrice() != null){
                variantVo.setVariantPrice(variantVo.getUsdPrice());
            }else {
                BigDecimal price = PriceUtils.cnyToUsdByDefaultRate(variantVo.getVariantPrice());
                variantVo.setVariantPrice(price);
            }
        }

        map.put("variants",variantVos);
        map.put("attributes",attributeMap);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,map);
    }


    List<VariantAttributeVo> getVariantAttributeVos(Long id){
        List<VariantAttributeVo> VariantAttributeVos = new ArrayList<>();
        List<AppProductVariantVo> variantVos = appProductService.productVariants(id);
        Map<String, Set<String>> attributeMap = new HashMap<>();
        for (AppProductVariantVo variantVo : variantVos) {
            List<AppProductVariantAttrVo> attrs = variantVo.getAttrs();
            attrs.forEach(appProductVariantAttrVo -> {
                if (!attributeMap.containsKey(appProductVariantAttrVo.getVariantAttrEname())){
                    attributeMap.put(appProductVariantAttrVo.getVariantAttrEname(),new HashSet<>());
                }
                attributeMap.get(appProductVariantAttrVo.getVariantAttrEname()).add(appProductVariantAttrVo.getVariantAttrEvalue());
            });
            if (variantVo.getUsdPrice() != null){
                variantVo.setVariantPrice(variantVo.getUsdPrice());
            }else {
                BigDecimal price = PriceUtils.cnyToUsdByDefaultRate(variantVo.getVariantPrice());
                variantVo.setVariantPrice(price);
            }
        }
        for (String name: attributeMap.keySet()){
            Set<String> values = attributeMap.get(name);
            VariantAttributeVo VariantAttributeVo = new VariantAttributeVo();
            VariantAttributeVo.setProductId(id);

            VariantAttributeVos.add(VariantAttributeVo);
        }
        return VariantAttributeVos;
    }

    @GetMapping("/{id}/info")
    public BaseResponse productInfo(@PathVariable Long id){

        ProductInfo productInfo = appProductService.selectProductInfo(id);

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,productInfo);
    }

    @GetMapping("/{id}/images")
    public ProductImgListResponse productImages(@PathVariable Long id){
        return appProductService.selectProductImages(id);
    }

    @GetMapping("/{id}/import/check")
    public BaseResponse checkProductImported(@PathVariable String id){
        Session session = UserUtil.getSession(redisTemplate);
        ImportProductAttribute attribute = new ImportProductAttribute();
        attribute.setSourceProductId(id);
        attribute.setState(0);
        attribute.setCustomerId(session.getCustomerId());
        ImportProductAttributeListRequest request = new ImportProductAttributeListRequest();
        request.setT(attribute);
        Long total = importProductAttributeService.count(request);
        if(null == total || 0 == total){
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,true);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,false);
    }

//    @PostMapping("/{id}/import/remove")
//    public ImportProductAttributeDelResponse removeImportByAppId(@PathVariable Long id){
//        ImportProductAttribute attribute = new ImportProductAttribute();
//        attribute.setSourceProductId(id);
//        attribute.setState(0);
//        ImportProductAttributeListRequest request = new ImportProductAttributeListRequest();
//        request.setT(attribute);
//        List<ImportProductAttribute> attributes = importProductAttributeService.select(request);
//        if(null != attributes){
//            attributes.forEach(importProductAttribute -> {
//                importProductService.importProductRemove(importProductAttribute.getId());
//            });
//        }
//        return new ImportProductAttributeDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//    }

    @PostMapping("/variant/ships")
    public AppVariantShipsResponse variantShips(@RequestBody @Valid AppVariantShipsRequest request){
        Session session = UserUtil.getSession(redisTemplate);

        return appProductService.variantShips(request,session);
    }



    @PostMapping("/search/ships")
    public BaseResponse productSearchShips(@RequestBody ProductVariantShipsRequest request){
        if(ListUtils.isEmpty(request.getVariantDetails())){
            return BaseResponse.failed();
        }
        return appProductService.productVariantsShipList(request);
    }
}
