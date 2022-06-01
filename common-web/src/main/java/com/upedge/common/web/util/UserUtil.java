package com.upedge.common.web.util;


import com.upedge.common.constant.Constant;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.EncryptUtil;
import com.upedge.common.utils.PropertyAppender;
import com.upedge.common.utils.TokenUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户工具类，可以操作token、密码、当前用户
 *
 * @author 荀立坤
 * @date 2020年6月16日
 */
public class UserUtil {

    private UserUtil() {

    }

    /**
     * 获取当前请求中的用户Token
     *
     * @return
     */
    public static String getToken() {
        return RequestUtil.getRequest().getHeader(Constant.TOKEN_HEADER_KEY);
    }

    /**
     * 从Redis中获取用户信息
     *
     * @param redisTemplate
     * @return
     */
    public static Session getSession(RedisTemplate<String, Object> redisTemplate) {
        String token = TokenUtil.getTokenKey(getToken());
        Session session = (Session) redisTemplate.opsForValue().get(token);
		/*if (session == null){
			throw new CustomerException(CustomerExceptionEnum.LOGIN_INFORMATION_INVALID);
		}*/
        return session;
    }

    public static Session getSession(RedisTemplate<String, Object> redisTemplate, String token) {
        Session session = (Session) redisTemplate.opsForValue().get(token);
		/*if (session == null){
			throw new CustomerException(CustomerExceptionEnum.LOGIN_INFORMATION_INVALID);
		}*/
        return session;
    }

    public static UserVo getUser(RedisTemplate<String, Object> redisTemplate) {
        Session session = (Session) redisTemplate.opsForValue().get(TokenUtil.getTokenKey(getToken()));
        return session.toUser();
    }


    /**
     * 更新当前用户信息
     *
     * @param redisTemplate
     */
    public static void updateUser(RedisTemplate<String, Object> redisTemplate, Session session) {
        String token = RequestUtil.getRequest().getHeader(Constant.TOKEN_HEADER_KEY);
        redisTemplate.opsForValue().set(TokenUtil.getTokenKey(token), session,
                Constant.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
    }

    /**
     * 更新Redis中的用户信息，不一定是本用户，用户也不一定处于登录状态
     *
     * @param redisTemplate
     * @param token
     */
    public static void setUser(RedisTemplate<String, Object> redisTemplate, String token, Session session) {
        redisTemplate.opsForValue().set(TokenUtil.getTokenKey(token), session,
                Constant.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
    }


    /**
     * 加密密码
     *
     * @param loginname
     * @param password
     * @return
     */
    public static String encryptPassword(String password, String loginname) {
        return EncryptUtil.getInstance().AESencode(password, loginname);
    }

    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return EncryptUtil.getInstance().AESencode(password, Constant.ENCRYPT_KEY);
    }

    /**
     * 对象动态添加用户名字段
     *
     */
    public static <T> T objectAddUserNameField(RedisTemplate redisTemplate,T t,Class c) {

        Field field = ReflectionUtils.findField(c, "customerId");
		if (field == null){
			return t;
		}
		Long customerId = 0L;
		try {
			customerId = (Long) field.get(t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,String.valueOf(customerId));
		if (userVo == null){
			return t;
		}
		Map<String, Object> propertiesMap = new HashMap<>(1);
		propertiesMap.put("username", userVo.getUsername());
		try {
			PropertyAppender.generate(t, propertiesMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return t;
	}

    public static void objectsAddUserNameField(RedisTemplate redisTemplate, Object obj,Class<T> cla) {
        List<T> list = new ArrayList<T>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
        }
		for (Object o : list) {
			objectAddUserNameField(redisTemplate,o,cla);
		}
	}

}
