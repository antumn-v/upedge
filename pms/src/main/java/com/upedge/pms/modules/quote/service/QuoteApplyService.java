package com.upedge.pms.modules.quote.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.request.ClaimQuoteApplyRequest;
import com.upedge.pms.modules.product.request.QuoteApplyProcessRequest;
import com.upedge.pms.modules.quote.dto.QuoteApplyListDto;
import com.upedge.pms.modules.quote.entity.QuoteApply;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.request.QuoteApplyListRequest;
import com.upedge.pms.modules.quote.vo.QuoteApplyVo;

import java.util.List;

/**
 * @author gx
 */
public interface QuoteApplyService{


    List<QuoteApplyVo> quoteApplyList(Page<QuoteApplyListDto> page);

    Long quoteApplyCount(Page<QuoteApplyListDto> page);

    BaseResponse finishQuoteApply(Long quoteApplyId,Session session);

    BaseResponse processQuoteApply(QuoteApplyProcessRequest request,Long quoteApplyId,Session session);


    BaseResponse claimQuoteApply(ClaimQuoteApplyRequest request, Session session);

    BaseResponse orderQuoteApply(OrderQuoteApplyRequest request);

    QuoteApply selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(QuoteApply record);

    int updateByPrimaryKeySelective(QuoteApply record);

    int insert(QuoteApply record);

    int insertSelective(QuoteApply record);

    List<QuoteApply> select(Page<QuoteApply> record);

    long count(Page<QuoteApply> record);
}

