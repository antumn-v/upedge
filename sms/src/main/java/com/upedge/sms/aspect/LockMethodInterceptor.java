package com.upedge.sms.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;



@Aspect
@Configuration
@Slf4j
public class LockMethodInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(1000)
            // 设置写缓存后 过期
            .expireAfterWrite(120, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(com.upedge.sms.aspect.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws CustomerException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pjp.getArgs());
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                log.error("请勿重复请求");
                throw new CustomerException(ResultCode.FAIL_CODE,"请勿重复请求");
            }
            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new CustomerException(ResultCode.FAIL_CODE,throwable.getMessage());
        } finally {
            CACHES.invalidate(key);
        }
    }

    /**
     * key 的生成策略
     * @param keyExpress 表达式
     * @param args       参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) throws CustomerException {
        Session session = UserUtil.getSession(redisTemplate);
        switch (keyExpress){
            default:
                return keyExpress;
        }
    }

}
