package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderProfit;
import com.upedge.oms.modules.order.request.OrderInsightSelectRequest;
import com.upedge.oms.modules.order.vo.OrderInsightsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderProfitDao{

    List<OrderInsightsVo> selectCustomerOrderInsights(@Param("customerId") Long customerId,
                                                      @Param("param") OrderInsightSelectRequest request);

    int replaceOrderProfit(OrderProfit orderProfit);

    OrderProfit selectByPrimaryKey(OrderProfit record);

    int deleteByPrimaryKey(OrderProfit record);

    int updateByPrimaryKey(OrderProfit record);

    int updateByPrimaryKeySelective(OrderProfit record);

    int insert(OrderProfit record);

    int insertSelective(OrderProfit record);

    int insertByBatch(List<OrderProfit> list);

    List<OrderProfit> select(Page<OrderProfit> record);

    long count(Page<OrderProfit> record);

}
