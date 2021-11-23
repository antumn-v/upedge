package com.upedge.common.component.aspect;


import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通用权限处理，在Controller的方法中添加 Permission注解
 * @author 荀立坤
 * @date 2020年6月20日
 */
//@Order(50)
@Aspect
@Component
public class PermissionAspect {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	 
	@Before("@annotation(permission)")
	public void beforeMethod(JoinPoint point, Permission permission) throws CustomerException {
		String perStr = permission.permission();
		Session session = UserUtil.getSession(redisTemplate);
		if(session.getUserType()== BaseCode.USER_ROLE_ADMIN||
				session.getUserType()==BaseCode.USER_ROLE_SUPERADMIN){
			return;
		}
		List<String> userPermission = session.getPermissions();
		if(!userPermission.contains(perStr)) {
			throw new CustomerException(ResultCode.FAIL_CODE,"no permission");
		}
		
	}
}
