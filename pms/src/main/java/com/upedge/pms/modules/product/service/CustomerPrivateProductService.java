package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.request.AllocationPrivateProductRequest;
import com.upedge.pms.modules.product.request.PrivateWinningProductsRequest;
import com.upedge.pms.modules.product.vo.AppProductVo;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerPrivateProductService{

    List<AppProductVo> selectPrivateWinningProducts(PrivateWinningProductsRequest request);

    Long countPrivateWinningProducts(PrivateWinningProductsRequest request);

    BaseResponse allocationPrivateProduct(AllocationPrivateProductRequest request, Session session);

    int countByProductId(Long productId);

    CustomerPrivateProduct selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKeySelective(CustomerPrivateProduct record);

    int insert(CustomerPrivateProduct record);

    int insertSelective(CustomerPrivateProduct record);

    List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record);

    long count(Page<CustomerPrivateProduct> record);
}

