package com.upedge.ums.modules.account.dao;


import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeLog;
import com.upedge.ums.modules.account.request.RechargeListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 海桐
 */
public interface RechargeLogMapper {

    List<RechargeLog> selectUndoneRecordByAccount(Long accountId);


    /**
     * 充值次数
     * @param page
     * @return
     */
    Long getRechargeCount(Page<RechargeLog> page);

    /**
     * 充值记录
     * @param request
     * @return
     */
    List<RechargeLog> selectRechargeList(RechargeListRequest request);

    /**
     * 撤销充值
     * @param id
     * @return
     */
    boolean revokeRecharge(Long id);

    int updateByBatch(@Param("rechargeLogs") List<RechargeLog> logs);

    int updateByMap(@Param("rechargeLogMap") Map<Long, RechargeLog> rechargeLogMap);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeLog record);

    int insertSelective(RechargeLog record);

    RechargeLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RechargeLog record);

    int updateByPrimaryKey(RechargeLog record);
    
}