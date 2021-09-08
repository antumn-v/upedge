package com.upedge.thirdparty.shopify.moudles.product.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.upedge.thirdparty.shopify.config.Shopify;
import com.upedge.thirdparty.shopify.entity.Response;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyRequestParam;
import com.upedge.thirdparty.shopify.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * @author 海桐
 */
@RestController
@RequestMapping("/product")
public class ShopifyProductApi {

    @PostMapping("/post")
    public static JSONObject postProduct(@RequestBody JSONObject jsonObject, String token, String shop) {


        ShopifyRequestParam param = new ShopifyRequestParam();
        String url = "https://" + shop + "/admin/api/" + param.getApiVersion() + "/products.json";
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, token, null, HttpMethod.POST, jsonObject);

        if (entity.getBody() == null) {
            return null;
        }
        jsonObject = entity.getBody();

//        jsonObject = JSONObject.parseObject(PostRequest.sendPost(url, param.getApiKey(), param.getAccessToken(), jsonObject));
        return jsonObject;
    }

    @PostMapping("/count")
    public Response getStoreProductCount(@RequestBody JSONObject jsonObject) {
        Response response = new Response();
        if (!jsonObject.containsKey("param"))
            return response.failed("null data");
        ShopifyRequestParam param = jsonObject.getJSONObject("param").toJavaObject(ShopifyRequestParam.class);
        jsonObject.remove("param");
        if (StringUtils.isBlank(param.getParam()))
            param.setParam(null);
        String url = "https://" + param.getShopName() + "/admin/api/" + param.getApiVersion() + "/products/count.json";
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, param.getToken(), param.getParam(), HttpMethod.POST, jsonObject);
        jsonObject = entity.getBody();
        Integer count = jsonObject.getInteger("count");
        response.addData("count", count);
        return response.success();
    }

    @PostMapping("/get")
    public static JSONObject getStoreProducts(String shop, String token, Object body, String nextUrl, String param) {
        String url = null;
        if (StringUtils.isBlank(nextUrl)) {
            url = "https://" + shop + "/admin/api/" + Shopify.version + "/products.json";
        } else {
            url = nextUrl;

        }
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, token, param, HttpMethod.GET, null);
        JSONObject jsonObject = entity.getBody();
        HttpHeaders headers = entity.getHeaders();
        String link = headers.getFirst("Link");
        if (link != null && link.contains("next")) {
            if (link.contains("previous")) {
                link = link.substring(link.indexOf("rel=\"previous\","));
            }
            link = link.substring(link.indexOf("<") + 1, link.indexOf(">")).replace("%2C", ",");
        } else {
            link = null;
        }
        if (StringUtils.isNotBlank(link)) {
            jsonObject.put("nextUrl", link);
        }
        return jsonObject;
    }

    @PostMapping("/{id}/image/post")
    public Response postProductImage(@RequestBody JSONObject jsonObject, String token, String shop, @PathVariable Long id) {
        Response response = new Response();
        if (!jsonObject.containsKey("param") || !jsonObject.containsKey("product"))
            return response.failed("null data");

        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/products/" + id + "/images.json";

        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, token, null, HttpMethod.POST, jsonObject);
        jsonObject = entity.getBody();
        response.addData("image", jsonObject.getJSONObject("image"));
        return response.success();
    }

    public static JSONObject updateProduct(JSONObject jsonObject, String token, String shop, @PathVariable String id) {
        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/products/" + id + ".json";


        jsonObject =
                RequestUtils.putRequest(jsonObject.toJSONString(), token, url);


        return jsonObject;

    }

    public static JSONObject getProduct(String id, String token, String shop) {
        Response response = new Response();

        String url = Shopify.getShopifyApi(shop, "products/" + id);
        ResponseEntity<JSONObject> entity =
                RequestUtils.sendRequest(url, token, null, HttpMethod.GET, null);
        if (null == entity.getBody()) {
            return null;
        }
        return entity.getBody();
    }

    public static JSONObject deleteProduct(String token, String shop, @PathVariable String id) {

        String url = "https://" + shop + "/admin/api/" + Shopify.version + "/products/" + id + ".json";
        return RequestUtils.sendRequest(url, token, null, HttpMethod.DELETE, null).getBody();

    }

    @PostMapping("/inventory/post")
    public Response postProductInventory(@RequestBody JSONObject jsonObject) {
        Response response = new Response();
        if (!jsonObject.containsKey("param") || !jsonObject.containsKey("level")) {
            return response.failed("null data");
        }
        ShopifyRequestParam param = jsonObject.getJSONObject("param").toJavaObject(ShopifyRequestParam.class);
        jsonObject.remove("param");
        String url = "https://" + param.getShopName() + "/admin/api/2020-01/inventory_levels/adjust.json";
        jsonObject = jsonObject.getJSONObject("level");
        RequestUtils.sendRequest(url, param.getToken(), null, HttpMethod.POST, jsonObject);
        return response.success();
    }

}
