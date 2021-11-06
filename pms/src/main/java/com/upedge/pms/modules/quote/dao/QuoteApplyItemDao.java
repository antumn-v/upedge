package com.upedge.pms.modules.quote.dao;

import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface QuoteApplyItemDao{

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
