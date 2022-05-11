package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.enums.TransactionType;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.request.ReturnOrderPayAmountToAccountRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.*;
import com.upedge.ums.modules.account.entity.*;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionRecordDao;
import com.upedge.ums.modules.affiliate.dao.AffiliateDao;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.user.dao.UserDao;
import com.upedge.ums.modules.user.entity.User;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 海桐
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AccountUserMapper accountUserMapper;

    @Autowired
    AccountLogDao accountLogDao;

    @Autowired
    AccountPayMethodMapper accountPayMethodMapper;

    @Autowired
    AccountPayMethodAttrMapper accountPayMethodAttrMapper;

    @Autowired
    PayMethodMapper payMethodMapper;

    @Autowired
    PayMethodAttrMapper payMethodAttrMapper;

    @Autowired
    PaymentLogDao paymentLogDao;

    @Autowired
    RechargeRequestLogMapper rechargeRequestLogMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    RechargeLogMapper rechargeLogMapper;

    @Autowired
    RechargeRecordDao rechargeRecordDao;

    @Autowired
    RefundRecordDao refundRecordDao;

    @Autowired
    AffiliateDao affiliateDao;
    @Autowired
    AffiliateCommissionRecordDao affiliateCommissionRecordDao;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Account selectCustomerDefaultAccount(Long customerId) {
        return accountMapper.selectCustomerDefaultAccount(customerId);
    }

    @Override
    public Account selectSessionAccount(Session session) {
        Account account = accountUserMapper.selectAccountByUser(session.getId());
        if (account.getStatus() != 1){
            return null;
        }
        return account;
    }

    @Override
    public Account selectById(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public AccountPayMethod selectByAccountBankNum(Long accountId, String bankNum) {
        return accountPayMethodMapper.selectByAccountIdAndBankNum(accountId, bankNum);
    }

    @GlobalTransactional
    @Override
    public BaseResponse returnOrderPayAmountToAccount(ReturnOrderPayAmountToAccountRequest request) {
        PaymentLog paymentLog = paymentLogDao.selectByPrimaryKey(request.getPaymentId());
        if (paymentLog == null){
            return BaseResponse.failed();
        }
        Long accountId = paymentLog.getAccountId();
        RechargeLog rechargeLog=new RechargeLog();
        rechargeLog.setAccountId(accountId);
        rechargeLog.setRelateId(request.getOrderId());
        rechargeLog.setAmount(request.getPayAmount());
        rechargeLog.setRebate(BigDecimal.ZERO);
        rechargeLog.setPayed(BigDecimal.ZERO);
        rechargeLog.setRechargeStatus(0);
        //0:电汇 1:paypal充值 2:payoneer充值，3=订单支付充值 4.退款充值
        rechargeLog.setRechargeType(4);
        rechargeLog.setCreateTime(new Date());
        rechargeLog.setUpdateTime(new Date());
        rechargeLogMapper.insert(rechargeLog);
        accountMapper.accountIncreaseBalance(accountId,request.getPayAmount(),BigDecimal.ZERO,BigDecimal.ZERO);
        return BaseResponse.success();
    }

    @Override
    public AccountListResponse pageAccount(AccountListRequest request) {

        if (null == request.getT()) {
            request.setT(new Account());
        }

        UserVo user = UserUtil.getUser(redisTemplate);

        request.getT().setCustomerId(user.getCustomerId());
        List<Account> accounts = accountMapper.selectAccountByPage(request);
        Long total = accountMapper.countAccount(request);
        request.setTotal(total);

        return new AccountListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, accounts, request);
    }

    @Transactional
    @Override
    public AccountAddResponse addAccount(AccountAddRequest request) {
        Long customerId = UserUtil.getSession(redisTemplate).getCustomerId();

        Account account = request.toAccount(customerId);
        accountMapper.insert(account);

        AccountPayMethod payMethod = request.toAccountPayMethod(account.getId());
        accountPayMethodMapper.insert(payMethod);

        return new AccountAddResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public void addBalanceAndBenefits(Long id, BigDecimal amount, BigDecimal benefitsAmount) {
        if (id != null
                && amount  != null
        && benefitsAmount != null){
            if (amount.compareTo(BigDecimal.ZERO) == 1 && benefitsAmount.compareTo(BigDecimal.ZERO) == 1){
                return;
            }
            accountMapper.addBalanceAndBenefits(id,amount,benefitsAmount);
        }
    }

    /**
     * 禁用账户，账户不能有余额或未还款的信用额度，禁用后删掉所有账户用户的关联信息
     *
     * @param accountId 账户ID
     * @return
     */
    @Transactional
    @Override
    public AccountRemoveResponse removeAccount(Long accountId) {
        Session session = UserUtil.getSession(redisTemplate);
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if(null == account){
            return new AccountRemoveResponse(ResultCode.FAIL_CODE,"账户不存在");
        }
        if (account.getIsDefault()){
            return new AccountRemoveResponse(ResultCode.FAIL_CODE,"默认账户无法删除");
        }
        List<User> users = accountUserMapper.selectUserByAccount(accountId);
        if(ListUtils.isNotEmpty(users)){
            return new AccountRemoveResponse(ResultCode.FAIL_CODE,"需要给账户下的用户分配新账户");
        }
        if(session.getUserType() != 0 && !session.getCustomerId().equals(account.getCustomerId())){
            return new AccountRemoveResponse(ResultCode.FAIL_CODE,"只能禁用自己的账户");
        }
        //账户内是否还有余额
        BigDecimal balance = accountMapper.getAccountAvailableBalanceById(accountId);
        if (balance.compareTo(BigDecimal.ZERO) != 0) {
            return new AccountRemoveResponse(ResultCode.FAIL_CODE, "Account has unused balance or outstanding credit limit");
        }
        //账户内是否还有未处理完的订单
        List<PaymentLog> logs = paymentLogDao.selectPendingPaymentByAccountId(accountId);
        if (null != logs && logs.size() > 0) {
            return new AccountRemoveResponse(ResultCode.FAIL_CODE, "There are outstanding transactions under the account");
        }
        accountMapper.disableAccount(accountId);//禁用
        return new AccountRemoveResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AccountPayMethodListResponse accountPayMethodList(Long accountId, AccountPayMethodListRequest request) {

        List<AccountPayMethod> payMethods = accountPayMethodMapper.selectPayMethodDetailByAccountId(accountId, request);

        UserVo user = UserUtil.getUser(redisTemplate);

        if (0 != user.getUserType()) {
            request.setStatus(1);
        }

        return new AccountPayMethodListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, payMethods);
    }

    @Override
    public AccountPaymethodAttrListResponse accountPaymethodAttrList(Integer accountPaymethodId) {
        List<AccountPayMethodAttr> attrs = accountPayMethodAttrMapper.selectByAccountPaymethodId(accountPaymethodId);

        return new AccountPaymethodAttrListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, attrs);
    }

    @Transactional
    @Override
    public AccountPayMethodAddResponse addAccountPayMethod(AccountPayMethodAddRequest request) {

        AccountPayMethod payMethod = accountPayMethodMapper.selectByAccountIdAndBankNum(request.getAccountId(), request.getBankNum());

        if (null != payMethod) {
            if (payMethod.getStatus() == 1) {
                return new AccountPayMethodAddResponse(ResultCode.FAIL_CODE, "Cannot add the same payment method");
            } else {
                Integer id = payMethod.getId();
                payMethod = request.toAccountPayMethod();
                payMethod.setId(id);
                payMethod.setUpdateTime(new Date());
                accountPayMethodMapper.updateByPrimaryKey(payMethod);
                accountPayMethodAttrMapper.deleteByAccountPaymethodId(id);
            }
        } else {
            payMethod = request.toAccountPayMethod();
            accountPayMethodMapper.insert(payMethod);
        }


        Integer accountPayMethodId = payMethod.getId();

        if (payMethod.getIsdefault() == 1) {
            accountPayMethodMapper.updateOtherPayMethodUnDefault(request.getAccountId(), accountPayMethodId);
        }

        insertPaymethodAttrs(request.getAttrs(), accountPayMethodId, request.getPaymethodId());

        return new AccountPayMethodAddResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AccountPaymethodUpdateResponse updateAccountPaymethod(AccountPayMethodUpdateRequest request, Integer accountPaymethodId) {
        AccountPayMethod payMethod = accountPayMethodMapper.selectByPrimaryKey(accountPaymethodId);
        if (null == payMethod) {
            return new AccountPaymethodUpdateResponse(ResultCode.FAIL_CODE, "Paymethod does not exist");
        }

        payMethod = request.toAccountPayMethod(payMethod);

        accountPayMethodMapper.updateByPrimaryKey(payMethod);
        accountPayMethodAttrMapper.deleteByAccountPaymethodId(accountPaymethodId);

        if (payMethod.getIsdefault() == 1) {
            accountPayMethodMapper.updateOtherPayMethodUnDefault(payMethod.getAccountId(), accountPaymethodId);
        }

        insertPaymethodAttrs(request.getAttrs(), accountPaymethodId, payMethod.getPaymethodId());

        return new AccountPaymethodUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public AccountRemovePayMethodResponse accountRemovePayMethod(Long accountId, Integer accountPaymethodId) {
        Integer status = accountMapper.selectAccountStatusById(accountId);
        if (null == status || 1 != status) {
            return new AccountRemovePayMethodResponse(ResultCode.FAIL_CODE, "Account is unavailable or account does not exit");
        }

        //禁用支付方式
        AccountPayMethod accountPayMethod = new AccountPayMethod();
        accountPayMethod.setId(accountPaymethodId);
        accountPayMethod.setStatus(0);
        accountPayMethodMapper.updateByPrimaryKeySelective(accountPayMethod);
        return new AccountRemovePayMethodResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AccountLinkUserResponse accountLinkUser(AccountUser accountUser) {
        Integer status = accountMapper.selectAccountStatusById(accountUser.getAccountId());
        if (null == status || 1 != status) {
            return new AccountLinkUserResponse(ResultCode.FAIL_CODE, "Account is unavailable or account does not exit");
        }

        accountUserMapper.insert(accountUser);

        return new AccountLinkUserResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AccountUnLinkUserResponse accountUnLinkUser(AccountUser accountUser) {

        List<RechargeRequestLog> logs = rechargeRequestLogMapper.selectUserAccountPendingRequest(accountUser);
        if (null != logs || logs.size() > 0) {
            return new AccountUnLinkUserResponse(ResultCode.FAIL_CODE, "There are outstanding transactions");
        }
        accountUserMapper.unLinkAccountUser(accountUser);

        return new AccountUnLinkUserResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AccountUserListResponse accountUserList(Long accountId) {

        List<User> users = accountUserMapper.selectUserByAccount(accountId);

        return new AccountUserListResponse(ResultCode.SUCCESS_CODE, users);
    }

    @Override
    public AccountCreditLimitUpdateResponse updateAccountCreditLimit(Long id, BigDecimal creditLimit) {
        accountMapper.updateAccountCreditLimit(id, creditLimit);
        return new AccountCreditLimitUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public int insert(Account account) {
        return accountMapper.insert(account);
    }

    @GlobalTransactional
    @Override
    public boolean accountPayment(AccountPaymentRequest request) throws CustomerException {
        Long accountId = request.getAccountId();

        String key = RedisKey.KEY_USER_ACCOUNT_PAY + accountId;

        boolean b = RedisUtil.lock(redisTemplate,key,3L,10*1000L);

        if(!b){
            return false;
        }

        Account account = accountMapper.selectByPrimaryKey(accountId);

        BigDecimal accountBalance = account.getBalance();

        BigDecimal accountAffiliateRebate = account.getAffiliateRebate();

        BigDecimal accountVipRebate = account.getVipRebate();

        BigDecimal accountAmount = accountBalance.add(accountAffiliateRebate).add(accountVipRebate);

        BigDecimal orderAmount = request.getPayAmount();

        if (accountAmount.compareTo(orderAmount) == -1) {
            RedisUtil.unLock(redisTemplate,key);
            return false;
        }

        BigDecimal balance = BigDecimal.ZERO;

        BigDecimal affiliateRebate = BigDecimal.ZERO;

        BigDecimal vipRebate = BigDecimal.ZERO;

        PaymentLog log = new PaymentLog();
        log.setId(request.getId());
        log.setAccountId(account.getId());
        log.setCustomerId(request.getCustomerId());
        log.setCreateTime(new Date());
        log.setFee(request.getFixFee());
        log.setPayType(request.getPayType());
        log.setOrderType(request.getOrderType());
        if (accountBalance.compareTo(orderAmount) > -1) {
            balance = orderAmount;
        } else if (accountBalance.add(accountAffiliateRebate).compareTo(orderAmount) > -1) {
            balance = accountBalance;
            affiliateRebate = orderAmount.subtract(accountBalance);
        } else {
            balance = accountBalance;
            affiliateRebate = accountAffiliateRebate;
            vipRebate = orderAmount.subtract(balance).subtract(affiliateRebate);
        }
        log.setVipRebate(vipRebate);
        log.setAffiliateRebate(affiliateRebate);
        log.setAmount(balance);
        log.setPayStatus(1);
        paymentLogDao.insert(log);

        int i = accountMapper.accountReduceBalance(account.getId(), balance, affiliateRebate, vipRebate);
        if (i != 1){
            RedisUtil.unLock(redisTemplate,key);
            throw new CustomerException("Account error!");
        }
        RedisUtil.unLock(redisTemplate,key);
        return true;
    }

    private void insertPaymethodAttrs(List<AccountPayMethodAttr> attrs, Integer accountPaymethodId, Integer paymentId) {
        PayMethodAttrsListRequest req = new PayMethodAttrsListRequest();
        PayMethodAttr payMethodAttr = new PayMethodAttr();
        payMethodAttr.setPaymethodId(paymentId);
        req.setT(payMethodAttr);
        List<PayMethodAttr> methodAttrs = payMethodAttrMapper.selectPayMethodAttrs(req);

        if (null != methodAttrs && methodAttrs.size() > 0) {

            if (null == attrs || attrs.size() != methodAttrs.size()) {
                throw new NullPointerException("Missing attributes");
            }

            List<AccountPayMethodAttr> accountPayMethodAttrs = new ArrayList<>();
            for (PayMethodAttr methodAttr : methodAttrs) {
                for (AccountPayMethodAttr attr : attrs) {
                    if (attr.getPaymethodAttrId().equals(methodAttr.getId())) {
                        //检查必填项
                        if (methodAttr.getNeeded() == 1) {
                            if (StringUtils.isBlank(attr.getAttrValue())) {
                                throw new NullPointerException(methodAttr.getAttrName() + " can't be null !");
                            }
                        }
                        attr.setAccountPaymethodId(accountPaymethodId);
                        accountPayMethodAttrs.add(attr);
                        continue;
                    }
                }
            }

            if (accountPayMethodAttrs.size() > 0 && accountPayMethodAttrs.size() == methodAttrs.size()) {
                accountPayMethodAttrMapper.batchInsert(accountPayMethodAttrs);
            } else {
                throw new RuntimeException("request error");
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public  boolean saveTransactionDetails(PaymentDetail paymentDetail) {

        Iterator<TransactionDetail> iterator = paymentDetail.getOrderTransactions().iterator();
        while (iterator.hasNext()){
            TransactionDetail next = iterator.next();
            Long orderId = next.getOrderId();
            Integer count = accountLogDao.selectAccountLog(orderId,paymentDetail.getOrderType());
            if (count > 0){
                return false;
            }
        }
        PaymentLog paymentLog = paymentLogDao.selectByPrimaryKey(paymentDetail.getPaymentId());
        if (null == paymentLog) {
            return false;
        }
        Account account = selectById(paymentLog.getAccountId());
        //------------记录本次支付消耗的余额返点信用额度信息------------
        BigDecimal balance = paymentLog.getAmount();

        BigDecimal affiliateRebate = paymentLog.getAffiliateRebate();

        BigDecimal vipRebate = paymentLog.getVipRebate();

        TransactionType transactionType = null;

        Integer orderType = paymentDetail.getOrderType();
        switch (orderType) {
            case OrderType.NORMAL:
                transactionType = TransactionType.BALANCE_PAY_ORDER;
                break;
            case OrderType.STOCK:
                transactionType = TransactionType.BALANCE_PAY_STOCK;
                break;
            case OrderType.EXTRA_SERVICE_WHOLESALE:
                transactionType = TransactionType.BALANCE_PAY_EXTRA_SERVICE_WHOLESALE;
                break;
            case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                transactionType = TransactionType.BALANCE_PAY_OVERSEA_WAREHOUSE_SERVICE_ORDER;
                break;
            case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                transactionType = TransactionType.BALANCE_PAY_PRODUCT_PHOTOGRAPHY;
                break;
            case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                transactionType = TransactionType.BALANCE_PAY_EXTRA_SERVICE_WINNING_PRODUCT;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderType);
        }
        Date date = new Date();
        //----------------支付的订单-----------------------
        List<TransactionDetail> transactionDetails = paymentDetail.getOrderTransactions();
        Iterator<TransactionDetail> transactionDetailIterator = transactionDetails.iterator();
        //------------------------------------------
        List<AccountLog> accountLogs = new ArrayList<>();
        //---------------------------------
        while (transactionDetailIterator.hasNext()) {
            AccountLog accountLog = new AccountLog();
            TransactionDetail detail = transactionDetailIterator.next();
            accountLog.setAccountId(account.getId());
            accountLog.setCustomerId(account.getCustomerId());
            accountLog.setTransactionId(detail.getOrderId());
            accountLog.setTransactionType(transactionType.getTransactionType());
            accountLog.setOrderType(transactionType.getOrderType());
            accountLog.setPayMethod(transactionType.getPayMethod());
            accountLog.setCreateTime(date);
            //----------------AccountLog保存每笔订单使用的余额返点信用额度-----------------
            BigDecimal transactionBalance = BigDecimal.ZERO;
            BigDecimal transactionAffiliateRebate = BigDecimal.ZERO;
            BigDecimal transactionVipRebate = BigDecimal.ZERO;
            //-------------------------------------------------------------------------
            //订单总额
            BigDecimal orderAmount = detail.getAmount();
            /**
             * 余额大于订单金额
             * 订单金额等于0
             */
            if (balance.compareTo(orderAmount) > -1) {
                transactionBalance = orderAmount;
                balance = balance.subtract(orderAmount);
                /**
                 * 余额+返点大于订单金额
                 * 余额，订单金额归0
                 * 返点=返点+余额-订单金额
                 */
            } else if (balance.add(affiliateRebate).compareTo(orderAmount) > -1) {
                transactionBalance = balance;
                orderAmount = orderAmount.subtract(balance);
                transactionAffiliateRebate = orderAmount;//订单支付用掉的返点
                affiliateRebate = affiliateRebate.subtract(orderAmount);//当前充值记录剩余返点
                balance = BigDecimal.ZERO;
            }else {
                orderAmount = orderAmount.subtract(balance).subtract(affiliateRebate);
                transactionBalance = balance;
                transactionAffiliateRebate = affiliateRebate;
                transactionVipRebate = orderAmount;
                vipRebate = vipRebate.subtract(orderAmount);
                balance = BigDecimal.ZERO;
                affiliateRebate = BigDecimal.ZERO;

            }
            //一个订单支付对应一条accountLog
            accountLog.setBalance(transactionBalance);
            accountLog.setAffiliateRebate(transactionAffiliateRebate);
            accountLog.setVipRebate(transactionVipRebate);
            accountLogs.add(accountLog);
        }

        if(balance.compareTo(BigDecimal.ZERO) != 0
        || affiliateRebate.compareTo(BigDecimal.ZERO) != 0
        || vipRebate.compareTo(BigDecimal.ZERO) != 0){
            return false;
        }
        accountLogDao.insertByBatch(accountLogs);
        return true;
    }

    /**
     * 账户订单退款
     * 备库退款,普通订单退款,批发订单退款
     *
     * @return
     */
    @GlobalTransactional
    @Override
    public BaseResponse accountOrderRefunded(AccountOrderRefundedRequest request) throws CustomerException{

        //客户
        Long customerId = request.getCustomerId();
        Account account = accountMapper.customerAccount(customerId);
        if (account == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "用户不存在!");
        }
        //校验订单类型
        Integer orderType = request.getOrderType();
        if (!orderType.equals(OrderType.STOCK)
                && !orderType.equals(OrderType.NORMAL)
                && !orderType.equals(OrderType.WHOLESALE)) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单类型异常!");
        }
        //订单支付金额
        BigDecimal payAmount = request.getPayAmount();
        BigDecimal refundAmount = request.getRefundAmount();
        //校验退款金额
        if (refundAmount == null || refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款金额异常!");
        }
        //检验支付金额
        if (payAmount == null || payAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单支付金额异常!");
        }
        //检查申请退款金额  不能大于支付总金额
        if (refundAmount.compareTo(payAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "申请退款金额，不能大于支付总金额!");
        }

        //支付流水列表
        //交易类型 transaction_type  支付/扣款 = 0，退款/收款 = 1，还款 = 2
        //支付资金流水
        AccountLog accountLog= accountLogDao.selectPayedAccountLogByTransactionId(request.getOrderId(), TransactionConstant.TransactionType.PAY_CUT_PAYMENT.getCode());
        if(accountLog==null){
            return new BaseResponse(ResultCode.FAIL_CODE, "流水异常!");
        }

        //获取支付流水返点
        BigDecimal payRebate = accountLog.getAffiliateRebate() == null ? BigDecimal.ZERO : accountLog.getAffiliateRebate().abs();
        //获取支付流水金额
        BigDecimal payBalance = accountLog.getBalance() == null ? BigDecimal.ZERO : accountLog.getBalance().abs();

        if(refundAmount.compareTo(payBalance.add(payRebate))>0){
            return new BaseResponse(ResultCode.FAIL_CODE, "申请退款金额，不能大于流水总金额!!");
        }
        //退款返点
        BigDecimal refundRebate = BigDecimal.ZERO;
        //退款余额
        BigDecimal refundBalance = BigDecimal.ZERO;
        if (payRebate.compareTo(BigDecimal.ZERO) > 0){
            if (payRebate.compareTo(refundAmount) > 0){
                refundRebate = refundAmount;
            }else {
                refundRebate = payRebate;
                refundBalance = refundAmount.subtract(payRebate);
            }
        }else {
            refundBalance = refundAmount;
        }

        //计算退款累计金额
//        List<RefundRecord> refundRecordList=new ArrayList<>();

        log.info("订单id:{},订单类型:{},申请退款金额:{}", request.getOrderId(), request.getOrderType(), request.getRefundAmount());

        log.info("id:{},customerId:{},用户当前数据:余额{},返点{},已用额度{},额度上限{}", account.getId(), account.getCustomerId(), account.getBalance(), account.getAffiliateRebate(), account.getCredit(), account.getCreditLimit());

        accountMapper.addBalanceAndBenefits(account.getId(), refundBalance, refundRebate);

        log.debug("实际退款余额:{},实际退款返点:{}", refundBalance, refundRebate);
        //增加退款流水
        AccountLog refundFlow = new AccountLog();
        refundFlow.setAccountId(account.getId());
        refundFlow.setCustomerId(customerId);
        //退款流水关联id为订单id
        refundFlow.setTransactionId(request.getOrderId());
        //transaction_type 支付/扣款 = 0，退款/收款 = 1，还款 = 2
        refundFlow.setTransactionType(1);
        refundFlow.setOrderType(orderType);
        //账户 = 0，paypal = 1，payoneer = 2，佣金 = 3
        refundFlow.setPayMethod(0);
        refundFlow.setBalance(refundBalance);
        refundFlow.setAffiliateRebate(refundRebate);
        refundFlow.setCredit(BigDecimal.ZERO);
        refundFlow.setCreateTime(new Date());
        accountLogDao.insert(refundFlow);

        //增加退款充值
//        RechargeLog rechargeLog=new RechargeLog();
//        rechargeLog.setAccountId(account.getId());
//        rechargeLog.setRelateId(request.getRefundId());
//        rechargeLog.setAmount(refundAmount);
//        rechargeLog.setRebate(BigDecimal.ZERO);
//        rechargeLog.setPayed(BigDecimal.ZERO);
//        rechargeLog.setRechargeStatus(0);
//        //0:电汇 1:paypal充值 2:payoneer充值，3=订单支付充值 4.退款充值
//        rechargeLog.setRechargeType(4);
//        rechargeLog.setCreateTime(new Date());
//        rechargeLog.setUpdateTime(new Date());
//        rechargeLogMapper.insert(rechargeLog);
//        refundRecordDao.insertByBatch(refundRecordList);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public Account selectDateByCustomerId(Long customerId) {

        return accountMapper.selectCustomerDefaultAccount(customerId);

    }

    @Override
    public AccountLog selectAccountLogById(Long id) {
        AccountLog record =new AccountLog();
        record.setId(id);
        return accountLogDao.selectByPrimaryKey(record);
    }

    //退款减佣金
    @Transactional(readOnly = false)
    public void subtractCommission(Long customerId, Long orderId, BigDecimal payAmount,
                                   BigDecimal benefitsAmount, BigDecimal refundAmount, Integer orderType)  {
        if (!orderType.equals(OrderType.NORMAL)
                && !orderType.equals(OrderType.WHOLESALE)) {
            return;
        }
        //查询该订单用户的推荐人
        Affiliate affiliate=affiliateDao.queryAffiliateByReferee(customerId);

        //如果有推荐人 扣除佣金
        if(affiliate!=null) {

            //交易id
            //检查该订单交易是否产生过佣金
            AffiliateCommissionRecord appRecord=affiliateCommissionRecordDao.queryPayRecordByOrderId(orderId);
            if(appRecord==null){
                return;
            }
            //产生佣金的实际金额
            BigDecimal totalAmount = payAmount.subtract(benefitsAmount);

            //申请金额
            BigDecimal applyAmount = refundAmount;

            //如果申请金额超过实际佣金金额 只扣取最大佣金
            if (applyAmount.compareTo(totalAmount) > 0) {
                applyAmount = totalAmount;
            }
            //扣取的佣金
            BigDecimal commission = applyAmount.multiply(new BigDecimal("0.01")).setScale(2, BigDecimal.ROUND_HALF_UP);

            if(commission.compareTo(BigDecimal.ZERO)>0) {
                //扣除被推荐人为推荐人产生的佣金 app_affiliate
                log.info("联盟佣金 affiliateId:" + affiliate.getId() + ",refereeCommission:" + affiliate.getRefereeCommission() + ",扣除佣金:" + commission);
                affiliateDao.subAffiliateCommission(affiliate.getId(), commission, new Date());

                Account userCommission = accountMapper.selectByPrimaryKey(affiliate.getReferrerId());
                if (userCommission != null) {
                    log.info("客户账户佣金 customerId:" + affiliate.getReferrerId() + ",commission:" + userCommission.getCommission() + ",扣除佣金:" + commission);
                }
                //扣除推荐人佣金 account_commission
                accountMapper.subAccountCommission(affiliate.getReferrerId(), commission);
                //增加佣金流水
                AffiliateCommissionRecord commissionRecord = new AffiliateCommissionRecord();
                //被推荐人
                commissionRecord.setRefereeId(affiliate.getRefereeId());
                commissionRecord.setReferrerId(affiliate.getReferrerId());
                commissionRecord.setOrderId(orderId);
                commissionRecord.setOrderType(orderType);
                BigDecimal subCommission = BigDecimal.ZERO.subtract(commission);
                commissionRecord.setCommission(subCommission);
                //退款=0，支付=1 提现=2
                commissionRecord.setState(0);
                commissionRecord.setCreateTime(new Date());
                commissionRecord.setUpdateTime(new Date());
                affiliateCommissionRecordDao.insert(appRecord);
            }
        }
    }

    @Override
    public int addAccountVipRebate(Long accountId, BigDecimal vipRebate) {
        if (null == accountId
                || null == vipRebate
                || BigDecimal.ZERO == vipRebate){
            return 0;
        }
        return accountMapper.addAccountVipRebate(accountId,vipRebate);
    }

    @Override
    public int addAccountAffiliateRebate(Long accountId, BigDecimal affiliateRebate) {
        if (null == accountId
                || null == affiliateRebate
                || BigDecimal.ZERO == affiliateRebate){
            return 0;
        }
        return accountMapper.addAccountAffiliateRebate(accountId,affiliateRebate);
    }

}
