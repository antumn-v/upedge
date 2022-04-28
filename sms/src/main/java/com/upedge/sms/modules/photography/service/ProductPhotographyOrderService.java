package com.upedge.sms.modules.photography.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderCreateRequest;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderPayRequest;
import com.upedge.sms.modules.photography.vo.ProductPhotographyOrderVo;

import java.util.List;

/**
 * @author gx
 */
public interface ProductPhotographyOrderService{

    BaseResponse pay(ProductPhotographyOrderPayRequest request,Session session);

    ProductPhotographyOrderVo orderDetail(Long id);

    BaseResponse create(ProductPhotographyOrderCreateRequest request, Session session);

    ProductPhotographyOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductPhotographyOrder record);

    int updateByPrimaryKeySelective(ProductPhotographyOrder record);

    int insert(ProductPhotographyOrder record);

    int insertSelective(ProductPhotographyOrder record);

    List<ProductPhotographyOrder> select(Page<ProductPhotographyOrder> record);

    long count(Page<ProductPhotographyOrder> record);

    void saveTransactionRecord(Long userId, Long orderId);
}

