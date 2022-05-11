package com.upedge.ums.modules.account.dao;


import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.RechargeLog;
import com.upedge.ums.modules.account.vo.AccountStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 海桐
 */
public interface AccountMapper {

    /**
     * 客户默认账户，默认账户只有一个
     *
     * @param customerId
     * @return
     */
    Account selectCustomerDefaultAccount(Long customerId);

    AccountStatisticsVo selectAccountStatistics(@Param("userManager") String userManager);


    int increaseAccountCommission(Long id, BigDecimal commission);

    int deductAccountCommission(Long id, BigDecimal commission);

    /**
     * 分页查询账户列表
     *
     * @param page
     * @return
     */
    List<Account> selectAccountByPage(Page<Account> page);

    /**
     * 条件查询账户数量
     *
     * @param page
     * @return
     */
    Long countAccount(Page<Account> page);

    /**
     * 获取账户可用余额
     *
     * @param id
     * @return
     */
    BigDecimal getAccountAvailableBalanceById(Long id);

    /**
     * 查询账户状态
     *
     * @param id
     * @return
     */
    Integer selectAccountStatusById(Long id);

    /**
     * 更新账户信用额度
     *
     * @param id
     * @param creditLimit
     * @return
     */
    boolean updateAccountCreditLimit(@Param("id") Long id,
                                     @Param("creditLimit") BigDecimal creditLimit);

    /**
     * 增加账户余额
     *
     * @param id
     * @param balance
     * @param affiliateRebate
     * @param credit
     * @return
     */
    int accountIncreaseBalance(@Param("id") Long id,
                               @Param("balance") BigDecimal balance,
                               @Param("affiliateRebate") BigDecimal affiliateRebate,
                               @Param("credit") BigDecimal credit);

    /**
     * 减少账户余额
     *
     * @param id
     * @param balance
     * @param rebate
     * @param credit
     * @return
     */
    int accountReduceBalance(@Param("id") Long id,
                             @Param("balance") BigDecimal balance,
                             @Param("affiliateRebate") BigDecimal affiliateRebate,
                             @Param("vipRebate") BigDecimal vipRebate);

    int accountRepaymentCredit(@Param("id") Long id,
                               @Param("amount") BigDecimal amount);

    void deductAccountBalance(RechargeLog log);

    void disableAccount(Long accountId);

    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account customerAccount(Long customerId);

    void addBalanceAndBenefits(@Param("id") Long id,
                               @Param("amount") BigDecimal amount, @Param("benefitsAmount") BigDecimal benefitsAmount);

    int repaymentCredit(@Param("id") Long id,
                        @Param("totalRepaymentAmount") BigDecimal totalRepaymentAmount);

    void updateCreditLimit(@Param("id") Long id, @Param("creditLimit") BigDecimal creditLimit);

    void subAccountCommission(@Param("id") Long id, @Param("commission") BigDecimal commission);

    /**
     * 客户剩余金额
     */
    BigDecimal userRestBalanceAmount(String userManager);

    /**
     * 已用信用额度
     */
    BigDecimal usedCredit(String userManager);

    /**
     * 开放信用额度
     */
    BigDecimal openCredit(String userManager);

    int addAccountVipRebate(@Param("accountId") Long accountId, @Param("vipRebate") BigDecimal vipRebate);

    int addAccountAffiliateRebate(@Param("accountId") Long accountId, @Param("affiliateRebate") BigDecimal affiliateRebate);

}