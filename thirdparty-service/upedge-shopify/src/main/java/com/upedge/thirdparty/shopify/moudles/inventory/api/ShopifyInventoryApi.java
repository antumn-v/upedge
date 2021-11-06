package com.upedge.thirdparty.shopify.moudles.inventory.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.utils.OkHttpRequest;
import com.upedge.thirdparty.shopify.config.Shopify;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

public class ShopifyInventoryApi {



    public static JSONObject updateInventoryItem(String shop,String token,Long id,Object body){
        String url = "https://" + shop + ".myshopify.com/admin/api/" + Shopify.version + "/inventory_items/"+id+".json";
        String result = OkHttpRequest.shopifyRequest(url, JSON.toJSONString(body),token, HttpMethod.PUT);
        if(StringUtils.isNotBlank(result)){
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        }
        return null;
    }
}
