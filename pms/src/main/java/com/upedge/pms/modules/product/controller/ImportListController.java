package com.upedge.pms.modules.product.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.entity.ImportProductDescription;
import com.upedge.pms.modules.product.entity.ImportProductImage;
import com.upedge.pms.modules.product.entity.ImportProductVariant;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 海桐
 */
@Slf4j
@RestController
//@RequestMapping("/import")
public class ImportListController {

    @Autowired
    ImportProductService importProductService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ImportProductAttributeService importProductAttributeService;

    @Autowired
    ImportProductImageService importProductImageService;

    @Autowired
    private ImportProductVariantService importProductVariantService;

    @Autowired
    private ImportProductDescriptionService importProductDescriptionService;

//    @Autowired
//    UmsFeignClient umsFeignClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

//    @ApiOperation("导入速卖通产品")
//    @PostMapping("/add/ae")
//    public ImportAeProductResponse importAeProduct(@RequestBody @Valid ImportAeProductRequest request) {
//        String url = request.getUrl();
//        Session session = UserUtil.getSession(redisTemplate);
//        BaseResponse baseResponse = umsFeignClient.getCustomerAe(session.getCustomerId());
//        if(ResultCode.FAIL_CODE == baseResponse.getCode()){
//            return new ImportAeProductResponse(ResultCode.FAIL_CODE, "Please re-authorize AliExpress first!");
//        }
//
//        return importProductService.importAeProduct(url, session, (String) baseResponse.getData());
//    }

    @ApiOperation("导入Winning Product产品")
    @PostMapping("/add/app")
    public ImportAddAppProductResponse addAppProduct(@RequestBody @Valid ImportAddAppProductRequest request) {
        return importProductService.addAppProduct(request);
    }

    @PostMapping("/list")
    @Permission(permission = "app:importproductattribute:list")
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

    @ApiOperation("importList数量")
    @PostMapping("/count")
    @Permission(permission = "app:importproductattribute:count")
    public ImportProductAttributeListResponse count(@RequestBody @Valid ImportProductAttributeListRequest request) {
        if (null == request.getT()) {
            request.setT(new ImportProductAttribute());
        }
        Session session = UserUtil.getSession(redisTemplate);
        request.getT().setCustomerId(session.getCustomerId());
        Long total = importProductAttributeService.count(request);
        request.setTotal(total);
        return new ImportProductAttributeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, total, request);

    }


    @RequestMapping(value="/product/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductattribute:update")
    public ImportProductAttributeUpdateResponse productUpdate(@PathVariable Long id, @RequestBody @Valid ImportProductAttributeUpdateRequest request) {
        ImportProductAttribute entity=request.toImportProductAttribute(id);
        importProductAttributeService.updateByPrimaryKeySelective(entity);
        ImportProductAttributeUpdateResponse res = new ImportProductAttributeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @GetMapping("/{id}/variants")
    public ImportProductVariantListResponse productVariants(@PathVariable Long id) {
        ImportProductVariantListRequest request = new ImportProductVariantListRequest();

        ImportProductVariant variant = new ImportProductVariant();
        variant.setProductId(id);

        request.setT(variant);

        return importProductService.selectProductVariants(id);
    }

    @GetMapping("/{id}/images")
    public ImportProductImageListResponse productImageList(@PathVariable Long id) {
        ImportProductImageListRequest request = new ImportProductImageListRequest();
        ImportProductImage image = new ImportProductImage();
        image.setProductId(id);
        request.setT(image);
        List<ImportProductImage> results = importProductImageService.select(request);
        ImportProductImageListResponse res = new ImportProductImageListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results);
        return res;
    }

    @GetMapping("/{id}/description")
    public ImportProductDescriptionListResponse productDescription(@PathVariable Long id) {
        return importProductService.selectProductDescription(id);
    }

    @ApiOperation("批量修改变体价格")
    @PostMapping("/variant/update/price")
    public ImportVariantBatchUpdateResponse batchUpdateVariantPrice(@RequestBody @Valid ImportVariantBatchUpdateRequest request){
        return importProductService.batchUpdateVariantPrice(request);
    }

    @ApiOperation("批量修改变体state")
    @PostMapping("/variant/update/state")
    public VariantUpdateStateResponse variantUpdateState(@RequestBody @Valid ImportVariantUpdateStateRequest request){
        return importProductService.updateVariantState(request);
    }

    @ApiOperation("导入产品到店铺")
    @PostMapping("/upload/store")
    public UploadProductToStoreResponse uploadProductToStore(@RequestBody @Valid UploadProductToStoreRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return importProductAttributeService.importProductToStore(request,session);
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

//    @ApiOperation("importList批量删除")
//    @PostMapping("/remove/batch")
//    public ImportProductAttributeDelResponse batchRemove(@RequestBody List<Long> ids){
//        ids.forEach(id ->{
//            importProductService.importProductRemove(id);
//        });
//        ImportProductAttributeDelResponse res = new ImportProductAttributeDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }

    @RequestMapping(value="/variant/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariant:update")
    public ImportProductVariantUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductVariantUpdateRequest request) {
        ImportProductVariant entity=request.toImportProductVariant(id);
        importProductVariantService.updateByPrimaryKeySelective(entity);
        ImportProductVariantUpdateResponse res = new ImportProductVariantUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/image/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductimage:update")
    public ImportProductImageUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductImageUpdateRequest request) {
        ImportProductImage entity=request.toImportProductImage(id);
        importProductImageService.updateByPrimaryKeySelective(entity);
        ImportProductImageUpdateResponse res = new ImportProductImageUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/description/{id}/update", method=RequestMethod.POST)
    @Permission(permission = "app:importproductdescription:update")
    public ImportProductDescriptionUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductDescriptionUpdateRequest request) {
        ImportProductDescription entity=request.toImportProductDescription(id);
        importProductDescriptionService.updateByPrimaryKeySelective(entity);
        ImportProductDescriptionUpdateResponse res = new ImportProductDescriptionUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }
}
