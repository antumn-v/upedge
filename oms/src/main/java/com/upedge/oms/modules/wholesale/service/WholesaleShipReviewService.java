package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.WholesaleOrderAppListRequest;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;

import java.util.List;

public interface WholesaleShipReviewService {

    List<WholesaleOrderAppVo> reviewList(WholesaleOrderAppListRequest request);

    BaseResponse confirm(Long id);

    BaseResponse reject(Long id);
}
