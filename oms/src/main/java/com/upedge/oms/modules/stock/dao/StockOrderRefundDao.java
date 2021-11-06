package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import com.upedge.oms.modules.stock.request.StockOrderRefundListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderRefundDao{

    StockOrderRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(StockOrderRefund record);

    int updateByPrimaryKey(StockOrderRefund record);

    int updateByPrimaryKeySelective(StockOrderRefund record);

    int insert(StockOrderRefund record);

    int insertSelective(StockOrderRefund record);

    int insertByBatch(List<StockOrderRefund> list);

    List<StockOrderRefund> select(Page<StockOrderRefund> record);

    long count(Page<StockOrderRefund> record);

    List<StockOrderRefund> selectAdminStockOrderRefundList(StockOrderRefundListRequest request);
    Long countAdminStockOrderRefundList(StockOrderRefundListRequest request);

    int rejectApply(StockOrderRefund stockOrderRefund);

    int updateConfirmRefund(StockOrderRefund stockOrderRefund);


    /**
     * 备库退款待处理
     * @return
     */
    long stockHandleRefundData(@Param("userManager") String userManager);
}
