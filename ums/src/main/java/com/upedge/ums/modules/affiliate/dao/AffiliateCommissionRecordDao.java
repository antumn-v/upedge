package com.upedge.ums.modules.affiliate.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.OrderBenefitsVo;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.vo.RefereeMonthCommissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AffiliateCommissionRecordDao{



    List<RefereeMonthCommissionVo> searchCommissionByMonth(Long referrerId);

    AffiliateCommissionRecord selectByPrimaryKey(AffiliateCommissionRecord record);

    int deleteByPrimaryKey(AffiliateCommissionRecord record);

    int updateByPrimaryKey(AffiliateCommissionRecord record);

    int updateByPrimaryKeySelective(AffiliateCommissionRecord record);

    int insert(AffiliateCommissionRecord record);

    int insertSelective(AffiliateCommissionRecord record);

    int insertByBatch(List<AffiliateCommissionRecord> list);

    List<AffiliateCommissionRecord> select(Page<AffiliateCommissionRecord> record);

    long count(Page<AffiliateCommissionRecord> record);

    AffiliateCommissionRecord queryPayRecordByOrderId(Long orderId);


    List<OrderBenefitsVo> getBenefitsList(
            @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId,
            @Param("startDay") String startDay, @Param("usdRate") Double usdRate, @Param("orderType") int orderType);
}
