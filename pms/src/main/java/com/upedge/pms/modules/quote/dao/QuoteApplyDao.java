package com.upedge.pms.modules.quote.dao;

import com.upedge.pms.modules.quote.dto.QuoteApplyListDto;
import com.upedge.pms.modules.quote.vo.QuoteApplyVo;
import com.upedge.pms.modules.quote.entity.QuoteApply;

import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface QuoteApplyDao{


    List<QuoteApplyVo> selectQuoteApplies(Page<QuoteApplyListDto> page);

    Long countQuoteApplies(Page<QuoteApplyListDto> page);

    QuoteApply selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(QuoteApply record);

    int updateByPrimaryKey(QuoteApply record);

    int updateByPrimaryKeySelective(QuoteApply record);

    int insert(QuoteApply record);

    int insertSelective(QuoteApply record);

    int insertByBatch(List<QuoteApply> list);

    List<QuoteApply> select(Page<QuoteApply> record);

    long count(Page<QuoteApply> record);

}
