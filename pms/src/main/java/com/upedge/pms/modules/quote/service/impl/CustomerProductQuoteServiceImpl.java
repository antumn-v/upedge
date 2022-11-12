package com.upedge.pms.modules.quote.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.QuotedProductSelectBySkuRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.StoreProductAttributeDao;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.pms.modules.quote.request.AllCustomerQuoteProductSearchRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.ProductQuoteRecordService;
import com.upedge.pms.modules.quote.service.QuoteApplyService;
import com.upedge.pms.mq.producer.ProductMqProducer;
import jodd.util.StringUtil;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CustomerProductQuoteServiceImpl implements CustomerProductQuoteService {

    @Autowired
    private CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;

    @Autowired
    StoreProductAttributeDao storeProductAttributeDao;

    @Autowired
    QuoteApplyService quoteApplyService;

    @Autowired
    ProductMqProducer productMqProducer;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductQuoteRecordService productQuoteRecordService;

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerProductQuote record = new CustomerProductQuote();
        record.setCustomerId(customerId);
        return customerProductQuoteDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerProductQuote record) {
        if (record.getQuoteState() == 1){
            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockService.selectByPrimaryKey(record.getVariantId(),"CNHZ");
            if (null == variantWarehouseStock){
                variantWarehouseStock = new VariantWarehouseStock(record.getVariantId(), "CNHZ", 0, 0, 0,0,"","");
                variantWarehouseStockService.insert(variantWarehouseStock);
            }
        }
        return customerProductQuoteDao.insert(record);
    }

    @Override
    public int insertByBatch(List<CustomerProductQuote> records) {

        if (ListUtils.isEmpty(records)){
            return 0;
        }
        List<VariantWarehouseStock> variantWarehouseStocks = new ArrayList<>();
        List<Long> variantIds = new ArrayList<>();
        for (CustomerProductQuote record : records) {
            if (record.getQuoteState() != 1 || variantIds.contains(record.getVariantId())){
                continue;
            }
            variantIds.add(record.getVariantId());
            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockService.selectByPrimaryKey(record.getVariantId(), "CNHZ");
            if (variantWarehouseStock == null){
                variantWarehouseStock = new VariantWarehouseStock(record.getVariantId(), "CNHZ", 0, 0, 0,0,"","");
                variantWarehouseStocks.add(variantWarehouseStock);
            }
        }
        if (ListUtils.isNotEmpty(variantWarehouseStocks)){
            variantWarehouseStockService.insertByBatch(variantWarehouseStocks);
        }
        return customerProductQuoteDao.insertByBatch(records);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerProductQuote record) {
        return customerProductQuoteDao.insert(record);
    }

    @Override
    public int updateVariantImageByVariantIds(String variantImage, List<Long> variantIds) {
        if (StringUtil.isBlank(variantImage) || ListUtils.isEmpty(variantIds)){
            return 0;
        }
        return customerProductQuoteDao.updateVariantImageByVariantIds(variantImage, variantIds);
    }

    @Override
    public int updateStoreVariantImageById(Long storeVariantId, String image) {
        if (StringUtil.isBlank(image)){
            return 0;
        }
        String key = RedisKey.STRING_QUOTED_STORE_VARIANT + storeVariantId;
        CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(key);
        if (customerProductQuoteVo != null){
            customerProductQuoteVo.setStoreVariantImage(image);
            redisTemplate.opsForValue().set(key,customerProductQuoteVo);
        }
        return customerProductQuoteDao.updateStoreVariantImageById(storeVariantId,image);
    }

    @Override
    public List<CustomerProductQuoteVo> selectAllQuoteDetail() {
        return customerProductQuoteDao.selectAllQuoteDetail();
    }

    @Override
    public BaseResponse revokeQuote(Long storeVariantId, Session session) {

        CustomerProductQuote customerProductQuote = customerProductQuoteDao.selectByStoreVariantId(storeVariantId);
        if (null == customerProductQuote){
            return BaseResponse.success();
        }
        if (customerProductQuote.getQuoteState() == 0){
            return BaseResponse.success();
        }
        customerProductQuote.setVariantSku(null);
        customerProductQuote.setVariantImage(null);
        customerProductQuote.setVariantName(null);
        customerProductQuote.setVariantId(null);
        customerProductQuote.setQuotePrice(null);
        customerProductQuote.setQuoteState(0);
        customerProductQuote.setUpdateTime(new Date());
        updateByPrimaryKey(customerProductQuote);


        List<Long> storeVariantIds = new ArrayList<>();
        storeVariantIds.add(storeVariantId);
        sendCustomerProductQuoteUpdateMessage(storeVariantIds);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse updateCustomerProductQuote(CustomerProductQuoteUpdateRequest request, Session session) {
        Long storeVariantId = request.getStoreVariantId();
        boolean b = storeProductVariantService.redisCheckIfSplitVariant(storeVariantId);
        if (b){
            return BaseResponse.failed("已拆分变体不能报价");
        }
        if (null == storeVariantId
                || StringUtil.isEmpty(request.getVariantSku())
                || null == request.getQuotePrice()) {
            return BaseResponse.failed();
        }
        CustomerProductQuote customerProductQuote = customerProductQuoteDao.selectByStoreVariantId(storeVariantId);
        if (null == customerProductQuote) {
            StoreProductVariant storeProductVariant = storeProductVariantDao.selectByPrimaryKey(storeVariantId);
            customerProductQuote = new CustomerProductQuote(storeProductVariant);
        }
        if (customerProductQuote.getQuotePrice() != null &&
                StringUtil.isNotBlank(customerProductQuote.getVariantSku()) &&
                customerProductQuote.getQuotePrice().compareTo(request.getQuotePrice()) == 0
                && customerProductQuote.getVariantSku().equals(request.getVariantSku())
                && customerProductQuote.getQuoteScale().equals(request.getQuoteScale())) {
            return BaseResponse.success();
        }
        ProductVariant productVariant = productVariantService.selectBySku(request.getVariantSku());
        if (null == productVariant
                || null == productVariant.getWeight()
                || null == productVariant.getVolumeWeight()
                || 0 == BigDecimal.ZERO.compareTo(productVariant.getVolumeWeight())
                || 0 == BigDecimal.ZERO.compareTo(productVariant.getWeight())) {
            return BaseResponse.failed("sku不存在或变体重量体积未编辑");
        }
        Product product = productService.selectByPrimaryKey(productVariant.getProductId());
        if (null == product.getShippingId()) {
            return BaseResponse.failed("产品运输属性不能为空！");
        }
        customerProductQuote.setProductId(productVariant.getProductId());
        customerProductQuote.setProductTitle(product.getProductTitle());
        customerProductQuote.setVariantId(productVariant.getId());
        customerProductQuote.setVariantImage(productVariant.getVariantImage());
        customerProductQuote.setVariantName(productVariant.getEnName());
        customerProductQuote.setVariantSku(productVariant.getVariantSku());
        customerProductQuote.setQuotePrice(request.getQuotePrice());
        customerProductQuote.setQuoteScale(request.getQuoteScale());
        customerProductQuote.setQuoteState(1);
        customerProductQuote.setUpdateTime(new Date());
        if (customerProductQuote.getCustomerId() == null
        && request.getCustomerId() != null){
            customerProductQuote.setCustomerId(request.getCustomerId());
        }
        if (customerProductQuote.getStoreVariantImage() == null){
            customerProductQuote.setStoreVariantImage(productVariant.getVariantImage());
        }
        insert(customerProductQuote);

        ProductQuoteRecord productQuoteRecord = new ProductQuoteRecord();
        BeanUtils.copyProperties(customerProductQuote, productQuoteRecord);
        productQuoteRecord.setUserId(session.getId());
        productQuoteRecord.setCreateTime(new Date());
        productQuoteRecordService.insert(productQuoteRecord);

        productVariantService.updateLatestQuotePrice(productVariant.getId(),request.getQuotePrice());

        List<Long> storeVariantIds = new ArrayList<>();
        storeVariantIds.add(storeVariantId);
        sendCustomerProductQuoteUpdateMessage(storeVariantIds);

        return BaseResponse.success();
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request) {
        if (request == null) {
            return new ArrayList<>();
        }

        List<Long> storeVariantIds = request.getStoreVariantIds();
        if (storeVariantIds == null){
            storeVariantIds = new ArrayList<>();
        }
        List<Long> splitParentIds = new ArrayList<>();
        List<Long> splitVariantIds = new ArrayList<>();
        //获取是组合产品的ID
        for (Long storeVariantId : storeVariantIds) {
            boolean b = storeProductVariantService.redisCheckIfSplitVariant(storeVariantId);
            if (b){
                splitVariantIds.addAll(storeProductVariantService.getSplitVariantIdsByParentId(storeVariantId));
                splitParentIds.add(storeVariantId);
            }
        }
        storeVariantIds.removeAll(splitParentIds);
        storeVariantIds.addAll(splitVariantIds);
        storeVariantIds = storeVariantIds.stream().distinct().collect(Collectors.toList());
        //报价中的产品
        List<CustomerProductQuoteVo> quotingVariants = new ArrayList<>();
        if (ListUtils.isNotEmpty(storeVariantIds)) {
            List<Long> quotingVariantIds = quoteApplyService.getQuotingVariantIds();
            if (ListUtils.isNotEmpty(quotingVariantIds)) {
                for (Long storeVariantId : storeVariantIds) {
                    //报价中的产品从请求列表里删除
                    if (quotingVariantIds.contains(storeVariantId)){
                        CustomerProductQuoteVo customerProductQuoteVo = new CustomerProductQuoteVo();
                        customerProductQuoteVo.setStoreVariantId(storeVariantId);
                        customerProductQuoteVo.setQuoteType(5);
                        customerProductQuoteVo.setStoreParentVariantId(0L);
                        quotingVariants.add(customerProductQuoteVo);
                    }
                }
                storeVariantIds.removeAll(quotingVariantIds);
                if (ListUtils.isEmpty(storeVariantIds)) {
                    return quotingVariants;
                }
            }
        }
        request.setStoreVariantIds(storeVariantIds);
        //已报价的产品
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteDao.selectQuoteDetail(request);
        if (ListUtils.isNotEmpty(customerProductQuoteVos)){
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                storeVariantIds.remove(customerProductQuoteVo.getStoreVariantId());
            }
        }else {
            customerProductQuoteVos = new ArrayList<>();
        }
        customerProductQuoteVos.addAll(quotingVariants);
        //验证是否还有没查询出来报价信息的产品
//        storeVariantIds = request.getStoreVariantIds();
//        if (ListUtils.isEmpty(storeVariantIds)){
//            return customerProductQuoteVos;
//        }
        //有未查询到报价信息的产品
//        if (ListUtils.isNotEmpty(storeVariantIds)) {
//            StoreProductVariant storeProductVariant = storeProductVariantDao.selectByPrimaryKey(storeVariantIds.get(0));
//            StoreProductAttribute storeProductAttribute = storeProductAttributeDao.selectByPrimaryKey(storeProductVariant.getProductId());
//            Long customerId = storeProductAttribute.getCustomerId();
//            request.setStoreVariantIds(storeVariantIds);
//            if (ListUtils.isNotEmpty(request.getStoreVariantIds())) {
//                storeVariantIds = request.getStoreVariantIds();
//                List<CustomerProductQuoteVo> customerProductQuoteVoList = storeProductVariantDao.selectQuoteDetailByIds(storeVariantIds);
//                if (ListUtils.isNotEmpty(customerProductQuoteVoList)){
//                    List<CustomerProductQuote> customerProductQuotes = new ArrayList<>();
//                    for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVoList) {
//                        CustomerProductQuote customerProductQuote = new CustomerProductQuote();
//                        BeanUtils.copyProperties(customerProductQuoteVo,customerProductQuote);
//                        customerProductQuote.setCustomerId(customerId);
//                        customerProductQuotes.add(customerProductQuote);
//                    }
//                    customerProductQuoteDao.insertByBatch(customerProductQuotes);
//                    customerProductQuoteVos.addAll(customerProductQuoteVoList);
//                }
//            }
//        }
        return customerProductQuoteVos;
    }

    @Override
    public List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId, List<Long> storeVariantIds) {
        if (ListUtils.isNotEmpty(storeVariantIds) && null != customerId) {
            return customerProductQuoteDao.selectByCustomerAndStoreVariantIds(customerId, storeVariantIds);
        }
        return null;
    }

    /**
     *
     */
    public CustomerProductQuote selectByPrimaryKey(Long storeVariantId) {
        return customerProductQuoteDao.selectByPrimaryKey(storeVariantId);
    }

    @Override
    public boolean sendCustomerProductQuoteUpdateMessage(List<Long> storeVariantIds) {
        CustomerProductQuoteSearchRequest request = new CustomerProductQuoteSearchRequest();
        request.setStoreVariantIds(storeVariantIds);
        List<CustomerProductQuoteVo> customerProductQuoteVos = selectQuoteDetail(request);
        if (ListUtils.isEmpty(customerProductQuoteVos)){
            return false;
        }
        for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
            String key = RedisKey.STRING_QUOTED_STORE_VARIANT + customerProductQuoteVo.getStoreVariantId();
            redisTemplate.opsForValue().set(key,customerProductQuoteVo);
        }
        String tag = "quote";
        String key = UUID.randomUUID().toString();
        Message message = new Message(RocketMqConfig.TOPIC_CUSTOMER_PRODUCT_QUOTE_UPDATE, tag, key, JSON.toJSONBytes(customerProductQuoteVos));
        return productMqProducer.sendMessage(message);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerProductQuote record) {
        return customerProductQuoteDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(CustomerProductQuote record) {
        return customerProductQuoteDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<CustomerProductQuote> select(Page<CustomerProductQuote> record) {
        record.initFromNum();
        return customerProductQuoteDao.select(record);
    }

    @Override
    public List<CustomerProductQuote> all(AllCustomerQuoteProductSearchRequest request) {
        request.initFromNum();
        return customerProductQuoteDao.all(request);
    }

    @Override
    public long countAllQuoteProduct(AllCustomerQuoteProductSearchRequest request) {
        return customerProductQuoteDao.countAllQuoteProduct(request);
    }

    /**
     *
     */
    public long count(Page<CustomerProductQuote> record) {
        return customerProductQuoteDao.count(record);
    }

    @Override
    public int updateBatchByStoreProductVariant(List<StoreProductVariant> variants) {
        if (ListUtils.isNotEmpty(variants)){
            for (StoreProductVariant variant : variants) {
                String key = RedisKey.STRING_QUOTED_STORE_VARIANT + variant.getId();
                CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(key);
                if (null == customerProductQuoteVo){
                    continue;
                }
                customerProductQuoteVo.setStoreVariantName(variant.getTitle());
                customerProductQuoteVo.setStoreVariantSku(variant.getSku());
                customerProductQuoteVo.setStoreVariantImage(variant.getImage());
                redisTemplate.opsForValue().set(key,customerProductQuoteVo);

                CustomerProductQuote customerProductQuote = new CustomerProductQuote();
                customerProductQuote.setStoreVariantName(variant.getTitle());
                customerProductQuote.setStoreVariantSku(variant.getSku());
                customerProductQuote.setStoreVariantImage(variant.getImage());
                customerProductQuote.setStoreVariantId(variant.getId());
                updateByPrimaryKeySelective(customerProductQuote);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteProductBySkus(QuotedProductSelectBySkuRequest request) {
        Long customerId = request.getCustomerId();
        List<String> skus = request.getSkus();
        skus = skus.stream().distinct().collect(Collectors.toList());

//        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteDao.selectQuoteProductBySkus(customerId,skus);
//        if (ListUtils.isEmpty(customerProductQuoteVos)){
//            customerProductQuoteVos = new ArrayList<>();
//        }
//
//        for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
//            skus.remove(customerProductQuoteVo.getVariantSku());
//        }
//        if (ListUtils.isNotEmpty(skus)){
//
//            customerProductQuoteVos.addAll(customerProductQuoteVoList);
//        }
//
//        return customerProductQuoteVos;
        List<CustomerProductQuoteVo> customerProductQuoteVoList = productVariantService.selectQuoteProductBySkus(skus);
        return customerProductQuoteVoList;
    }
}