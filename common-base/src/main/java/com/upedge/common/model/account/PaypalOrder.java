package com.upedge.common.model.account;

import com.upedge.common.model.user.vo.Session;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class PaypalOrder extends AccountPayAmount {

    /**
     * 交易ID
     */
    private Long id;


    private Session session;

    private Integer orderType;

    private String successUrl;

    private String failedUrl;

    private BigDecimal productAmount = BigDecimal.ZERO;

    private BigDecimal shipPrice = BigDecimal.ZERO;

    private BigDecimal fixFee = BigDecimal.ZERO;

    private BigDecimal dischargeAmount = BigDecimal.ZERO;

    private String storeName;

    private List<PaypalOrderItem> items;

    @Data
    public static class PaypalOrderItem{

        private Long orderId;

        private Long itemId;

        private Integer quantity;

        private BigDecimal price;

        private String sku;

        private String name;
    }

    public PaypalOrder() {
    }
}
