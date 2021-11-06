package com.upedge.thirdparty.shoplazza.moudles.order.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaFulfilliment;
import com.upedge.thirdparty.shoplazza.utils.ShoplazzaRequestUtils;
import org.springframework.http.HttpMethod;

import java.util.List;

public class ShoplazzaOrderApi {



    public static JSONObject getShoplazzaOrders(String shop, String token, String param, String body){
        String url = "https://" + shop + "/openapi/2020-01/orders";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,param, HttpMethod.GET, body);
        return jsonObject;
    }


    public static JSONObject getShoplazzaOrdersCount(String shop,String token,String param,String body){
        String url = "https://" + shop + "/openapi/2020-01/orders/count";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,param, HttpMethod.GET, body);
        return jsonObject;
    }

    public static List<ShoplazzaFulfilliment> getOrderFulfillment(String shop, String token, String orderId){
        String url = "https://" + shop + "/openapi/2020-01/orders/"+orderId+"/fulfillments";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,null, HttpMethod.GET, null);
        if(jsonObject != null && jsonObject.containsKey("fulfillments")){
            List<ShoplazzaFulfilliment> fulfilliments = jsonObject.getJSONArray("fulfillments").toJavaList(ShoplazzaFulfilliment.class);
            return fulfilliments;
        }
        return null;
    }


    public static ShoplazzaFulfilliment postOrderFulfillment(String shop,String token,String orderId,Object body){
        String url = "https://" + shop + "/openapi/2020-01/orders/"+orderId+"/fulfillments";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,null, HttpMethod.POST, body);
        if(jsonObject != null && jsonObject.containsKey("fulfillment")){
            ShoplazzaFulfilliment fulfilliment = jsonObject.getJSONObject("fulfillment").toJavaObject(ShoplazzaFulfilliment.class);
            return fulfilliment;
        }
        return null;
    }

    public static ShoplazzaFulfilliment putOrderFulfillment(String shop,String token,String orderId,String fulfillmentId,ShoplazzaFulfilliment fulfilliment){
        String url = "https://" + shop + "/openapi/2020-01/orders/"+orderId+"/fulfillments/" + fulfillmentId;
        JSONObject jsonObject = ShoplazzaRequestUtils.putRequest(JSON.toJSONString(fulfilliment),token,url);
        if(jsonObject != null && jsonObject.containsKey("fulfillment")){
            fulfilliment = jsonObject.getJSONObject("fulfillment").toJavaObject(ShoplazzaFulfilliment.class);
            return fulfilliment;
        }
        return null;
    }
}
