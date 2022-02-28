package com.upedge.oms.modules.cart.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.cart.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface CartDao{

    /**
     * 根据ID和类型查找购物车数据
     * @param ids
     * @param cartType 备库=0，批发=1
     * @return
     */
    List<Cart> selectByIdsAndType(@Param("ids") List<Long> ids,
                                  @Param("cartType") Integer cartType,
                                  @Param("customerId") Long customerId);

    Cart selectByMarkId(@Param("customerId") Long customerId,
                        @Param("markId") Long markId);

    /**
     * 修改购物车状态
     * @param ids
     * @param state 正常0，被删除2，创建订单1
     * @return
     */
    int updateStateByIds(@Param("ids") List<Long> ids,
                         @Param("state") Integer state);

    //------------------mq修改购物车价格，体重，体积----------------------------------
    int updatePriceByVariantId(@Param("variantId") Long variantId, @Param("price") BigDecimal price);

    int updateWeightByVariantId(@Param("variantId") Long variantId, @Param("weight") BigDecimal weight);

    int updateVolumeByVariantId(@Param("variantId") Long variantId, @Param("volume") BigDecimal volume);
    //------------------------------------------------------------------------------
    Cart selectCart(Cart cart);

    int updateQuantity(Cart cart);

    List<Long> selectVariantIdByCartType(@Param("customerId") Long customerId,
                                         @Param("cartType") Integer cartType);

    Cart selectByPrimaryKey(Cart record);

    int deleteByPrimaryKey(Cart record);

    int updateByPrimaryKey(Cart record);

    int updateByPrimaryKeySelective(Cart record);

    int insert(Cart record);

    int insertSelective(Cart record);

    int insertByBatch(List<Cart> list);

    List<Cart> select(Page<Cart> record);

    long count(Page<Cart> record);

}
