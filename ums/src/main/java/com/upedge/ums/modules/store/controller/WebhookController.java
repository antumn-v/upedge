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
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

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

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    public HttpStatus shopifyWebhook() {
        HttpServletRequest request = RequestUtil.getRequest();
        String topic = request.getHeader("X-Shopify-Topic");
        String originalStr = request.getHeader("X-Shopify-Hmac-Sha256");
        String shopName = request.getHeader("X-Shopify-Shop-Domain");
        String resultData = getRequestData(request);
        String hashStr = verifyWebhook(resultData, ShopifyConfig.api_select_key);
        if (null == request) {
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
            JSONObject jsonObject = JSONObject.parseObject(resultData);
            StoreApiRequest apiRequest = new StoreApiRequest();
            apiRequest.setStoreVo(storeVo);
            apiRequest.setJsonObject(jsonObject);
            switch (topic) {
                case "products/update":
                    pmsFeignClient.updateShopifyProduct(apiRequest);
                    break;
                case "orders/updated":
                    omsFeignClient.updateShopifyOrder(apiRequest);
                    break;
                case "app/uninstalled":
                    store.setStatus(0);
                    store.setUpdateTime(new Date());
                    storeService.updateByPrimaryKeySelective(store);
                    break;
                default:
                    break;
            }
        }
        return HttpStatus.OK;
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


    @GetMapping("/customers/redact")
    public HttpStatus customerRedact(){
        return HttpStatus.OK;
    }

    @GetMapping("/customers/delete")
    public HttpStatus customerDelete(){
        return HttpStatus.OK;
    }

    @GetMapping("/store/delete")
    public HttpStatus storeDelete(){
        return HttpStatus.OK;
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
