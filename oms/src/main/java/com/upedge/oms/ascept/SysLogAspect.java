package com.upedge.oms.ascept;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaqi on 2020/10/21.
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Pointcut("execution(public * com.upedge.oms.modules.*.controller.*.*(..))")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    private void before(JoinPoint joinPoint)throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String method="";
        method=className + "." + methodName + "()";

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params="";
        try{
            params = new Gson().toJson(args);
        }catch (Exception e){

        }

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.debug("请求IP：{}", request.getRemoteAddr());
        log.debug("请求路径：{}", request.getRequestURL());
        log.debug("请求方式：{}", request.getMethod());
        log.debug("token：{}", request.getHeader("X-Token"));
        log.debug("referer：{}", request.getHeader("referer"));
        log.debug("method：{}", method);
        log.debug("请求参数：{}", params);

    }

    /**
     * Print the time that request method execution spend
     * @param joinPoint
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        Object retVal = joinPoint.proceed(args);
        long endTime = System.currentTimeMillis();
        log.debug("执行时间：{} ms", endTime - startTime);
//        log.debug("返回值：{}\n\t", new Gson().toJson(retVal));
        return retVal;
    }

    /**
     * Print exception
     * @param ex
     */
//    @AfterThrowing(throwing = "ex", pointcut = "logPointCut()")
//    public void afterThrowing(Throwable ex) {
//        ex.printStackTrace();
//        log.error("发生异常：{}", ex.toString());
//    }
}
