package com.upedge.thirdparty.woocommerce.moudles.shop.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.woocommerce.entity.AuthParam;
import com.upedge.thirdparty.woocommerce.util.WoocommerceRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author 海桐
 */
public class WoocommerceShopApi {


    public static JSONObject shopSystemStatus(AuthParam auth){
        String url = "https://"+auth.getShopName()+"/wp-json/wc/"+auth.getVersion()+"/system_status";

        ResponseEntity<String> entity = WoocommerceRequest.sendRequest(null,auth.getToken(),url,null, HttpMethod.GET);

        if(entity.getStatusCode() == HttpStatus.OK){
            return JSONObject.parseObject(entity.getBody());
        }
        return null;
    }
}
