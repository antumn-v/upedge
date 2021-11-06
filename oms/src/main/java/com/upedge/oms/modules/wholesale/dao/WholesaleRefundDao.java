package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundListRequest;
import com.upedge.oms.modules.wholesale.vo.WholesaleRefundVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleRefundDao{

    WholesaleRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WholesaleRefund record);

    int updateByPrimaryKey(WholesaleRefund record);

    int updateByPrimaryKeySelective(WholesaleRefund record);

    int insert(WholesaleRefund record);

    int insertSelective(WholesaleRefund record);

    int insertByBatch(List<WholesaleRefund> list);

    List<WholesaleRefund> select(Page<WholesaleRefund> record);

    long count(Page<WholesaleRefund> record);

    List<WholesaleRefundVo> refundOrderList(WholesaleRefundListRequest request);
    Long refundOrderCount(WholesaleRefundListRequest request);


    int rejectRefund(WholesaleRefund orderRefund);

    void updateRemark(@Param("id") Long id, @Param("remark") String remark);


    int updateConfirmRefund(WholesaleRefund orderRefund);

    void updateConfirmAppRefundTrackingState(@Param("id") Long id,
                                             @Param("trackingState") Integer trackingState, @Param("orderSourceId") Integer orderSourceId);

    /**
     * 批发订单退款待处理
     * @return
     */
    long wholesaleHandleRefundData(@Param("userManager") String userManager);

    void updateRefundTrackingState(@Param("id") String id, @Param("trackingState") Integer trackingState, @Param("orderSourceId") Integer orderSourceId);
}
