package com.upedge.pms.modules.quote.service;

import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface QuoteApplyItemService{

    List<Long> selectAllQuotingStoreVariantIds();

    QuoteApplyItem selectByStoreVariantId(Long storeVariantId);

    QuoteApplyItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(QuoteApplyItem record);

    int updateByPrimaryKeySelective(QuoteApplyItem record);

    int insert(QuoteApplyItem record);

    int insertBatch(List<QuoteApplyItem> records);

    int insertSelective(QuoteApplyItem record);

    List<QuoteApplyItem> select(Page<QuoteApplyItem> record);

    long count(Page<QuoteApplyItem> record);
}

