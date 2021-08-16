package com.upedge.common.utils;

import com.upedge.common.constant.Constant;

import java.util.Random;

public class TokenUtil {

	/**
	 * redis中的key
	 * @param token
	 * @return
	 */
	public static String getTokenKey(String token) {
		return Constant.TOKEN_REDIS_PREFIX + token;
	}

	public static String getManagerKey(String token) {
		return Constant.MANAGER_REDIS_PREFIX + token;
	}

	/**
	 * 生成token 时间+随机long+用户id
	 * @param id
	 * @return
	 */
	public static String genToken(Long id) {
		Random r = new Random();
		String be = ""+ System.currentTimeMillis()+r.nextLong()+id;
		return EncryptUtil.getInstance().MD5(be);
	}
}
