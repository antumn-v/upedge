package com.upedge.thirdparty.shopify.moudles.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.panda.common.utils.ListUtils;
import com.panda.common.utils.OkHttpRequest;
import com.upedge.thirdparty.shopify.config.Shopify;
import com.upedge.thirdparty.shopify.entity.Response;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyFulfillment;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyRequestParam;
import com.upedge.thirdparty.shopify.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class ShopifyOrderApi {

    @PostMapping("/get")
    public static JSONObject getStoreOrder(String shop,String token,Object body,String nextUrl,String param){
        String url = null;
        if (StringUtils.isBlank(nextUrl)){
            url = "https://" + shop + "/admin/api/" + Shopify.version + "/orders.json";
        } else {
            url = nextUrl;
        }
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, token, param, HttpMethod.GET, null);
        if (entity == null){
            return null;
        }
        JSONObject jsonObject = entity.getBody();
        HttpHeaders headers = entity.getHeaders();
        String link = headers.getFirst("Link");
        if (link != null && link.contains("next")) {
            if (link.contains("previous")) {
                link = link.substring(link.indexOf("rel=\"previous\","));
            }
            link = link.substring(link.indexOf("<") + 1, link.indexOf(">")).replace("%2C",",");
        }else{
            link = null;
        }
        if(StringUtils.isNotBlank(link)) {
            jsonObject.put("nextUrl", link);
        }
        return jsonObject;
    }

    public static JSONObject getOrderDetailById(@PathVariable String id,String shop,String token){
        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/orders/"+id+".json";

        ResponseEntity<JSONObject> entity = RequestUtils.sendRequest(url,token,null,HttpMethod.GET,null);
        if (entity == null){
            return null;
        }

        return entity.getBody();
    }

    public static void main(String[] args) {
        System.out.println(getOrderDetailById("3935044173939","technigadget.myshopify.com","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"));
    }

    public static ShopifyFulfillment orderFulfillment(String orderId,String shop,String token,Object body){
        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/orders/"+orderId+"/fulfillments.json";
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest( url, token, null, HttpMethod.POST, body);
        if(null != entity){
            JSONObject jsonObject = entity.getBody();
            if(jsonObject != null && jsonObject.containsKey("fulfillment")){
                ShopifyFulfillment fulfillment = jsonObject.getJSONObject("fulfillment").toJavaObject(ShopifyFulfillment.class);
                return fulfillment;
            }
        }
        return null;
    }

    public static JSONObject getOrderRefundList(String id,String shop,String token){

        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/orders/"+id+"/refunds.json";

        String result = OkHttpRequest.shopifyRequest(url,null,token,HttpMethod.GET);
        if(StringUtils.isNotBlank(result)){
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        }
        return null;
    }

    public static ShopifyFulfillment orderFulfillmentUpdate(Object body,String token,String shop, String orderId,String fulfillmentId){
        String url = "https://" + shop + "/admin/api/"+Shopify.version+"/orders/"+orderId+"/fulfillments/"+fulfillmentId+".json";
        JSONObject jsonObject =
                RequestUtils.putRequest(JSON.toJSONString(body), token,url);
        if(null != jsonObject){
            ShopifyFulfillment fulfillment = jsonObject.getJSONObject("fulfillment").toJavaObject(ShopifyFulfillment.class);
            return fulfillment;
        }
        return null;
    }

    @PostMapping("/{orderId}/transactions")
    public Response getOrderTransactions(@PathVariable Long orderId,@RequestBody JSONObject jsonObject){
        Response response = new Response();
        if (!jsonObject.containsKey("param"))
            return response.failed("null data");
        ShopifyRequestParam param = jsonObject.getJSONObject("param").toJavaObject(ShopifyRequestParam.class);
        jsonObject.remove("param");
        String url = "https://" + param.getShopName() + "/admin/api/" + param.getApiVersion() + "/orders/"+orderId+"/transactions.json";
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest( url, param.getToken(), null, HttpMethod.GET, null);
        jsonObject = entity.getBody();
        response.addData("transactions", jsonObject.getJSONObject("transactions"));
        return response.success();
    }
}
