package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleOrderItemService{

    List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId);

    WholesaleOrderItem selectByPrimaryKey(Long id);

    void updateItemByVariantId(VariantDetail variantDetail, String tag);

    boolean orderItemUpdateQuantity(Long itemId, Integer quantity);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKeySelective(WholesaleOrderItem record);

    int insert(WholesaleOrderItem record);

    int insertSelective(WholesaleOrderItem record);

    List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record);

    long count(Page<WholesaleOrderItem> record);
}

