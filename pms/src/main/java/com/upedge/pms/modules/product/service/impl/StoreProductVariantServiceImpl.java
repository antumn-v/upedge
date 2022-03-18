package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.FileUtil;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.dto.StoreProductVariantSplitVo;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.StoreProductVariantQuoteRequest;
import com.upedge.pms.modules.product.request.StoreProductVariantSplitRequest;
import com.upedge.pms.modules.product.request.StoreSplitVariantUpdateRequest;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        CustomerProductQuoteUpdateRequest customerProductQuoteUpdateRequest = new CustomerProductQuoteUpdateRequest();
        customerProductQuoteUpdateRequest.setQuotePrice(request.getQuotePrice());
        customerProductQuoteUpdateRequest.setStoreVariantId(storeVariantId);
        customerProductQuoteUpdateRequest.setVariantSku(request.getVariantSku());
        try {
            return customerProductQuoteService.updateCustomerProductQuote(customerProductQuoteUpdateRequest,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseResponse splitVariantDelete(Long storeVariantId, Session session) {
        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        if (storeProductVariant == null
        || storeProductVariant.getSplitType() != 2){
            return BaseResponse.failed("只能撤销拆分子体");
        }
        storeProductVariant = new StoreProductVariant();
        storeProductVariant.setId(storeVariantId);
        storeProductVariant.setSplitType(3);
        updateByPrimaryKeySelective(storeProductVariant);
        return customerProductQuoteService.revokeQuote(storeVariantId,session);
    }

    @Override
    public BaseResponse splitVariantUpdate(StoreSplitVariantUpdateRequest request, Session session) {
        Long storeVariantId = request.getStoreVariantId();
        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        if (storeProductVariant == null
        || storeProductVariant.getSplitType() != 2){
            return BaseResponse.failed("只能修改拆分子体");
        }
        storeProductVariant = new StoreProductVariant();
        storeProductVariant.setTitle(request.getName());
        String image = request.getImage();
        if (StringUtils.isBlank(image)){
            if (StringUtils.isBlank(request.getImageBase64())){
                return BaseResponse.failed("图片不能为空");
            }
            image = FileUtil.uploadImage(request.getImageBase64(),pmsImagePrefix,pmsImageLocal);
        }
        storeProductVariant.setImage(image);
        updateByPrimaryKeySelective(storeProductVariant);
        return BaseResponse.success();
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

        StoreProductVariant storeProductVariant = selectByPrimaryKey(storeVariantId);
        //未拆分的变体可以拆
        if (null == storeProductVariant
        || storeProductVariant.getSplitType() != 0){
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
        List<StoreProductVariant> splitVariants = new ArrayList<>();
        for (int i = 1; i <= splitVos.size(); i++) {
            StoreProductVariantSplitVo storeProductVariantSplitVo = splitVos.get(i-1);
            String image = storeProductVariantSplitVo.getImage();
            if (StringUtils.isBlank(image)){
                if (StringUtils.isBlank(storeProductVariantSplitVo.getImageBase64())){
                    return BaseResponse.failed("图片不能为空");
                }
                image = FileUtil.uploadImage(storeProductVariantSplitVo.getImageBase64(),pmsImagePrefix,pmsImageLocal);
            }
            StoreProductVariant splitVariant = new StoreProductVariant();
            BeanUtils.copyProperties(storeProductVariant,splitVariant);
            splitVariant.setImage(image);
            splitVariant.setTitle(storeProductVariantSplitVo.getName());
            splitVariant.setParentVariantId(storeVariantId);
            splitVariant.setSku(variantSku + "-" + i);
            splitVariant.setSplitType(2);
            splitVariant.setId(IdGenerate.nextId());
            splitVariants.add(splitVariant);
        }
        storeProductVariantDao.insertByBatch(splitVariants);
        //修改变体拆分状态
        storeProductVariant = new StoreProductVariant();
        storeProductVariant.setId(storeVariantId);
        storeProductVariant.setSplitType(1);
        updateByPrimaryKeySelective(storeProductVariant);
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

}