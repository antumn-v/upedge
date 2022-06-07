package com.upedge.pms.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.utils.UrlUtils;
import com.upedge.pms.modules.alibaba.entity.translate.TranslateResult;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.alibaba.service.TranslateService;
import com.upedge.pms.modules.alibaba.vo.AlibabaProductVo;
import com.upedge.pms.modules.alibaba.vo.ProductVariantAttrVo;
import com.upedge.pms.modules.category.entity.Category;
import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.pms.modules.category.service.CategoryMappingService;
import com.upedge.pms.modules.category.service.CategoryService;
import com.upedge.pms.modules.product.dao.AppProductVariantDao;
import com.upedge.pms.modules.product.dao.ImportProductAttributeDao;
import com.upedge.pms.modules.product.dao.ProductDao;
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
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.processUpdateProduct.*;
import com.upedge.thirdparty.saihe.service.SaiheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    DefaultMQProducer defaultMQProducer;

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

    @Autowired
    AppProductVariantDao appProductVariantDao;

    @Autowired
    ProductLogService productLogService;


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

    @Transactional
    @Override
    public BaseResponse updateInfo(Long id, UpdateInfoProductRequest request, Session session) throws Exception {
        ProductAttribute productAttribute=productAttributeService.selectByProductId(id);
        if(productAttribute==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Product product = productDao.selectByPrimaryKey(id);
        Long shippingId = product.getShippingId();
        if(!StringUtils.isBlank(request.getProductTitle())||!StringUtils.isBlank(request.getCategoryCode())
                ||request.getCateType()!=null||request.getShippingId()!=null){
            product=new Product();
            product.setId(id);
            product.setProductTitle(request.getProductTitle());
            Category category=categoryService.selectByCateCode(request.getCategoryCode());
            if (category != null){
                product.setCategoryId(category.getId());
            }
            if (StringUtils.isBlank(product.getProductSku())&& StringUtils.isNotBlank(request.getProductSku())){
                product.setProductSku(request.getProductSku());
            }
            product.setCateType(request.getCateType());
            product.setShippingId(request.getShippingId());
            if(request.getShippingId()!=null){
                Product old=productDao.selectByPrimaryKey(id);
                //个人产品池 修改运输模板记录日志
                if(old.getState()!=ProductConstant.State.EDITING.getCode()&&!old.getShippingId().equals(request.getShippingId())){
                    ProductLog productLog=new ProductLog();
                    productLog.setId(IdGenerate.nextId());
                    productLog.setAdminUser(String.valueOf(session.getId()));
                    productLog.setCreateTime(new Date());
                    productLog.setProductId(id);
                    productLog.setSku(old.getProductSku());
                    //操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
                    productLog.setOptType(3);
                    productLog.setOldInfo(String.valueOf(old.getShippingId()));
                    productLog.setNewInfo(String.valueOf(request.getShippingId()));
                    productLogService.insert(productLog);
                }
            }
        }
        String productSku = request.getProductSku();
        updateProductSku(product,productSku);
        product.setRemark(request.getRemark());
        productDao.updateByPrimaryKeySelective(product);
        if(!StringUtils.isBlank(request.getEntryCname())||!StringUtils.isBlank(request.getEntryCname())
                ||request.getWarehouseCode()!=null){
            ProductAttribute attribute=new ProductAttribute();
            attribute.setId(productAttribute.getId());
            attribute.setEntryCname(request.getEntryCname());
            attribute.setEntryEname(request.getEntryEname());
            attribute.setWarehouseCode(request.getWarehouseCode());
//            attribute.setShippingAttributeId(request.getShippingAttributeId());
            productAttributeService.updateByPrimaryKeySelective(attribute);
        }

        if ( request.getShippingId() != null
        && !request.getShippingId().equals(shippingId)){
            List<VariantDetail> variantDetails = new ArrayList<>();
            VariantDetail variantDetail = new VariantDetail();
            variantDetail.setProductId(id);
            variantDetail.setProductShippingId(request.getShippingId());
            variantDetails.add(variantDetail);
            boolean b = sendUpdateVariantMessage(variantDetails,"shippingId");
            if (!b){
                throw new Exception("消息队列异常，请重新提交或联系IT！");
            }
        }



        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    void updateProductSku(Product product,String productSku){
        if (product.getProductSource() == 0){
            return;
        }
        if (StringUtils.isNotBlank(productSku)){
            productSku = UrlUtils.getNameByUrl(productSku);
            if (StringUtils.isNotBlank(productSku)){
                AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
                AlibabaProductVo alibabaProductVo = Ali1688Service.getProduct(productSku, alibabaApiVo);
                if (null != alibabaProductVo){
                    Supplier supplier = supplierService.selectByLoginId(alibabaProductVo.getSupplierVo().getLoginId());
                    if (supplier == null) {
                        supplier = new Supplier();
                        BeanUtils.copyProperties(alibabaProductVo.getSupplierVo(), supplier);
                        supplier.setUpdateTime(new Date());
                        supplier.setCreateTime(new Date());
                        supplierService.insert(supplier);
                    }
                    product.setProductSku(productSku);
                    product.setSupplierId(Integer.toUnsignedLong(supplier.getId()));
                }
            }
        }
    }

    @Override
    public AppProductVo showCustomerProductDetail(Long productId,Session session) {
        Product product =  productDao.selectByPrimaryKey(productId);
        AppProductVo appProductVo=  new AppProductVo();
        if (product != null){
            BeanUtils.copyProperties(product,appProductVo);
            appProductVo.initUsdPriceRange();
        }

        List<AppProductVariantVo> variantVos = appProductVariantDao.selectProductVariantsByProductId(productId);
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
        appProductVo.setVariantVos(variantVos);
        appProductVo.setAttributeMap(attributeMap);
        ImportProductAttribute importProductAttribute = importProductAttributeDao.selectBySourceProductId(productId.toString(),session.getCustomerId());
        if (importProductAttribute != null){
            appProductVo.setImportState(1);
        }else {
            appProductVo.setImportState(0);
        }
        List<VariantNameVo> variantNameVos = productVariantAttrService.selectNameValueByProductId(productId);
        appProductVo.setVariantNameValues(variantNameVos);
        ProductInfo productInfo = productInfoService.selectByProductId(productId);
        appProductVo.setProductInfo(productInfo);
        List<ProductImg> productImgs = productImgService.selectByProductId(productId);
        appProductVo.setProductImgs(productImgs);
        return appProductVo;
    }

    @Override
    public String refreshProductPriceRange(Long productId) {
        Map<String, BigDecimal> map = productVariantService.selectVariantPriceRange(productId);
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        if (
                null != map.get("minPrice") &&
                        null != map.get("maxPrice")) {
            BigDecimal minPrice = map.get("minPrice");
            BigDecimal maxPrice = map.get("maxPrice");
            String priceRange = "0";
            if (minPrice.compareTo(maxPrice) == 0) {
                priceRange = minPrice.toString();
            } else {
                priceRange = minPrice + "~" + maxPrice;
            }
            productDao.updatePriceRangeById(priceRange, productId, minPrice, maxPrice);
            return priceRange;
        }
        return null;
    }

    @Override
    public BaseResponse winningProductList(WinningProductListRequest request, Session session) {
        List<AppProductVo> productVos = productDao.selectWinningProducts(request);
        //检查产品是否已导入my product
        checkImportProducts(productVos,session.getCustomerId());
        Long total = productDao.countWinningProduct(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, productVos, request);
    }

    @Override
    public ProductVo productDetail(Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null) {
            return null;
        }
        ProductVo adminProductVo = new ProductVo();
        BeanUtils.copyProperties(product, adminProductVo);
//        Category category=categoryService.selectByPrimaryKey(product.getCategoryId());
//        if(category!=null) {
//            adminProductVo.setCategoryId(Long.parseLong(category.getCateCode()));
//        }
        //开启异步任务 获取属性
        CompletableFuture<Void> productAttributeFuture = CompletableFuture.runAsync(() -> {
            ProductAttribute productAttribute = productAttributeService.selectByProductId(id);
            if (productAttribute == null){
                productAttribute = new ProductAttribute();
                productAttribute.setProductId(id);
                productAttribute.setWarehouseCode(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID);
                productAttributeService.insert(productAttribute);
            }
            adminProductVo.setProductAttribute(productAttribute);
        }, threadPoolExecutor);

        //开启异步任务  获取图片列表
        CompletableFuture<Void> productImgListFuture = CompletableFuture.runAsync(() -> {
            List<ProductImg> productImgList = productImgService.selectByProductId(id);
            adminProductVo.setProductImgList(productImgList);
        }, threadPoolExecutor);

        //开启异步任务  获取产品描述
        CompletableFuture<Void> productInfoFuture = CompletableFuture.runAsync(() -> {
            ProductInfo productInfo = productInfoService.selectByProductId(id);
            if (productInfo == null){
                productInfo = new ProductInfo();
                productInfo.setProductId(id);
                productInfoService.insert(productInfo);
            }
            adminProductVo.setProductInfo(productInfo);
        }, threadPoolExecutor);
        //开启异步任务  获取产品描述
        //属性-值列表
        Map<String, VariantAttrVo> attrMap = new HashMap<>();
        CompletableFuture<Void> productVariantListFuture = CompletableFuture.runAsync(() -> {
            List<ProductVariant> productVariantList = productVariantService.selectByProductId(id);
            adminProductVo.setProductVariantList(productVariantList);
        }, threadPoolExecutor);
        //等到所有任务都完成
        try {
            CompletableFuture.allOf(productAttributeFuture, productImgListFuture, productInfoFuture, productVariantListFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return adminProductVo;
    }


    /**
     * 设为公有产品
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse publicProduct(Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null || product.getProductType() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        if (product.getProductType() != ProductConstant.ProductType.PRIVATE.getCode()) {
            return BaseResponse.success();
        }
        int existCount = customerPrivateProductService.countByProductId(id);
        if (existCount > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "请先移除授权用户！");
        }
        productDao.publicProduct(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 设为私有产品
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse privateProduct(Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null || product.getProductType() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        if (product.getProductType() == ProductConstant.ProductType.PRIVATE.getCode()) {
            return BaseResponse.success();
        }
        productDao.privateProduct(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 添加个人产品
     *
     * @param addProductVo
     * @param session
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addProduct(AddProductVo addProductVo, Session session) {
        if (addProductVo.getProductVariantList().size() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Long productId = IdGenerate.nextId();
        //变体信息
        List<ProductVariant> productVariantList = addProductVo.getProductVariantList();
        List<ProductVariantAttr> productVariantAttrList = new ArrayList<>();
        for (ProductVariant productVariant : productVariantList) {
            Long variantId = IdGenerate.nextId();
            productVariant.setId(variantId);
            productVariant.setProductId(productId);
            //生成sku
            String variantSku = IdGenerate.generateUniqueId();
            productVariant.setVariantSku(variantSku);
            //重量库存异常的禁用  价格要大于0
            if (productVariant.getVariantPrice() == null || productVariant.getVariantPrice().compareTo(BigDecimal.ZERO) <= 0
                    || productVariant.getWeight() == null || productVariant.getWeight().compareTo(BigDecimal.ZERO) <= 0
                    || productVariant.getVolumeWeight() == null || productVariant.getVolumeWeight().compareTo(BigDecimal.ZERO) <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "变体价格或重量异常!");
            } else {
                //设置状态勾选
                productVariant.setState(1);
            }
            productVariant.setVariantType(0);
            List<ProductVariantAttr> variantAttrList = productVariant.getProductVariantAttrList();
            // product_variant  的中英文名{cn_name ,en_name}
            String cnName = "[";
            String enName = "[";
            // 英汉互译api参数为list
            ArrayList<String> strList = new ArrayList<>();
            for (int i = 0; i < variantAttrList.size(); i++) {
                ProductVariantAttr productVariantAttr = variantAttrList.get(i);
                productVariantAttr.setId(IdGenerate.nextId());
                if (StringUtils.isBlank(productVariantAttr.getVariantAttrCname()) ||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrEname()) ||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrCvalue()) ||
                        StringUtils.isBlank(productVariantAttr.getVariantAttrEvalue())) {
                    return new BaseResponse(ResultCode.FAIL_CODE, "变体属性异常!");
                }
                // 在这里将 variantAttrList的属性转为英文 拼接格式 并存入 productVariant的cname  ename
                String variantAttrCvalue = productVariantAttr.getVariantAttrCvalue();
                if (i + 1 == variantAttrList.size()) {
                    cnName += variantAttrCvalue + "]";
                } else {
                    cnName += variantAttrCvalue + ",";
                }
                strList.clear();
                strList.add(variantAttrCvalue);
                TranslateResult translateResult = TranslateService.translate(strList);
                if (translateResult != null && translateResult.getResult() != null) {
                    //英文属性值
                    String cValue = translateResult.getResult().get(variantAttrCvalue);
                    productVariantAttr.setVariantAttrCvalue(cValue);
                    if (i + 1 == variantAttrList.size()) {
                        enName += cValue + "]";
                    } else {
                        enName += cValue + ",";
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
        BeanUtils.copyProperties(addProductVo, product);
        product.setId(productId);
        //生成sku
        String productSku = IdGenerate.generateUniqueId();
        Product p = productDao.selectByProductSku(productSku);
        if (p != null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "重复sku请重试");
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
        Category category = categoryService.selectByCateCode(String.valueOf(addProductVo.getCateCode()));
        if (category != null) {
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
        List<ProductImg> productImgList = addProductVo.getProductImgList();
        for (ProductImg productImg : productImgList) {
            productImg.setId(IdGenerate.nextId());
            productImg.setImageSeq(10);
            //状态
            productImg.setState(1);
            productImg.setProductId(productId);
        }
        productImgService.insertByBatch(productImgList);
        //产品描述
        ProductInfo productInfo = addProductVo.getProductInfo();
        productInfo.setId(IdGenerate.nextId());
        productInfo.setProductId(productId);
        productInfoService.insert(productInfo);
        //优化变体插入
        productVariantService.insertByBatch(productVariantList);
        productVariantAttrService.insertByBatch(productVariantAttrList);
        //更新价格区间
        refreshProductPriceRange(productId);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public BaseResponse importFrom1688(AlibabaProductVo AlibabaProductVo, Session session) {
        Long productId = IdGenerate.nextId();
        String mainImage = AlibabaProductVo.getProductImage();
        //产品供应商
        Supplier supplier = supplierService.selectByLoginId(AlibabaProductVo.getSupplierVo().getLoginId());
        if (supplier == null) {
            supplier = new Supplier();
            BeanUtils.copyProperties(AlibabaProductVo.getSupplierVo(), supplier);
            supplier.setUpdateTime(new Date());
            supplier.setCreateTime(new Date());
            supplierService.insert(supplier);
        }

        //产品类目
        Long aliCnCategoryId = AlibabaProductVo.getProductAttributeVo().getAliCnCategoryId();
        CategoryMapping categoryMapping = categoryMappingService.selectByAliCateCode(String.valueOf(aliCnCategoryId));

        //产品
        Product product = new Product();
        BeanUtils.copyProperties(AlibabaProductVo, product);
        product.setOriginalId(AlibabaProductVo.getProductSku());
        if (categoryMapping != null) {
            product.setCategoryId(categoryMapping.getCategoryId());
        }
        product.setSupplierId(Integer.toUnsignedLong(supplier.getId()));
        product.setId(productId);
        //导入到选品池
        product.setState(ProductConstant.State.EDITING.getCode());
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
        if (session != null) {
            product.setUserId(String.valueOf(session.getId()));
        }
        productDao.insert(product);
        ProductAttribute productAttribute = new ProductAttribute();
        BeanUtils.copyProperties(AlibabaProductVo.getProductAttributeVo(), productAttribute);
        productAttribute.setId(IdGenerate.nextId());
        productAttribute.setProductId(productId);
        if (categoryMapping != null) {
            productAttribute.setAliCnCategoryName(categoryMapping.getAliCnCategoryName());
        }
        productAttribute.setTurnover(0);
        productAttribute.setWarehouseCode(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID);
        productAttributeService.insert(productAttribute);
        //产品图片
        List<ProductImg> productImgList = new ArrayList<>();
        AlibabaProductVo.getProductImgVoList().forEach(productImgVo -> {
            ProductImg productImg = new ProductImg();
            BeanUtils.copyProperties(productImgVo, productImg);
            productImg.setId(IdGenerate.nextId());
            productImg.setProductId(productId);
            productImgList.add(productImg);
        });
        productImgService.insertByBatch(productImgList);
        //产品描述
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(AlibabaProductVo.getProductInfoVo(), productInfo);
        productInfo.setId(IdGenerate.nextId());
        productInfo.setProductId(productId);
        productInfoService.insert(productInfo);
        //产品变体
        List<ProductVariant> productVariantList = new ArrayList<>();
        List<ProductVariantAttr> productVariantAttrList = new ArrayList<>();
        AlibabaProductVo.getProductVariantVoList().forEach(productVariantVo -> {
            ProductVariant productVariant = new ProductVariant();
            BeanUtils.copyProperties(productVariantVo, productVariant);
            Long variantId = IdGenerate.nextId();
            productVariant.setProductId(productId);
            productVariant.setId(variantId);
            //0:正常产品 1:捆绑产品
            productVariant.setVariantType(0);
            productVariant.setState(1);
//            if (StringUtils.isBlank(productVariant.getVariantImage())){
//                productVariant.setVariantImage(mainImage);
//            }
            List<String> cnNameList = productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrCvalue).collect(Collectors.toList());
            List<String> enNameList = productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrEvalue).collect(Collectors.toList());
            productVariant.setCnName(cnNameList.toString());
            productVariant.setEnName(enNameList.toString());
            //变体价格
            productVariant.setVariantPrice(productVariantVo.getVariantPrice());
            productVariant.setUsdPrice(PriceUtils.cnyToUsdByDefaultRate(productVariant.getVariantPrice()));
            //变体体积
            productVariant.setHeight(BigDecimal.ONE);
            productVariant.setWidth(BigDecimal.ONE);
            productVariant.setLength(BigDecimal.ONE);
            productVariant.setVolumeWeight(BigDecimal.ONE);
            if (null == productVariant.getWeight()){
                productVariant.setWeight(BigDecimal.ZERO);
            }
            productVariantList.add(productVariant);
            productVariantVo.getVariantAttrVoList().forEach(productVariantAttrVo -> {
                ProductVariantAttr productVariantAttr = new ProductVariantAttr();
                BeanUtils.copyProperties(productVariantAttrVo, productVariantAttr);
                productVariantAttr.setId(IdGenerate.nextId());
                productVariantAttr.setProductId(productId);
                productVariantAttr.setVariantId(variantId);
                productVariantAttrList.add(productVariantAttr);
            });
        });
        productVariantService.insertByBatch(productVariantList);
        productVariantAttrService.insertByBatch(productVariantAttrList);
        //更新价格区间
        refreshProductPriceRange(productId);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }

    @Override
    public Product selectByProductSku(String productSku) {
        return productDao.selectByProductSku(productSku);
    }

    @Override
    public Product selectByOriginalId(String originalId) {
        if (StringUtils.isBlank(originalId)){
            return null;
        }
        return productDao.selectByOriginalId(originalId);
    }

    @Transactional
    @Override
    public BaseResponse putawayProduct(Long id, Session session) throws CustomerException {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //无运输属性
        if (product.getShippingId() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "无运输属性!");
        }
        //产品（收藏夹、下架、机器上架）状态可以上架
        if (product.getState() != ProductConstant.State.AUTOPUTAWAY.getCode() &&
                product.getState() != ProductConstant.State.UNSHELVE.getCode() &&
                product.getState() != ProductConstant.State.EDITING.getCode()) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        List<ProductVariant> productVariantList = productVariantService.selectByProductId(id);
        boolean flag1 = false, flag2 = false, flag3 = false;
        for (ProductVariant productVariant : productVariantList) {
            if (productVariant.getState() == 0) {
                continue;
            }
            //存在可用变体
            if (productVariant.getState() == 1) {
                flag1 = true;
            }
            //变体重量异常
            if (null == productVariant.getWeight()
                || null == productVariant.getVolumeWeight()
                || productVariant.getVolumeWeight().compareTo(BigDecimal.ZERO) <= 0
                    || productVariant.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "变体重量异常");
            }
            //变体价格异常
            if (productVariant.getVariantPrice().compareTo(BigDecimal.ZERO) <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "变体价格异常");
            }
        }
        if (!flag1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "无可用变体");
        }
        product = new Product();
        product.setId(id);
        product.setState(3);
        product.setUpdateTime(new Date());
        productDao.updateByPrimaryKeySelective(product);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public BaseResponse unshelveProduct(Long id, Session session) {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //产品（下架、机器上架）状态可以上架
        if (product.getState() != ProductConstant.State.AUTOPUTAWAY.getCode() &&
                product.getState() != ProductConstant.State.UNSHELVE.getCode() &&
                product.getState() != ProductConstant.State.PUTAWAY.getCode()) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        product = new Product();
        product.setId(id);
        product.setState(1);
        product.setUpdateTime(new Date());
        productDao.updateByPrimaryKeySelective(product);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public BaseResponse uploadToSaihe(Long productId,Long variantId) throws Exception {
        List<SaiheSkuVo> list = new ArrayList<>();
        if (null == productId && null == variantId){
            return BaseResponse.failed("请求数据为空");
        }
        if (null != variantId){
            SaiheSkuVo saiheSkuVo = productVariantService.selectSaiheSkuVoById(variantId);
            if (null != saiheSkuVo){
                list.add(saiheSkuVo);
                productId = Long.parseLong(saiheSkuVo.getProductId());
            }

        }else {
            list = productVariantService.selectSaiheSkuVoByProductId(productId);
        }
        if (list == null || list.size() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "无可用变体!");
        }
        List<ApiImportProductInfo> ImportProductList = new ArrayList<>();
        for (SaiheSkuVo saiheSkuVo : list) {

            ShippingTemplateRedis shippingTemplateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash()
                    .get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(saiheSkuVo.getShippingId()));
            if (null != shippingTemplateRedis && shippingTemplateRedis.getSaiheId() != null) {
                saiheSkuVo.setShippingAttribute(shippingTemplateRedis.getSaiheId());
            }

//            UserInfoVo userInfoVo = (UserInfoVo) redisTemplate.opsForHash().get("user:info:vo", saiheSkuVo.getUserId());
            saiheSkuVo.setDevelopUser("章总");
            saiheSkuVo.setChargeUser("章总");
            saiheSkuVo.setEditorUser("章总");
            saiheSkuVo.setImageHandleUser("章总");
            saiheSkuVo.setPurchaseUser("章总");

            //报关中文名
            StringJoiner joinerName = new StringJoiner("  ");
            joinerName.add(saiheSkuVo.getProductEntryCnName());
            //变体属性名
            StringJoiner joiner = new StringJoiner("-");
            for (int i = 0; i < saiheSkuVo.getAttrList().size(); i++) {
                SaiheSkuAttrVo attrVo = saiheSkuVo.getAttrList().get(i);
                //color
                if (i == 0) {
                    saiheSkuVo.setColor(attrVo.getVariantAttrCvalue());
                }
                //size
                if (i == 1) {
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

        ApiUploadProductsResponse apiUploadProductsResponse = SaiheService.
                processUpdateProduct(ImportProductList);

        if (apiUploadProductsResponse.getProcessUpdateProductResult().getStatus().equals("OK")) {
            Presult result = apiUploadProductsResponse.getProcessUpdateProductResult().getPresult();
            if (result != null && result.getApiUploadResult() != null
                    && !result.getApiUploadResult().getSuccess()) {
                //赛盒用户不匹配
                return new BaseResponse(ResultCode.FAIL_CODE, result.getApiUploadResult().getOperateMessage());
            } else {
                //更新状态
                Product product = new Product();
                product.setId(productId);
                product.setSaiheState(1);
                updateByPrimaryKeySelective(product);
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
            }
        } else {
            throw new CustomerException("订单上传赛盒失败，请联系IT坚持! 失败原因: " + apiUploadProductsResponse.getProcessUpdateProductResult().getStatus());

        }
    }

    /**
     * 选品池列表
     *
     * @param request
     * @return
     */
    @Override
    public ProductListResponse selectionList(ProductListRequest request) {


        List<Product> results = this.select(request);
        Long total = this.count(request);


        List<SelectionProductVo> selectionProductVoList = results.stream().map(product -> {
            SelectionProductVo selectionProductVo = new SelectionProductVo();
            BeanUtils.copyProperties(product, selectionProductVo);
            selectionProductVo.setProductTitle(product.getOriginalTitle());
            return selectionProductVo;
        }).collect(Collectors.toList());
        ;
        request.setTotal(total);
        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, selectionProductVoList, request);
        return res;
    }

    @Override
    public ImportFavoriteResponse importFavorite(ImportFavoriteRequest request, Session session) {
        if (request.getIds().size() == 0) {
            return new ImportFavoriteResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //选品池->收藏夹

        int res = productDao.importFavorite(request.getIds(), session.getId(),new Date());
        return new ImportFavoriteResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, res);
    }

    @Override
    public ProductListResponse favoriteList(ProductListRequest request) {
        List<Product> results = this.select(request);
        Long total = this.count(request);
        List<FavoriteProductVo> selectionProductVoList = results.stream().map(product -> {
            FavoriteProductVo selectionProductVo = new FavoriteProductVo();
            BeanUtils.copyProperties(product, selectionProductVo);

            return selectionProductVo;
        }).collect(Collectors.toList());
        ;
        request.setTotal(total);

        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, selectionProductVoList, request);
        return res;
    }

    @Override
    public MultiReleaseResponse multiRelease(MultiReleaseRequest request, Session session) {
        if (request.getIds().size() == 0) {
            return new MultiReleaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //收藏夹->选品池
        int res = productDao.multiRelease(request.getIds(), session.getId());
        return new MultiReleaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, res);
    }

    @Override
    public AbandonProductResponse abandonProduct(Long id, Session session) {
        Product product = productDao.selectByPrimaryKey(id);
        if (product == null || product.getState() != ProductConstant.State.EDITING.getCode()) {
            return new AbandonProductResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        int res = productDao.abandonProduct(id, session.getId());
        return new AbandonProductResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     *
     */
    public Product selectByPrimaryKey(Long id) {

        return productDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(Product record) throws Exception {
        Product product = productDao.selectByPrimaryKey(record.getId());
        Long shippingId = product.getShippingId();
        int i = productDao.updateByPrimaryKeySelective(record);
        if (i == 1){
            if ( record.getShippingId() != null
                    && !record.getShippingId().equals(shippingId)){
                List<VariantDetail> variantDetails = new ArrayList<>();
                VariantDetail variantDetail = new VariantDetail();
                variantDetail.setProductId(record.getId());
                variantDetail.setProductShippingId(record.getShippingId());
                variantDetails.add(variantDetail);
                boolean b = sendUpdateVariantMessage(variantDetails,"shippingId");
                if (!b){
                    throw new Exception("消息队列异常，请重新提交或联系IT！");
                }
            }
        }
        return i;
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
    public List<Product> select(Page<Product> record) {
        record.initFromNum();
        return productDao.select(record);
    }

    /**
     *
     */
    public long count(Page<Product> record) {
        return productDao.count(record);
    }


    public static ApiImportProductInfo convertApiImportProductInfo(SaiheSkuVo saiheSkuVo) {
        if (saiheSkuVo == null) {
            return null;
        }
        ApiImportProductInfo apiImportProductInfo = new ApiImportProductInfo();
        apiImportProductInfo.setClientSKU(saiheSkuVo.getCustomerSku());//自定义SKU
        apiImportProductInfo.setProductColor(saiheSkuVo.getColor());//产品颜色英文
        apiImportProductInfo.setProductSize(saiheSkuVo.getSize());//产品尺码英文
        apiImportProductInfo.setProductGroupSKU(saiheSkuVo.getParentSku());//母体ID
        apiImportProductInfo.setComeSource(0);//产品来源 系统采集
        if (saiheSkuVo.getCateType() != null && saiheSkuVo.getCateType().equals(1)) {
            apiImportProductInfo.setProductClassNameEN("upedgepackaging");//产品英文类别名
            //定制包装 潘达物流包材
            //商品类别是“定制包装”的产品，传到赛盒产品类别字段的"潘达物流包材"，普通商品传“潘达”，产品编辑页面，新增商品类别选项，默认普通商品
            apiImportProductInfo.setProductClassNameCN("戎安物流包材");
        } else {
            apiImportProductInfo.setProductClassNameEN("upedge");//产品英文类别名
            apiImportProductInfo.setProductClassNameCN("戎安产品");//产品中文类别名
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

        ApiImportProductDeclaration apiImportProductDeclaration = new ApiImportProductDeclaration();
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

        ApiImportProductAdmin apiImportProductAdmin = new ApiImportProductAdmin();
        apiImportProductAdmin.setAssignDevelopAdminName(saiheSkuVo.getChargeUser());//负责人员
        apiImportProductAdmin.setEditAdminName(saiheSkuVo.getEditorUser());//编辑人员
        apiImportProductAdmin.setImageAdminName(saiheSkuVo.getImageHandleUser());//图片处理人员
        apiImportProductAdmin.setDevelopAdminName(saiheSkuVo.getDevelopUser());//开发人员
        apiImportProductAdmin.setToProcurementCheckMemo(saiheSkuVo.getProcurementCheckMemo());//质检备注
        apiImportProductAdmin.setToDeliveryPackNoteMemo(saiheSkuVo.getDeliveryPackNote());//发货打包备注
        apiImportProductInfo.setProductAdmin(apiImportProductAdmin);

        //赛盒仓库 默认仓库
        Integer warehouseCode = saiheSkuVo.getWarehouseCode() == null ? SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID : saiheSkuVo.getWarehouseCode();
        apiImportProductInfo.setDefaultLocalWarehouse(warehouseCode);//默认本地发货仓库

        ApiImportProductSupplier productSupplier = new ApiImportProductSupplier();//产品供应商
        productSupplier.setSupplierName(saiheSkuVo.getSupplierName());//供应商名称
        productSupplier.setSupplierType(2);//供应商类型 2. 网络采购
        apiImportProductInfo.setProductSuppiler(productSupplier);

        ApiImportProductSupplierPrice ProductSupplierPrice = new ApiImportProductSupplierPrice();//产品供应商报价
        ProductSupplierPrice.setSupplierSKU(saiheSkuVo.getSupplierCode());//供应商产品编码  //todo 货号
        ProductSupplierPrice.setWebProductUrl(saiheSkuVo.getPurchaseLink());//网络采购链接
        apiImportProductInfo.setProductSupplierPrice(ProductSupplierPrice);

        List<ApiImportProductImage> imgList = new ArrayList<>();//产品图片(最多9张)
        ApiImportProductImage apiImportProductImage = new ApiImportProductImage();
        apiImportProductImage.setCover(1);
        apiImportProductImage.setOriginalImageUrl(saiheSkuVo.getMainImage());//产品主图
        apiImportProductImage.setSortBy(1);
        imgList.add(apiImportProductImage);

        ImagesList imagesList = new ImagesList();
        imagesList.setImagesList(imgList);
        apiImportProductInfo.setImagesList(imagesList);

        return apiImportProductInfo;
    }


    @Override
    public List<Product> selectByIds(List<Long> productIds) {
        if (ListUtils.isEmpty(productIds)){
            return new ArrayList<>();
        }
        return productDao.selectByIds(productIds);
    }

    @Override
    public List<AppProductVo> checkImportProducts(List<AppProductVo> productVos,Long customerId) {
        if (ListUtils.isNotEmpty(productVos)) {
            List<String> sourceProductIds = importProductAttributeDao.selectImportedSourceProductIds(customerId);
            boolean b = ListUtils.isNotEmpty(sourceProductIds);
            productVos.forEach(appProductVo -> {
                appProductVo.initUsdPriceRange();
                if (b) {
                    if (sourceProductIds.contains(String.valueOf(appProductVo.getId()))) {
                        appProductVo.setImportState(1);
                    } else {
                        appProductVo.setImportState(0);
                    }
                } else {
                    appProductVo.setImportState(0);
                }
            });
        }
        return productVos;
    }

    @Override
    public boolean sendUpdateVariantMessage(List<VariantDetail> variantDetails, String tag) {
        if(ListUtils.isEmpty(variantDetails)){
            return false;
        }
        String key = UUID.randomUUID().toString();
        log.debug("变体更新发送消息，key:{},tag:{},数据:{}",key,tag, JSON.toJSON(variantDetails));
        Message message = new Message(RocketMqConfig.TOPIC_VARIANT_UPDATE,tag,key, JSON.toJSONBytes(variantDetails));
        message.setDelayTimeLevel(1);
        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message,variantDetails.toString());
        boolean b = false;
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())){
            try {
                status =  defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("变体更新发送消息，key:{},交易信息发送失败,失败次数:{}",key,i);
            }finally {
                i += 1;
            }
        }
        if(status.equals(SendStatus.SEND_OK.name())){
            b = true;
            messageLog.setIsSendSuccess(1);
            log.warn("变体更新发送消息，key:{},交易信息发送成功",key);
        }else {
            messageLog.setIsSendSuccess(0);
            log.warn("变体更新发送消息，key:{}",key);
        }
        umsFeignClient.saveMqLog(messageLog);
        return b;
    }

}