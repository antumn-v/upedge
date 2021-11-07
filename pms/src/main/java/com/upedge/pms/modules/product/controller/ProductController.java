package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.UploadImgUtil;
import com.upedge.common.utils.UrlUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.AppProductService;
import com.upedge.pms.modules.product.vo.AddProductVo;
import com.upedge.pms.modules.product.vo.ProductVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.service.ProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.StringJoiner;

import com.upedge.common.constant.Constant;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 商品信息
 *
 * @author gx
 */
@Api(tags = "产品管理")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    AppProductService appProductService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("1688导入产品")
    @RequestMapping(value="/importFrom1688", method=RequestMethod.POST)
    public BaseResponse importFrom1688(@RequestBody ImportFrom1688Request request) {
        if(!StringUtils.isBlank(request.getUrl())){
            String aliProductId= UrlUtils.getNameByUrl(request.getUrl());
            request.setOriginalProductId(aliProductId);
        }
        if(StringUtils.isBlank(request.getOriginalProductId())){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Product p=productService.selectByProductSku(request.getOriginalProductId());
        if(p!=null){
            return BaseResponse.success();
        }
        AlibabaProductVo AlibabaProductVo= Ali1688Service.getProduct(request.getOriginalProductId());
        Session session = UserUtil.getSession(redisTemplate);
        if(AlibabaProductVo==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        try {
            return productService.importFrom1688(AlibabaProductVo,session);
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }
    }


    /**
     * 选品池列表
     * @param request
     * @return
     */
    @ApiOperation("选品池列表")
    @RequestMapping(value="/selectionList", method=RequestMethod.POST)
    public ProductListResponse list(@RequestBody @Valid ProductListRequest request) {
        if(request.getT()==null){
            Product product=new Product();
            request.setT(product);
        }
        Product p=request.getT();
        if(!StringUtils.isBlank(p.getProductTitle())){
            p.setOriginalTitle(p.getProductTitle());
            p.setProductTitle(null);
        }
        p.setState(0);
        request.setFields("p.id,p.product_sku,p.original_title,p.product_title,p.product_image,p.create_time");
        request.setOrderBy("update_time desc");
        return productService.selectionList(request);
    }

    /**
     * 导入收藏夹
     * @param request
     * @return
     */
    @ApiOperation("导入收藏夹")
    @RequestMapping(value="/importFavorite", method=RequestMethod.POST)
    public ImportFavoriteResponse importFavorite(@RequestBody @Valid ImportFavoriteRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productService.importFavorite(request,session);
    }


    @RequestMapping(value="/ship/{shippingId}/count", method= RequestMethod.POST)
    BaseResponse countProductByShippingId(@PathVariable Long shippingId){
        Page<Product> page = new Page<>();
        Product product = new Product();
        product.setShippingId(shippingId);
        page.setT(product);
        Long count = productService.count(page);
        return BaseResponse.success(count);
    }

    /**
     * 收藏夹列表
     * @param request
     * @return
     */
    @ApiOperation("收藏夹列表")
    @RequestMapping(value="/favoriteList", method=RequestMethod.POST)
    public ProductListResponse favoriteList(@RequestBody @Valid ProductListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if(request.getT()==null){
            Product product=new Product();
            request.setT(product);
        }
        Product p=request.getT();
        p.setUserId(String.valueOf(session.getId()));
        p.setState(1);
        request.setFields("p.id,p.product_sku,p.product_title,p.product_image,p.create_time,p.user_id,p.cate_type," +
                "p.update_time");
        request.setOrderBy("update_time desc");
        return productService.favoriteList(request);
    }

    /**
     *释放产品到选品池
     */
    @ApiOperation("释放产品到选品池")
    @RequestMapping(value="/multiRelease", method=RequestMethod.POST)
    public MultiReleaseResponse multiRelease(@RequestBody @Valid MultiReleaseRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productService.multiRelease(request,session);
    }

    /**
     * 废弃产品
     */
    @ApiOperation("废弃产品")
    @RequestMapping(value="/abandonProduct/{id}", method=RequestMethod.POST)
    public AbandonProductResponse abandonProduct(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return productService.abandonProduct(id,session);
    }

    @ApiOperation("产品详情")
    @GetMapping("/detail/{id}")
    public BaseResponse productDetail(@PathVariable Long id) {
        ProductVo productVo = productService.productDetail(id);
        return BaseResponse.success(productVo);
    }

    @ApiOperation("产品变体运输方式")
    @PostMapping("/variant/ships")
    public AppVariantShipsResponse variantShips(@RequestBody @Valid AppVariantShipsRequest request){
        Session session = UserUtil.getSession(redisTemplate);

        return appProductService.variantShips(request,session);
    }

    /**
     *产品上架
     */
    @ApiOperation("产品上架")
    @RequestMapping(value="/putaway/{id}",method = RequestMethod.POST)
    public BaseResponse putawayProduct(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return productService.putawayProduct(id,session);
    }

    /**
     *产品下架
     */
    @ApiOperation("产品下架")
    @RequestMapping(value="/unshelve/{id}",method = RequestMethod.POST)
    public BaseResponse unshelveProduct(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return productService.unshelveProduct(id,session);
    }


    /**
     * 添加个人产品
     */
    @RequestMapping(value="/addProduct",method = RequestMethod.POST)
    public BaseResponse addProduct(@RequestBody @Valid AddProductVo addProductVo){
        Session session = UserUtil.getSession(redisTemplate);
        return productService.addProduct(addProductVo,session);
    }

    /**
     * 添加个人产品（上传图片）
     */
    @RequestMapping(value = "/addProduct/uploadImg",method = RequestMethod.POST)
    public BaseResponse addProductUploadImg(@RequestParam("file") List<MultipartFile> files) {
        List<String> imgList= UploadImgUtil.uploadImg(files,UploadImgUtil.IMGDIR.PRODUCT.getDir());
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,imgList);
    }

    /**
     * 产品导入赛盒
     */
    @ApiOperation("产品导入赛盒")
    @RequestMapping(value = "/uploadToSaihe",method = RequestMethod.POST)
    public BaseResponse uploadToSaihe(@RequestBody @Valid ProductUoloadToSaiheRequest request){
        if(!SaiheConfig.SAIHE_PRODUCT_SWITCH){
            return new BaseResponse(ResultCode.FAIL_CODE,"未开启");
        }
        Session session = UserUtil.getSession(redisTemplate);
        return productService.uploadToSaihe(request);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:product:info:id")
    public ProductInfoResponse info(@PathVariable Long id) {
        Product result = productService.selectByPrimaryKey(id);
        ProductInfoResponse res = new ProductInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:product:list")
    public ProductListResponse productList(@RequestBody @Valid ProductListRequest request) {
        List<Product> results = productService.select(request);
        Long total = productService.count(request);
        request.setTotal(total);
        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:product:update")
    public ProductUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductUpdateRequest request) {
        Product entity=request.toProduct(id);
        productService.updateByPrimaryKeySelective(entity);
        ProductUpdateResponse res = new ProductUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}