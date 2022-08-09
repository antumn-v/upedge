package com.upedge.oms.modules.pick.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.TwicePickSubmitRequest;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPickService{

    //打印拣货单
    BaseResponse printPickInfo(Long pickId,Session session);

    BaseResponse twicePickSubmit(TwicePickSubmitRequest request,Session session);

    BaseResponse twicePickInfo(Long pickId);

    BaseResponse previewList(OrderPickPreviewListRequest request);

    BaseResponse create(OrderPickCreateRequest request, Session session);

    OrderPick selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderPick record);

    int updateByPrimaryKeySelective(OrderPick record);

    int insert(OrderPick record);

    int insertSelective(OrderPick record);

    List<OrderPick> select(Page<OrderPick> record);

    long count(Page<OrderPick> record);
}

