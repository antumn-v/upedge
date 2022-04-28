package com.upedge.sms.modules.winningProduct.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import com.upedge.common.base.Page;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderAddRequest;

import java.util.List;

/**
 * @author gx
 */
public interface WinningProductServiceOrderService{

    BaseResponse create(WinningProductServiceOrderAddRequest request, Session session);

    WinningProductServiceOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WinningProductServiceOrder record);

    int updateByPrimaryKeySelective(WinningProductServiceOrder record);

    int insert(WinningProductServiceOrder record);

    int insertSelective(WinningProductServiceOrder record);

    List<WinningProductServiceOrder> select(Page<WinningProductServiceOrder> record);

    long count(Page<WinningProductServiceOrder> record);

    void saveTransactionRecordMessage(Long userId, Long orderId);
}

