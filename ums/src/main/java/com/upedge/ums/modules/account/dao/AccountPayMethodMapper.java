package com.upedge.ums.modules.account.dao;


import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.request.AccountPayMethodListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 海桐
 */
public interface AccountPayMethodMapper {


    /**
     * 查询账户下某支付方式的信息
     * @param accountId
     * @param methodId
     * @return
     */
    List<AccountPayMethod> selectByAccountIdAndPaymethodId(@Param("accountId") Long accountId,
                                                           @Param("methodId") Integer methodId);

    AccountPayMethod selectByAccountIdAndBankNum(@Param("accountId") Long accountId,
                                                 @Param("bankNum") String bankNum);


    List<AccountPayMethod> selectPayMethodDetailByAccountId(@Param("accountId") Long accountId,
                                                            @Param("r") AccountPayMethodListRequest request);

    /**
     * 查询账户所有支付方式
     * @param accountId
     * @return
     */
    List<AccountPayMethod> selectByAccount(Long accountId);

    /**
     * 取消其他订单的默认支付状态
     * @param accountId
     * @param id
     */
    void updateOtherPayMethodUnDefault(@Param("accountId") Long accountId,
                                       @Param("id") Integer id);

    /**
     * 禁用所有账户的某种支付方式
     * @param paymethodId
     * @return
     */
    int disableAccountPayMethod(Integer paymethodId);

    /**
     * 恢复所有账户的某种支付方式
     * @param paymethodId
     * @return
     */
    int enableAccountPayMethod(Integer paymethodId);

    /**
     * 删除帐户支付方式
     * @param methodId
     * @param accountId
     * @return
     */
    int removePayMethodByAccountId(@Param("methodId") Integer methodId, @Param("accountId") Long accountId);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountPayMethod record);

    int insertSelective(AccountPayMethod record);

    AccountPayMethod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountPayMethod record);

    int updateByPrimaryKey(AccountPayMethod record);
}