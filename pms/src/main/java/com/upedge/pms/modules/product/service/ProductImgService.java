package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.request.ProductUploadImageRequest;

import java.util.List;

/**
 * @author gx
 */
public interface ProductImgService{

    List<ProductImg> selectByProductId(Long productId);

    ProductImg selectByPrimaryKey(Long id);

    BaseResponse uploadImage(ProductUploadImageRequest request);

    int insertByBatch(List<ProductImg> productImgs);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductImg record);

    int updateByPrimaryKeySelective(ProductImg record);

    int insert(ProductImg record);

    int insertSelective(ProductImg record);

    List<ProductImg> select(Page<ProductImg> record);

    long count(Page<ProductImg> record);
}

