package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.order.vo.OrderProductAmountVo;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderItemVo;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author author
 */
public interface WholesaleOrderItemDao{



    List<ItemDischargeQuantityVo> selectDischargeQuantityByOrderId(Long orderId);

    List<OrderProductAmountVo> selectOrderItemAmountByPaymentId(Long paymentId);

    void updateAdminVariantDetailByVariantId(@Param("name") String name,
                                             @Param("value") BigDecimal value,
                                             @Param("adminVariantId") Long adminVariantId);

    int updateShippingIdByAdminProductId(@Param("shippingId") Long shippingId,
                                         @Param("adminProductId") Long adminProductId);

    List<WholesaleOrderItem> selectByOrderPaymentId(Long paymentId);

    List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId);

    Integer selectCountQuantityByOrderId(Long orderId);

    List<VariantDetail> selectAdminVariantDetailByOrder(Long orderId);

    boolean updateQuantityById(Long id, Integer quantity);

    int updateDischargeQuantityByMap(@Param("map") Map<Long, Integer> map);

    WholesaleOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKeySelective(WholesaleOrderItem record);

    int insert(WholesaleOrderItem record);

    int insertSelective(WholesaleOrderItem record);

    int insertByBatch(List<WholesaleOrderItem> list);

    List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record);

    long count(Page<WholesaleOrderItem> record);

    List<WholesaleOrderItemVo> listWholesaleOrderItem(Long orderId);

    WholesaleOrderItem queryWholesaleOrderItemByIdAndOrderId(@Param("id") Long id, @Param("orderId") Long orderId);

    List<SaiheOrderItem> listSaiheOrderItemByOrderId(Long orderId);

    List<WholesaleOrderItem> selectOrderItemListByProduct(@Param("productId") Long productId);

    List<WholesaleOrderItem> selectOrderItemListByVariantId(@Param("variantId") Long variantId);
}
