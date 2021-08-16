package com.upedge.common.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectMapper {

	public enum Tenant {ENABLE,DISABLE}
	public enum Create {ENABLE,DISABLE}
	public enum Update {ENABLE,DISABLE}
	
	Tenant tenant() default Tenant.ENABLE;
	Create create() default Create.ENABLE;
	Update update() default Update.ENABLE;
	
	String tenantField() default "customerId";
	String createField() default "createTime";
	String updateField() default "updateTime";
}
