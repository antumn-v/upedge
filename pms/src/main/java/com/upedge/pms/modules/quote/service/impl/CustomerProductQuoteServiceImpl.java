package com.upedge.pms.modules.quote.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.mq.producer.ProductMqProducer;
import jodd.util.StringUtil;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class CustomerProductQuoteServiceImpl implements CustomerProductQuoteService {

    @Autowired
    private CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;

    @Autowired
    QuoteApplyItemDao quoteApplyItemDao;

    @Autowired
    ProductMqProducer productMqProducer;

    @Autowired
    ProductVariantDao productVariantDao;

    @Autowired
    ProductService productService;



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
    public BaseResponse updateCustomerProductQuote(CustomerProductQuoteUpdateRequest request) {
        Long storeVariantId = request.getStoreVariantId();
        if (null == storeVariantId
        || StringUtil.isEmpty(request.getVariantSku())
        || null == request.getQuotePrice()
        || 1 != request.getQuotePrice().compareTo(BigDecimal.ZERO)){
            return BaseResponse.failed();
        }
        CustomerProductQuote customerProductQuote = customerProductQuoteDao.selectByStoreVariantId(storeVariantId);
        if (null == customerProductQuote){
            return BaseResponse.failed("该产品未报价");
        }
        ProductVariant productVariant = productVariantDao.selectBySku(request.getVariantSku());
        if (null == productVariant){
            return BaseResponse.failed("sku不存在");
        }
        Product product = productService.selectByPrimaryKey(productVariant.getProductId());
        customerProductQuote.setProductId(productVariant.getProductId());
        customerProductQuote.setProductTitle(product.getProductTitle());
        customerProductQuote.setVariantId(productVariant.getId());
        customerProductQuote.setVariantImage(productVariant.getVariantImage());
        customerProductQuote.setVariantName(productVariant.getEnName());
        customerProductQuote.setVariantSku(productVariant.getVariantSku());
        customerProductQuote.setQuotePrice(request.getQuotePrice());
        customerProductQuoteDao.updateByPrimaryKeySelective(customerProductQuote);

        List<Long> storeVariantIds = new ArrayList<>();
        storeVariantIds.add(request.getStoreVariantId());
        sendCustomerProductQuoteUpdateMessage(storeVariantIds);
        return BaseResponse.success();
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request) {
        if (request == null){
            return new ArrayList<>();
        }
        List<CustomerProductQuoteVo> quotingVariants = new ArrayList<>();
        List<Long> storeVariantIds = quoteApplyItemDao.selectQuotingStoreVariantIds(request.getStoreVariantIds());
        if (ListUtils.isNotEmpty(storeVariantIds)){
            for (Long storeVariantId : storeVariantIds) {
                CustomerProductQuoteVo customerProductQuoteVo = new CustomerProductQuoteVo();
                customerProductQuoteVo.setStoreVariantId(storeVariantId);
                customerProductQuoteVo.setQuoteType(5);
                quotingVariants.add(customerProductQuoteVo);
            }
            request.getStoreVariantIds().removeAll(storeVariantIds);
            if (ListUtils.isEmpty(request.getStoreVariantIds())){
                return quotingVariants;
            }
        }
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteDao.selectQuoteDetail(request);
        if (ListUtils.isEmpty(customerProductQuoteVos) && ListUtils.isNotEmpty(request.getStoreVariantIds())) {
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                storeVariantIds.add(customerProductQuoteVo.getStoreVariantId());
            }
            request.getStoreVariantIds().removeAll(storeVariantIds);
            if (ListUtils.isNotEmpty(request.getStoreVariantIds())){
                storeVariantIds = request.getStoreVariantIds();
                List<CustomerProductQuoteVo> customerProductQuoteVoList = storeProductVariantDao.selectQuoteDetailByIds(storeVariantIds);
                customerProductQuoteVos.addAll(customerProductQuoteVoList);
            }
        }
        customerProductQuoteVos.addAll(quotingVariants);
        return customerProductQuoteVos;
    }

    @Override
    public List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId, List<Long> storeVariantIds) {
        if (ListUtils.isNotEmpty(storeVariantIds) && null != customerId){
            return customerProductQuoteDao.selectByCustomerAndStoreVariantIds(customerId,storeVariantIds);
        }
        return null;
    }

    /**
     *
     */
    public CustomerProductQuote selectByPrimaryKey(Long customerId){
        CustomerProductQuote record = new CustomerProductQuote();
        record.setCustomerId(customerId);
        return customerProductQuoteDao.selectByPrimaryKey(record);
    }

    @Override
    public boolean sendCustomerProductQuoteUpdateMessage(List<Long> storeVariantIds) {
        CustomerProductQuoteSearchRequest request = new CustomerProductQuoteSearchRequest();
        request.setStoreVariantIds(storeVariantIds);
        List<CustomerProductQuoteVo> customerProductQuoteVos = selectQuoteDetail(request);
        String tag = "quote";
        String key = UUID.randomUUID().toString();
        Message message = new Message(RocketMqConfig.TOPIC_CUSTOMER_PRODUCT_QUOTE_UPDATE,tag,key, JSON.toJSONBytes(customerProductQuoteVos));
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
    public List<CustomerProductQuote> select(Page<CustomerProductQuote> record){
        record.initFromNum();
        return customerProductQuoteDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerProductQuote> record){
        return customerProductQuoteDao.count(record);
    }

}