package com.upedge.oms.modules.pick.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.OrderPickWaveReleaseRequest;
import com.upedge.oms.modules.pick.request.TwicePickSubmitRequest;
import com.upedge.oms.modules.pick.vo.OrderPickInfoVo;
import com.upedge.oms.modules.pick.vo.OrderPickWaveInfoVo;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPickService{

    BaseResponse releaseOrderWave(OrderPickWaveReleaseRequest request, Session session);

    List<OrderPickInfoVo> selectOrderPickInfo(Integer waveNo);

    OrderPickWaveInfoVo wavePickInfo(Integer waveNo);

    //打印拣货单
    BaseResponse printPickInfo(Integer waveNo,Session session);

    BaseResponse twicePickSubmit(TwicePickSubmitRequest request,Session session);

    BaseResponse twicePickInfo(Integer waveNo);

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

