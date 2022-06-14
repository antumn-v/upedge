package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.request.PrivateWinningProductsRequest;
import com.upedge.pms.modules.product.vo.AppProductVo;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerPrivateProductDao{

    List<Long> selectCustomerIdsByProductId(Long productId);

    List<AppProductVo> selectPrivateWinningProducts(PrivateWinningProductsRequest request);

    Long countPrivateWinningProducts(PrivateWinningProductsRequest request);

    int countByProductId(Long productId);

    CustomerPrivateProduct selectByPrimaryKey(CustomerPrivateProduct record);

    int deleteByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKeySelective(CustomerPrivateProduct record);

    int insert(CustomerPrivateProduct record);

    int insertSelective(CustomerPrivateProduct record);

    int insertByBatch(List<CustomerPrivateProduct> list);

    List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record);

    long count(Page<CustomerPrivateProduct> record);

    void deleteByProductId(Long productId);

}
