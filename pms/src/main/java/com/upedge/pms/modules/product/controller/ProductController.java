package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.UploadImgUtil;
import com.upedge.common.utils.UrlUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.AppProductService;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.product.vo.AddProductVo;
import com.upedge.pms.modules.product.vo.AppProductVo;
import com.upedge.pms.modules.product.vo.ProductVo;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.alibaba.vo.AlibabaProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    ProductVariantService productVariantService;

    @Autowired
    OmsFeignClient omsFeignClient;

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
            return BaseResponse.failed("产品不可重复导入");
        }
        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
        AlibabaProductVo AlibabaProductVo= Ali1688Service.getProduct(request.getOriginalProductId(),alibabaApiVo);
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

    @ApiOperation("产品展示详情")
    @GetMapping("/showDetail/{id}")
    public BaseResponse productShowDetail(@PathVariable Long id){
        AppProductVo appProductVo = productService.showCustomerProductDetail(id);
        return BaseResponse.success(appProductVo);
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

    @ApiOperation("废弃池列表")
    @PostMapping("/abandonList")
    public BaseResponse abandonProductList(@RequestBody @Valid ProductListRequest request) {

        if(request.getT()==null){
            Product product=new Product();
            request.setT(product);
        }
        Product p=request.getT();
        p.setState(5);
        request.setOrderBy("create_time desc");
        List<Product> results = productService.select(request);
        Long total = productService.count(request);
        request.setTotal(total);
        return BaseResponse.success(results,request);
    }

    /**
     *释放产品到选品池
     */
    @ApiOperation("释放产品到选品池")
//    @RequestMapping(value="/multiRelease", method=RequestMethod.POST)
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

    @ApiOperation("恢复产品")
    @RequestMapping(value="/restoreProduct/{id}", method=RequestMethod.POST)
    public BaseResponse restoreProduct(@PathVariable Long id) throws Exception {
        Product product = productService.selectByPrimaryKey(id);
        if (product == null
        || product.getState() != 5){
            return BaseResponse.failed();
        }
        product = new Product();
        product.setId(id);
        product.setState(1);
        product.setUpdateTime(new Date());
        productService.updateByPrimaryKeySelective(product);
        return BaseResponse.success();
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
        try {
            return productService.putawayProduct(id,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("修改产品信息")
    @RequestMapping(value = "/updateInfo/{id}",method = RequestMethod.POST)
    public BaseResponse updateInfo(@PathVariable Long id,@RequestBody @Valid UpdateInfoProductRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = null;
        try {
            response = productService.updateInfo(id,request,session);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        if (response.getCode() == ResultCode.SUCCESS_CODE && null != request.getShippingId()){

        }
        return response;
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

        try {
            return productService.uploadToSaihe(request);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("产品导入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cateType",value = "备库=0，批发=1",required = true)
    })
    @PostMapping("/importCart")
    public BaseResponse productImportCart(@RequestBody @Valid ProductImportCartRequest request){
        Session session = UserUtil.getSession(redisTemplate);

        Product product = productService.selectByPrimaryKey(request.getProductId());
        ProductVariant variant = productVariantService.selectByPrimaryKey(request.getVariantId());

        CartAddRequest cartAddRequest = new CartAddRequest();
        cartAddRequest.setCartType(1);
        cartAddRequest.setCustomerId(session.getCustomerId());
        cartAddRequest.setQuantity(request.getQuantity());
        cartAddRequest.setVariantId(variant.getId());
        cartAddRequest.setUsdPrice(variant.getUsdPrice());
        cartAddRequest.setVariantImage(request.getImage());
        cartAddRequest.setVariantName(variant.getEnName());
        cartAddRequest.setVariantSku(variant.getVariantSku());
        cartAddRequest.setProductId(product.getId());
        cartAddRequest.setProductTitle(product.getProductTitle());
        cartAddRequest.setWeight(variant.getWeight());
        cartAddRequest.setVolume(variant.getVolumeWeight());
        cartAddRequest.setMarkId(variant.getId());
        return omsFeignClient.cartAdd(cartAddRequest);

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
        request.setOrderBy("update_time desc");
        List<Product> results = productService.select(request);
        Long total = productService.count(request);
        request.setTotal(total);
        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


    @ApiOperation("修改产品信息")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:product:update")
    public BaseResponse update(@PathVariable Long id,@RequestBody @Valid ProductUpdateRequest request) {
        Product entity=request.toProduct(id);
        try {
            productService.updateByPrimaryKeySelective(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        BaseResponse res = new ProductUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


    @GetMapping("/variantDetail/{id}")
    public List<VariantDetail> getVariantDetail(@PathVariable Long id){
        Product product = productService.selectByPrimaryKey(id);
        if (null == product){
            return null;
        }
        String productTitle = product.getProductTitle();
        Long shippingId = product.getShippingId();
        List<ProductVariant> productVariants = productVariantService.selectByProductId(id);

        List<VariantDetail> variantDetails = new ArrayList<>();
        for (ProductVariant productVariant : productVariants) {
            VariantDetail variantDetail = new VariantDetail();
            BeanUtils.copyProperties(productVariant,variantDetail);
            variantDetail.setCnyPrice(productVariant.getVariantPrice());
            variantDetail.setProductTitle(productTitle);
            variantDetail.setProductShippingId(shippingId);
            variantDetail.setVariantId(productVariant.getId());
            variantDetail.setVolume(productVariant.getVolumeWeight());
            variantDetail.setVariantName(productVariant.getEnName());
            variantDetails.add(variantDetail);
        }
        return variantDetails;
    }


    @ApiOperation("根据产品ID获取变体")
    @GetMapping("/variants/{id}")
    public BaseResponse productVariants(@PathVariable Long id){
        List<ProductVariant> productVariants = productVariantService.selectByProductId(id);
        return BaseResponse.success(productVariants);
    }

}
