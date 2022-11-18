package com.upedge.pms.modules.product.controller;


import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyProduct;
import com.upedge.thirdparty.woocommerce.moudles.product.entity.WoocProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductWebhookController {

    @Autowired
    StoreProductService storeProductService;

    @Autowired
    RedisTemplate redisTemplate;


    @PostMapping("/shopify/webhook/product/update")
    public void updateShopifyProduct(@RequestBody @Valid StoreApiRequest request){
        ShopifyProduct product = request.getJsonObject().toJavaObject(ShopifyProduct.class);
        storeProductService.saveShopifyProduct(product,request.getStoreVo());
    }

//    @PostMapping("/shoplazza/webhook/product/update")
//    public void updateShoplazzaProduct(@RequestBody @Valid StoreApiRequest request){
//        ShoplazzaProduct product = request.getJsonObject().toJavaObject(ShoplazzaProduct.class);
//        storeProductService.saveShoplazzaProduct(product,request.getStoreVo());
//    }

    @PostMapping("/webhook/product/delete")
    public void deleteStoreProduct(@RequestBody @Valid StoreApiRequest request){
        String id = request.getJsonObject().getString("id");
        StoreVo storeVo = request.getStoreVo();
        storeProductService.storeProductDeleteByStore(id,storeVo.getId());
    }

    @PostMapping("/woocommerce/webhook/product/update")
    public void updateWoocommerceProduct(@RequestBody @Valid StoreApiRequest request){
        WoocProduct product = null;
        try {
            product = request.getJsonObject().toJavaObject(WoocProduct.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(request);
            return;
        }
        Long storeProductId = storeProductService.saveWoocProduct(product,request.getStoreVo());
    }
}
