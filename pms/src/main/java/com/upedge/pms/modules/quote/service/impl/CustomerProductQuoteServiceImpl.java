package com.upedge.pms.modules.quote.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.dao.StoreProductAttributeDao;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.ProductQuoteRecordService;
import com.upedge.pms.mq.producer.ProductMqProducer;
import jodd.util.StringUtil;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class CustomerProductQuoteServiceImpl implements CustomerProductQuoteService {

    @Autowired
    private CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;

    @Autowired
    StoreProductAttributeDao storeProductAttributeDao;

    @Autowired
    QuoteApplyItemDao quoteApplyItemDao;

    @Autowired
    ProductMqProducer productMqProducer;

    @Autowired
    ProductVariantDao productVariantDao;

    @Autowired
    ProductService productService;

    @Autowired
    ProductQuoteRecordService productQuoteRecordService;


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
        return customerProductQuoteDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerProductQuote record) {
        return customerProductQuoteDao.insert(record);
    }

    @Override
    public BaseResponse revokeQuote(Long storeVariantId, Session session) {

        CustomerProductQuote customerProductQuote = customerProductQuoteDao.selectByStoreVariantId(storeVariantId);
        if (null == customerProductQuote){
            return BaseResponse.failed("请求信息有误");
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
        updateByPrimaryKey(customerProductQuote);


        List<Long> storeVariantIds = new ArrayList<>();
        storeVariantIds.add(storeVariantId);
        sendCustomerProductQuoteUpdateMessage(storeVariantIds);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse updateCustomerProductQuote(CustomerProductQuoteUpdateRequest request, Session session) throws CustomerException {
        Long storeVariantId = request.getStoreVariantId();
        if (null == storeVariantId
                || StringUtil.isEmpty(request.getVariantSku())
                || null == request.getQuotePrice()) {
            return BaseResponse.failed();
        }
        CustomerProductQuote customerProductQuote = customerProductQuoteDao.selectByStoreVariantId(storeVariantId);
        if (null == customerProductQuote) {
            return BaseResponse.failed("该产品未报价");
        }
        if (customerProductQuote.getQuotePrice() != null &&
                StringUtil.isNotBlank(customerProductQuote.getVariantSku()) &&
                customerProductQuote.getQuotePrice().compareTo(request.getQuotePrice()) == 0
                && customerProductQuote.getVariantSku().equals(request.getVariantSku())) {
            return BaseResponse.success();
        }
        ProductVariant productVariant = productVariantDao.selectBySku(request.getVariantSku());
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
        customerProductQuoteDao.updateByPrimaryKeySelective(customerProductQuote);

        ProductQuoteRecord productQuoteRecord = new ProductQuoteRecord();
        BeanUtils.copyProperties(customerProductQuote, productQuoteRecord);
        productQuoteRecord.setUserId(session.getId());
        productQuoteRecord.setCreateTime(new Date());
        productQuoteRecordService.insert(productQuoteRecord);

        List<Long> storeVariantIds = new ArrayList<>();
        storeVariantIds.add(request.getStoreVariantId());
        boolean b = sendCustomerProductQuoteUpdateMessage(storeVariantIds);
        if (!b){
            throw new CustomerException("mq异常，请重新提交或联系IT!");
        }
        return BaseResponse.success();
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request) {
        if (request == null) {
            return new ArrayList<>();
        }
        //报价中的产品
        List<CustomerProductQuoteVo> quotingVariants = new ArrayList<>();
        List<Long> storeVariantIds = new ArrayList<>();
        if (ListUtils.isNotEmpty(request.getStoreVariantIds())) {
            storeVariantIds = quoteApplyItemDao.selectQuotingStoreVariantIds(request.getStoreVariantIds());
            if (ListUtils.isNotEmpty(storeVariantIds)) {
                for (Long storeVariantId : storeVariantIds) {
                    CustomerProductQuoteVo customerProductQuoteVo = new CustomerProductQuoteVo();
                    customerProductQuoteVo.setStoreVariantId(storeVariantId);
                    customerProductQuoteVo.setQuoteType(5);
                    quotingVariants.add(customerProductQuoteVo);
                }
                request.getStoreVariantIds().removeAll(storeVariantIds);
                if (ListUtils.isEmpty(request.getStoreVariantIds())) {
                    return quotingVariants;
                }
            }
        }
        //已报价的产品
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteDao.selectQuoteDetail(request);
        //验证是否还有没查询出来报价信息的产品
        storeVariantIds = request.getStoreVariantIds();
        if (ListUtils.isEmpty(storeVariantIds)){
            return customerProductQuoteVos;
        }
        if (ListUtils.isNotEmpty(customerProductQuoteVos)){
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                storeVariantIds.remove(customerProductQuoteVo.getStoreVariantId());
            }
        }else {
            customerProductQuoteVos = new ArrayList<>();
        }
        //有未查询到报价信息的产品
        if (ListUtils.isNotEmpty(storeVariantIds)) {
            StoreProductVariant storeProductVariant = storeProductVariantDao.selectByPrimaryKey(storeVariantIds.get(0));
            StoreProductAttribute storeProductAttribute = storeProductAttributeDao.selectByPrimaryKey(storeProductVariant.getProductId());
            Long customerId = storeProductAttribute.getCustomerId();
            request.setStoreVariantIds(storeVariantIds);
            if (ListUtils.isNotEmpty(request.getStoreVariantIds())) {
                storeVariantIds = request.getStoreVariantIds();
                List<CustomerProductQuoteVo> customerProductQuoteVoList = storeProductVariantDao.selectQuoteDetailByIds(storeVariantIds);
                if (ListUtils.isNotEmpty(customerProductQuoteVoList)){
                    List<CustomerProductQuote> customerProductQuotes = new ArrayList<>();
                    for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVoList) {
                        CustomerProductQuote customerProductQuote = new CustomerProductQuote();
                        BeanUtils.copyProperties(customerProductQuoteVo,customerProductQuote);
                        customerProductQuote.setCustomerId(customerId);
                        customerProductQuotes.add(customerProductQuote);
                        customerProductQuoteDao.insertByBatch(customerProductQuotes);
                    }
                    customerProductQuoteVos.addAll(customerProductQuoteVoList);
                }
            }
        }
        customerProductQuoteVos.addAll(quotingVariants);
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
        String tag = "quote";
        String key = UUID.randomUUID().toString();
        Message message = new Message(RocketMqConfig.TOPIC_CUSTOMER_PRODUCT_QUOTE_UPDATE, tag, key, JSON.toJSONBytes(customerProductQuoteVos));
        message.setDelayTimeLevel(0);
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

    /**
     *
     */
    public long count(Page<CustomerProductQuote> record) {
        return customerProductQuoteDao.count(record);
    }

}