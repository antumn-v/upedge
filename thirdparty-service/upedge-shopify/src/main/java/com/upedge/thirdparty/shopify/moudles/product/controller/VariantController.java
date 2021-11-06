package com.upedge.thirdparty.shopify.moudles.product.controller;

import com.alibaba.fastjson.JSONObject;

import com.upedge.thirdparty.shopify.entity.Response;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyRequestParam;
import com.upedge.thirdparty.shopify.utils.RequestUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/variant")
@RestController
public class VariantController {


    @PostMapping("/post")
    public Response variantPost(@RequestBody JSONObject jsonObject){
        Response response = new Response();
        Long storeProductId = jsonObject.getLong("productId");
        ShopifyRequestParam param = jsonObject.getJSONObject("param").toJavaObject(ShopifyRequestParam.class);
        String url = "https://" + param.getShopName() + ".myshopify.com/admin/api/" + param.getApiVersion() + "/products/"+storeProductId+"/variants.json";
        jsonObject.remove("param");
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, param.getToken(),null, HttpMethod.POST,jsonObject);
        jsonObject = entity.getBody();
        response.addData("variant", jsonObject.getJSONObject("variant"));
        return response.success();
    }


    @PostMapping("/{id}/update")
    public Response updateVariantById(@RequestBody JSONObject jsonObject, @PathVariable Long id){
        Response response = new Response();
        if (!jsonObject.containsKey("param") || !jsonObject.containsKey("variant"))
            return response.failed("null data");
        ShopifyRequestParam param = jsonObject.getJSONObject("param").toJavaObject(ShopifyRequestParam.class);
        String url = "https://" + param.getShopName() + ".myshopify.com/admin/api/" + param.getApiVersion() + "/variants/" + id + ".json";
        jsonObject.remove("param");

        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, param.getToken(),null, HttpMethod.PUT,jsonObject);
        jsonObject = entity.getBody();
        response.addData("variant", jsonObject.getJSONObject("variant"));
        return response.success();
    }
}
