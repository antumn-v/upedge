package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.request.OrderAccountLogRequest;
import com.upedge.common.model.user.vo.OrderAccountLogVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.request.AccountLogListRequest;
import com.upedge.ums.modules.account.response.AccountLogListResponse;
import com.upedge.ums.modules.account.service.AccountLogService;
import com.upedge.ums.modules.account.vo.AccountLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author author
 */
@Api(tags = "账单记录")
@RestController
@RequestMapping("/accountLog")
public class AccountLogController {
    @Autowired
    private AccountLogService accountLogService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 账单流水
     * @param request
     * @return
     */
    @ApiOperation("列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account.log.list")
    public AccountLogListResponse list(@RequestBody @Valid AccountLogListRequest request) {
        if(null == request.getT()){
            request.setT(new AccountLog());
        }
        Session session = UserUtil.getSession(redisTemplate);
        if(session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            request.getT().setCustomerId(session.getCustomerId());
        }
        if(request.getBeginTime()!=null){
            Date createDate = DateUtils.// 将日期的时间改为0点0分0秒
                    getOfDayFirst(request.getBeginTime());
            request.setBeginTime(createDate);
        }
        if(request.getEndTime()!=null){
            Date createDate = DateUtils.// 将日期的时间改为23点59分59秒
                    getOfDayLast(request.getEndTime());
            request.setEndTime(createDate);
        }
        List<AccountLog> results = accountLogService.select(request);
        Long total = accountLogService.count(request);
        request.setTotal(total);
        AccountLogListResponse res = new AccountLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * app账单流水导出
     * @param request
     * @return
     */
    @RequestMapping(value="/export", method=RequestMethod.POST)
    @Permission(permission = "account.log.export")
    public AccountLogListResponse export(@RequestBody @Valid AccountLogListRequest request) {

        if(null == request.getT()){
            request.setT(new AccountLog());
        }
        Session session = UserUtil.getSession(redisTemplate);

        if(session.getUserType() == 1){
            request.getT().setCustomerId(session.getCustomerId());
        }
        List<AccountLog> results = accountLogService.selectAllByTime(request);
        return new AccountLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results);

    }

    /**
     * admin资金流水
     * @param request
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.POST)
    public AccountLogListResponse all(@RequestBody @Valid AccountLogListRequest request) throws CustomerException {
        if(null == request.getT()){
            request.setT(new AccountLog());
        }

        if(request.getBeginTime()!=null){
            Date createDate = DateUtils.// 将日期的时间改为0点0分0秒
                    getOfDayFirst(request.getBeginTime());
            request.setBeginTime(createDate);
        }
        if(request.getEndTime()!=null){
            Date createDate = DateUtils.// 将日期的时间改为23点59分59秒
                    getOfDayLast(request.getEndTime());
            request.setEndTime(createDate);
        }

        if ((request.getPageSize() == -1 && request.getBeginTime()==null)||(request.getPageSize() == -1 && request.getEndTime() ==null) ){
            throw new CustomerException(CustomerExceptionEnum.TIME_CANNOT_BE_EMPTY);
        }
        Session session = UserUtil.getSession(redisTemplate);
        List<AccountLog> results = accountLogService.select(request);
        Long total = accountLogService.count(request);
        List<AccountLogVo> accountLogVoList=new ArrayList<>();
        request.setTotal(total);
        results.forEach(accountLog -> {
            AccountLogVo accountLogVo=new AccountLogVo();
            String transactionDesc= TransactionConstant.transactionDesc(accountLog.getTransactionType(),accountLog.getOrderType(),accountLog.getPayMethod());
            accountLogVo.setTransactionDesc(transactionDesc);
            BeanUtils.copyProperties(accountLog,accountLogVo);
            accountLogVoList.add(accountLogVo);
        });
        AccountLogListResponse res = new AccountLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,accountLogVoList,request);
        return res;
    }

    /**
     * admin订单资金流水详情
     * @return
     */
    @RequestMapping(value="/orderInfoAccountFlow/{transactionId}", method=RequestMethod.POST)
    public BaseResponse orderInfoAccountFlow(@PathVariable Long transactionId) {
        return accountLogService.orderInfoAccountFlow(transactionId);
    }

    /**
     * admin订单支付日志信息
     * @return
     */
    @RequestMapping(value="/payInfo/{transactionId}", method=RequestMethod.POST)
    public BaseResponse accountLogPayInfo(@PathVariable String transactionId) {
        return accountLogService.accountLogPayInfo(transactionId);
    }

    @RequestMapping(value = "/queryCount/accountLog", method = RequestMethod.POST)
    public  BaseResponse selectAccountLogByOrder(@Validated @RequestBody OrderAccountLogRequest accountLogQuery){
        return BaseResponse.success(accountLogService.selectAccountLogByOrder(accountLogQuery));
    }
    @PostMapping("/payInfo/accountLogPayInfoByTransactionDetail")
    public BaseResponse accountLogPayInfoByTransactionDetail(@RequestBody TransactionDetail transactionDetail){
        OrderAccountLogVo accountLogVo = accountLogService.accountLogPayInfoByTransactionDetail(transactionDetail);
        return  new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,accountLogVo);
    };
}
