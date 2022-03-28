package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.FileUtil;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.dto.StoreProductVariantSplitVo;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.StoreProductVariantQuoteRequest;
import com.upedge.pms.modules.product.request.StoreProductVariantSplitRequest;
import com.upedge.pms.modules.product.request.StoreSplitVariantUpdateRequest;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.product.vo.SplitVariantIdVo;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class StoreProductVariantServiceImpl implements StoreProductVariantService {

    @Autowired
    private StoreProductVariantDao storeProductVariantDao;

    @Autowired
    CustomerProductQuoteService customerProductQuoteService;

    @Autowired
    StoreProductAttributeService storeProductAttributeService;

    @Autowired
    QuoteApplyItemService quoteApplyItemService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Value("${files.image.prefix}")
    String pmsImagePrefix;

    @Value("${files.image.local}")
    String pmsImageLocal;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreProductVariant record = new StoreProductVariant();
        record.setId(id);
        return storeProductVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreProductVariant record) {
        return storeProductVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreProductVariant record) {
        return storeProductVariantDao.insert(record);
    }

    @Override
    public BaseResponse variantQuote(StoreProductVariantQuoteRequest request, Session session) {
        Long storeVariantId = request.getStoreVariantId();
        if (null == storeVariantId){
            return BaseResponse.failed("请求数据为空");
        }
        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        if (null == storeProductVariant){
            return BaseResponse.failed("店铺子体不存在");
        }
        StoreProductAttribute storeProductAttribute = storeProductAttributeService.selectByPrimaryKey(storeProductVariant.getProductId());
        CustomerProductQuoteUpdateRequest customerProductQuoteUpdateRequest = new CustomerProductQuoteUpdateRequest();
        customerProductQuoteUpdateRequest.setQuotePrice(request.getQuotePrice());
        customerProductQuoteUpdateRequest.setStoreVariantId(storeVariantId);
        customerProductQuoteUpdateRequest.setVariantSku(request.getVariantSku());
        customerProductQuoteUpdateRequest.setQuoteScale(request.getQuoteScale());
        customerProductQuoteUpdateRequest.setCustomerId(storeProductAttribute.getCustomerId());
        try {
            return customerProductQuoteService.updateCustomerProductQuote(customerProductQuoteUpdateRequest,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @Override
    public List<SplitVariantIdVo> selectSplitVariantIds() {
        return storeProductVariantDao.selectSplitVariantIds();
    }

    @Transactional
    @Override
    public BaseResponse splitVariantDelete(Long storeVariantId, Session session) {
        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        if (storeProductVariant == null
        || storeProductVariant.getSplitType() != 2){
            return BaseResponse.failed("只能撤销拆分子体");
        }
        storeProductVariant.setSplitType(3);
        updateByPrimaryKeySelective(storeProductVariant);
        //更新redis拆分子体信息
        List<Long> splitVariantIds = getSplitVariantIdsByParentId(storeProductVariant.getParentVariantId());
        if (ListUtils.isNotEmpty(splitVariantIds)){
            splitVariantIds.remove(storeVariantId);
            redisUpdateSplitVariant(storeProductVariant.getParentVariantId(),splitVariantIds);
        }
        //验证父体下的拆分子体是否全都被删除
        List<StoreProductVariant> storeProductVariants = selectSplitVariantByParentId(storeProductVariant.getParentVariantId());
        if (ListUtils.isEmpty(storeProductVariants)){
            storeProductVariant = new StoreProductVariant();
            storeProductVariant.setId(storeVariantId);
            storeProductVariant.setSplitType(0);
            updateByPrimaryKeySelective(storeProductVariant);
            //从redis中删除
            redisDeleteIfSplitVariant(storeVariantId);
        }
        return customerProductQuoteService.revokeQuote(storeVariantId,session);
    }

    @Transactional
    @Override
    public BaseResponse splitVariantUpdate(StoreSplitVariantUpdateRequest request, Session session) {
        Long storeVariantId = request.getStoreVariantId();
        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        if (storeProductVariant == null
        || storeProductVariant.getSplitType() != 2){
            return BaseResponse.failed("只能修改拆分子体");
        }
        String image = request.getImage();
        if (StringUtils.isBlank(image)){
            if (StringUtils.isBlank(request.getImageBase64())){
                return BaseResponse.failed("图片不能为空");
            }
            image = FileUtil.uploadImage(request.getImageBase64(),pmsImagePrefix,pmsImageLocal);
        }
        storeProductVariant = new StoreProductVariant();
        storeProductVariant.setTitle(request.getName());
        storeProductVariant.setId(storeVariantId);
        storeProductVariant.setImage(image);
        updateByPrimaryKeySelective(storeProductVariant);
        //更新订单产品信息
        OrderItemUpdateImageNameRequest orderItemUpdateImageNameRequest = new OrderItemUpdateImageNameRequest();
        orderItemUpdateImageNameRequest.setName(request.getName());
        orderItemUpdateImageNameRequest.setImage(image);
        orderItemUpdateImageNameRequest.setStoreVariantId(storeVariantId);

        CustomerProductQuote customerProductQuote = new CustomerProductQuote();
        customerProductQuote.setStoreVariantId(storeVariantId);
        customerProductQuote.setStoreVariantName(request.getName());
        customerProductQuote.setStoreVariantImage(image);
        customerProductQuoteService.updateByPrimaryKeySelective(customerProductQuote);

        return omsFeignClient.updateImageNameByStoreVariantId(orderItemUpdateImageNameRequest);
    }

    @Override
    public List<StoreProductVariant> selectSplitVariantByParentId(Long parentVariantId) {
        if (null != parentVariantId){
            return storeProductVariantDao.selectSplitVariantByParentId(parentVariantId);
        }
        return null;
    }

    //拆分店铺变体
    @Transactional
    @Override
    public BaseResponse variantSplit(StoreProductVariantSplitRequest request, Session session) {
        Long storeVariantId = request.getStoreVariantId();
        CustomerProductQuote customerProductQuote = customerProductQuoteService.selectByPrimaryKey(storeVariantId);
        if (customerProductQuote!= null
        && customerProductQuote.getQuoteState() == 1){
            return BaseResponse.failed("已报价的变体不能拆分");
        }
        QuoteApplyItem quoteApplyItem = quoteApplyItemService.selectByStoreVariantId(storeVariantId);
        if (quoteApplyItem != null
        && quoteApplyItem.getState() == 0){
            return BaseResponse.failed("报价中的变体不能拆分");
        }

        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        //未拆分的变体可以拆
        if (null == storeProductVariant
        || storeProductVariant.getSplitType() > 1){
            return BaseResponse.failed("变体不存在或拆分变体不允许拆分");
        }
        List<StoreProductVariantSplitVo> splitVos = request.getSplitVos();
        if (ListUtils.isEmpty(splitVos)){
            return BaseResponse.failed("子变体不能为空");
        }
        String variantSku = storeProductVariant.getSku();
        if (StringUtils.isBlank(variantSku)){
            variantSku = String.valueOf(System.currentTimeMillis());
        }
        List<Long> splitVariantIds = new ArrayList<>();
        List<StoreProductVariant> splitVariants = new ArrayList<>();
        int i = 1;
        for (StoreProductVariantSplitVo storeProductVariantSplitVo : splitVos) {
            String image = storeProductVariantSplitVo.getImage();
            if (StringUtils.isBlank(image)){
                if (StringUtils.isBlank(storeProductVariantSplitVo.getImageBase64())){
                    return BaseResponse.failed("图片不能为空");
                }
                image = FileUtil.uploadImage(storeProductVariantSplitVo.getImageBase64(),pmsImagePrefix,pmsImageLocal);
            }
            Long splitVariantId = IdGenerate.nextId();
            StoreProductVariant splitVariant = new StoreProductVariant();
            BeanUtils.copyProperties(storeProductVariant,splitVariant);
            splitVariant.setImage(image);
            splitVariant.setTitle(storeProductVariantSplitVo.getName());
            splitVariant.setParentVariantId(storeVariantId);
            splitVariant.setSku(variantSku + "-" + i);
            splitVariant.setSplitType(2);
            splitVariant.setId(splitVariantId);
            splitVariantIds.add(splitVariantId);
            splitVariants.add(splitVariant);
            i++;
        }
        storeProductVariantDao.insertByBatch(splitVariants);
        //修改变体拆分状态
        storeProductVariant = new StoreProductVariant();
        storeProductVariant.setId(storeVariantId);
        storeProductVariant.setSplitType(1);
        updateByPrimaryKeySelective(storeProductVariant);
        //redis保存已拆分的变体
        redisAddSplitVariant(storeVariantId,splitVariantIds);
        return BaseResponse.success();
    }

    @Override
    public List<StoreProductVariant> selectBySku(String sku) {
        if (StringUtils.isNotBlank(sku)){
            return storeProductVariantDao.selectBySku(sku);
        }
        return null;
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetailByIds(List<Long> ids) {
        return storeProductVariantDao.selectQuoteDetailByIds(ids);
    }

    /**
     *
     */
    public StoreProductVariant selectByPrimaryKey(Long id){
        return storeProductVariantDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreProductVariant record) {
        return storeProductVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreProductVariant record) {
        return storeProductVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreProductVariant> select(Page<StoreProductVariant> record){
        record.initFromNum();
        return storeProductVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreProductVariant> record){
        return storeProductVariantDao.count(record);
    }

    @Override
    public boolean redisCheckIfSplitVariant(Long storeVariantId){
       List<Long> splitVariantIds = getSplitVariantIdsByParentId(storeVariantId);
        if (ListUtils.isEmpty(splitVariantIds)){
            return false;
        }
        return true;

    }

    @Override
    public List<Long> getSplitVariantIdsByParentId(Long storeVariantId){
        List<Long> splitVariantIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_STORE_SPLIT_VARIANT,storeVariantId.toString());
        return splitVariantIds;
    }

    public void redisDeleteIfSplitVariant(Long storeVariantId){
        redisTemplate.opsForHash().delete(RedisKey.HASH_STORE_SPLIT_VARIANT,storeVariantId.toString());
    }

    public void redisAddSplitVariant(Long storeVariantId,List<Long> splitVariantIds){
        if (ListUtils.isEmpty(splitVariantIds)){
            return;
        }
        List<Long> ids = getSplitVariantIdsByParentId(storeVariantId);
        if (ListUtils.isNotEmpty(ids)){
            splitVariantIds.addAll(ids);
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_STORE_SPLIT_VARIANT,storeVariantId.toString(),splitVariantIds);
    }

    public void redisUpdateSplitVariant(Long storeVariantId,List<Long> splitVariantIds){
        if (ListUtils.isEmpty(splitVariantIds)){
            redisDeleteIfSplitVariant(storeVariantId);
            return;
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_STORE_SPLIT_VARIANT,storeVariantId.toString(),splitVariantIds);
    }
}