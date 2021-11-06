package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class WholesaleOrderItemServiceImpl implements WholesaleOrderItemService {

    @Autowired
    private WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    private WholesaleOrderService wholesaleOrderService;

    @Autowired
    private OrderShippingUnitService orderShippingUnitService;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrderItem record = new WholesaleOrderItem();
        record.setId(id);
        return wholesaleOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    @Override
    public List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId) {
        return wholesaleOrderItemDao.selectDischargeQuantityByPaymentId(paymentId);
    }

    /**
     *
     */
    public WholesaleOrderItem selectByPrimaryKey(Long id) {
        return wholesaleOrderItemDao.selectByPrimaryKey(id);
    }

    @Override
    public void updateItemByVariantId(VariantDetail variantDetail, String tag) {
        String name = null;
        BigDecimal value = null;
        List<WholesaleOrderItem> list = null;
        switch (tag) {
            case "price":
                name = "cny_price";
                value = variantDetail.getPrice();
                break;
            case "weight":
                name = "admin_variant_weight";
                value = variantDetail.getWeight();
                break;
            case "volume":
                name = "admin_variant_volume";
                value = variantDetail.getVolume();
                break;
            case "shippingId":
                if (null != variantDetail.getProductId() && null != variantDetail.getProductShippingId()) {
                    // 修改 订单item信息
                    wholesaleOrderItemDao.updateShippingIdByAdminProductId(variantDetail.getProductShippingId(), variantDetail.getProductId());
                    // 删除运输规则
                    orderShippingUnitService.delByProductId(variantDetail.getProductId(), OrderType.WHOLESALE);
                    list = wholesaleOrderItemDao.selectOrderItemListByProduct(variantDetail.getProductId());
                    // 重新匹配运输规则
                    wholesaleOrderService.matchingShipInfoByProductId(list);
                    return;
                }
                break;
            default:
                return;
        }
        if (null == value) {
            return;
        }
        wholesaleOrderItemDao.updateAdminVariantDetailByVariantId(name, value, variantDetail.getVariantId());
        if (tag.equals("price")) {
            wholesaleOrderItemDao.updateAdminVariantDetailByVariantId("usd_price", variantDetail.getUsdPrice(), variantDetail.getVariantId());
        } else {
            orderShippingUnitService.delByVariantId(variantDetail.getVariantId(), OrderType.WHOLESALE);
            list = wholesaleOrderItemDao.selectOrderItemListByVariantId(variantDetail.getVariantId());
            // 重新匹配运输规则
            wholesaleOrderService.matchingShipInfoByVariantId(list);
        }

    }


    @Override
    public boolean orderItemUpdateQuantity(Long itemId, Integer quantity) {
        WholesaleOrderItem item = wholesaleOrderItemDao.selectByPrimaryKey(itemId);
        if (null == item) {
            return false;
        }
        if (quantity.equals(item.getQuantity())) {
            return true;
        }
        if (quantity == 0) {
            Integer totalQuantity = wholesaleOrderItemDao.selectCountQuantityByOrderId(item.getOrderId());
            if (totalQuantity > item.getQuantity()) {
                return wholesaleOrderItemDao.updateQuantityById(itemId, quantity);
            }
        } else if (quantity > 0) {
            return wholesaleOrderItemDao.updateQuantityById(itemId, quantity);
        }
        return false;
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record) {
        record.initFromNum();
        return wholesaleOrderItemDao.select(record);
    }

    /**
     *
     */
    public long count(Page<WholesaleOrderItem> record) {
        return wholesaleOrderItemDao.count(record);
    }

}