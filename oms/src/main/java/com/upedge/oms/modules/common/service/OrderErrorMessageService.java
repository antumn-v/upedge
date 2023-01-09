package com.upedge.oms.modules.common.service;

import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrderErrorMessageService{

    OrderErrorMessage selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrderErrorMessage record);

    int updateByPrimaryKeySelective(OrderErrorMessage record);

    int insert(OrderErrorMessage record);

    int insertSelective(OrderErrorMessage record);

    List<OrderErrorMessage> select(Page<OrderErrorMessage> record);

    long count(Page<OrderErrorMessage> record);
}

