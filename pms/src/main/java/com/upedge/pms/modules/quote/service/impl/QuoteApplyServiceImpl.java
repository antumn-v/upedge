package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.ClaimQuoteApplyRequest;
import com.upedge.pms.modules.product.request.QuoteApplyProcessRequest;
import com.upedge.pms.modules.product.request.QuoteApplyProcessRequest.*;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.dto.QuoteApplyListDto;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.request.QuoteApplyListRequest;
import com.upedge.pms.modules.quote.vo.QuoteApplyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.quote.dao.QuoteApplyDao;
import com.upedge.pms.modules.quote.entity.QuoteApply;
import com.upedge.pms.modules.quote.service.QuoteApplyService;


@Service
public class QuoteApplyServiceImpl implements QuoteApplyService {

    @Autowired
    private QuoteApplyDao quoteApplyDao;

    @Autowired
    QuoteApplyItemDao quoteApplyItemDao;

    @Autowired
    CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;



    @Autowired
    ProductVariantDao productVariantDao;


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
        if (page.getT() == null){
            QuoteApplyListDto quoteApplyListDto = new QuoteApplyListDto();
            quoteApplyListDto.setQuoteState(QuoteApply.STATE_INIT);
            page.setT(quoteApplyListDto);
        }
        return quoteApplyDao.selectQuoteApplies(page);
    }

    @Override
    public Long quoteApplyCount(Page<QuoteApplyListDto> page) {
        if (page.getT() == null){
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
        || !quoteApply.getHandleUserId().equals(session.getId())){
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
    public BaseResponse processQuoteApply(QuoteApplyProcessRequest request, Long quoteApplyId, Session session) {
        QuoteApply quoteApply = quoteApplyDao.selectByPrimaryKey(quoteApplyId);
        if (!quoteApply.getHandleUserId().equals(session.getId()) || quoteApply.getQuoteState() != QuoteApply.STATE_PROCESSING){
            return BaseResponse.failed("无处理权限");
        }
        List<QuoteApplyProcessItem> quoteApplyProcessItems = request.getItems();
        List<QuoteApplyItem> quoteApplyItems = quoteApplyItemDao.selectByQuoteApplyId(quoteApplyId);
        List<CustomerProductQuote> customerProductQuotes = new ArrayList<>();
        for (QuoteApplyProcessItem quoteApplyProcessItem : quoteApplyProcessItems) {
            ProductVariant productVariant = productVariantDao.selectBySku(quoteApplyProcessItem.getVariantSku());
            if (null != productVariant){
                for (QuoteApplyItem quoteApplyItem : quoteApplyItems) {
                    if (quoteApplyItem.getId().equals(quoteApplyProcessItem.getQuoteApplyItemId())){
                        quoteApplyItem.setVariantId(productVariant.getId());
                        quoteApplyItem.setVariantImage(productVariant.getVariantImage());
                        quoteApplyItem.setVariantName(productVariant.getEnName());
                        quoteApplyItem.setVariantSku(productVariant.getVariantSku());
                        quoteApplyItem.setQuotePrice(quoteApplyProcessItem.getPrice());
                        quoteApplyItem.setProductId(productVariant.getProductId());
                        quoteApplyItemDao.updateByPrimaryKey(quoteApplyItem);

                        CustomerProductQuote customerProductQuote = new CustomerProductQuote();
                        BeanUtils.copyProperties(quoteApplyItem,customerProductQuote);
                        customerProductQuote.setCustomerId(quoteApply.getCustomerId());
                        customerProductQuotes.add(customerProductQuote);
                    }
                }
            }
        }
        if (ListUtils.isNotEmpty(customerProductQuotes)){
            customerProductQuoteDao.insertByBatch(customerProductQuotes);
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse claimQuoteApply(ClaimQuoteApplyRequest request, Session session) {
        Long applyId = request.getQuoteApplyId();

        QuoteApply quoteApply = quoteApplyDao.selectByPrimaryKey(applyId);
        if (quoteApply == null){
            return BaseResponse.failed("报价请求不存在");
        }
        if (quoteApply.getQuoteState() != QuoteApply.STATE_INIT){
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

        List<CustomerProductQuote> customerProductQuotes = customerProductQuoteDao.selectByCustomerAndStoreVariantIds(customerId,storeVariantIds);
        if (ListUtils.isNotEmpty(customerProductQuotes)){
            for (CustomerProductQuote customerProductQuote : customerProductQuotes) {
                if (storeVariantIds.contains(customerProductQuote.getStoreVariantId())){
                    storeVariantIds.remove(customerProductQuote.getStoreVariantId());
                }
            }
        }
        if (ListUtils.isEmpty(storeVariantIds)){
            return BaseResponse.failed();
        }
        Long applyId = IdGenerate.nextId();
        List<QuoteApplyItem> quoteApplyItems = new ArrayList<>();

        List<StoreProductVariant> storeProductVariants = storeProductVariantDao.selectByIds(storeVariantIds);
        for (StoreProductVariant storeProductVariant : storeProductVariants) {
            QuoteApplyItem quoteApplyItem = new QuoteApplyItem();
            quoteApplyItem.setStoreProductId(storeProductVariant.getProductId());
            quoteApplyItem.setStoreProductSku(storeProductVariant.getSku());
            quoteApplyItem.setStoreVariantId(storeProductVariant.getId());
            quoteApplyItem.setStoreVariantImage(storeProductVariant.getImage());
            quoteApplyItem.setStoreVariantName(storeProductVariant.getTitle());
            quoteApplyItem.setStoreProductTitle(storeProductVariant.getTitle());
            quoteApplyItem.setQuoteApplyId(applyId);
            quoteApplyItems.add(quoteApplyItem);
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

        return BaseResponse.success();
    }

    /**
     *
     */
    public QuoteApply selectByPrimaryKey(Long id){
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
    public List<QuoteApply> select(Page<QuoteApply> record){
        record.initFromNum();
        return quoteApplyDao.select(record);
    }

    /**
    *
    */
    public long count(Page<QuoteApply> record){
        return quoteApplyDao.count(record);
    }

}