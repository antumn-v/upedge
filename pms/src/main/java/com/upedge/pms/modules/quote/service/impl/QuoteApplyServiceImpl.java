package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.ClaimQuoteApplyRequest;
import com.upedge.pms.modules.product.request.QuoteApplyProcessRequest;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.dao.QuoteApplyDao;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.dto.QuoteApplyListDto;
import com.upedge.pms.modules.quote.dto.QuoteApplyProcessItem;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.pms.modules.quote.entity.QuoteApply;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.ProductQuoteRecordService;
import com.upedge.pms.modules.quote.service.QuoteApplyService;
import com.upedge.pms.modules.quote.vo.QuoteApplyVo;
import com.upedge.pms.mq.producer.ProductMqProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class QuoteApplyServiceImpl implements QuoteApplyService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private QuoteApplyDao quoteApplyDao;

    @Autowired
    QuoteApplyItemDao quoteApplyItemDao;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;

    @Autowired
    CustomerProductQuoteService customerProductQuoteService;

    @Autowired
    ProductVariantDao productVariantDao;

    @Autowired
    ProductQuoteRecordService productQuoteRecordService;

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    StoreProductService storeProductService;

    @Autowired
    ProductMqProducer productMqProducer;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        QuoteApply record = new QuoteApply();
        record.setId(id);
        return quoteApplyDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(QuoteApply record) {
        return quoteApplyDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(QuoteApply record) {
        return quoteApplyDao.insert(record);
    }

    @Override
    public List<QuoteApplyVo> quoteApplyList(Page<QuoteApplyListDto> page) {
        if (page.getT() == null) {
            QuoteApplyListDto quoteApplyListDto = new QuoteApplyListDto();
            quoteApplyListDto.setQuoteState(QuoteApply.STATE_INIT);
            page.setT(quoteApplyListDto);
        }
        return quoteApplyDao.selectQuoteApplies(page);
    }

    @Override
    public Long quoteApplyCount(Page<QuoteApplyListDto> page) {
        if (page.getT() == null) {
            QuoteApplyListDto quoteApplyListDto = new QuoteApplyListDto();
            quoteApplyListDto.setQuoteState(QuoteApply.STATE_INIT);
            page.setT(quoteApplyListDto);
        }
        return quoteApplyDao.countQuoteApplies(page);
    }

    @Override
    public BaseResponse finishQuoteApply(Long quoteApplyId, Session session) {
        QuoteApply quoteApply = quoteApplyDao.selectByPrimaryKey(quoteApplyId);
        if (null == quoteApply
                || quoteApply.getQuoteState() != QuoteApply.STATE_PROCESSING
                || !quoteApply.getHandleUserId().equals(session.getId())) {
            return BaseResponse.failed();
        }
        quoteApply = new QuoteApply();
        quoteApply.setId(quoteApplyId);
        quoteApply.setQuoteState(QuoteApply.STATE_FINISHED);
        quoteApplyDao.updateByPrimaryKeySelective(quoteApply);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse processQuoteApply(QuoteApplyProcessRequest request, Long quoteApplyId, Session session) throws CustomerException {
        QuoteApply quoteApply = quoteApplyDao.selectByPrimaryKey(quoteApplyId);
        if (!quoteApply.getHandleUserId().equals(session.getId()) || quoteApply.getQuoteState() != QuoteApply.STATE_PROCESSING) {
            return BaseResponse.failed("权限不足");
        }
        List<QuoteApplyProcessItem> quoteApplyProcessItems = request.getItems();

        //想先将该报价请求的所有产品存入map
        List<QuoteApplyItem> quoteApplyItems = quoteApplyItemDao.selectByQuoteApplyId(quoteApplyId);
        Map<Long, QuoteApplyItem> quoteApplyItemMap = new HashMap<>();
        for (QuoteApplyItem quoteApplyItem : quoteApplyItems) {
            quoteApplyItemMap.put(quoteApplyItem.getId(), quoteApplyItem);
        }
        List<CustomerProductQuote> customerProductQuotes = new ArrayList<>();
        Map<Long, Product> map = new HashMap<>();
        List<Long> storeVariantIds = new ArrayList<>();
        Date date = new Date();
        List<ProductQuoteRecord> productQuoteRecords = new ArrayList<>();

        //处理的报价信息挨个从map里取出对应的报价产品
        for (QuoteApplyProcessItem quoteApplyProcessItem : quoteApplyProcessItems) {
            QuoteApplyItem quoteApplyItem = quoteApplyItemMap.get(quoteApplyProcessItem.getQuoteApplyItemId());
            if (null == quoteApplyItem) {
                continue;
            }
            //检查是否是已拆分变体
            Long storeVariantId = quoteApplyItem.getStoreVariantId();
            boolean b = storeProductVariantService.redisCheckIfSplitVariant(storeVariantId);
            if (b){
                return BaseResponse.failed("已拆分变体不能报价");
            }
            StoreProductVariant storeProductVariant = storeProductVariantService.selectByPrimaryKey(storeVariantId);
            CustomerProductQuote customerProductQuote = new CustomerProductQuote();
            BeanUtils.copyProperties(quoteApplyItem, customerProductQuote);
            customerProductQuote.setStoreParentVariantId(storeProductVariant.getParentVariantId());
            customerProductQuote.setCustomerId(quoteApply.getCustomerId());
            //取消报价的产品
            if (!quoteApplyProcessItem.isCanQuote()) {
                ProductQuoteRecord productQuoteRecord = new ProductQuoteRecord();
                productQuoteRecord.setStoreVariantId(quoteApplyItem.getStoreVariantId());
                productQuoteRecord.setStoreProductId(quoteApplyItem.getStoreProductId());
                productQuoteRecord.setUserId(session.getId());
                productQuoteRecord.setCreateTime(date);
                productQuoteRecords.add(productQuoteRecord);
                quoteApplyItem.setState(2);
                quoteApplyItemDao.updateByPrimaryKey(quoteApplyItem);
                customerProductQuote.setQuoteState(0);
                customerProductQuote.setQuoteScale(0);
                customerProductQuotes.add(customerProductQuote);
                storeVariantIds.add(quoteApplyItem.getStoreVariantId());
                continue;
            }
            //价格或sku为空的不能报价
            if (StringUtils.isBlank(quoteApplyProcessItem.getVariantSku())
            || null == quoteApplyProcessItem.getPrice()){
                continue;
            }
            //判读sku
            ProductVariant productVariant = productVariantDao.selectBySku(quoteApplyProcessItem.getVariantSku());
            if (null == productVariant
                    || null == productVariant.getWeight()
                    || null == productVariant.getVolumeWeight()
                    || 0 == BigDecimal.ZERO.compareTo(productVariant.getVolumeWeight())
                    || 0 == BigDecimal.ZERO.compareTo(productVariant.getWeight())) {
                return BaseResponse.failed(quoteApplyProcessItem.getVariantSku() + " 变体未编辑完成");
            } else {
                //匹配报价申请产品并保存报价记录
                quoteApplyItem.setVariantId(productVariant.getId());
                quoteApplyItem.setVariantImage(productVariant.getVariantImage());
                quoteApplyItem.setVariantName(productVariant.getEnName());
                quoteApplyItem.setVariantSku(productVariant.getVariantSku());
                quoteApplyItem.setProductId(productVariant.getProductId());
                quoteApplyItem.setQuotePrice(quoteApplyProcessItem.getPrice());
                quoteApplyItem.setQuoteScale(quoteApplyProcessItem.getQuoteScale());
                quoteApplyItem.setState(1);
                quoteApplyItemDao.updateByPrimaryKey(quoteApplyItem);

                //报价记录
                ProductQuoteRecord productQuoteRecord = new ProductQuoteRecord();
                BeanUtils.copyProperties(quoteApplyItem, productQuoteRecord);
                productQuoteRecord.setUserId(session.getId());
                productQuoteRecord.setCreateTime(date);
                productQuoteRecords.add(productQuoteRecord);
                //保存报价关联
                Product product = map.get(productVariant.getProductId());
                if (product == null) {
                    product = productService.selectByPrimaryKey(productVariant.getProductId());
                    if (product == null
                            || product.getShippingId() == null) {
                        return BaseResponse.failed(product.getId() + " 产品不存在或产品未配置运输模板");
                    }
                    map.put(product.getId(), product);
                }
                BeanUtils.copyProperties(quoteApplyItem, customerProductQuote);
                customerProductQuote.setProductTitle(product.getProductTitle());
                customerProductQuote.setQuoteState(1);
                customerProductQuote.setUpdateTime(new Date());
                if(StringUtils.isBlank(customerProductQuote.getStoreVariantImage())){
                    customerProductQuote.setStoreVariantImage(productVariant.getVariantImage());
                }
                customerProductQuotes.add(customerProductQuote);
                storeVariantIds.add(quoteApplyItem.getStoreVariantId());
            }
        }
        //判断是否还有未报价的产品
        List<QuoteApplyItem> unQuoteItems = quoteApplyItemDao.selectUnQuoteItemByApplyId(quoteApplyId);
        quoteApply = new QuoteApply();
        quoteApply.setId(quoteApplyId);
        quoteApply.setUpdateTime(new Date());
        if (ListUtils.isNotEmpty(unQuoteItems)) {
            quoteApply.setQuoteType(QuoteApply.PART_QUOTED);
        } else {
            quoteApply.setQuoteType(QuoteApply.ALL_QUOTED);
            quoteApply.setQuoteState(QuoteApply.STATE_FINISHED);
        }
        quoteApplyDao.updateByPrimaryKeySelective(quoteApply);
        if (ListUtils.isNotEmpty(customerProductQuotes)) {
            customerProductQuoteDao.insertByBatch(customerProductQuotes);
        }
        if (ListUtils.isNotEmpty(productQuoteRecords)) {
            productQuoteRecordService.insertByBatch(productQuoteRecords);
        }
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CustomerProductQuoteSearchRequest customerProductQuoteSearchRequest = new CustomerProductQuoteSearchRequest();
                customerProductQuoteSearchRequest.setStoreVariantIds(storeVariantIds);
                customerProductQuoteService.sendCustomerProductQuoteUpdateMessage(storeVariantIds);
            }
        },threadPoolExecutor);

        redisDeleteQuotingVariant(storeVariantIds);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse claimQuoteApply(ClaimQuoteApplyRequest request, Session session) {
        Long applyId = request.getQuoteApplyId();

        QuoteApply quoteApply = quoteApplyDao.selectByPrimaryKey(applyId);
        if (quoteApply == null) {
            return BaseResponse.failed("报价请求不存在");
        }
        if (quoteApply.getQuoteState() != QuoteApply.STATE_INIT) {
            return BaseResponse.failed("报价请求已被处理");
        }
        quoteApply = new QuoteApply();
        quoteApply.setId(applyId);
        quoteApply.setHandleUserId(session.getId());
        quoteApply.setQuoteState(QuoteApply.STATE_PROCESSING);
        quoteApply.setUpdateTime(new Date());
        quoteApplyDao.updateByPrimaryKeySelective(quoteApply);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse orderQuoteApply(OrderQuoteApplyRequest request) {
        List<Long> storeVariantIds = request.getStoreVariantId();
        Long customerId = request.getCustomerId();

        Long applyId = IdGenerate.nextId();
        List<QuoteApplyItem> quoteApplyItems = new ArrayList<>();
        List<Long> quotingVariantIds = new ArrayList<>();
        List<StoreProductVariant> storeProductVariants = storeProductVariantDao.selectByIds(storeVariantIds);
        for (StoreProductVariant storeProductVariant : storeProductVariants) {
            //判断产品是否已报价
            String key = RedisKey.STRING_QUOTED_STORE_VARIANT + storeProductVariant.getId();
            if (redisTemplate.hasKey(key)){
                continue;
            }
            CustomerProductQuoteVo customerProductQuoteVo = new CustomerProductQuoteVo();
            customerProductQuoteVo.setStoreVariantId(storeProductVariant.getId());
            customerProductQuoteVo.setQuoteType(5);
            customerProductQuoteVo.setStoreParentVariantId(0L);
            redisTemplate.opsForValue().set(key,customerProductQuoteVo);

            QuoteApplyItem quoteApplyItem = new QuoteApplyItem();
            quoteApplyItem.setStoreProductId(storeProductVariant.getProductId());
            quoteApplyItem.setStoreVariantSku(storeProductVariant.getSku());
            quoteApplyItem.setStoreVariantId(storeProductVariant.getId());
            quoteApplyItem.setStoreVariantImage(storeProductVariant.getImage());
            quoteApplyItem.setStoreVariantName(storeProductVariant.getTitle());
            quoteApplyItem.setStoreProductTitle(storeProductVariant.getTitle());
            quoteApplyItem.setQuoteApplyId(applyId);
            quoteApplyItem.setState(0);
            quoteApplyItems.add(quoteApplyItem);
            quotingVariantIds.add(quoteApplyItem.getStoreVariantId());

            storeProductService.toNormalProduct(storeProductVariant.getProductId(),0L);
        }
        if(ListUtils.isEmpty(quoteApplyItems)){
            return BaseResponse.failed();
        }
        quoteApplyItemDao.insertByBatch(quoteApplyItems);
        QuoteApply quoteApply = new QuoteApply();
        quoteApply.setId(applyId);
        quoteApply.setApplyUserId(request.getUserId());
        quoteApply.setCustomerId(customerId);
        quoteApply.setQuoteState(0);
        quoteApply.setQuoteType(0);
        quoteApply.setRelateOrderId(request.getOrderId());
        quoteApply.setCreateTime(new Date());
        quoteApply.setUpdateTime(new Date());
        quoteApplyDao.insert(quoteApply);
        redisAddQuotingVariant(quotingVariantIds);
        return BaseResponse.success();
    }

    /**
     *
     */
    public QuoteApply selectByPrimaryKey(Long id) {
        QuoteApply record = new QuoteApply();
        record.setId(id);
        return quoteApplyDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(QuoteApply record) {
        return quoteApplyDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(QuoteApply record) {
        return quoteApplyDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<QuoteApply> select(Page<QuoteApply> record) {
        record.initFromNum();
        return quoteApplyDao.select(record);
    }

    /**
     *
     */
    public long count(Page<QuoteApply> record) {
        return quoteApplyDao.count(record);
    }

    public boolean redisCheckIfQuotingVariant(Long storeVariantId){
        List<Long> splitVariantIds = getQuotingVariantIds();
        if (ListUtils.isEmpty(splitVariantIds)){
            return false;
        }
        return true;
    }

    @Override
    public List<Long> getQuotingVariantIds(){
        List<Long> splitVariantIds = (List<Long>) redisTemplate.opsForList().range(RedisKey.LIST_QUOTING_STORE_VARIANT,0,-1);
        if (null == splitVariantIds){
            return new ArrayList<>();
        }
        return splitVariantIds;
    }

    public void redisDeleteQuotingVariant(List<Long> storeVariantIds){
        if(ListUtils.isEmpty(storeVariantIds)){
            return;
        }
        for (Long storeVariantId : storeVariantIds) {
            redisTemplate.opsForList().remove(RedisKey.LIST_QUOTING_STORE_VARIANT,0,storeVariantId);
        }
    }

    public void redisAddQuotingVariant(List<Long> storeVariantIds){
        if (ListUtils.isEmpty(storeVariantIds)){
            return;
        }
        redisTemplate.opsForList().leftPushAll(RedisKey.LIST_QUOTING_STORE_VARIANT,storeVariantIds);
    }

}