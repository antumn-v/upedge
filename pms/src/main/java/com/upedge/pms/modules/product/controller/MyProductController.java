package com.upedge.pms.modules.product.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.entity.ImportProductDescription;
import com.upedge.pms.modules.product.entity.ImportProductImage;
import com.upedge.pms.modules.product.entity.ImportProductVariant;
import com.upedge.pms.modules.product.request.ImportAddAppProductRequest;
import com.upedge.pms.modules.product.request.ImportProductAttributeListRequest;
import com.upedge.pms.modules.product.request.ImportProductPublishRequest;
import com.upedge.pms.modules.product.response.ImportProductAttributeListResponse;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.ImportVariantVo;
import com.upedge.pms.modules.product.vo.MyProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "My Product")
@RestController
@RequestMapping("/myProduct")
public class MyProductController {



    @Autowired
    ImportProductService importProductService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ImportProductAttributeService importProductAttributeService;

    @Autowired
    ImportProductImageService importProductImageService;

    @Autowired
    private ImportProductVariantService importProductVariantService;

    @Autowired
    private ImportProductDescriptionService importProductDescriptionService;

    @ApiOperation("从winning product导入")
    @PostMapping("/import/winningProduct")
    public BaseResponse importFormWinningProduct(@RequestBody @Valid ImportAddAppProductRequest request){
        return importProductService.addAppProduct(request);
    }


    @ApiOperation("My Product列表")
    @PostMapping("/list")
    public ImportProductAttributeListResponse list(@RequestBody @Valid ImportProductAttributeListRequest request) {
        if (null == request.getT()) {
            request.setT(new ImportProductAttribute());
        }
        Session session = UserUtil.getSession(redisTemplate);
        request.getT().setCustomerId(session.getCustomerId());
        request.setOrderBy("create_time desc");
        List<ImportProductAttribute> results = importProductAttributeService.select(request);
        Long total = importProductAttributeService.count(request);
        request.setTotal(total);
        return new ImportProductAttributeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }


    @GetMapping("/{id}/detail")
    public BaseResponse myProductDetail(@PathVariable Long id){
        ImportProductAttribute importProductAttribute = importProductAttributeService.selectByPrimaryKey(id);
        if (null == importProductAttribute){
            return BaseResponse.failed();
        }
        ImportProductDescription importProductDescription = importProductDescriptionService.selectByProductId(id);
        List<ImportVariantVo> variants = importProductVariantService.selectByProductId(id);
        List<ImportProductImage> images = importProductImageService.selectByProductId(id);
        MyProductVo myProductVo = new MyProductVo();
        BeanUtils.copyProperties(importProductAttribute,myProductVo);
        myProductVo.setDescription(importProductDescription.getDescription());
        myProductVo.setImages(images);
        myProductVo.setVariants(variants);
        return BaseResponse.success(myProductVo);
    }


    @PostMapping("/publish")
    public BaseResponse publishToStore(@RequestBody@Valid ImportProductPublishRequest request){
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + request.getStoreId());
        List<Long> productIds = request.getProductIds();
        for (Long productId : productIds) {
            importProductService.uploadProductToShopify(storeVo,productId);
        }
        return BaseResponse.success();
    }
}
