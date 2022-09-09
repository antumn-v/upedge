package com.upedge.ums.modules.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.config.ShopifyConfig;
import com.upedge.common.model.store.request.CustomStoreSelectRequest;
import com.upedge.common.model.store.request.StoreFuzzySearchRequest;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.HMACValidation;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.thirdparty.shopify.moudles.shop.controller.ShopifyShopApi;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyGetTokenParam;
import com.upedge.ums.async.StoreAsync;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.ums.modules.store.request.*;
import com.upedge.ums.modules.store.response.*;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.UserService;
import com.upedge.ums.modules.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.upedge.ums.modules.store.service.impl.StoreServiceImpl.getShopifyAuthUrl;

/**
 * @author author
 */
@RestController
@RequestMapping("/store")
public class StoreController {


    @Autowired
    private StoreService storeService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StoreAsync storeAsync;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @ApiOperation("请求授权shopify店铺")
    @PostMapping("/shopifyConnect")
    public ShopifyAuthResponse shopifyAuthRequest(@RequestBody @Valid ShopifyAuthRequest request) {
        return storeService.shopifyAuthRequest(request.getShopName());
    }

    @ApiOperation("授权回调地址")
    @GetMapping("/shopifyAuth")
    public BaseResponse shopifyAuth(@RequestParam("code") String code,
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
            Store store = new Store();
            store.setStoreUrl(shop);
            store = storeService.selectByPrimaryKey(store);
            Map<String,Object> map = new HashMap<>();
            map.put("token",null);
            map.put("guideNotice",false);
            if (session == null && store == null){
                store = new Store();
                store.setStoreUrl(shop);
                store.setApiToken(token);
                redisTemplate.opsForValue().set(state,store,30,TimeUnit.MINUTES);
                map.put("state",state);
                return BaseResponse.success(map);
            }
            store = storeService.updateShopifyStore(shop, token, session);

            if (store != null) {
                if (session == null){
                    Customer customer = customerService.selectByPrimaryKey(store.getCustomerId());
                    User user = userService.selectByPrimaryKey(customer.getCustomerSignupUserId());
                    map = userServiceImpl.userSignIn(user, 1L);
                    map.put("state",null);
                    return BaseResponse.success(map);
                }
                return BaseResponse.success();
            }
            return BaseResponse.failed();
        }
        return BaseResponse.success("Verification failed");
    }


    @GetMapping("/connectShopify")
    public BaseResponse shopifyConnectRequest(@RequestParam("hmac") String hmac,
                                              @RequestParam("shop") String shop) {
        Map<String, Object> result = new HashMap<>();
        result.put("url", null);
        result.put("token", null);
        result.put("guideNotice",false);
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
            shop = shop.replace(".myshopify.com","");
            Store store = new Store();
            store.setStoreName(shop);
            store = storeService.selectByPrimaryKey(store);
            if (store == null || store.getStatus() != 1) {
                String nonce = System.currentTimeMillis() + "";
                String url = getShopifyAuthUrl(shop, nonce);
                redisTemplate.opsForValue().set(nonce, null,1, TimeUnit.HOURS);
                result.put("url", url);
                return new ShopifyAuthResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result);
            } else {
                Customer customer = customerService.selectByPrimaryKey(store.getCustomerId());
                User user = userService.selectByPrimaryKey(customer.getCustomerSignupUserId());
                result = userServiceImpl.userSignIn(user, 1L);
                result.put("url", null);
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result);
            }
        }
        return BaseResponse.failed("Verification failed");
    }

    @PostMapping("/addShopifyStore/manual")
    public ShopifyAuthResponse manualAddShopifyStore(@RequestBody ShopifyStoreManualAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        String shop = request.getShop();
        String token = request.getToken();
        storeService.updateShopifyStore(shop, token, session);
        return new ShopifyAuthResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    public static String verifyStoreAddress(String storeAddress) {
        if (StringUtils.isBlank(storeAddress)) {
            return null;
        }
        String pattern = "[\\w\\-]+\\.myshopify.com";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(storeAddress);
        if (m.find()) {
            return m.group(0);
        } else {
            return null;
        }
    }

    //    @ApiOperation("请求授权woocommerce店铺")
//    @PostMapping("/auth/woocommerce")
    public WoocommerceAuthResponse woocommerceAuth(@RequestBody @Valid WoocommerceAuthRequest request) {
        return storeService.woocommerceAuth(request);
    }

    //    @ApiOperation("请求授权shoplazza店铺")
//    @PostMapping("/auth/shoplazza")
    public BaseResponse shoplazzaAuth(@RequestBody @Valid ShoplazzaAuthRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return storeService.shoplazzaAuth(request, session);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public StoreInfoResponse info(@PathVariable Long id) {
        Store store = new Store();
        store.setId(id);
        store = storeService.selectByPrimaryKey(store);
        StoreInfoResponse res = new StoreInfoResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, store, id);
        return res;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission(permission = "store:store:list")
    public StoreListResponse list(@RequestBody @Valid StoreListRequest request) {

        if (null == request.getT()) {
            request.setT(new Store());
        }
        Session session = UserUtil.getSession(redisTemplate);
        List<Long> orgIds = new ArrayList<>();
        List<Store> results = new ArrayList<>();
        Long total = 0L;
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            orgIds = session.getOrgIds();
            if (ListUtils.isEmpty(orgIds)) {
                return new StoreListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>(), request);
            }
            int i = 0;
            results = storeService.selectByCustomerOrgIds(session.getCustomerId(), orgIds);
            if (null != results) {
                i = results.size();
            }
            request.setTotal(Long.parseLong(i + ""));
        } else {
            request.getT().setCustomerId(session.getCustomerId());

            results = storeService.select(request);
            total = storeService.count(request);
            request.setTotal(total);
        }

        StoreListResponse res = new StoreListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }

    @GetMapping("/setting/list")
    public BaseResponse storeSettingList() {
        return storeService.storeSettingList();

    }

    /**
     * 查询当前店铺设置信息
     *
     * @param storeId
     * @return
     */
    @GetMapping("/setting/list/{storeId}")
    public BaseResponse oneStoreSettingList(@PathVariable Long storeId) {
        return storeService.oneStoreSettingList(storeId);

    }

    @PostMapping("/setting/update")
    public BaseResponse updateStoreSetting(@RequestBody @Valid StoreSetting storeSetting) {
        return storeService.storeSettingUpdate(storeSetting);
    }

    /**
     * 批量保存电偶设置
     *
     * @param list
     * @return
     */
    @PostMapping("/settingList/update")
    public BaseResponse updateStoreSettingList(@RequestBody @Valid List<StoreSetting> list) {
        return storeService.storeSettingListUpdate(list);
    }


    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @Permission(permission = "store:store:update")
    public StoreUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StoreUpdateRequest request) {
        Store entity = request.toStore(id);
        storeService.updateByPrimaryKeySelective(entity);
        StoreUpdateResponse res = new StoreUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        return res;
    }


    @PostMapping("/search")
    public BaseResponse storeSearch(@RequestBody StoreSearchRequest request) {
        Store store = new Store();
        BeanUtils.copyProperties(request, store);
        store = storeService.selectByPrimaryKey(store);
        if (null != store) {
            StoreVo storeVo = storeService.queryStoreSetting(store.getId());
            BeanUtils.copyProperties(store, storeVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(), storeVo);
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, storeVo);
        }
        return BaseResponse.failed("The shop does not exist");

    }

    @ApiOperation("店铺名模糊搜索")
    @PostMapping("/fuzzySearch")
    public BaseResponse fuzzySearch(@RequestBody@Valid StoreFuzzySearchRequest request) {
        String storeName = request.getStoreName();
        List<StoreVo> storeVos = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisKey.STRING_STORE + "*");
        for (String key : keys) {
            StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(key);
            if (storeVo.getStoreName().contains(storeName)){
                storeVos.add(storeVo);
            }
        }
        return BaseResponse.success(storeVos);

    }

    @ApiOperation("所有店铺")
    @GetMapping("/all")
    public BaseResponse all() {
        List<StoreVo> storeVos = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisKey.STRING_STORE + "*");
        for (String key : keys) {
            StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(key);
            if (null == storeVo || StringUtils.isBlank(storeVo.getStoreName())){
                continue;
            }
            storeVos.add(storeVo);
        }
        return BaseResponse.success(storeVos);

    }

    @PostMapping("/getStoreData")
    public BaseResponse getStoreData(String storeName) {
        if (StringUtils.isNotBlank(storeName)) {
            Store store = new Store();
            store.setStoreName(storeName);
            store = storeService.selectByPrimaryKey(store);
            if (store != null) {
                storeAsync.getStoreData(store);
            }
        } else {
            Page<Store> storePage = new Page<>();
            storePage.setPageSize(-1);
            List<Store> stores = storeService.select(storePage);
            for (Store store1 : stores) {
                storeAsync.getStoreData(store1);
            }
        }

        return BaseResponse.success();
    }


    /**
     * 店铺货币及汇率修改
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateStore/{id}", method = RequestMethod.POST)
    @Permission(permission = "store:store:update")
    public BaseResponse updateStore(@PathVariable Long id, @RequestBody @Valid Store request) {
        request.setId(id);
        int i = storeService.updateStoreByPrimaryKey(request);
        if (i == 1) {
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    @PostMapping("/customStoreSelect")
    public List<StoreVo> selectCustomStore(@RequestBody CustomStoreSelectRequest request){
        List<Store> stores = storeService.selectCustomStores(request);
        List<StoreVo> storeVos = new ArrayList<>();
        for (Store store : stores) {
            StoreVo storeVo = new StoreVo();
            BeanUtils.copyProperties(store,storeVo);
            storeVos.add(storeVo);
            String key = RedisKey.STRING_STORE + store.getId();
            if (!redisTemplate.hasKey(key)){
                redisTemplate.opsForValue().set(key,storeVo);
            }
        }
        return storeVos;
    }

}
