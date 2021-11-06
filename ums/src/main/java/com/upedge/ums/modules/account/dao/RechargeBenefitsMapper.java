package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeBenefits;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
public interface RechargeBenefitsMapper {

    /**
     * 获取返点比例
     * @param applicationId 应用ID
     * @param amount 充值金额
     * @return
     */
    BigDecimal selectBenefitsByAppAndAmount(@Param("applicationId") Long applicationId,
                                            @Param("amount") BigDecimal amount);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeBenefits record);

    int insertSelective(RechargeBenefits record);

    RechargeBenefits selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeBenefits record);

    int updateByPrimaryKey(RechargeBenefits record);
}