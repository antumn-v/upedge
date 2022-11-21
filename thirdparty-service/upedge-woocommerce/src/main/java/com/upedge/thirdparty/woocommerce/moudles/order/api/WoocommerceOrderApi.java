package com.upedge.thirdparty.woocommerce.moudles.order.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.utils.OkHttpRequest;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderNote;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderShipment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.List;

public class WoocommerceOrderApi {

    public static JSONObject getOrder(String token, String shopName, String orderId){
        String url = "https://"+shopName+"/wp-json/wc/v3/orders/" + orderId;

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data);
        }

        return null;
    }


    public static JSONArray getWoocommerceOrders(String token, String shopName,String param){
        String url = "https://"+shopName+"/wp-json/wc/v3/orders" ;
        if(StringUtils.isNotBlank(param)){
            url = url + "?" + param;
        }

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,param,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data);
        }

        return null;
    }

    public static JSONArray getWoocommerceOrderCount(String token, String shopName,String param){
        String url = "https://"+shopName+"/wp-json/wc/v3/reports/orders/totals" ;

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,param,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data);
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(getWoocommerceOrderCount("Y2tfMmI3ZmM4NjY1ZmMzMzAyY2UyNWY1YjBjYzVhMWI2ZDBiZTcyNmYxODpjc180NGJhNmU0ZGY1ZjQwZTg5N2M3ZDQ5YjQ1Nzc4NzIxNDRhN2M4Mjk3","www.evershape.at",null));
    }


    public static JSONArray getOrderRefund(String token, String shopName, String orderId){
        String url = "https://"+shopName+"/wp-json/wc/v3/orders/" + orderId + "/refunds";

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data);
        }

        return null;
    }

    public static WoocommerceOrderNote postOrderNote(String orderId,String shop,String token,WoocommerceOrderNote woocommerceOrderNote){
        String url = "https://"+shop+"/wp-json/wc/v1/orders/"+orderId+"/notes";
        String data =
                OkHttpRequest.woocommerceRequest(woocommerceOrderNote,token,url,null,HttpMethod.POST);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data).toJavaObject(WoocommerceOrderNote.class);
        }
        return null;
    }

    public static List<WoocommerceOrderNote> getOrderNotes(String orderId,String shop,String token){
        String url = "https://"+shop+"/wp-json/wc/v1/orders/"+orderId+"/notes";
        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data).toJavaList(WoocommerceOrderNote.class);
        }
        return null;
    }

    public static WoocommerceOrderShipment postOrderShipmentTracking(String orderId,String shop,String token,WoocommerceOrderShipment body){
        String url = "https://"+shop+"/wp-json/wc/v1/orders/"+orderId+"/shipment-trackings";
        String data =
                OkHttpRequest.woocommerceRequest(body,token,url,null,HttpMethod.POST);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data).toJavaObject(WoocommerceOrderShipment.class);
        }
        return null;
    }

    public static WoocommerceOrderShipment getOrderShipmentTracking(String orderId,String shop,String token){
        String url = "https://"+shop+"/wp-json/wc/v1/orders/"+orderId+"/shipment-trackings";
        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data).toJavaObject(WoocommerceOrderShipment.class);
        }
        return null;
    }
}
