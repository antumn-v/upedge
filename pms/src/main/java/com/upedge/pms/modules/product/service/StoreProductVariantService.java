package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.StoreProductVariantQuoteRequest;
import com.upedge.pms.modules.product.request.StoreProductVariantSplitRequest;
import com.upedge.pms.modules.product.request.StoreSplitVariantUpdateRequest;
import com.upedge.pms.modules.product.vo.SplitVariantIdVo;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductVariantService{

    StoreProductVariant selectByPlatId(String platVariantId);

    int updateId(Long oldId, Long newId);

    List<StoreProductVariant> selectSplitVariantsByPlatVariantId(String platVariantId);

    List<StoreProductVariant> selectWrongParentVariants();

    BaseResponse variantQuote(StoreProductVariantQuoteRequest request, Session session);

    List<SplitVariantIdVo> selectSplitVariantIds();

    BaseResponse splitVariantDelete(Long storeVariantId,Session session);

    BaseResponse splitVariantUpdate(StoreSplitVariantUpdateRequest request,Session session);

    List<StoreProductVariant> selectSplitVariantByParentId(Long parentVariantId);

    BaseResponse variantSplit(StoreProductVariantSplitRequest request, Session session);

    List<StoreProductVariant> selectBySku(String sku);

    List<CustomerProductQuoteVo> selectQuoteDetailByIds(List<Long> ids);

    StoreProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductVariant record);

    int updateByPrimaryKeySelective(StoreProductVariant record);

    int insert(StoreProductVariant record);

    int insertSelective(StoreProductVariant record);

    List<StoreProductVariant> select(Page<StoreProductVariant> record);

    long count(Page<StoreProductVariant> record);

    boolean redisCheckIfSplitVariant(Long storeVariantId);

    List<Long> getSplitVariantIdsByParentId(Long storeVariantId);
}

