package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderAttrDao{

    OrderAttr selectByOrderIdAndName(@Param("orderId") Long orderId,
                                     @Param("attrName") String attrName);

    void deleteByOrderIdAndName(@Param("orderId") Long orderId,
                                @Param("attrName") String attrName);

    OrderAttr selectByPrimaryKey(OrderAttr record);

    int deleteByPrimaryKey(OrderAttr record);

    int updateByPrimaryKey(OrderAttr record);

    int updateByPrimaryKeySelective(OrderAttr record);

    int insert(OrderAttr record);

    int insertSelective(OrderAttr record);

    int insertByBatch(List<OrderAttr> list);

    List<OrderAttr> select(Page<OrderAttr> record);

    long count(Page<OrderAttr> record);

}
