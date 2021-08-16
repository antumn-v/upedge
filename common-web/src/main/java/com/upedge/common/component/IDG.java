package com.upedge.common.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class IDG {
	
	// 默认1个大小
	private static HashMap<String, AtomicInteger> idgCache = new HashMap<String, AtomicInteger>(1);
	
	private static final ReentrantLock lock = new ReentrantLock();

	// 因为有锁，所以是变成了线程安全的，省去每次 new 的消耗，耗时降低约一半
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
	
	private static final int defaultStartValue = 0;
	
	@Value("spring.application.id:0")
	private static int machine;
	
	public static String getTimeStampSequence() {

		String timestamp = null;
		String inc = null;
		lock.lock();
		try {
			timestamp = sdf.format(new Date());
			AtomicInteger value = idgCache.get(timestamp);
			if (value == null) {
				idgCache.clear();
				idgCache.put(timestamp, new AtomicInteger(defaultStartValue));
				inc = String.valueOf(defaultStartValue);
			} else {
				inc = String.valueOf(value.addAndGet(1));
			}
		} finally {
			lock.unlock();
		}

		return timestamp + StringUtils.leftPad(inc, 5, '0') // 时间戳（毫秒）+ 序号 =15+5
				+StringUtils.leftPad(String.valueOf(machine), 4, '0'); // 机器号 =4
	}
	
//	public static void main(String[] args) {
//		long now = System.currentTimeMillis();
//		for(long i=0;i<10000000000l;i++) {
////			Date s = new Date(); 33933
//			long time = System.currentTimeMillis();
//		}
//		System.out.println(System.currentTimeMillis()-now);
//	}
}
