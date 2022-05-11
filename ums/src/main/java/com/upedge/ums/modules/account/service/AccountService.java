package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.request.ReturnOrderPayAmountToAccountRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.account.response.*;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
public interface AccountService {

    /**
     * 客户默认账户，默认账户只有一个
     * @param customerId
     * @return
     */
    Account selectCustomerDefaultAccount(Long customerId);

    Account selectSessionAccount(Session session);

    Account selectById(Long id);

    AccountPayMethod selectByAccountBankNum(Long accountId, String bankNum);

    BaseResponse returnOrderPayAmountToAccount(ReturnOrderPayAmountToAccountRequest request);
    /**分页查询账户
     * @param request
     * @return
     */
    AccountListResponse pageAccount(AccountListRequest request);
    /**
     * 增加账户
     * @param request
     * @return
     */
    AccountAddResponse addAccount(AccountAddRequest request);

    void addBalanceAndBenefits( Long id,
                                BigDecimal amount, BigDecimal benefitsAmount);

    /**
     * 逻辑删除账户
     * @param accountId 账户ID
     * @return
     */
    AccountRemoveResponse removeAccount(Long accountId);

    /**
     * 查询账户所有支付方式
     * @param accountId
     * @return
     */
    AccountPayMethodListResponse accountPayMethodList(Long accountId, AccountPayMethodListRequest request);


    AccountPaymethodAttrListResponse accountPaymethodAttrList(Integer accountPaymethodId);
    /**
     * 账户增加支付方式
     * @param request
     * @return
     */
    AccountPayMethodAddResponse addAccountPayMethod(AccountPayMethodAddRequest request);


    /**
     * 修改账户支付方式
     * @param request
     * @return
     */
    AccountPaymethodUpdateResponse updateAccountPaymethod(AccountPayMethodUpdateRequest request, Integer accountPaymethodId);
    /**
     * 账户移除支付方式
     * @param accountId
     * @param accountPaymethodId
     * @return
     */
    AccountRemovePayMethodResponse accountRemovePayMethod(Long accountId, Integer accountPaymethodId);

    /**
     * 账户关联用户
     * @return
     */
    AccountLinkUserResponse accountLinkUser(AccountUser accountUser);

    /**
     * 账户解除关联用户
     * @param accountUser
     * @return
     */
    AccountUnLinkUserResponse accountUnLinkUser(AccountUser accountUser);

    /**
     * 账户用户列表
     * @param accountId
     * @return
     */
    AccountUserListResponse accountUserList(Long accountId);

    AccountCreditLimitUpdateResponse updateAccountCreditLimit(Long id, BigDecimal creditLimit);

    int insert(Account account);

    boolean accountPayment(AccountPaymentRequest request) throws CustomerException;

    boolean saveTransactionDetails(PaymentDetail paymentDetail);

    BaseResponse accountOrderRefunded(AccountOrderRefundedRequest request) throws CustomerException;

    Account selectDateByCustomerId(Long customerId);

    /**
     * 根据id查询 account_log
     * @param id
     * @return
     */
    AccountLog selectAccountLogById(Long id);

    int addAccountVipRebate(Long accountId, BigDecimal vipRebate);

    int addAccountAffiliateRebate(Long accountId, BigDecimal affiliateRebate);
}
