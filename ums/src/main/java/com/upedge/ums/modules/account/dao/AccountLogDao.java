package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.OrderAccountLogVo;
import com.upedge.ums.modules.account.entity.AccountLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AccountLogDao{

    AccountLog selectByPrimaryKey(AccountLog record);

    int deleteByPrimaryKey(AccountLog record);

    int updateByPrimaryKey(AccountLog record);

    int updateByPrimaryKeySelective(AccountLog record);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    int insertByBatch(List<AccountLog> list);

    List<AccountLog> select(Page<AccountLog> record);

    long count(Page<AccountLog> record);

    AccountLog selectPayedAccountLogByTransactionId(@Param("transactionId") Long transactionId, @Param("transactionType") Integer transactionType);

    List<AccountLog> listAccountLogByTransactionId(@Param("transactionId") Long transactionId);


    OrderAccountLogVo selectAccountLogByOrder(@Param("orderType") Integer orderType, @Param("transactionId") Long transactionId);

    Integer selectAccountLog(@Param("orderId") Long orderId, @Param("orderType") Integer orderType);

    OrderAccountLogVo selectAccountLogPayInfoByTransactionDetail(@Param("transactionId") Long orderId, @Param("orderType") Integer orderType, @Param("transactionType") Integer transactionType);
}
