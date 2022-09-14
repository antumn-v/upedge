package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.pms.modules.product.dao.ProductLogDao;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.entity.*;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.ProductVariantAttrService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.product.service.VariantSkuUpdateLogService;
import com.upedge.pms.modules.product.vo.SaiheSkuVo;
import com.upedge.pms.modules.product.vo.VariantAttrVo;
import com.upedge.pms.modules.product.vo.VariantValVo;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantDao productVariantDao;

    @Autowired
    ProductVariantAttrService productVariantAttrService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductLogDao productLogDao;

    @Autowired
    VariantSkuUpdateLogService variantSkuUpdateLogService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductVariant record) {
        Long barcode = getMaxBarCode(1);
        record.setBarcode(barcode + 1);
        return productVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductVariant record) throws Exception {
        Long barcode = getMaxBarCode(1);
        record.setBarcode(barcode + 1);
        int i = productVariantDao.insert(record);
        if (i == 1 && record.getState() == 1){
            Product product = productService.selectByPrimaryKey(record.getProductId());
            if (product.getSaiheState() == 1){
                productService.uploadToSaihe(null, record.getId());
            }
        }
        return i;
    }

    /**
     * 更新变体属性
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public ProductVariantUpdateAttrResponse updateAttr(ProductVariantUpdateAttrRequest request) {
        Map<String, Map<String, VariantValVo>> valMap = new HashMap<>();
        Map<String, String> nameMap = new HashMap<>();
        for (VariantAttrVo variantAttrVo : request.getVariantAttrVoList()) {
            if (StringUtils.isBlank(variantAttrVo.getEname())
                    || StringUtils.isBlank(variantAttrVo.getCname())) {
                return new ProductVariantUpdateAttrResponse(ResultCode.FAIL_CODE, "属性名称不能为空!");
            }
            nameMap.put(variantAttrVo.getCname(), variantAttrVo.getEname());
            Map<String, VariantValVo> map = valMap.get(variantAttrVo.getCname());
            if (map == null) {
                map = new HashMap<>();
                valMap.put(variantAttrVo.getCname(), map);
            }
            for (VariantValVo variantValVo : variantAttrVo.getVariantValVoList()) {
                if (StringUtils.isBlank(variantValVo.getCvalue())
                        || StringUtils.isBlank(variantValVo.getOriginalCvalue())
                        || StringUtils.isBlank(variantValVo.getEvalue())) {
                    return new ProductVariantUpdateAttrResponse(ResultCode.FAIL_CODE, "属性值不能为空!");
                }
                map.put(variantValVo.getOriginalCvalue(), variantValVo);
            }
        }
        //获取产品属性
        List<ProductVariantAttr> productVariantAttrList = productVariantAttrService.selectByProductId(request.getProductId());
        Map<Long, List<ProductVariantAttr>> listMap = new HashMap<>();
        for (ProductVariantAttr productVariantAttr : productVariantAttrList) {
            String cname = productVariantAttr.getVariantAttrCname();
            String originalValue = productVariantAttr.getOriginalAttrCvalue();
            String ename = nameMap.get(cname);
            //更新英文名称
            productVariantAttr.setVariantAttrEname(ename);
            Map<String, VariantValVo> m = valMap.get(cname);
            VariantValVo attrVo = m.get(originalValue);
            productVariantAttr.setVariantAttrCvalue(attrVo.getCvalue());
            productVariantAttr.setVariantAttrEvalue(attrVo.getEvalue());
            List<ProductVariantAttr> attrList = listMap.get(productVariantAttr.getVariantId());
            if (attrList == null) {
                attrList = new ArrayList<>();
                listMap.put(productVariantAttr.getVariantId(), attrList);
            }
            attrList.add(productVariantAttr);
        }
        List<ProductVariant> productVariantList = new ArrayList<>();
        for (Map.Entry<Long, List<ProductVariantAttr>> entry : listMap.entrySet()) {
            ProductVariant productVariant = new ProductVariant();
            List<ProductVariantAttr> attrList = entry.getValue();
            List<String> cnNameList = attrList.stream().map(ProductVariantAttr::getVariantAttrCvalue).collect(Collectors.toList());
            List<String> enNameList = attrList.stream().map(ProductVariantAttr::getVariantAttrEvalue).collect(Collectors.toList());
            productVariant.setId(entry.getKey());
            productVariant.setCnName(cnNameList.toString());
            productVariant.setEnName(enNameList.toString());
            productVariantList.add(productVariant);
        }
        productVariantDao.updateByBatch(productVariantList);
        productVariantAttrService.updateByBatch(productVariantAttrList);
        return new ProductVariantUpdateAttrResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public BaseResponse updateSku(ProductVariantUpdateSkuRequest request, Session session) {
        String sku = request.getVariantSku();
        String purchaseSku = request.getPurchaseSku();
        Long id = request.getId();
        ProductVariant productVariant = selectByPrimaryKey(id);
        if (null == productVariant){
            return BaseResponse.failed("变体不存在");
        }
        if (StringUtils.isNotBlank(sku)){
            updateVariantSku(sku,id,productVariant,session);
        }
        if (StringUtils.isNotBlank(purchaseSku)){
            updatePurchaseSku(purchaseSku,id,productVariant,session);
        }
        return BaseResponse.success();
    }

    public void updatePurchaseSku(String purchaseSku,Long id,ProductVariant productVariant, Session session){
        if (null == productVariant){
            return;
        }
        if (productVariant.getPurchaseSku().equals(purchaseSku)){
            return ;
        }
        ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(purchaseSku);
        if (null == productPurchaseInfo){
            return;
        }
        productVariant = new ProductVariant();
        productVariant.setPurchaseSku(purchaseSku);
        productVariant.setId(id);
        updateByPrimaryKeySelective(productVariant);

        VariantSkuUpdateLog updateLog = new VariantSkuUpdateLog(id,purchaseSku,session.getId(),1);
        variantSkuUpdateLogService.insert(updateLog);
    }

    public void updateVariantSku(String sku,Long id,ProductVariant productVariant, Session session) {
        if (null == productVariant){
            return;
        }
        if (productVariant.getVariantSku().equals(sku)){
            return ;
        }
        String oldSku = productVariant.getVariantSku();
        productVariant = new ProductVariant();
        productVariant.setVariantSku(sku);
        productVariant.setId(id);
        updateByPrimaryKeySelective(productVariant);

        VariantSkuUpdateLog updateLog = new VariantSkuUpdateLog(id,sku,session.getId(),0);
        variantSkuUpdateLogService.insert(updateLog);

        redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_UPDATE_SKU_LOG,oldSku,id);
    }

    @Transactional
    @Override
    public BaseResponse updateAttrs(ProductVariantUpdateRequest request, Session session) {
        Long variantId = request.getId();
        ProductVariant productVariant = selectByPrimaryKey(request.getId());
        if (null == productVariant){
            return BaseResponse.failed("变体不存在");
        }

        List<ProductVariantAttr> variantAttrs = request.getVariantAttrs();
        for (ProductVariantAttr variantAttr : variantAttrs) {
            if (StringUtils.isBlank(variantAttr.getVariantAttrEname())
            ||StringUtils.isBlank(variantAttr.getVariantAttrEvalue())
            ||StringUtils.isBlank(variantAttr.getVariantAttrCname())
            ||StringUtils.isBlank(variantAttr.getVariantAttrCvalue())){
                return BaseResponse.failed("属性值不能为空");
            }
            variantAttr.setVariantId(variantId);
            variantAttr.setProductId(productVariant.getProductId());
        }
        productVariant = new ProductVariant();
        List<String> cnNameList = variantAttrs.stream().map(ProductVariantAttr::getVariantAttrCvalue).collect(Collectors.toList());
        List<String> enNameList = variantAttrs.stream().map(ProductVariantAttr::getVariantAttrEvalue).collect(Collectors.toList());
        productVariant.setCnName(cnNameList.toString());
        productVariant.setEnName(enNameList.toString());
        productVariant.setId(request.getId());
        updateByPrimaryKeySelective(productVariant);

        productVariantAttrService.deleteByVariantId(request.getId());
        productVariantAttrService.insertByBatch(variantAttrs);

        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse addVariant(ProductVariantAddRequest request, Session session) {
        Product product = productService.selectByPrimaryKey(request.getProductId());
        if (null == product){
            return BaseResponse.failed("产品不存在");
        }

        ProductVariant productVariant = request.toProductVariant();

        insert(productVariant);

        Long id = productVariant.getId();
        List<ProductVariantAttr> variantAttrs = request.getVariantAttrs();
        for (ProductVariantAttr variantAttr : variantAttrs) {
            variantAttr.setVariantId(id);
            variantAttr.setProductId(productVariant.getProductId());
        }
        productVariantAttrService.insertByBatch(variantAttrs);

        return BaseResponse.success();
    }

    @Override
    public int updateSaiheSku(List<ProductVariant> variants) {
        if (ListUtils.isEmpty(variants)){
            return 0;
        }
        return productVariantDao.updateSaiheSku(variants);
    }

    @Override
    public int updateLatestQuotePrice(Long id, BigDecimal quotePrice) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(id);
        productVariant.setLatestQuotePrice(quotePrice);
        return updateByPrimaryKeySelective(productVariant);
    }


    @Override
    public List<SaiheSkuVo> selectSaiheSkuVoByProductId(Long productId) {
        if (null != productId){
            return productVariantDao.selectSaiheSkuVoByProductId(productId);
        }
        return null;
    }

    @Override
    public SaiheSkuVo selectSaiheSkuVoById(Long id) {
        if (null != id){
            return productVariantDao.selectSaiheSkuVoById(id);
        }
        return null;
    }

    /**
     * 更新变体实重
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductVariantUpdateWeightResponse updateWeight(ProductVariantUpdateWeightRequest request, Session session) throws CustomerException {
        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(request.getIds());
        List<ProductLog> productLogList = new ArrayList<>();
        List<VariantDetail> variantDetails = new ArrayList<>();
        for (ProductVariant productVariant : productVariantList) {
            if (productVariant.getWeight() == null
                    || productVariant.getWeight().compareTo(request.getWeight()) != 0) {
                ProductLog productLog = new ProductLog();
                productLog.setId(IdGenerate.nextId());
                productLog.setAdminUser(String.valueOf(session.getId()));
                productLog.setCreateTime(new Date());
                productLog.setProductId(productVariant.getProductId());
                productLog.setSku(productVariant.getVariantSku());
                //操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
                productLog.setOptType(1);
                productLog.setOldInfo(String.valueOf(productVariant.getWeight()));
                productLog.setNewInfo(String.valueOf(request.getWeight()));
                productLogList.add(productLog);

                VariantDetail variantDetail = new VariantDetail();
                variantDetail.setWeight(request.getWeight());
                variantDetail.setVariantId(productVariant.getId());
                variantDetails.add(variantDetail);
            }
        }
        productVariantDao.updateWeight(request.getIds(), request.getWeight());
        if (productLogList.size() > 0) {
            productLogDao.insertByBatch(productLogList);
        }
        if (ListUtils.isNotEmpty(variantDetails)){
            boolean b = productService.sendUpdateVariantMessage(variantDetails,"weight");
            if (!b){
                throw new CustomerException("mq异常，请重新提交或联系IT!");
            }
        }
        return new ProductVariantUpdateWeightResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 更新体积重
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductVariantUpdateVolumeWeightResponse updateVolumeWeight(ProductVariantUpdateVolumeWeightRequest request, Session session) throws CustomerException {
        BigDecimal width = request.getWidth();
        BigDecimal height = request.getHeight();
        BigDecimal length = request.getLength();
        if (null == width
        || null == height
        || null == length
        || BigDecimal.ZERO.compareTo(width) == 0
        || BigDecimal.ZERO.compareTo(height) == 0
        || BigDecimal.ZERO.compareTo(length) == 0){
            return new ProductVariantUpdateVolumeWeightResponse(ResultCode.FAIL_CODE,"长宽高不能为0!");
        }
        BigDecimal volume = width.multiply(height).multiply(length);

        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(request.getIds());
        List<ProductLog> productLogList = new ArrayList<>();

        List<VariantDetail> variantDetails = new ArrayList<>();
        for (ProductVariant productVariant : productVariantList) {
            if (productVariant.getVolumeWeight() == null
                    || productVariant.getVolumeWeight().compareTo(volume) != 0) {
                ProductLog productLog = new ProductLog();
                productLog.setId(IdGenerate.nextId());
                productLog.setAdminUser(String.valueOf(session.getId()));
                productLog.setCreateTime(new Date());
                productLog.setProductId(productVariant.getProductId());
                productLog.setSku(productVariant.getVariantSku());
                //操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
                productLog.setOptType(2);
                productLog.setOldInfo(String.valueOf(productVariant.getWeight()));
                productLog.setNewInfo(String.valueOf(volume));
                productLogList.add(productLog);

                VariantDetail variantDetail = new VariantDetail();
                variantDetail.setVolume(productVariant.getVolumeWeight());
                variantDetail.setVariantId(productVariant.getId());
                variantDetail.setLength(length);
                variantDetail.setWidth(width);
                variantDetail.setHeight(height);
                variantDetails.add(variantDetail);
            }
        }
        productVariantDao.updateVolumeWeight(request.getIds(), volume,width,height,length);
        if (productLogList.size() > 0) {
            productLogDao.insertByBatch(productLogList);
        }

        boolean b = productService.sendUpdateVariantMessage(variantDetails,"volume");
        if (!b){
            throw new CustomerException("mq异常，请重新提交或联系IT!");
        }
        return new ProductVariantUpdateVolumeWeightResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductVariantUpdatePriceResponse updatePrice(ProductVariantUpdatePriceRequest request, Session session) throws CustomerException {
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new ProductVariantUpdatePriceResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        List<Long> variantIds = request.getIds();
        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(variantIds);
        BigDecimal usdPrice = PriceUtils.cnyToUsdByDefaultRate(request.getPrice());

        List<ProductLog> productLogList = new ArrayList<>();
        List<VariantDetail> variantDetails = new ArrayList<>();

        for (ProductVariant productVariant : productVariantList) {
            if (productVariant.getVariantPrice().compareTo(request.getPrice()) != 0) {
                ProductLog productLog = new ProductLog();
                productLog.setId(IdGenerate.nextId());
                productLog.setAdminUser(String.valueOf(session.getId()));
                productLog.setCreateTime(new Date());
                productLog.setProductId(productVariant.getProductId());
                productLog.setSku(productVariant.getVariantSku());
                //操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
                productLog.setOptType(4);
                productLog.setOldInfo(String.valueOf(productVariant.getWeight()));
                productLog.setNewInfo(String.valueOf(request.getPrice()));
                productLogList.add(productLog);

                VariantDetail variantDetail = new VariantDetail();
                variantDetail.setCnyPrice(productVariant.getVariantPrice());
                variantDetail.setUsdPrice(productVariant.getUsdPrice());
                variantDetail.setVariantId(productVariant.getId());
                variantDetails.add(variantDetail);
            }else {
                variantIds.remove(productVariant.getId());
            }
        }
        if (ListUtils.isEmpty(variantIds)){
            return new ProductVariantUpdatePriceResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        }
        productVariantDao.updatePrice(request.getIds(), request.getPrice(),usdPrice);
        productService.refreshProductPriceRange(productVariantList.get(0).getProductId());
        if (productLogList.size() > 0) {
            productLogDao.insertByBatch(productLogList);
        }
        boolean b =productService.sendUpdateVariantMessage(variantDetails,"price");
        if (!b){
            throw new CustomerException("mq异常，请重新提交或联系IT!");
        }
        return new ProductVariantUpdatePriceResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 更新变体图片
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public ProductVariantUpdateVariantImageResponse updateVariantImage(ProductVariantUpdateVariantImageRequest request, Session session) {
        productVariantDao.updateVariantImage(request.getIds(), request.getVariantImage());
        return new ProductVariantUpdateVariantImageResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用变体
     *
     * @param request
     * @return
     */
    @Override
    public ProductVariantEnableResponse enableVariant(ProductVariantEnableRequest request) {
        int i = productVariantDao.enableVariant(request.getIds());
        if (request.getIds().size() != i){
           return new ProductVariantEnableResponse(ResultCode.FAIL_CODE, "启用变体前需完善变体重量，体积，价格");
        }
        return new ProductVariantEnableResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用变体
     *
     * @param request
     * @return
     */
    @Override
    public ProductVariantDisableResponse disableVariant(ProductVariantDisableRequest request) {
        productVariantDao.disableVariant(request.getIds());
        return new ProductVariantDisableResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<ProductVariant> listVariantByIds(List<Long> variantIds) {
        List<ProductVariant> productVariantList = productVariantDao.listProductVariantByIds(variantIds);

        return productVariantList;
    }

    @Override
    public Map<String, BigDecimal> selectVariantPriceRange(Long productId) {

        if (productId != null){
            return productVariantDao.selectVariantPriceRange(productId);
        }
        return null;
    }

    @Override
    public List<SaiheSkuVo> listSaiheSkuVo(List<Long> ids) {
        if (ListUtils.isNotEmpty(ids)){
            return productVariantDao.listSaiheSkuVo(ids);
        }
        return null;
    }

    @Override
    public List<ProductVariant> getProductVariantList(List<Long> variantIds, Map<String, VariantAttrVo> attrMap, Map<String, Set<String>> attrValSet, Map<Long, ProductVariant> productVariantMap) {
        //获取产品属性
        List<ProductVariantAttr> productVariantAttrList = productVariantAttrService.selectByVariantIds(variantIds);
        productVariantAttrList.forEach(productVariantAttr -> {

            //属性
            VariantAttrVo variantAttrVo = attrMap.get(productVariantAttr.getVariantAttrCname());
            if (variantAttrVo == null) {
                variantAttrVo = new VariantAttrVo();
                variantAttrVo.setCname(productVariantAttr.getVariantAttrCname());
                variantAttrVo.setEname(productVariantAttr.getVariantAttrEname());
                attrMap.put(productVariantAttr.getVariantAttrCname(), variantAttrVo);
                attrValSet.put(productVariantAttr.getVariantAttrCname(), new HashSet<>());
            }
            //获取属性的值列表
            Set<String> valSet = attrValSet.get(productVariantAttr.getVariantAttrCname());
            //属性值
            if (!valSet.contains(productVariantAttr.getOriginalAttrCvalue())) {
                VariantValVo variantValVo = new VariantValVo();
                variantValVo.setOriginalCvalue(productVariantAttr.getOriginalAttrCvalue());
                variantValVo.setCvalue(productVariantAttr.getVariantAttrCvalue());
                variantValVo.setEvalue(productVariantAttr.getVariantAttrEvalue());
                variantAttrVo.getVariantValVoList().add(variantValVo);
                valSet.add(productVariantAttr.getOriginalAttrCvalue());
            }

            ProductVariant variant = productVariantMap.get(productVariantAttr.getVariantId());
            variant.getProductVariantAttrList().add(productVariantAttr);
        });
        List<ProductVariant> productVariantList = new ArrayList(productVariantMap.values());
        return productVariantList;
    }

    @Override
    public List<ProductVariant> selectByProductId(Long productId) {
        Map<String,Object> map = new HashMap<>();
        CompletableFuture<Void> variants = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                List<ProductVariant> productVariants = productVariantDao.selectByProductId(productId);
                map.put("variants",productVariants);
            }
        },threadPoolExecutor);

        CompletableFuture<Void> attributes = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                List<ProductVariantAttr> variantAttrs = productVariantAttrService.selectByProductId(productId);
                map.put("attrs",variantAttrs);
            }
        },threadPoolExecutor);

        try {
            CompletableFuture.allOf(variants,attributes).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<ProductVariant> productVariants = (List<ProductVariant>) map.get("variants");
        List<ProductVariantAttr> variantAttrs = (List<ProductVariantAttr>) map.get("attrs");

        if (ListUtils.isNotEmpty(productVariants)){

            for (ProductVariant productVariant : productVariants) {
                if (null == productVariant.getLatestQuotePrice()){
                    productVariant.setLatestQuotePrice(BigDecimal.ZERO);
                }
                if (null == productVariant.getSaiheSku()){
                    productVariant.setSaiheSku("");
                }
                List<ProductVariantAttr> variantAttrList = productVariant.getProductVariantAttrList();
                for (ProductVariantAttr variantAttr : variantAttrs) {
                    if (variantAttr.getVariantId().equals(productVariant.getId())){
                        variantAttrList.add(variantAttr);
                    }
                }
                variantAttrs.removeAll(variantAttrList);
            }
        }
        return productVariants;
    }

    @Override
    public int insertByBatch(List<ProductVariant> productVariants) {
        if (ListUtils.isEmpty(productVariants)){
            return 0;
        }
        Long barcode = getMaxBarCode(productVariants.size());
        for (ProductVariant productVariant : productVariants) {
            productVariant.setBarcode(++barcode);
        }
        return productVariantDao.insertByBatch(productVariants);
    }

    /**
     *
     */
    public ProductVariant selectByPrimaryKey(Long id){
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductVariant record) {
        return productVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductVariant record) {
        return productVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductVariant> select(Page<ProductVariant> record){
        record.initFromNum();
        return productVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductVariant> record){
        return productVariantDao.count(record);
    }

    @Override
    public ProductVariant selectBySku(String variantSku) {
        if (StringUtils.isBlank(variantSku)){
            return null;
        }
        Long id = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_VARIANT_UPDATE_SKU_LOG,variantSku);
        if (id != null){
            return selectByPrimaryKey(id);
        }
        return productVariantDao.selectBySku(variantSku);
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteProductBySkus(List<String> skus) {
        if (ListUtils.isEmpty(skus)){
            return new ArrayList<>();
        }
        return productVariantDao.selectQuoteProductBySkus(skus);
    }

    private Long getMaxBarCode(int length){
        String key = "key:" + RedisKey.STRING_VARIANT_MAX_NUMBER;
        boolean b = RedisUtil.lock(redisTemplate,key,5L,10L);
        if (!b){
            return null;
        }
        Long barcode = (Long) redisTemplate.opsForValue().get(RedisKey.STRING_VARIANT_MAX_NUMBER);
        if (null == barcode){
            barcode = productVariantDao.selectMaxBarcode();
        }
        redisTemplate.opsForValue().set(RedisKey.STRING_VARIANT_MAX_NUMBER,barcode + length);
        RedisUtil.unLock(redisTemplate,key);
        return barcode;

    }

}