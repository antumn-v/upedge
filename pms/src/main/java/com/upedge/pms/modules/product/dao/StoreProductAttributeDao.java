package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.CustomerProductVo;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductAttributeDao{

    List<StoreProductAttribute> selectStoreProduct(StoreProductListRequest request);

    Long countStoreProduct(StoreProductListRequest request);

    StoreProductAttribute selectStoreProductByPlatId(@Param("storeId") Long storeId,
                                                     @Param("platProductId") String platProductId);

    int updateTransformStateById(@Param("id") Long id,
                                 @Param("transformState") Integer transformState);

    StoreProductAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(StoreProductAttribute record);

    int updateByPrimaryKey(StoreProductAttribute record);

    int updateByPrimaryKeySelective(StoreProductAttribute record);

    int insert(StoreProductAttribute record);

    int insertSelective(StoreProductAttribute record);

    int insertByBatch(List<StoreProductAttribute> list);

    List<StoreProductAttribute> select(Page<StoreProductAttribute> record);

    long count(Page<StoreProductAttribute> record);

    int updatePushState(@Param("id") Long id, @Param("pushState") int pushState);

    List<CustomerProductVo> selectOrderBySale(Page<StoreProductAttribute> record);
    long countOrderBySale(Page<StoreProductAttribute> record);
}
