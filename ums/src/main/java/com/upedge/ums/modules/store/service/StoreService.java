package com.upedge.ums.modules.store.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.old.ums.StoreNameAndUserVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.ums.modules.store.request.ShoplazzaAuthRequest;
import com.upedge.ums.modules.store.request.WoocommerceAuthRequest;
import com.upedge.ums.modules.store.response.ShopifyAuthResponse;
import com.upedge.ums.modules.store.response.WoocommerceAuthResponse;

import java.util.List;

/**
 * @author author
 */
public interface StoreService{

    List<Store> selectStoreByCustomer(Long customerId);

    List<Store> selectByCustomerOrgIds(Long customerId, List<Long> orgIds);

    List<String> selectAllStoreCurrency();

    BaseResponse storeSettingList();

    BaseResponse storeSettingUpdate(StoreSetting storeSetting);

    void updateUsdRate();

    Store updateShopifyStore(String storeUrl, String token, Session session);

    void getStoreData(Store store, List<Store> stores);

    WoocommerceAuthResponse woocommerceAuth(WoocommerceAuthRequest request);

    ShopifyAuthResponse shopifyAuthRequest(String storeUrl);

    BaseResponse shoplazzaAuth(ShoplazzaAuthRequest request, Session session);

    Store selectByPrimaryKey(Store store);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Store record);

    int updateByPrimaryKeySelective(Store record);

    int insert(Store record);

    int insertSelective(Store record);

    List<Store> select(Page<Store> record);

    long count(Page<Store> record);

    StoreVo queryStoreSetting(Long storeId);

    BaseResponse oneStoreSettingList(Long storeId);

    int updateStoreByPrimaryKey(Store request);

    BaseResponse storeSettingListUpdate(List<StoreSetting> list);

    List<StoreNameAndUserVo> selectStoreNameByGroupuserId();

}

