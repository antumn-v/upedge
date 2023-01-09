package com.upedge.oms.modules.common.dao;

import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrderErrorMessageDao{

    OrderErrorMessage selectByPrimaryKey(OrderErrorMessage record);

    int deleteByPrimaryKey(OrderErrorMessage record);

    int updateByPrimaryKey(OrderErrorMessage record);

    int updateByPrimaryKeySelective(OrderErrorMessage record);

    int insert(OrderErrorMessage record);

    int insertSelective(OrderErrorMessage record);

    int insertByBatch(List<OrderErrorMessage> list);

    List<OrderErrorMessage> select(Page<OrderErrorMessage> record);

    long count(Page<OrderErrorMessage> record);

}
