package com.upedge.ums.modules.store.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.enums.StoreSettingEnum;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.old.ums.StoreNameAndUserVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.config.ShopifyConfig;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.TokenUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.thirdparty.shopify.entity.shop.Shop;
import com.upedge.thirdparty.shopify.moudles.shop.controller.ShopifyShopApi;
import com.upedge.thirdparty.shoplazza.moudles.shop.api.ShoplazzaShopApi;
import com.upedge.thirdparty.shoplazza.moudles.shop.entity.ShoplazzaShop;
import com.upedge.thirdparty.woocommerce.entity.AuthParam;
import com.upedge.thirdparty.woocommerce.moudles.shop.api.WoocommerceShopApi;
import com.upedge.ums.async.StoreAsync;
import com.upedge.ums.enums.ShopifyAttr;
import com.upedge.ums.enums.WoocommerceAttr;
import com.upedge.ums.modules.log.service.MqMessageLogService;
import com.upedge.ums.modules.organization.dao.OrganizationDao;
import com.upedge.ums.modules.organization.dao.OrganizationMenuDao;
import com.upedge.ums.modules.organization.dao.OrganizationRoleDao;
import com.upedge.ums.modules.organization.dao.OrganizationUserDao;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import com.upedge.ums.modules.organization.entity.OrganizationRole;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import com.upedge.ums.modules.store.config.StoreConfig;
import com.upedge.ums.modules.store.dao.StoreAttrDao;
import com.upedge.ums.modules.store.dao.StoreDao;
import com.upedge.ums.modules.store.dao.StoreSettingDao;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.entity.StoreAttr;
import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.ums.modules.store.request.ShoplazzaAuthRequest;
import com.upedge.ums.modules.store.request.WoocommerceAuthRequest;
import com.upedge.ums.modules.store.response.ShopifyAuthResponse;
import com.upedge.ums.modules.store.response.WoocommerceAuthResponse;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.dao.RoleDao;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.request.CustomerSignUpRequest;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    StoreAttrDao storeAttrDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    OrganizationRoleDao organizationRoleDao;

    @Autowired
    OrganizationUserDao organizationUserDao;

    @Autowired
    StoreSettingDao storeSettingDao;

    @Autowired
    OrganizationMenuDao organizationMenuDao;

    @Autowired
    StoreAsync storeAsync;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    CustomerService customerService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    MqMessageLogService mqMessageLogService;


    /**
     *
     */
    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        Store record = new Store();
        record.setId(id);
        return storeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insert(Store record) {
        return storeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int insertSelective(Store record) {
        return storeDao.insert(record);
    }

    @Override
    public List<Store> selectStoreByCustomer(Long customerId) {
        return storeDao.selectStoreByCustomer(customerId);
    }

    @Override
    public List<Store> selectByCustomerOrgIds(Long customerId, List<Long> orgIds) {
        return storeDao.selectByCustomerOrgIds(customerId, orgIds);
    }

    @Override
    public List<String> selectAllStoreCurrency() {
        return storeDao.selectAllStoreCurrency();
    }

    @Override
    public BaseResponse storeSettingList() {

        Session session = UserUtil.getSession(redisTemplate);

        List<Store> stores = storeDao.selectStoreByCustomer(session.getCustomerId());

        Map<String, List<StoreSetting>> map = new HashMap<>();

        stores.forEach(store -> {
            if (1 == store.getStatus()) {
                map.put(store.getStoreName(), storeSettingDao.selectByStoreId(store.getId()));
            }
        });

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map);
    }

    @Override
    public BaseResponse storeSettingUpdate(StoreSetting storeSetting) {
        storeSettingDao.updateStoreSettingValueByName(storeSetting);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public void updateUsdRate() {
        storeDao.updateUsdRate();
    }

    @Transactional
    @Override
    public Store updateShopifyStore(String storeUrl, String token, Session session) {

        storeUrl = storeUrl.replace(".myshopify.com","");
        Shop shopDetail = ShopifyShopApi.getShopDetail(storeUrl, token);
        if (null == shopDetail) {
            return null;
        }
        Store store = storeDao.selectByStoreName(storeUrl);
        User user = null;
        Map<String, Object> result = new HashMap<>();
        if (session == null){
            if(store != null){
                user = userServiceImpl.selectByPrimaryKey(store.getUserId());
            }else {
                String password = System.currentTimeMillis()+"";
                String loginName = shopDetail.getEmail();
                user = userServiceImpl.selectByLoginName(loginName);
                if (user != null){
                    loginName = password;
                }
                CustomerSignUpRequest request = new CustomerSignUpRequest();
                request.setEmail(shopDetail.getEmail());
                request.setUsername(shopDetail.getName());
                request.setCountry(shopDetail.getCountry());
                request.setMobile(shopDetail.getPhone());
                request.setLoginName(loginName);
                request.setPassword(password);
                request.setApplicationId(1L);
                user = userServiceImpl.userSignUp(request);
                redisTemplate.opsForHash().put(RedisKey.HASH_USER_NEED_RESET_PASSWTORD,user.getLoginName(),password);
            }
            result = userServiceImpl.userSignIn(user, 1L);
            session = (Session) redisTemplate.opsForValue().get(TokenUtil.getTokenKey((String) result.get("token")));
        }

        boolean b = false;
        Long storeId = null;
        Long orgId;
        if (null != store) {
            b = true;
            orgId = store.getOrgId();
            storeId = store.getId();
            storeAttrDao.deleteByStoreId(storeId);
        } else {
            orgId = IdGenerate.nextId();
            storeId = IdGenerate.nextId();
            store = new Store();
        }

        store.setId(storeId);
        store.setOrgId(orgId);
        store.setStoreName(storeUrl);
        store.setStoreUrl(shopDetail.getDomain());
        store.setUserId(session.getId());
        store.setCustomerId(session.getCustomerId());
        store.setStatus(1);
        store.setStoreType(StoreConfig.shopify_store);
        store.setCurrency(shopDetail.getCurrency());
        store.setTimezone(shopDetail.getTimezone());
        store.setUpdateTime(new Date());
        store.setApiToken(token);

        Organization organization = toOrganization(store);

        if (b) {
            organizationDao.updateByPrimaryKeySelective(organization);
            storeDao.updateByPrimaryKey(store);
        } else {
            String orgPath = session.getParentOrganization().getId() + "|" + orgId;
            organization.setOrgParent(session.getParentOrganization().getId());
            organization.setOrgPath(orgPath);
            store.setOrgPath(orgPath);
            organizationDao.insert(organization);
            storeDao.insert(store);
            saveDefaultRole(organization, session);
            saveStoreSetting(storeId);
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(),storeVo);
        getStoreData(store,null);
        return store;
    }

    @Override
    public void getStoreData(Store store, List<Store> stores){
        List<Message> messages = new ArrayList<>();

        if (ListUtils.isNotEmpty(stores)){
            for (Store store1 : stores) {
                if (store1.getStatus() == 1){
                    Message message = new Message(RocketMqConfig.TOPIC_GET_STORE_DATA,"store",IdGenerate.uuid(),JSONObject.toJSONBytes(store1));
                    messages.add(message);
                }
            }
        }
        Message message = null;
        if (store != null && store.getStatus() == 1){
             message = new Message(RocketMqConfig.TOPIC_GET_STORE_DATA,"store",IdGenerate.uuid(),JSONObject.toJSONBytes(store));
        }

        try {
            Integer i = sendMessage(message,messages);
            if (i == 1){
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (store != null){
            storeAsync.getStoreData(store);
        }
    }

    public Integer sendMessage(Message message,List<Message> messages)  {
        Integer sendStatus = 0;
        if (ListUtils.isEmpty(messages)){
            messages = new ArrayList<>();
        }
        if (message != null){
            messages.add(message);
        }
        if (messages.size() == 0){
            return sendStatus;
        }
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())){
            try {
                status =  defaultMQProducer.send(messages).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                i += 1;
            }
        }

        if(status.equals(SendStatus.SEND_OK.name())){
            sendStatus = 1;
        }
        List<MqMessageLog> mqMessageLogs = new ArrayList<>();
        for (Message message1 : messages) {
            MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message1,null);
            messageLog.setIsSendSuccess(sendStatus);
            mqMessageLogs.add(messageLog);
        }
        mqMessageLogService.insertByBatch(mqMessageLogs);
        return sendStatus;
    }


    @Transactional
    @Override
    public WoocommerceAuthResponse woocommerceAuth(WoocommerceAuthRequest request) {
        String storeUrl = request.getStoreUrl();

        AuthParam auth = new AuthParam();
        auth.setShopName(storeUrl);
        auth.setApiKey(request.getApiKey());
        auth.setApiSecret(request.getApiSecret());

        Session session = UserUtil.getSession(redisTemplate);

        Organization parent = new Organization();

        Store store = storeDao.selectByStoreName(storeUrl);

        boolean b = false;

        Long storeId = null;

        Long orgId = null;

        if (null != store) {
            if (!store.getCustomerId().equals(session.getCustomerId())) {
                return new WoocommerceAuthResponse(ResultCode.FAIL_CODE, "The store has been bound to other users");
            }
            b = true;
            orgId = store.getOrgId();
            storeId = store.getId();
            storeAttrDao.deleteByStoreId(storeId);
        } else {
            orgId = IdGenerate.nextId();
            storeId = IdGenerate.nextId();
            store = new Store();
        }

        store.setId(storeId);
        store.setOrgId(orgId);
        store.setStoreName(storeUrl);
        store.setStoreUrl(storeUrl);
        store.setUserId(session.getId());
        store.setCustomerId(session.getCustomerId());
        store.setStatus(1);

        try {
            JSONObject jsonObject = WoocommerceShopApi.shopSystemStatus(auth);
            store.setCurrency(jsonObject.getJSONObject("settings").getString("currency"));
            store.setTimezone(jsonObject.getJSONObject("environment").getString("default_timezone"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        store.setStoreType(StoreConfig.woocommerce_store);
        store.setUpdateTime(new Date());

        Organization organization = toOrganization(store);

        List<StoreAttr> attrs = new ArrayList<>();

        StoreAttr key = new StoreAttr();
        key.setAttrKey(WoocommerceAttr.api_key.toString());
        key.setAttrValue(request.getApiKey());
        key.setStoreId(storeId);
        attrs.add(key);

        StoreAttr secret = new StoreAttr();
        secret.setStoreId(storeId);
        secret.setAttrKey(WoocommerceAttr.api_secret.toString());
        secret.setAttrValue(request.getApiSecret());
        attrs.add(secret);

        storeAttrDao.insertByBatch(attrs);

        String accessToken = getStoreToken(store.getStoreType(), attrs);
        store.setApiToken(accessToken);

        if (b) {
            organizationDao.updateByPrimaryKeySelective(organization);
            /**
             * 店铺不可更改用户组织信息
             */
            storeDao.updateByPrimaryKey(store);
        } else {
            String orgPath = parent.getId() + "|" + orgId;
            organization.setOrgParent(parent.getId());
            organization.setOrgPath(orgPath);
            store.setOrgPath(orgPath);
            organizationDao.insert(organization);
            storeDao.insert(store);
            saveDefaultRole(organization, session);
            saveStoreSetting(storeId);
            OrganizationMenu organizationMenu = new OrganizationMenu();
            organizationMenu.setOrgId(organization.getId());
            organizationMenu.setMenuId(0L);
            organizationMenuDao.insert(organizationMenu);
        }

        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(),storeVo);
        getStoreData(store,null);
        return new WoocommerceAuthResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, store);
    }

    @Override
    public ShopifyAuthResponse shopifyAuthRequest(String storeUrl) {

        if (null == storeUrl) {
            return new ShopifyAuthResponse(ResultCode.FAIL_CODE, "wrong url");
        }
        storeUrl = verifyStoreAddress(storeUrl);
        if (StringUtils.isBlank(storeUrl)){
            return new ShopifyAuthResponse(ResultCode.FAIL_CODE, "wrong url");
        }
        storeUrl = storeUrl.replace(".myshopify.com","");
        Session session = UserUtil.getSession(redisTemplate);
        Store store = storeDao.selectByStoreName(storeUrl);
        if (null != store) {
            if (!session.getCustomerId().equals(store.getCustomerId())) {
                return new ShopifyAuthResponse(ResultCode.FAIL_CODE, "The store has been bound to other users");
            }
            if (1 == store.getStatus()) {
                return new ShopifyAuthResponse(ResultCode.FAIL_CODE, "Store cannot be re-authorized");
            }
        }
        String nonce = System.currentTimeMillis() + "";
        String url = getShopifyAuthUrl(storeUrl, nonce);
        redisTemplate.opsForValue().set(nonce, session);
        return new ShopifyAuthResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, url);
    }

    public static String verifyStoreAddress(String storeAddress) {
        if (StringUtils.isBlank(storeAddress)){
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

    @Transactional
    @Override
    public BaseResponse shoplazzaAuth(ShoplazzaAuthRequest request, Session session) {
        String storeUrl = request.getStoreUrl();
        String token = request.getAccessToken();
        Organization parent = new Organization();

        Store store = storeDao.selectByStoreName(storeUrl);

        boolean b = false;

        Long storeId = null;

        Long orgId = null;

        if (null != store) {
            if (!store.getCustomerId().equals(session.getCustomerId())) {
                return new WoocommerceAuthResponse(ResultCode.FAIL_CODE, "The store has been bound to other users");
            }
            b = true;
            orgId = store.getOrgId();
            storeId = store.getId();
            storeAttrDao.deleteByStoreId(storeId);
        } else {
            orgId = IdGenerate.nextId();
            storeId = IdGenerate.nextId();
            store = new Store();
        }

        store.setId(storeId);
        store.setOrgId(orgId);
        store.setStoreName(storeUrl);
        store.setStoreUrl(storeUrl);
        store.setUserId(session.getId());
        store.setCustomerId(session.getCustomerId());
        store.setStatus(1);

        JSONObject shopDetail = ShoplazzaShopApi.shoplazzaShopDetail(storeUrl, token);
        if (shopDetail != null) {
            ShoplazzaShop shoplazzaShop = shopDetail.toJavaObject(ShoplazzaShop.class);
            store.setTimezone(shoplazzaShop.getTimezone());
            store.setCurrency(shoplazzaShop.getCurrency());
        }

        store.setStoreType(StoreConfig.shoplazza_store);
        store.setUpdateTime(new Date());

        Organization organization = toOrganization(store);

        store.setApiToken(request.getAccessToken());

        if (b) {
            organizationDao.updateByPrimaryKeySelective(organization);
            /**
             * 店铺不可更改用户组织信息
             */
            storeDao.updateByPrimaryKey(store);
        } else {
            String orgPath = parent.getId() + "|" + orgId;
            organization.setOrgParent(parent.getId());
            organization.setOrgPath(orgPath);
            store.setOrgPath(orgPath);
            organizationDao.insert(organization);
            storeDao.insert(store);
//            saveDefaultRole(organization, session);
            saveStoreSetting(storeId);
            OrganizationMenu organizationMenu = new OrganizationMenu();
            organizationMenu.setOrgId(organization.getId());
            organizationMenu.setMenuId(0L);
            organizationMenuDao.insert(organizationMenu);
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(),storeVo);
        getStoreData(store,null);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, store);
    }

    /**
     *
     */
    @Override
    public Store selectByPrimaryKey(Store record) {
        return storeDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(Store record) {

        int i = storeDao.updateByPrimaryKeySelective(record);

        if (StringUtils.isNotBlank(record.getCurrency())) {
            storeDao.updateUsdRateById(record.getId());
        }

        record = storeDao.selectByPrimaryKey(record);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(record,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + record.getId(),storeVo);

        return i;
    }

    /**
     *
     */
    @Transactional
    @Override
    public int updateByPrimaryKey(Store record) {
        int i = storeDao.updateByPrimaryKey(record);
        if (StringUtils.isNotBlank(record.getCurrency())) {
            storeDao.updateUsdRateById(record.getId());
        }

        record = storeDao.selectByPrimaryKey(record);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(record,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + record.getId(),storeVo);
        return i;
    }

    /**
     *
     */
    @Override
    public List<Store> select(Page<Store> record) {

        record.initFromNum();
        return storeDao.select(record);
    }

    /**
     *
     */
    @Override
    public long count(Page<Store> record) {
        return storeDao.count(record);
    }


    public Organization toOrganization(Store store) {
        Organization organization = new Organization();
        organization.setCustomerId(store.getCustomerId());
        organization.setOrgName(store.getStoreName());
        organization.setId(store.getOrgId());
        organization.setCreateTime(store.getUpdateTime());
        organization.setUpdateTime(store.getUpdateTime());
        return organization;
    }

    public void saveStoreSetting(Long storeId) {



        List<StoreSetting> storeSettings = new ArrayList<>();
        for (StoreSettingEnum storeSettingEnum : StoreSettingEnum.values()) {
            StoreSetting storeSetting = new StoreSetting();
            storeSetting.setStoreId(storeId);
            storeSetting.setSettingName(storeSettingEnum.name());
            storeSetting.setSettingValue(storeSettingEnum.getValue());
            storeSettings.add(storeSetting);
        }
        if (0 < storeSettings.size()) {
            storeSettingDao.insertByBatch(storeSettings);
        }
    }

    public static String getShopifyAuthUrl(String shop, String nonce) {
        StringBuffer url = new StringBuffer();
        StringBuffer s = url.append("https://").append(shop).append(".myshopify.com/admin/oauth/authorize?client_id=")
                .append(ShopifyConfig.api_key).append("&scope=")
                .append(ShopifyConfig.scope).append("&redirect_uri=")
                .append(ShopifyConfig.redirect_url)
                .append("&state=").append(nonce);
        return s.toString();
    }

    public void saveDefaultRole(Organization organization, Session session) {
        Role role = organization.createDefaultRole(session.getApplicationId(), session.getCustomerId());
        roleDao.insert(role);

        OrganizationRole organizationRole = new OrganizationRole();
        organizationRole.setDataType(0);
        organizationRole.setRoleId(role.getId());
        organizationRole.setOrgId(organization.getId());
        organizationRoleDao.insert(organizationRole);

        OrganizationUser organizationUser = new OrganizationUser();
        organizationUser.setUserId(session.getId());
        organizationUser.setOrgId(organization.getId());
        organizationUserDao.insert(organizationUser);
    }

    public String getStoreToken(Integer storeType, List<StoreAttr> attrs) {

        if (StoreConfig.shopify_store.equals(storeType)) {
            for (StoreAttr a : attrs) {
                if (ShopifyAttr.access_token.toString().equals(a.getAttrKey())) {
                    String base64UserMsg = Base64.getEncoder().encodeToString((ShopifyConfig.api_key + ":" + a.getAttrValue()).getBytes());
                    return base64UserMsg;
                }
            }
        } else {
            String authmsg = null;
            for (StoreAttr a : attrs) {
                if (WoocommerceAttr.api_key.toString().equals(a.getAttrKey())) {
                    if (null != authmsg) {
                        authmsg = a.getAttrValue() + authmsg;
                    } else {
                        authmsg = a.getAttrValue();
                    }
                }
                if (WoocommerceAttr.api_secret.toString().equals(a.getAttrKey())) {
                    authmsg = authmsg + ":" + a.getAttrValue();
                }
            }
            String base64UserMsg = Base64.getEncoder().encodeToString(authmsg.getBytes());
            return base64UserMsg;
        }
        return null;
    }


    @Override
    public StoreVo queryStoreSetting(Long storeId) {
        List<StoreSetting> storeSettingList = storeSettingDao.selectByStoreId(storeId);
        StoreVo storeVo = new StoreVo();
        for (StoreSetting storeSetting : storeSettingList) {
            if (storeSetting.getSettingName().equals("tracking_url")
                    && !StringUtils.isBlank(storeSetting.getSettingValue())) {
                storeVo.setTrackingUrl(storeSetting.getSettingValue());
            }
            if (storeSetting.getSettingName().equals("email_prompt")
                    && storeSetting.getSettingValue().equals("1")) {
                storeVo.setEmailPrompt(true);
            }
        }
        return storeVo;
    }

    /**
     * 查询单个店铺设置
     * @return
     */
    @Override
    public BaseResponse oneStoreSettingList(Long storeId) {

        List<StoreSetting> resultList = storeSettingDao.selectByStoreId(storeId);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, resultList);
        }

    @Override
    public int updateStoreByPrimaryKey(Store record) {
        int i = storeDao.updateStoreByPrimaryKey(record);
        if (StringUtils.isNotBlank(record.getCurrency())) {
            storeDao.updateUsdRateById(record.getId());
        }
        record = storeDao.selectByPrimaryKey(record);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(record,storeVo);
        redisTemplate.opsForValue().set(RedisKey.STRING_STORE + record.getId(),storeVo);
        return i;
    }

    @Override
    public BaseResponse storeSettingListUpdate(List<StoreSetting> list) {
        for (StoreSetting storeSetting : list) {
            storeSettingDao.updateStoreSettingValueByName(storeSetting);
        }
        return  BaseResponse.success();
    }

    @Override
    public List<StoreNameAndUserVo> selectStoreNameByGroupuserId() {
        return storeDao.selectStoreNameByGroupuserId();
    }





}