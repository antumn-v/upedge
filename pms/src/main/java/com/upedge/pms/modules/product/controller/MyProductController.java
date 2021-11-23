package com.upedge.pms.modules.product.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
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
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.ImportVariantVo;
import com.upedge.pms.modules.product.vo.MyProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation("产品上架店铺")
    @PostMapping("/publish")
    public BaseResponse publishToStore(@RequestBody@Valid ImportProductPublishRequest request){
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + request.getStoreId());
        List<Long> productIds = request.getProductIds();
        for (Long productId : productIds) {
            importProductService.uploadProductToShopify(storeVo,productId);
        }
        return BaseResponse.success();
    }


    @ApiOperation("批量修改变体价格")
    @PostMapping("/variant/updatePrice")
    public ImportVariantBatchUpdateResponse batchUpdateVariantPrice(@RequestBody @Valid ImportVariantBatchUpdateRequest request){
        return importProductService.batchUpdateVariantPrice(request);
    }

    @ApiOperation("批量修改变体state")
    @PostMapping("/variant/updateState")
    public VariantUpdateStateResponse variantUpdateState(@RequestBody @Valid ImportVariantUpdateStateRequest request){
        return importProductService.updateVariantState(request);
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "app:importproductattribute:update")
    public ImportProductAttributeUpdateResponse productUpdate(@PathVariable Long id, @RequestBody @Valid ImportProductAttributeUpdateRequest request) {
        ImportProductAttribute entity=request.toImportProductAttribute(id);
        importProductAttributeService.updateByPrimaryKeySelective(entity);
        ImportProductAttributeUpdateResponse res = new ImportProductAttributeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("importList删除产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id",required = false,paramType = "body"),
            @ApiImplicitParam(name = "productId", value = "产品ID",required = false,paramType = "body"),
            @ApiImplicitParam(name = "ids",value = "id集合",required = false,paramType = "body")
    })
    @RequestMapping(value="/remove", method=RequestMethod.POST)
    @Permission(permission = "app:importproductattribute:del:id")
    public ImportProductAttributeDelResponse del(@RequestBody ImportListRemoveRequest request) {
        importProductService.importProductRemove(request);
        ImportProductAttributeDelResponse res = new ImportProductAttributeDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


    @ApiOperation("变体修改")
    @RequestMapping(value="/variant/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariant:update")
    public ImportProductVariantUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductVariantUpdateRequest request) {
        ImportProductVariant entity=request.toImportProductVariant(id);
        importProductVariantService.updateByPrimaryKeySelective(entity);
        ImportProductVariantUpdateResponse res = new ImportProductVariantUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("图片修改")
    @RequestMapping(value="/image/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductimage:update")
    public ImportProductImageUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductImageUpdateRequest request) {
        ImportProductImage entity=request.toImportProductImage(id);
        importProductImageService.updateByPrimaryKeySelective(entity);
        ImportProductImageUpdateResponse res = new ImportProductImageUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("产品描述修改")
    @RequestMapping(value="/description/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductdescription:update")
    public ImportProductDescriptionUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductDescriptionUpdateRequest request) {
        ImportProductDescription entity= new ImportProductDescription();
        entity.setProductId(id);
        entity.setDescription(request.getDesc());
        importProductDescriptionService.updateByProductId(entity);
        ImportProductDescriptionUpdateResponse res = new ImportProductDescriptionUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }
}
