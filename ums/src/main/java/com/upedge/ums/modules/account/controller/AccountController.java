package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.request.AccountAddRequest;
import com.upedge.ums.modules.account.request.AccountListRequest;
import com.upedge.ums.modules.account.request.AccountPayMethodAddRequest;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author 海桐
 */
@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    AccountService accountService;

    String payoneerAuthUrl;



    /**
     * 账户列表
     * @param request
     * @return
     */
    @PostMapping("/list")
    public AccountListResponse accountList(@RequestBody AccountListRequest request){
        return accountService.pageAccount(request);
    }

    @ApiOperation("账户详情")
    @GetMapping("/sessionDetail")
    public BaseResponse accountDetail(){
        Session session = UserUtil.getSession(redisTemplate);
        Account account = accountService.selectSessionAccount(session);
        if (null == account){
            return BaseResponse.failed();
        }
        return BaseResponse.success(account);
    }



    /**
     * 添加账户
     * @param request
     * @return
     */
    @ApiOperation(value = "添加账户")
    @PostMapping("/add")
    public AccountAddResponse addAccount(@RequestBody @Valid AccountAddRequest request){
        return accountService.addAccount(request);
    }

    /**
     * 逻辑删除账户
     * @param accountId
     * @return
     */
    @ApiOperation("禁用账户")
    @PostMapping("/{accountId}/remove")
    public AccountRemoveResponse removeAccount(@PathVariable Long accountId){
        return accountService.removeAccount(accountId);
    }

    /**
     * 账户添加支付方式
     * @param request
     * @return
     */
    @ApiOperation("账户添加支付方式")
    @PostMapping("/paymethod/add")
    public AccountPayMethodAddResponse accountAddPayMethod(@RequestBody AccountPayMethodAddRequest request){
        return accountService.addAccountPayMethod(request);
    }

    @GetMapping("/paymethod/{accountPaymethodId}/attrs")
    public AccountPaymethodAttrListResponse listAccountPaymethodAttr(@PathVariable Integer accountPaymethodId){
        return accountService.accountPaymethodAttrList(accountPaymethodId);
    }

    @ApiOperation("账户添加payoneer")
    @PostMapping("/paymethod/add/payoneer")
    public BaseResponse getPayoneerAuthUrl(@RequestBody AccountPayMethodAddRequest request){

        AccountPayMethod payMethod = accountService.selectByAccountBankNum(request.getAccountId(),request.getBankNum());

        if(payMethod != null){
            return new AccountPayMethodAddResponse(ResultCode.FAIL_CODE,"Cannot add the same payment method");
        }

        String state = UUID.randomUUID().toString().replace("-","");

        redisTemplate.opsForValue().set(state,request,30 * 60 * 1000);

        String url = payoneerAuthUrl.replace("{state}",state);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,url);
    }





    /**
     * 使用某账户的用户列表
     * @param accountId
     * @return
     */
    @GetMapping("/{accountId}/user/list")
    public AccountUserListResponse accountUserList(@PathVariable Long accountId){
        return accountService.accountUserList(accountId);
    }

    /**
     * 用户账户关联
     * @param accountUser
     * @return
     */
//    @ApiOperation("账户关联用户")
//    @PostMapping("/user/link")
//    public AccountLinkUserResponse userLinkUser(@RequestBody AccountUser accountUser){
//        return accountService.accountLinkUser(accountUser);
//    }
//
//    /**
//     * 用户账户解除关联
//     * @param accountUser
//     * @return
//     */
//    @ApiOperation("账户解除关联用户")
//    @PostMapping("/user/unlink")
//    public AccountUnLinkUserResponse userUnlinkUser(@RequestBody AccountUser accountUser){
//        return accountService.accountUnLinkUser(accountUser);
//    }
//
//    /**
//     * 更新账户信用额度
//     * @param id
//     * @param request
//     * @return
//     */
//    @ApiOperation("更改账户信用额度上限")
//    @PostMapping("/{id}/credit/update")
//    public AccountCreditLimitUpdateResponse updateAccountCredit(@PathVariable Long id, @RequestBody AccountCreditLimitUpdateRequest request){
//        BigDecimal creditLimit=request.getCreditLimit();
//        return accountService.updateAccountCreditLimit(id, creditLimit);
//    }

    /**
     * 账户支付订单
     * @param request
     * @return
     */
    @ApiOperation("账户支付订单")
    @PostMapping("/payment")
    public BaseResponse accountPayment(@RequestBody AccountPaymentRequest request){
        boolean b = accountService.accountPayment(request);
        if(b){
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    /**
     * 只能内部调用 订单退款账户处理
     * @param request
     * @return
     */
    @PostMapping("/internal-service/orderRefunded")
    public BaseResponse accountOrderRefunded(@RequestBody AccountOrderRefundedRequest request) {
        try {
            BaseResponse response= accountService.accountOrderRefunded(request);
            log.debug("response code:{},msg:{}",response.getCode(),response.getMsg());
            return response;
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        }
    }

}
