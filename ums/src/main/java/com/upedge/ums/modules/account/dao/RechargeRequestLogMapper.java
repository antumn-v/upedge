package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.request.RechargeHistoryListRequest;
import com.upedge.ums.modules.account.vo.RechargeHistoryListVo;
import com.upedge.ums.modules.statistics.dto.RechargeData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 海桐
 */
public interface RechargeRequestLogMapper {


     RechargeRequestLog selectMaxNewRechargeRequestLog(@Param("customerId") Long customerId);

    int updateRepaymentAmountById(@Param("repaymentAmount") BigDecimal repaymentAmount,
                                  @Param("id") Long id);

    RechargeRequestLog selectByRemarks(String remarks);


    /**
     * 充值申请历史
     * @param page
     * @return
     */
    List<RechargeRequestLog> selectRechargeRequestList(Page<RechargeRequestLog> page);

    /**
     * 充值申请数量
     * @param page
     * @return
     */
    Long getRechargeRequestTotal(Page<RechargeRequestLog> page);

    /**
     * 用户账户下未完成的交易
     * @param accountUser
     * @return
     */
    List<RechargeRequestLog> selectUserAccountPendingRequest(AccountUser accountUser);

    int deleteByPrimaryKey(Long id);

    int insert(RechargeRequestLog record);

    int insertSelective(RechargeRequestLog record);

    RechargeRequestLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RechargeRequestLog record);

    int updateByPrimaryKey(RechargeRequestLog record);

    int cancelRechargeRequest(RechargeRequestLog requestLog);

    int updateRechargeRequest(RechargeRequestLog requestLog);

    int confirmRechargeRequest(RechargeRequestLog requestLog);

    /**
     * 近30天充值
     */
    BigDecimal rechargeAmount30Day(@Param("todayDate") String todayDate,
                                   @Param("userManager") String userManager);

    //仪表盘财务数据
    List<RechargeData> dashboardFinanceData(@Param("userManager") String userManager);

    /**
     * 充值记录
     * @param request
     * @return
     */
    List<RechargeHistoryListVo> rechargeHistoryList(RechargeHistoryListRequest request);
    Long rechargeHistoryListCount(RechargeHistoryListRequest request);

    RechargeRequestLog selectRechargeRequestLogByCustomerIdAndTime(@Param("customerId") Long customerId, @Param("time") Date createTime);

    void updateZoneByNull();
}