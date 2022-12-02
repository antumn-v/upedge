/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.upedge.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author ThinkGem
 * @version 2014-8-19
 */
public class IdGenerate {

	private static SecureRandom random = new SecureRandom();
	private static IdWorker idWorker = new IdWorker(-1, -1);

	public static final int ID_LENGTH = 12;

	public static String generateUniqueId() {
		return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
	}

	public static String generateUniqueId(Integer length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	
	/**
	 * 生成UUID, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {

		return Math.abs(random.nextLong());
	}

	public static int randomInt() {

		return Math.abs(random.nextInt());
	}

	/**
	 * 获取新唯一编号（18为数值）
	 * 来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
	 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
	 */
	public static Long nextId() {
		return idWorker.nextId();
	}



	/**
	* 获取八位随机数
	* */
	public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };


	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}


	public static void main(String[] args) {
//		System.out.println(IdGenerate.nextId());
		System.out.println(generateUniqueId(5));
	}

}
