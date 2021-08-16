package com.upedge.redis.quote;

import com.alibaba.fastjson.JSONObject;

@Deprecated
public class SerializeQuote<T> {

	private String name;
	
	private T t;
	
	public SerializeQuote() {
		
	}
	
	public SerializeQuote(T t){
		this.name = t.getClass().getName();
		this.t = t;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public T getT() {
		if(t.getClass().getName().equalsIgnoreCase("com.alibaba.fastjson.JSONObject")) {
			try {
				Class<T> clazz = (Class<T>) Class.forName(name);
				return JSONObject.toJavaObject((JSONObject)t, clazz);
			} catch (ClassNotFoundException e) {
				return null;
			}
		}
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
