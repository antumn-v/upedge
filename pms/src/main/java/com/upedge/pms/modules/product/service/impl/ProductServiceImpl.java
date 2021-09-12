package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.category.entity.Category;
import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.pms.modules.category.service.CategoryMappingService;
import com.upedge.pms.modules.category.service.CategoryService;
import com.upedge.pms.modules.product.entity.*;
import com.upedge.pms.modules.product.service.*;
import com.upedge.pms.modules.product.vo.ProductVo;
import com.upedge.pms.modules.product.vo.VariantAttrVo;
import com.upedge.pms.modules.supplier.entity.Supplier;
import com.upedge.pms.modules.supplier.service.SupplierService;
import com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    ThreadPoolExecutor threadPoolExecutor;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Product record = new Product();
        record.setId(id);
        return productDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Product record) {
        return productDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Product record) {
        return productDao.insert(record);
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
//                if (StringUtils.isBlank(productVariant.getVariantImage())){
//                    productVariant.setVariantImage(mainImage);
//                }
                List<String> cnNameList=productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrCvalue).collect(Collectors.toList());
                List<String> enNameList=productVariantVo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrEvalue).collect(Collectors.toList());
                productVariant.setCnName(cnNameList.toString());
                productVariant.setEnName(enNameList.toString());
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

}