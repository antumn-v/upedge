package com.upedge.pms.modules.quote.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface QuoteApplyItemDao{

    List<Long> selectAllQuotingStoreVariantIds();

    QuoteApplyItem selectByStoreVariantId(Long storeVariantId);

    List<QuoteApplyItem> selectUnQuoteItemByApplyId(Long applyId);

    List<Long> selectQuotingStoreVariantIds(@Param("storeVariantIds") List<Long> storeVariantIds);

    int updateProductTitleByApplyId(Long quoteApplyId);

    List<QuoteApplyItem> selectByQuoteApplyId(Long quoteApplyId);

    QuoteApplyItem selectByPrimaryKey(QuoteApplyItem record);

    int deleteByPrimaryKey(QuoteApplyItem record);

    int updateByPrimaryKey(QuoteApplyItem record);

    int updateByPrimaryKeySelective(QuoteApplyItem record);

    int insert(QuoteApplyItem record);

    int insertSelective(QuoteApplyItem record);

    int insertByBatch(List<QuoteApplyItem> list);

    List<QuoteApplyItem> select(Page<QuoteApplyItem> record);

    long count(Page<QuoteApplyItem> record);

}
