package com.upedge.redis.serializer;

import com.alibaba.fastjson.JSONObject;
import com.upedge.redis.quote.SerializeQuote;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * alibaba fastjson 本就提供序列化的工具，因此本工具废弃
 * @author 荀立坤
 * @date 2020年6月19日
 * @param <T>
 */
@Deprecated
public class FastJSONRedisSerializer<T> implements RedisSerializer<T> {

	@Override
	public byte[] serialize(T t) throws SerializationException {
		SerializeQuote<T> sq = new SerializeQuote<T>(t);
		return JSONObject.toJSONBytes(sq);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		SerializeQuote<T> sw = JSONObject.parseObject(bytes, SerializeQuote.class);
		return sw.getT();
	}

}
