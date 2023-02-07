package com.upedge.ums.modules.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.config.ShopifyConfig;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 海桐
 */
@Slf4j
@RestController
@RequestMapping("/webHook")
public class WebhookController {

    @Autowired
    StoreService storeService;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    public HttpStatus shopifyWebhook() {
        HttpServletResponse response = RequestUtil.getResponse();
        HttpServletRequest request = RequestUtil.getRequest();
        String topic = request.getHeader("X-Shopify-Topic");
        String originalStr = request.getHeader("X-Shopify-Hmac-Sha256");
        String shopName = request.getHeader("X-Shopify-Shop-Domain");
        String resultData = getRequestData(request);
        String hashStr = verifyWebhook(resultData, ShopifyConfig.api_select_key);
        if (null == request) {
            response.setStatus(401);
            return HttpStatus.BAD_REQUEST;
        }
        shopName = shopName.replace(".myshopify.com","");
        Store store = new Store();
        store.setStoreName(shopName);
        store = storeService.selectByPrimaryKey(store);
        if (store == null) {
            return HttpStatus.OK;
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        if (hashStr.equals(originalStr)) {
            Store finalStore = store;
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.parseObject(resultData);
                    StoreApiRequest apiRequest = new StoreApiRequest();
                    apiRequest.setStoreVo(storeVo);
                    apiRequest.setJsonObject(jsonObject);
                    switch (topic) {
                        case "products/update":
//                            pmsFeignClient.updateShopifyProduct(apiRequest);
                            break;
                        case "orders/updated":
                            omsFeignClient.updateShopifyOrder(apiRequest);
                            break;
                        case "app/uninstalled":
                            finalStore.setStatus(0);
                            finalStore.setUpdateTime(new Date());
                            storeService.updateByPrimaryKeySelective(finalStore);
                            break;
                        default:
                            break;
                    }
                }
            },threadPoolExecutor);

            return HttpStatus.OK;
        }
        response.setStatus(401);
        return HttpStatus.UNAUTHORIZED;
    }

    @RequestMapping(value = "/woocommerce", method = RequestMethod.POST)
    public HttpStatus woocommerce(HttpServletRequest request) {
        String topic = request.getHeader("X-WC-Webhook-Topic");
        String shop = request.getHeader("X-WC-Webhook-Source");
        String sign = request.getHeader("X-WC-Webhook-Signature");

        String body = getRequestData(request);
        if (StringUtils.isBlank(body)) {
            return HttpStatus.BAD_REQUEST;
        }

        Store store = new Store();
        store.setStoreName(shop);
        store = storeService.selectByPrimaryKey(store);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        JSONObject jsonObject = JSONObject.parseObject(body);
        StoreApiRequest apiRequest = new StoreApiRequest();
        apiRequest.setStoreVo(storeVo);
        switch (topic) {
            case "products/create":
                break;
            case "products/update":
                pmsFeignClient.updateWoocommerceProduct(apiRequest);
                break;
            case "products/delete":
                break;
            case "orders/updated":
                omsFeignClient.woocommerceOrderUpdate(apiRequest);
                break;
            default:
                break;
        }
        return HttpStatus.OK;
    }

    @PostMapping("/shoplazza")
    public HttpStatus shoplazzaWebhook(HttpServletRequest request) {
        String topic = request.getHeader("X-Shoplazza-Topic");
        String shop = request.getHeader("X-Shoplazza-Shop-Domain");
        String hmac = request.getHeader("X-Shoplazza-Hmac-Sha256");
        String body = getRequestData(request);

        Store store = new Store();
        store.setStoreName(shop);
        store = storeService.selectByPrimaryKey(store);
        if (StringUtils.isBlank(body) || null == store) {
            return HttpStatus.BAD_REQUEST;
        }
        String hashStr = verifyWebhook(body, store.getApiToken());
        if (!hmac.equals(hashStr)) {
            return HttpStatus.BAD_REQUEST;
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        CompletableFuture<Void> webhook = CompletableFuture.runAsync(() -> {
            JSONObject jsonObject = JSONObject.parseObject(body);
            StoreApiRequest apiRequest = new StoreApiRequest();
            apiRequest.setStoreVo(storeVo);
            switch (topic) {
                case "products/create":
                    pmsFeignClient.updateWoocommerceProduct(apiRequest);
                    break;
                case "products/update":
                    pmsFeignClient.updateWoocommerceProduct(apiRequest);
                    break;
                case "products/delete":
                    pmsFeignClient.deleteStoreProduct(apiRequest);
                    break;
                case "orders/updated":
                    omsFeignClient.updateShoplazzaOrder(apiRequest);
                    break;
                default:
                    break;
            }
        });

        return HttpStatus.OK;
    }


    @RequestMapping("/customers/redact")
    public HttpStatus customerRedact(){
        HttpServletResponse response = RequestUtil.getResponse();
        HttpServletRequest request = RequestUtil.getRequest();
        String originalStr = request.getHeader("X-Shopify-Hmac-Sha256");
        String resultData = getRequestData(request);
        String hashStr = verifyWebhook(resultData, ShopifyConfig.api_select_key);
        JSONObject jsonObject = JSONObject.parseObject(resultData);
        String shop = jsonObject.getString("shop_domain");
        log.warn("/customers/redact--->" + resultData);
        if (!hashStr.equals(originalStr)){
            response.setStatus(401);
            return HttpStatus.UNAUTHORIZED;
        }
        Store store = new Store();
        store.setStoreName(shop.replace(".myshopify.com",""));
        store = storeService.selectByPrimaryKey(store);
        if (store != null){
            response.setStatus(200);
            store.setStatus(0);
            store.setUpdateTime(new Date());
            storeService.updateByPrimaryKeySelective(store);
            return HttpStatus.OK;
        }
        response.setStatus(401);
        return HttpStatus.UNAUTHORIZED;
    }

    @RequestMapping("/customers/delete")
    public HttpStatus customerDelete(){
        HttpServletResponse response = RequestUtil.getResponse();
        HttpServletRequest request = RequestUtil.getRequest();
        String originalStr = request.getHeader("X-Shopify-Hmac-Sha256");
        String resultData = getRequestData(request);
        String hashStr = verifyWebhook(resultData, ShopifyConfig.api_select_key);
        JSONObject jsonObject = JSONObject.parseObject(resultData);
        String shop = jsonObject.getString("shop_domain");
        log.warn("/customers/delete--->" + resultData);
        if (!hashStr.equals(originalStr)){
            response.setStatus(401);
            return HttpStatus.UNAUTHORIZED;
        }
        Store store = new Store();
        store.setStoreName(shop.replace(".myshopify.com",""));
        store = storeService.selectByPrimaryKey(store);
        if (store != null){
            response.setStatus(200);
            store.setStatus(0);
            store.setUpdateTime(new Date());
            storeService.updateByPrimaryKeySelective(store);
            return HttpStatus.OK;
        }
        response.setStatus(401);
        return HttpStatus.UNAUTHORIZED;
    }

    @PostMapping("/store/delete")
    public HttpStatus storeDelete(){
        HttpServletResponse response = RequestUtil.getResponse();
        HttpServletRequest request = RequestUtil.getRequest();
        String originalStr = request.getHeader("X-Shopify-Hmac-Sha256");
        String resultData = getRequestData(request);
        String hashStr = verifyWebhook(resultData, ShopifyConfig.api_select_key);
        JSONObject jsonObject = JSONObject.parseObject(resultData);
        String shop = jsonObject.getString("shop_domain");
        log.warn("/store/delete---> data:{}, ---> hmac: {}", resultData,originalStr);
        if (!hashStr.equals(originalStr)){
            response.setStatus(401);
            return HttpStatus.UNAUTHORIZED;
        }

        Store store = new Store();
        store.setStoreName(shop.replace(".myshopify.com",""));
        store = storeService.selectByPrimaryKey(store);
        if (store != null){
            response.setStatus(200);
            store.setStatus(0);
            store.setUpdateTime(new Date());
            storeService.updateByPrimaryKeySelective(store);
            return HttpStatus.OK;
        }
        response.setStatus(401);
        return HttpStatus.UNAUTHORIZED;
    }

    public static String verifyWebhook(String message, String appSecret) {
        String result = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(appSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            result = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    public static String getRequestData(HttpServletRequest request) {
        BufferedReader in = null;
        String result = "";
        try {
            in = new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
