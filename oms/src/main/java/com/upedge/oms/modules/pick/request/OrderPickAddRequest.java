package com.upedge.oms.modules.pick.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pick.entity.OrderPick;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrderPickAddRequest{

    /**
    * 
    */
    private Integer pickType;
    /**
    * 
    */
    private Long shipMethodId;
    /**
    * -1=作废，0=待拣货，1=待包装，2=包装中，3=已发货
    */
    private Integer pickState;
    /**
    * 
    */
    private Integer skuQuantity;
    /**
    * 
    */
    private Integer skuType;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private Long operatorId;

    public OrderPick toOrderPick(){
        OrderPick orderPick=new OrderPick();
        orderPick.setPickType(pickType);
        orderPick.setShipMethodId(shipMethodId);
        orderPick.setPickState(pickState);
        orderPick.setSkuQuantity(skuQuantity);
        orderPick.setSkuType(skuType);
        orderPick.setCreateTime(createTime);
        orderPick.setUpdateTime(updateTime);
        orderPick.setOperatorId(operatorId);
        return orderPick;
    }

}
