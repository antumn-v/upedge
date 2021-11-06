package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHandleDataVo {

    /**
     * 普通订单未导入赛盒
     */
    private long normalOrderUnImportSaihe = 0;
    /**
     * 普通订单未发货数
     */
    private long normalOrderNotShipped;
    /**
     * 普通订单未导入产品
     */
    private long normalOrderProductUnImportSaihe = 0;
    /**
     * 普通订单补发待审核
     */
    private long normalReshipOrderPendingReview = 0;
    /**
     * 普通订单退款
     */
    private long normalOrderRefundReview = 0;
    /**
     * 批发订单退款
     */
    private long wholesaleOrderRefundReview = 0;
    /**
     * 批发订单未导入赛盒
     */
    private long wholesaleOrderUnImportSaihe = 0;
    /**
     * 批发订单补发待审核
     */
    private long wholesaleReshipOrderPendingReview = 0;
    /**
     * 批发订单未发货数量
     */
    private long wholesaleOrderNotShipped;
    /**
     * 备库订单退款待处理
     */
    private long stockOrderRefundReview = 0;
    /**
     * 备库订单待处理
     */
    private long stockOrderPendingReview = 0;
    /**
     * supportTicket未读消息数
     */
    private long unReadTicketMessageCount;
    /**
     * supportTicket待处理
     */
    private long ticketPendingReview;
    /**
     * 普通订单未导入产品
     */
    private List<NormalHandleProductVo> managerUnImportProductsVos = new ArrayList<>();
}
