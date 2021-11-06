package com.upedge.ums.modules.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.store.config.ShopifyConfig;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.HMACValidation;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.thirdparty.shopify.moudles.shop.controller.ShopifyShopApi;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyGetTokenParam;
import com.upedge.ums.async.StoreAsync;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
public class ShopifyAuthController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreAsync storeAsync;

    @GetMapping("/shopify")
    public BaseResponse test(@RequestParam("code") String code,
                       @RequestParam("hmac") String hmac,
                       @RequestParam("shop") String shop,
                       @RequestParam("state") String state,
                       @RequestParam("timestamp") String timestamp) throws IOException {
        Session session = (Session) redisTemplate.opsForValue().get(state);
        HttpServletRequest request = RequestUtil.getRequest();
        String string = request.getQueryString();
        String[] params = string.split("&");
        List<String> args = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            String s = params[i];
            if (!s.contains("hmac")) {
                args.add(s);
            }
        }
        boolean verify = HMACValidation.Valicate(hmac, args, ShopifyConfig.api_select_key);
        if (verify) {
            ShopifyGetTokenParam param = new ShopifyGetTokenParam();
            param.setApiKey(ShopifyConfig.api_key);
            param.setApiSecret(ShopifyConfig.api_select_key);
            param.setShopName(shop);
            param.setCode(code);
            JSONObject jsonObject = ShopifyShopApi.getToken(param);
            if (!jsonObject.getBoolean("success")) {
                return BaseResponse.failed();
            }
            String token = jsonObject.getJSONObject("data").getString("token");
            Store store = storeService.updateShopifyStore(shop, token, session);
            if (store != null){
                try {
                    storeAsync.getStoreData(store);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return BaseResponse.success();
            }
            return BaseResponse.failed();

        }
        return BaseResponse.success("Verification failed");
    }
}
