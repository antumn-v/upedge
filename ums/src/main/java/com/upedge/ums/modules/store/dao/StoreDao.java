package com.upedge.ums.modules.store.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.old.ums.StoreNameAndUserVo;
import com.upedge.ums.modules.statistics.dto.CustomerCharts;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.user.vo.CustomerStoreListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface StoreDao{

    List<Store> selectByCustomerOrgIds(@Param("customerId") Long customerId,
                                       @Param("orgIds") List<Long> orgIds);

    List<String> selectAllStoreCurrency();

    List<Store> selectStoreByCustomer(Long customerId);

    Store selectByStoreName(String storeName);

    Store selectByPrimaryKey(Store record);

    void updateUsdRateById(Long id);

    void updateUsdRate();

    int deleteByPrimaryKey(Store record);

    int updateByPrimaryKey(Store record);

    int updateByPrimaryKeySelective(Store record);

    int insert(Store record);

    int insertSelective(Store record);

    int insertByBatch(List<Store> list);

    List<Store> select(Page<Store> record);

    long count(Page<Store> record);

    //List<Store> customerStoreList(Long customerId);
    List<CustomerStoreListVo> customerStoreList(Long customerId);

    Integer authorizationUserNum(@Param("userManager") String userManager);

    List<CustomerCharts> registerStoreCount(@Param("startDay") String startDay,
                                            @Param("endDay") String endDay);

    int updateStoreByPrimaryKey(Store record);

    List<StoreNameAndUserVo> selectStoreNameByGroupuserId();

}
