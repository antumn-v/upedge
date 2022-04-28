package com.upedge.common.web.advice;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guoxing on 2020/10/22.
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse handleRoleException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException:{}",e);
        return new BaseResponse(ResultCode.FAIL_CODE,e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = CustomerException.class)
    public BaseResponse handleCustomerException(CustomerException e) {
        log.error("CustomerException:{}",e);
        return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
    }
}
