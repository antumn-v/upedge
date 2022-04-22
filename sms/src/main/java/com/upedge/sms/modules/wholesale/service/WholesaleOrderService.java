package com.upedge.sms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.wholesale.WholesaleOrderVo;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderCreateRequest;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderService{

    WholesaleOrderVo orderDetail(Long orderId);

    BaseResponse create(WholesaleOrderCreateRequest request, Session session);

    WholesaleOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKeySelective(WholesaleOrder record);

    int insert(WholesaleOrder record);

    int insertSelective(WholesaleOrder record);

    List<WholesaleOrder> select(Page<WholesaleOrder> record);

    long count(Page<WholesaleOrder> record);
}

