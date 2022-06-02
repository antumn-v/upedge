package com.upedge.common.web.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.web.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ResponseObjectAspect implements ResponseBodyAdvice {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @AfterReturning(value = "execution( * com.upedge.pms.modules.*.controller.*.*(..))",returning = "result")
//    public void responseAfter(Object result){
//        if (result == null){
//            return;
//        }
//        String s = "customerId";
//        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
//        if (!jsonObject.containsKey("data")){
//            return;
//        }
//        result = jsonObject.get("data");
//
//        if (result instanceof JSONObject){
//            jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
//            if (jsonObject.containsKey("customerId")){
//                Long customerId = jsonObject.getLong(s);
//                if (customerId == null){
//                    return;
//                }
//                UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,String.valueOf(customerId));
//                jsonObject.put("username",userVo.getUsername());
//            }
//        }
//        if (result instanceof JSONArray){
//            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(result));
//            jsonArray = UserUtil.objectAddUserNameField(redisTemplate,jsonArray,JSONArray.class);
//        }
//    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            Session session = UserUtil.getSession(redisTemplate);
            if (session == null
            || session.getApplicationId() == Constant.APP_APPLICATION_ID){
                return result;
            }

            if (result == null) {
                return result;
            }
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
            if (!jsonObject.containsKey("data")) {
                return result;
            }

            Object data = jsonObject.get("data");

            if (data instanceof JSONObject) {
                JSONObject jsonData = JSONObject.parseObject(JSON.toJSONString(data));
                if (jsonData.containsKey("customerId")) {
                    Long customerId = jsonObject.getLong("customerId");
                    if (customerId == null) {
                        return result;
                    }
                    UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO, String.valueOf(customerId));
                    if (userVo != null) {
                        jsonData.put("username", userVo.getUsername());
                    }
                    jsonObject.put("data", jsonData);
                }
            }

            if (data instanceof JSONArray) {
                JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(data));
                List<Object> list = new ArrayList<>();
                for (Object a : jsonArray) {
                    JSONObject x = (JSONObject) JSON.toJSON(a);
                    Long customerId = x.getLong("customerId");
                    if (customerId == null) {
                        return result;
                    }
                    UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO, String.valueOf(customerId));
                    if (userVo != null) {
                        x.put("username", userVo.getUsername());
                    }
                    list.add(x);
                }
                jsonObject.put("data", list);
            }
            return jsonObject;
        } catch (Exception e) {

        }
        return result;
    }
}
