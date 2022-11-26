package com.upedge.ums.modules.affiliate.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.vo.RefereeCommissionVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
public interface AffiliateDao {

    List<RefereeCommissionVo> searchCommissionByReferee(Long referrerId);

    int updateRebateStateByRefereeId(@Param("refereeId") Long refereeId, @Param("rebateState") Boolean rebateState);

    void updateRefereeCommission(@Param("refereeId") Long refereeId,
                                 @Param("commission") BigDecimal commission);

    Affiliate selectByPrimaryKey(Affiliate record);

    int deleteByPrimaryKey(Affiliate record);

    int updateByPrimaryKey(Affiliate record);

    int updateByPrimaryKeySelective(Affiliate record);

    int insert(Affiliate record);

    int insertSelective(Affiliate record);

    int insertByBatch(List<Affiliate> list);

    List<Affiliate> select(Page<Affiliate> record);

    long count(Page<Affiliate> record);

    Affiliate queryAffiliateByReferee(Long refereeId);

    void subAffiliateCommission(@Param("id") Long id,
                                @Param("commission") BigDecimal commission,
                                @Param("updateTime") Date updateTime);

    Affiliate selectAffiliateVoByRefereeId(@Param("customerId") Long customerId);

    BigDecimal selectTotalByReferrerId(Long customerId);

    List<Affiliate> allAffiliates();
}
