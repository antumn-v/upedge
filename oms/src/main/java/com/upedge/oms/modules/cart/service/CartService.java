package com.upedge.oms.modules.cart.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.cart.entity.Cart;
import com.upedge.oms.modules.cart.request.CartAddStockRequest;
import com.upedge.oms.modules.cart.request.CartSubmitOrderRequest;
import com.upedge.oms.modules.cart.request.CartSubmitWholesaleRequest;
import com.upedge.oms.modules.cart.request.DelCarts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface CartService{

    BaseResponse addStockCart(CartAddStockRequest request,Session session);

    Long cartSubmitStockOrder(CartSubmitOrderRequest request, Session session);

    boolean cartSubmitWholesaleOrder(CartSubmitWholesaleRequest request);

    /**
     * 根据购物车类型和ID查询购物车列表
     * @param ids
     * @param cartType 备库=0，批发=1
     * @return
     */
    List<Cart> selectByIdsAndType(@Param("ids") List<Long> ids,
                                  @Param("cartType") Integer cartType);

    void updateCartByVariant(VariantDetail variantDetail, String tag);

    void addCarts(CartAddRequest request);

    void updatePriceByVariantId(VariantDetail variantDetail);

    Cart selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Cart record);

    int updateByPrimaryKeySelective(Cart record);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> select(Page<Cart> record);

    long count(Page<Cart> record);

    void delIds(DelCarts delCarts);
}

