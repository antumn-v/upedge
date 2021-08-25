package com.upedge.ums.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author xunlikun
 * @date 2018-10-08 20:41
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // 如果不是映射到方法直接通过
//        if(!(object instanceof HandlerMethod)){
//            return true;
//        }
//        String token = httpServletRequest.getHeader("X-Token");// 从 http 请求头中取出 token
//        logger.info("URL：{}", httpServletRequest.getRequestURI());
//
//        if(StringUtils.isBlank(token)){
//            throw new CustomerException(ResultCode.FAIL_TOKEN_CODE,"没有token,无操作权限");
//        }
//        Session session = UserUtil.getSession(redisTemplate);
//        if(session==null){
//            throw new CustomerException(ResultCode.FAIL_TOKEN_CODE,"token失效");
//        }
//        logger.info("当前userId：{}", session.getId());
//        //刷新缓存 更新有效时间
//        redisTemplate.expire(TokenUtil.getTokenKey(token), 3, TimeUnit.HOURS);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
