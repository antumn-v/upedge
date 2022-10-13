package com.upedge.pms.modules.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.response.StoreProductListResponse;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import com.upedge.thirdparty.shopify.moudles.product.controller.ShopifyProductApi;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 海桐
 */
@Api("店铺产品")
@RestController
@RequestMapping("/store/product")
public class StoreProductController {

    @Autowired
    StoreProductService storeProductService;

    @Autowired
    StoreProductAttributeService storeProductAttributeService;

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("店铺产品列表")
    @PostMapping("/list")
    public StoreProductListResponse storeProductList(@RequestBody StoreProductListRequest request){
        return storeProductService.storeProductList(request);
    }

    @ApiOperation("店铺产品列表数量")
    @PostMapping("/count")
    public BaseResponse storeProductListCount(@RequestBody StoreProductListRequest request){
        return storeProductService.storeProductListCount(request);
    }

    @ApiOperation("转换成普通产品")
    @PostMapping("/toNormalProduct/{id}")
    public BaseResponse toNormalProduct(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return storeProductService.toNormalProduct(id,session.getId());
    }
//    @ApiOperation("店铺产品关联详情")
//    @GetMapping("/{id}/relateDetail")
    public BaseResponse storeProductRelateDetail(@PathVariable Long id){
        List<StoreProductRelateVo> storeProductRelateVos = storeProductService.selectStoreVariantRelateDetail(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,storeProductRelateVos);
    }

    @ApiOperation("查看所有店铺产品")
    @PostMapping("/all")
    public BaseResponse allStoreProducts(@RequestBody StoreProductListRequest request){
        if (null != request.getT()){
            String sku = request.getT().getStoreVariantSku();
            if (StringUtils.isNotBlank(sku)){
                List<StoreProductVariant> storeProductVariants = storeProductVariantService.selectBySku(sku);
                if (ListUtils.isNotEmpty(storeProductVariants)){
                    if (storeProductVariants.size() == 1){
                        request.getT().setId(storeProductVariants.get(0).getProductId());
                    }else {
                        List<Long> ids = new ArrayList<>();
                        for (StoreProductVariant storeProductVariant : storeProductVariants) {
                            ids.add(storeProductVariant.getProductId());
                        }
                        request.getT().setIds(ids);
                    }
                }
            }
        }
        List<StoreProductAttribute> attributes = storeProductAttributeService.selectStoreProduct(request);
        Long total = storeProductAttributeService.countStoreProduct(request);
        request.setTotal(total);
        return BaseResponse.success(attributes,request);
    }

    @ApiOperation("获取店铺产品图片")
    @GetMapping("/images/{id}")
    public BaseResponse storeProductImages(@PathVariable Long id){
        StoreProductAttribute storeProductAttribute = storeProductAttributeService.selectByPrimaryKey(id);
        if (null == storeProductAttribute){
            return BaseResponse.success(new ArrayList<>());
        }
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + storeProductAttribute.getStoreId());
        try {
            List<ShopifyImage> shopifyImages = ShopifyProductApi.getProductImage(storeVo.getApiToken(),storeVo.getStoreName(),Long.parseLong(storeProductAttribute.getPlatProductId()));
            return BaseResponse.success(shopifyImages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success(new ArrayList<>());
    }


    @GetMapping("/getSingleShopifyProduct")
    public BaseResponse getSingleShopifyProduct(Long storeId,String productId){
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + storeId);
        JSONObject jsonObject = ShopifyProductApi.getProduct(productId,storeVo.getApiToken(),storeVo.getStoreName());
        ShopifyProduct shopifyProduct = jsonObject.getJSONObject("product").toJavaObject(ShopifyProduct.class);
        storeProductService.saveShopifyProduct(shopifyProduct, storeVo);
        return BaseResponse.success();
    }
}
