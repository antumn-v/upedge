package com.upedge.oms.modules.pick.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.vo.OrderPickInfoVo;
import com.upedge.oms.modules.pick.vo.OrderPickPreviewVo;
import com.upedge.oms.modules.pick.vo.OrderPickQuantityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPickDao {

    OrderPick selectByWaveNo(Integer waveNo);

    Integer selectMaxWaveNo();

    List<OrderPickInfoVo> selectOrderPickInfo(Long pickId);

    List<OrderPickQuantityVo> selectOneSkuOneProductOrderQuantity(@Param("shipMethodIds") List<Long> shipMethodIds, @Param("size") Integer size);

    List<OrderPickQuantityVo> selectOneSkuMultiProductOrderQuantity(@Param("shipMethodIds") List<Long> shipMethodIds, @Param("size") Integer size);

    List<OrderPickQuantityVo> selectMultiSkuOrderQuantity(@Param("shipMethodIds") List<Long> shipMethodIds, @Param("size") Integer size);

    List<OrderPickPreviewVo> countOrderPickPreview(OrderPickPreviewListRequest request);

    OrderPick selectByPrimaryKey(OrderPick record);

    int deleteByPrimaryKey(OrderPick record);

    int updateByPrimaryKey(OrderPick record);

    int updateByPrimaryKeySelective(OrderPick record);

    int insert(OrderPick record);

    int insertSelective(OrderPick record);

    int insertByBatch(List<OrderPick> list);

    List<OrderPick> select(Page<OrderPick> record);

    long count(Page<OrderPick> record);

}
