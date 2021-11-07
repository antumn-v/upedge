package com.upedge.pms.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserInfoVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.category.entity.Category;
import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.pms.modules.category.service.CategoryMappingService;
import com.upedge.pms.modules.category.service.CategoryService;
import com.upedge.pms.modules.product.dao.ImportProductAttributeDao;
import com.upedge.pms.modules.product.entity.*;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.AbandonProductResponse;
import com.upedge.pms.modules.product.response.ImportFavoriteResponse;
import com.upedge.pms.modules.product.response.MultiReleaseResponse;
import com.upedge.pms.modules.product.response.ProductListResponse;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.*;
import com.upedge.pms.modules.supplier.entity.Supplier;
import com.upedge.pms.modules.supplier.service.SupplierService;
import com.upedge.thirdparty.ali1688.entity.translate.TranslateResult;
import com.upedge.thirdparty.ali1688.service.TranslateService;
import com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.processUpdateProduct.*;
import com.upedge.thirdparty.saihe.service.SaiheService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductDao;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ImportProductAttributeDao importProductAttributeDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductVariantAttrService productVariantAttrService;

    @Autowired
    ProductImgService productImgService;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ProductAttributeService productAttributeService;

    @Autowired
    ProductAttrService productAttrService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    CategoryMappingService categoryMappingService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CustomerPrivateProductService customerPrivateProductService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Product record = new Product();
        record.setId(id);
        return productDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Override
    @Transactional
    public int insert(Product record) {
        return productDao.insert(record);
    }

    /**
     *
     */
    @Override
    @Transactional
    public int insertSelective(Product record) {
        return productDao.insert(record);
    }

    @Override
    public String refreshProductPriceRange(Long productId) {
        Map<String, BigDecimal> map = productVariantService.selectVariantPriceRange(productId);
        if (MapUtils.isEmpty(map)){
            return null;
        }
        if(
                null != map.get("minPrice") &&
                        null != map.get("maxPrice")){
            BigDecimal minPrice = map.get("minPrice");
            BigDecimal maxPrice = map.get("maxPrice");
            String priceRange = "0";
            if(minPrice.compareTo(maxPrice) == 0){
                priceRange = minPrice.toString();
            }else {
                priceRange = minPrice + "~" + maxPrice;
            }
            productDao.updatePriceRangeById(priceRange,productId,minPrice,maxPrice);
            return priceRange;
        }
        return null;
    }

    @Override
    public BaseResponse winningProductList(WinningProductListRequest request, Session session) {

        List<AppProductVo> productVos = productDao.selectWinningProducts(request);

        if (ListUtils.isNotEmpty(productVos)) {
            List<String> sourceProductIds = importProductAttributeDao.selectImportedSourceProductIds(session.getCustomerId(), productVos);
            boolean b = ListUtils.isNotEmpty(sourceProductIds);
            productVos.forEach(appProductVo -> {
                if (StringUtils.isBlank(appProductVo.getPriceRange())){
                    appProductVo.setPriceRange(refreshProductPriceRange(appProductVo.getId()));
                }
                if (b) {
                    if (sourceProductIds.contains(String.valueOf(appProductVo.getId()))) {
                        appProductVo.setImportState(1);
                    } else {
                        appProductVo.setImportState(0);
                    }
                }else {
                    appProductVo.setImportState(0);
                }
            });
        }
        Long total = productDao.countWinningProduct(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, productVos, request);
    }

    @Override
    public ProductVo productDetail(Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        if(product==null){
            return null;
        }
        ProductVo adminProductVo=new ProductVo()
                ;
        BeanUtils.copyProperties(product,adminProductVo);
        Category category=categoryService.selectByPrimaryKey(product.getCategoryId());
        if(category!=null) {
            adminProductVo.setCategoryId(Long.parseLong(category.getCateCode()));
        }
        //开启异步任务 获取属性
        CompletableFuture<Void> productAttributeFuture = CompletableFuture.runAsync(() -> {
            ProductAttribute productAttribute=productAttributeService.selectByProductId(id);
            adminProductVo.setProductAttribute(productAttribute);
        }, threadPoolExecutor);

        //开启异步任务  获取图片列表
        CompletableFuture<Void> productImgListFuture = CompletableFuture.runAsync(() -> {
            List<ProductImg> productImgList=productImgService.selectByProductId(id);
            adminProductVo.setProductImgList(productImgList);
        }, threadPoolExecutor);

        //开启异步任务  获取产品描述
        CompletableFuture<Void> productInfoFuture = CompletableFuture.runAsync(() -> {
            ProductInfo productInfo=productInfoService.selectByProductId(id);
            adminProductVo.setProductInfo(productInfo);
        }, threadPoolExecutor);


        //开启异步任务  获取产品描述
        List<Long> variantIds=new ArrayList<>();
        //属性-值列表
        Map<String, VariantAttrVo> attrMap=new HashMap<>();
        Map<String,Set<String>> attrValSet=new HashMap<>();
        Map<Long,ProductVariant> productVariantMap=new HashMap<>();
        CompletableFuture<Void> productVariantListFuture = CompletableFuture.runAsync(() -> {
            List<ProductVariant> productVariantList=productVariantService.selectByProductId(id);
            productVariantList.forEach(productVariant -> {
                variantIds.add(productVariant.getId());
                productVariantMap.put(productVariant.getId(),productVariant);
            });
        }, threadPoolExecutor).thenRunAsync(()->{
            //获取产品属性
            List<ProductVariant> productVariantList =productVariantService.getProductVariantList(variantIds, attrMap, attrValSet, productVariantMap);
            adminProductVo.setProductVariantList(productVariantList);

        },threadPoolExecutor);

        //等到所有任务都完成
        try {
            CompletableFuture.allOf(productAttributeFuture,productImgListFuture,productInfoFuture,productVariantListFuture).get();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //装载属性
        List<VariantAttrVo> variantAttrVoList=new ArrayList<>();
        for(VariantAttrVo v:attrMap.values()){
            variantAttrVoList.add(v);
        }
        adminProductVo.setVariantAttrVos(variantAttrVoList);

        return adminProductVo;
    }


    /**
     * 设为公有产品
     * @param id
     * @return
     */
    @Override
    public BaseResponse publicProduct(Long id) {
        Product product=productDao.selectByPrimaryKey(id);
        if(product==null||product.getProductType()==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        if(product.getProductType()!=ProductConstant.ProductType.PRIVATE.getCode()){
            return BaseResponse.success();
        }
        int existCount=customerPrivateProductService.countByProductId(id);
        if(existCount>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"请先移除授权用户！");
        }
        productDao.publicProduct(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 设为私有产品
     * @param id
     * @return
     */
    @Override
    public BaseResponse privateProduct(Long id) {
        Product product=productDao.selectByPrimaryKey(id);
        if(product==null||product.getProductType()==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        if(product.getProductType()==ProductConstant.ProductType.PRIVATE.getCode()){
            return BaseResponse.success();
        }
        productDao.privateProduct(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 添加个人产品
     * @param addProductVo
     * @param session
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addProduct(AddProductVo addProductVo,Session session) {



        if(addProductVo.getProductVariantList().size()==0){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }

        Long productId=IdGenerate.nextId();
        //变体信息
        List<ProductVariant> productVariantList=addProductVo.getProductVariantList();


        List<ProductVariantAttr> productVariantAttrList=new ArrayList<>();
        for(ProductVariant productVariant:productVariantList){
            Long variantId=IdGenerate.nextId();
            productVariant.setId(variantId);
            productVariant.setProductId(productId);
            //生成sku
            String variantSku= IdGenerate.generateUniqueId();
            productVariant.setVariantSku(variantSku);
            //重量库存异常的禁用  价格要大于0
            if(productVariant.getVariantPrice()==null||productVariant.getVariantPrice().compareTo(BigDecimal.ZERO)<=0
                    ||productVariant.getWeight()==null||productVariant.getWeight().compareTo(BigDecimal.ZERO)<=0
                    ||productVariant.getVolumeWeight()==null||productVariant.getVolumeWeight().compareTo(BigDecimal.ZERO)<=0){
                return new BaseResponse(ResultCode.FAIL_CODE,"变体价格或重量异常!");
            }else {
                //设置状态勾选
                productVariant.setState(1);
            }
            productVariant.setVariantType(0);
            List<ProductVariantAttr> variantAttrList=productVariant.getProductVariantAttrList();

            // product_variant  的中英文名{cn_name ,en_name}
            String cnName = "[";
            String enName = "[";
            // 英汉互译api参数为list
            ArrayList<String>   strList= new ArrayList<>();
            for(int i=0;i<variantAttrList.size();i++){
                ProductVariantAttr productVariantAttr=variantAttrList.get(i);
                productVariantAttr.setId(IdGenerate.nextId());
                if(StringUtils.isBlank(productVariantAttr.getVariantAttrCname())||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrEname())||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrCvalue())||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrEvalue())){
                    return new BaseResponse(ResultCode.FAIL_CODE,"变体属性异常!");
                }
                // 在这里将 variantAttrList的属性转为英文 拼接格式 并存入 productVariant的cname  ename
                String variantAttrCvalue = productVariantAttr.getVariantAttrCvalue();
                if (i+1 ==variantAttrList.size()){
                    cnName += variantAttrCvalue  +"]";
                }else{
                    cnName += variantAttrCvalue +",";
                }

                strList.clear();
                strList.add(variantAttrCvalue);
                TranslateResult translateResult = TranslateService.translate(strList);
                if(translateResult!=null&&translateResult.getResult()!=null) {
                    //英文属性值
                    String cValue = translateResult.getResult().get(variantAttrCvalue);
                    productVariantAttr.setVariantAttrCvalue(cValue);
                    if (i+1 ==variantAttrList.size()){
                        enName += cValue +"]";
                    }else{
                        enName +=  cValue +",";
                    }
                }
                productVariant.setInventory(999L);
                productVariantAttr.setVariantId(variantId);
                productVariantAttr.setProductId(productId);
                productVariantAttr.setOriginalAttrCvalue(productVariantAttr.getVariantAttrCvalue());
                productVariantAttr.setSeq(i);
                productVariantAttrList.add(productVariantAttr);
            }

            productVariant.setEnName(enName);
            productVariant.setCnName(cnName);
        }

        Product product = new Product();
        BeanUtils.copyProperties(addProductVo,product);
        product.setId(productId);
        //生成sku
        String productSku= IdGenerate.generateUniqueId();
        Product p =productDao.selectByProductSku(productSku);
        if(p != null){
            return new BaseResponse(ResultCode.FAIL_CODE,"重复sku请重试");
        }
        product.setProductSku(productSku);
        product.setOriginalId(null);
        product.setSupplierId(0L);
        product.setOriginalTitle(addProductVo.getProductTitle());
        //商品状态默认为下架
        product.setState(ProductConstant.State.UNSHELVE.getCode());
        //1:个人添加
        product.setProductSource(1);
        product.setProductType(0);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
//        product.setUserId(userCode);
        product.setReplaceState(0);
        product.setSaiheState(0);
        //获取类目id
        Category category =categoryService.selectByCateCode(String.valueOf(addProductVo.getCateCode()));
        if (category != null){
            product.setCategoryId(category.getId());
        }

        productDao.insert(product);


        //产品属性
        ProductAttribute productAttribute = addProductVo.getProductAttribute();
        productAttribute.setId(IdGenerate.nextId());
        productAttribute.setProductId(productId);
        productAttribute.setScore(5);
        productAttribute.setTurnover(0);
        productAttributeService.insert(productAttribute);
        //产品图片
        List<ProductImg> productImgList=addProductVo.getProductImgList();
        for(ProductImg productImg:productImgList) {
            productImg.setId(IdGenerate.nextId());
            productImg.setImageSeq(10);
            //状态
            productImg.setState(1);
            productImg.setProductId(productId);
        }
        productImgService.insertByBatch(productImgList);
        //产品描述
        ProductInfo productInfo=addProductVo.getProductInfo();
        productInfo.setId(IdGenerate.nextId());
        productInfo.setProductId(productId);
        productInfoService.insert(productInfo);

        //优化变体插入
        productVariantService.insertByBatch(productVariantList);
        productVariantAttrService.insertByBatch(productVariantAttrList);
        //更新价格区间
        refreshProductPriceRange(productId);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public BaseResponse importFrom1688(AlibabaProductVo AlibabaProductVo, Session session) {
        long start=System.currentTimeMillis();
        Long productId= IdGenerate.nextId();
        String mainImage = AlibabaProductVo.getProductImage();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            //产品供应商
            Supplier supplier=supplierService.selectByLoginId(AlibabaProductVo.getSupplierVo().getLoginId());
            if(supplier==null){
                supplier=new Supplier();
                BeanUtils.copyProperties(AlibabaProductVo.getSupplierVo(),supplier);
                supplier.setUpdateTime(new Date());
                supplier.setCreateTime(new Date());
                supplierService.insert(supplier);
            }

            //产品类目
            Long aliCnCategoryId=AlibabaProductVo.getProductAttributeVo().getAliCnCategoryId();
            CategoryMapping categoryMapping= categoryMappingService.selectByAliCateCode(String.valueOf(aliCnCategoryId));

            //产品
            Product product=new Product();
            BeanUtils.copyProperties(AlibabaProductVo,product);
            product.setOriginalId(AlibabaProductVo.getProductSku());
            if(categoryMapping!=null) {
                product.setCategoryId(categoryMapping.getCategoryId());
            }
            product.setSupplierId(Integer.toUnsignedLong(supplier.getId()));
            product.setId(productId);
            //导入到选品池
            product.setState(ProductConstant.State.CHOOSING.getCode());
            product.setReplaceState(ProductConstant.ReplaceState.NO.getCode());
            product.setSaiheState(ProductConstant.SaiheState.NO.getCode());
            //0:1688 1:个人添加 2:复制产品3:捆绑产品
            product.setProductSource(ProductConstant.ProductSource.AlI1688.getCode());
            //0:公有产品 1:私有产品
            product.setProductType(ProductConstant.ProductType.PUBLIC.getCode());
            //0:普通商品 1:定制包装
            product.setCateType(0);
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            //有session记录导入人
            if(session!=null){
                product.setUserId(String.valueOf(session.getId()));
            }
            productDao.insert(product);

            ProductAttribute productAttribute=new ProductAttribute();
            BeanUtils.copyProperties(AlibabaProductVo.getProductAttributeVo(),productAttribute);
            productAttribute.setId(IdGenerate.nextId());
            productAttribute.setProductId(productId);
            if(categoryMapping!=null){
                productAttribute.setAliCnCategoryName(categoryMapping.getAliCnCategoryName());
            }
            productAttribute.setTurnover(0);
            productAttribute.setWarehouseId(ProductConstant.DEFAULT_WAREHOURSE_ID);
            productAttributeService.insert(productAttribute);
        }, threadPoolExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //产品图片
            List<ProductImg> productImgList=new ArrayList<>();
            AlibabaProductVo.getProductImgVoList().forEach(productImgVo -> {
                ProductImg productImg=new ProductImg();
                BeanUtils.copyProperties(productImgVo,productImg);
                productImg.setId(IdGenerate.nextId());
                productImg.setProductId(productId);
                productImgList.add(productImg);
            });
            productImgService.insertByBatch(productImgList);
        }, threadPoolExecutor);

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            //产品描述
            ProductInfo productInfo=new ProductInfo();
            BeanUtils.copyProperties(AlibabaProductVo.getProductInfoVo(),productInfo);
            productInfo.setId(IdGenerate.nextId());
            productInfo.setProductId(productId);
            productInfoService.insert(productInfo);
        }, threadPoolExecutor);

        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
            //产品变体
            List<ProductVariant> productVariantList=new ArrayList<>();
            List<ProductVariantAttr> productVariantAttrList=new ArrayList<>();
            AlibabaProductVo.getProductVariantVoList().forEach(productVariantVo -> {
                ProductVariant productVariant=new ProductVariant();
                BeanUtils.copyProperties(productVariantVo,productVariant);
                Long variantId=IdGenerate.nextId();
                productVariant.setProductId(productId);
                productVariant.setId(variantId);
                //0:正常产品 1:捆绑产品
                productVariant.setVariantType(0);
                productVariant.setState(1);
//                if (StringUtils.isBlank(productVariant.getVariantImage())){
//                    productVariant.setVariantImage(mainImage);
//                }
                List<String> cnNameList=productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrCvalue).collect(Collectors.toList());
                List<String> enNameList=productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrEvalue).collect(Collectors.toList());
                productVariant.setCnName(cnNameList.toString());
                productVariant.setEnName(enNameList.toString());
                productVariant.setUsdPrice(productVariant.getVariantPrice().divide(new BigDecimal("6.3")));
                productVariantList.add(productVariant);
                productVariantVo.getVariantAttrVoList().forEach(productVariantAttrVo -> {
                    ProductVariantAttr productVariantAttr=new ProductVariantAttr();
                    BeanUtils.copyProperties(productVariantAttrVo,productVariantAttr);
                    productVariantAttr.setId(IdGenerate.nextId());
                    productVariantAttr.setProductId(productId);
                    productVariantAttr.setVariantId(variantId);
                    productVariantAttrList.add(productVariantAttr);
                });
            });
            productVariantService.insertByBatch(productVariantList);
            productVariantAttrService.insertByBatch(productVariantAttrList);
            //更新价格区间
//            refreshProductPriceRange(productId);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(future1, future2, future3, future4).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        long end=System.currentTimeMillis();

        System.out.println("导入产品时间:"+(end-start));
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }

    @Override
    public Product selectByProductSku(String productSku) {
        return null;
    }

    @Override
    public BaseResponse putawayProduct(Long id, Session session) {
        Product product=productDao.selectByPrimaryKey(id);
        if(product==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        //无运输属性
        if(product.getShippingId()==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"无运输属性!");
        }
        //产品（收藏夹、下架、机器上架）状态可以上架
        if(product.getState()!=ProductConstant.State.AUTOPUTAWAY.getCode()&&
                product.getState()!=ProductConstant.State.UNSHELVE.getCode()&&
                product.getState()!=ProductConstant.State.EDITING.getCode()){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        List<ProductVariant> productVariantList=productVariantService.selectByProductId(id);
        boolean flag1=false,flag2=false,flag3=false;
        for(ProductVariant productVariant:productVariantList){
            if(productVariant.getState()==0){
                continue;
            }
            //存在可用变体
            if(productVariant.getState()==1){
                flag1=true;
            }
            //变体重量异常
            if(productVariant.getVolumeWeight().compareTo(BigDecimal.ZERO)<=0
                    ||productVariant.getWeight().compareTo(BigDecimal.ZERO)<=0){
                flag2=true;
            }
            //变体价格异常
            if(productVariant.getVariantPrice().compareTo(BigDecimal.ZERO)<=0){
                flag3=true;
            }
        }
        if(!flag1){
            return new BaseResponse(ResultCode.FAIL_CODE,"无可用变体");
        }
        if(flag2){
            return new BaseResponse(ResultCode.FAIL_CODE,"变体重量异常");
        }
        if(flag3){
            return new BaseResponse(ResultCode.FAIL_CODE,"变体价格异常");
        }
        product = new Product();
        product.setId(id);
        product.setState(3);
        product.setUpdateTime(new Date());
        productDao.updateByPrimaryKeySelective(product);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public BaseResponse unshelveProduct(Long id, Session session) {
        Product product=productDao.selectByPrimaryKey(id);
        if(product==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        //产品（下架、机器上架）状态可以上架
        if(product.getState()!=ProductConstant.State.AUTOPUTAWAY.getCode()&&
                product.getState()!=ProductConstant.State.UNSHELVE.getCode()&&
                product.getState()!=ProductConstant.State.PUTAWAY.getCode()){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        product = new Product();
        product.setId(id);
        product.setState(2);
        product.setUpdateTime(new Date());
        productDao.updateByPrimaryKeySelective(product);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public BaseResponse uploadToSaihe(ProductUoloadToSaiheRequest request) {

        List<SaiheSkuVo> list=productVariantService.listSaiheSkuVo(request.getProductIds());
        if(list==null||list.size()==0){
            return new BaseResponse(ResultCode.FAIL_CODE,"无可用变体!");
        }
        List<ApiImportProductInfo> ImportProductList=new ArrayList<>();
        for(SaiheSkuVo saiheSkuVo:list) {
            /**
             *              p.shipping_id as shippingId,
             * 				p.user_id as userId,
             *
             * user_info
             * --         jsu.username as developUser,
             * --         jsu.username as chargeUser,
             * --         jsu.username as editorUser,
             * --         jsu.username as imageHandleUser,
             * --         jsu.username as purchaseUser,
             *
             * shipping_template
             *  --     t.saihe_id as shippingAttribute,
             */
            ShippingTemplateRedis shippingTemplateRedis = (ShippingTemplateRedis)redisTemplate.opsForHash()
                    .get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(saiheSkuVo.getShippingId()));
            if (null != shippingTemplateRedis && shippingTemplateRedis.getSaiheId() != null){
                saiheSkuVo.setShippingAttribute(shippingTemplateRedis.getSaiheId());
            }

            UserInfoVo userInfoVo = (UserInfoVo) redisTemplate.opsForHash().get("user:info:vo",saiheSkuVo.getUserId());
            saiheSkuVo.setDevelopUser(userInfoVo.getUsername());
            saiheSkuVo.setChargeUser(userInfoVo.getUsername());
            saiheSkuVo.setEditorUser(userInfoVo.getUsername());
            saiheSkuVo.setImageHandleUser(userInfoVo.getUsername());
            saiheSkuVo.setPurchaseUser(userInfoVo.getUsername());

            //报关中文名
            StringJoiner joinerName=new StringJoiner("  ");
            joinerName.add(saiheSkuVo.getProductEntryCnName());
            //变体属性名
            StringJoiner joiner=new StringJoiner("-");
            for(int i=0;i<saiheSkuVo.getAttrList().size();i++){
                SaiheSkuAttrVo attrVo=saiheSkuVo.getAttrList().get(i);
                //color
                if(i==0){
                    saiheSkuVo.setColor(attrVo.getVariantAttrCvalue());
                }
                //size
                if(i==1){
                    saiheSkuVo.setSize(attrVo.getVariantAttrCvalue());
                }
                joiner.add(attrVo.getVariantAttrCvalue());
            }
            joinerName.add(joiner.toString());
            saiheSkuVo.setProductName(joinerName.toString());

            ApiImportProductInfo apiImportProductInfo =
                    convertApiImportProductInfo(saiheSkuVo);
            ImportProductList.add(apiImportProductInfo);
        }

        ApiUploadProductsResponse apiUploadProductsResponse= SaiheService.
                processUpdateProduct(ImportProductList);

        if(apiUploadProductsResponse.getProcessUpdateProductResult().getStatus().equals("OK")){
            Presult result=apiUploadProductsResponse.getProcessUpdateProductResult().getPresult();
            if(result!=null&&result.getApiUploadResult()!=null
                    &&!result.getApiUploadResult().getSuccess()){
                //赛盒用户不匹配
                return new BaseResponse(ResultCode.FAIL_CODE,result.getApiUploadResult().getOperateMessage());
            }else {
                //更新状态
                productDao.updateSaiheState(request.getProductIds(),request.getProductIds().size());
                /**
                 * 产品导入赛盒，从未导入订单产品缓存集合中移除
                 */
                redisTemplate.opsForHash().delete(RedisKey.HASH_BAD_NORMAL_ORDER_PRODUCT,String.valueOf(request.getProductIds()));
                redisTemplate.opsForHash().delete(RedisKey.HASH_BAD_WHOLESALE_ORDER_PRODUCT,String.valueOf(request.getProductIds()));
                return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
            }
        }else {
            return new BaseResponse(ResultCode.FAIL_CODE,apiUploadProductsResponse.getProcessUpdateProductResult().getStatus());
        }
    }

    /**
     * 选品池列表
     * @param request
     * @return
     */
    @Override
    public ProductListResponse selectionList(ProductListRequest request) {


        List<Product> results = this.select(request);
        Long total = this.count(request);


        List<SelectionProductVo> selectionProductVoList=results.stream().map(product -> {
            SelectionProductVo selectionProductVo=new SelectionProductVo();
            BeanUtils.copyProperties(product,selectionProductVo);
            selectionProductVo.setProductTitle(product.getOriginalTitle());
            return selectionProductVo;
        }).collect(Collectors.toList());;
        request.setTotal(total);
        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,selectionProductVoList,request);
        return res;
    }
    @Override
    public ImportFavoriteResponse importFavorite(ImportFavoriteRequest request, Session session) {
        if(request.getIds().size()==0){
            return new ImportFavoriteResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        //选品池->收藏夹
        int res=productDao.importFavorite(request.getIds(),session.getId());
        return new ImportFavoriteResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,res);
    }

    @Override
    public ProductListResponse favoriteList(ProductListRequest request) {
        List<Product> results = this.select(request);
        Long total = this.count(request);
        List<FavoriteProductVo> selectionProductVoList=results.stream().map(product -> {
            FavoriteProductVo selectionProductVo=new FavoriteProductVo();
            BeanUtils.copyProperties(product,selectionProductVo);

            return selectionProductVo;
        }).collect(Collectors.toList());;
        request.setTotal(total);

        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,selectionProductVoList,request);
        return res;
    }

    @Override
    public MultiReleaseResponse multiRelease(MultiReleaseRequest request, Session session) {
        if(request.getIds().size()==0){
            return new MultiReleaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        //收藏夹->选品池
        int res=productDao.multiRelease(request.getIds(),session.getId());
        return new MultiReleaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,res);
    }

    @Override
    public AbandonProductResponse abandonProduct(Long id, Session session) {
        Product product = productDao.selectByPrimaryKey(id);
        if(product==null||product.getState()!=ProductConstant.State.EDITING.getCode()){
            return new AbandonProductResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        int res=productDao.abandonProduct(id,session.getId());
        return new AbandonProductResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     *
     */
    public Product selectByPrimaryKey(Long id){

        return productDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Product record) {
        return productDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Product record) {
        return productDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Product> select(Page<Product> record){
        record.initFromNum();
        return productDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Product> record){
        return productDao.count(record);
    }


    public static ApiImportProductInfo convertApiImportProductInfo(SaiheSkuVo saiheSkuVo){
        if(saiheSkuVo==null){
            return null;
        }
        ApiImportProductInfo apiImportProductInfo=new ApiImportProductInfo();
        apiImportProductInfo.setClientSKU(saiheSkuVo.getCustomerSku());//自定义SKU
        apiImportProductInfo.setProductColor(saiheSkuVo.getColor());//产品颜色英文
        apiImportProductInfo.setProductSize(saiheSkuVo.getSize());//产品尺码英文
        apiImportProductInfo.setProductGroupSKU(saiheSkuVo.getParentSku());//母体ID
        apiImportProductInfo.setComeSource(0);//产品来源 系统采集
        if(saiheSkuVo.getCateType()!=null&&saiheSkuVo.getCateType().equals(1)){
            apiImportProductInfo.setProductClassNameEN("upedgepackaging");//产品英文类别名
            //定制包装 潘达物流包材
            //商品类别是“定制包装”的产品，传到赛盒产品类别字段的"潘达物流包材"，普通商品传“潘达”，产品编辑页面，新增商品类别选项，默认普通商品
            apiImportProductInfo.setProductClassNameCN("潘达物流包材");
        }else {
            apiImportProductInfo.setProductClassNameEN("upedge");//产品英文类别名
            apiImportProductInfo.setProductClassNameCN("潘达产品");//产品中文类别名
        }
        apiImportProductInfo.setProductName(saiheSkuVo.getProductName());//产品中文名
        apiImportProductInfo.setProductNameCN(saiheSkuVo.getProductName());//产品中文名

        apiImportProductInfo.setLength(new BigDecimal(20.0));//产品尺寸长（CM）20
        apiImportProductInfo.setWidth(new BigDecimal(10.0));//产品尺寸宽（CM）10
        apiImportProductInfo.setHeight(new BigDecimal(2.0));//产品尺寸高（CM）2
        apiImportProductInfo.setPack_Length(new BigDecimal(20.0));//产品包装尺寸长（CM）20
        apiImportProductInfo.setPack_Width(new BigDecimal(10.0));//产品包装尺寸宽（CM）10
        apiImportProductInfo.setPack_Height(new BigDecimal(2.0));//产品包装尺寸高（CM）2
        //物流属性
        apiImportProductInfo.setWithBattery(saiheSkuVo.getShippingAttribute());

        ApiImportProductDeclaration apiImportProductDeclaration=new ApiImportProductDeclaration();
        apiImportProductDeclaration.setDeclarationName(saiheSkuVo.getProductEntryEnName());//产品报关英文名
        apiImportProductDeclaration.setDeclarationNameCN(saiheSkuVo.getProductEntryCnName());//产品报关中文名
        apiImportProductDeclaration.setDeclarationPriceRate(new BigDecimal(saiheSkuVo.getEntryPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));//报关价
        apiImportProductDeclaration.setDeclarationUnit(saiheSkuVo.getEntryUnit());//报关单位 USD
        apiImportProductInfo.setProductDeclaration(apiImportProductDeclaration);

        apiImportProductInfo.setLastSupplierPrice(new BigDecimal(saiheSkuVo.getSupplyPrice()));//供货价(人民币)

        apiImportProductInfo.setUnitName(saiheSkuVo.getUnit());//单位
        apiImportProductInfo.setNetWeight(new BigDecimal(saiheSkuVo.getNetWeight()));//净重(克)
        apiImportProductInfo.setGrossWeight(new BigDecimal(saiheSkuVo.getGrossWeight()));//毛重(克)
        apiImportProductInfo.setPackWeight(new BigDecimal(saiheSkuVo.getPerProductPackWeight()));//单个产品包裹重量(克)
        apiImportProductInfo.setProductState("0");//产品状态  正常 = 0,仅批量 = 1,停产 = 2,锁定 = 3,暂时缺货 = 4,清库 = 5

        apiImportProductInfo.setBuyerName(saiheSkuVo.getPurchaseUser());//采购人员
        apiImportProductInfo.setReceiptRemark(saiheSkuVo.getReceiptRemark());//采购收货备注

        ApiImportProductAdmin apiImportProductAdmin=new ApiImportProductAdmin();
        apiImportProductAdmin.setAssignDevelopAdminName(saiheSkuVo.getChargeUser());//负责人员
        apiImportProductAdmin.setEditAdminName(saiheSkuVo.getEditorUser());//编辑人员
        apiImportProductAdmin.setImageAdminName(saiheSkuVo.getImageHandleUser());//图片处理人员
        apiImportProductAdmin.setDevelopAdminName(saiheSkuVo.getDevelopUser());//开发人员
        apiImportProductAdmin.setToProcurementCheckMemo(saiheSkuVo.getProcurementCheckMemo());//质检备注
        apiImportProductAdmin.setToDeliveryPackNoteMemo(saiheSkuVo.getDeliveryPackNote());//发货打包备注
        apiImportProductInfo.setProductAdmin(apiImportProductAdmin);

        //赛盒仓库 默认仓库
        Integer warehouseId=saiheSkuVo.getWareHouseId()==null? SaiheConfig.SOURCINBOX_DEFAULT_WAREHOURSE_ID:saiheSkuVo.getWareHouseId();
        apiImportProductInfo.setDefaultLocalWarehouse(warehouseId);//默认本地发货仓库

        ApiImportProductSupplier ProductSuppiler=new ApiImportProductSupplier();//产品供应商
        ProductSuppiler.setSupplierName(saiheSkuVo.getSupplierName());//供应商名称
        ProductSuppiler.setSupplierType(2);//供应商类型 2. 网络采购
        apiImportProductInfo.setProductSuppiler(ProductSuppiler);

        ApiImportProductSupplierPrice ProductSupplierPrice=new ApiImportProductSupplierPrice();//产品供应商报价
        ProductSupplierPrice.setSupplierSKU(saiheSkuVo.getSupplierCode());//供应商产品编码  //todo 货号
        ProductSupplierPrice.setWebProductUrl(saiheSkuVo.getPurchaseLink());//网络采购链接
        apiImportProductInfo.setProductSupplierPrice(ProductSupplierPrice);

        List<ApiImportProductImage> imgList=new ArrayList<>();//产品图片(最多9张)
        ApiImportProductImage apiImportProductImage=new ApiImportProductImage();
        apiImportProductImage.setCover(1);
        apiImportProductImage.setOriginalImageUrl(saiheSkuVo.getMainImage());//产品主图
        apiImportProductImage.setSortBy(1);
        imgList.add(apiImportProductImage);

        ImagesList imagesList=new ImagesList();
        imagesList.setImagesList(imgList);
        apiImportProductInfo.setImagesList(imagesList);

        return apiImportProductInfo;
    }

}