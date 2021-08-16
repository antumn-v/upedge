package com.upedge.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Object getProperty(Object owner, String fieldName) throws Exception {
	     Class ownerClass = owner.getClass();
	     Field field = ownerClass.getField(fieldName);
	     Object property = field.get(owner);
	     return property;  
	}
	
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
	     Class ownerClass = owner.getClass();
	     Class[] argsClass = new Class[args.length];
	     for (int i = 0, j = args.length; i < j; i++) {  
	         argsClass[i] = args[i].getClass();  
	     }  
	      Method method = ownerClass.getMethod(methodName,argsClass);
	     return method.invoke(owner, args);  
	}  
	
	/**
	 * 适用于有 getter 和 setter的静态property
	 * @param owner
	 * @param fieldName
	 * @param fieldValue
	 * @throws Exception
	 */
	public static void setProperty(Object owner, String fieldName, Object fieldValue) throws Exception {
		String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		invokeMethod(owner,methodName,new Object[] {fieldValue});
	}
}
