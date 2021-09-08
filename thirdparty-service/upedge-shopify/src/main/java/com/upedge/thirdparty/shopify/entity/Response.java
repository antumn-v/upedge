package com.upedge.thirdparty.shopify.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
public class Response extends JSONObject {

    String message;

    boolean success;

    Integer code;

    String timestamp = new Timestamp(System.currentTimeMillis()).toString();;

    /**
     * 消息头meta 存放状态信息 code message
     */
    private Map<String,Object> data = new HashMap<String,Object>();

    public Response() {
        put("data",this.data);
    }

    /**
     * 消息内容  存储实体交互数据
     */


    public Map<String, Object> getData() {
        return data;
    }

    public Response setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public Response addData(String key,Object object) {
        this.data.put(key,object);
        return this;
    }


    public Response success(){
        put("success",Boolean.TRUE);
        put("code",200);
        put("message","success");
        return this;
    }

    public Response success(String message){
        put("success",Boolean.TRUE);
        put("code",200);
        put("message",message);
        return this;
    }

    public Response failed(String statusMsg) {
        put("success",Boolean.FALSE);
        put("code",500);
        put("message",statusMsg);
        return this;
    }

}
