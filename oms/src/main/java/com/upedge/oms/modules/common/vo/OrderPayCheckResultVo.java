package com.upedge.oms.modules.common.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单支付方法中  orderPayCheck 的结果Vo  包含 状态message和 GA需要的信息
 */
@Data
public class OrderPayCheckResultVo {

    /**
     * payMessage
     */
    private String payMessage;

    /**
     * 交易数据
     */
    private  TradingDataVo tradingDataVo = new TradingDataVo();




    /**
     *交易数据
     */
    @Data
    public static class TradingDataVo{
        /**
         * 唯一交易标识符  这里使用paymentId   必填
         */
        private String transactionId;

        /**
         * 合作伙伴或联营店铺     非必填
         */
        private String transactionAffiliation;

        /**
         * transactionTotal  交易总价值   必填
         */
        private BigDecimal transactionTotal = BigDecimal.ZERO;

        /**
         * transactionShipping    交易的运费 非必填
         */
        private BigDecimal transactionShipping = BigDecimal.ZERO;

        /**
         * transactionTax 交易的税额 非必填
         */
        private BigDecimal transactionTax = null;

        /**
         * transactionProducts 交易中所购买的商品列表  产品对象数组 非必填
         */
        private List<TransactionProduct> transactionProducts = new ArrayList<TransactionProduct>();

    }
    /**
     *交易中所购买的商品列表
     */
    @Data
    @NoArgsConstructor
    public static class TransactionProduct{

        /**
         * 产品名称   name   必填
         */
        private String name;

        /**
         * 产品sku sku  字符串 必填
         */
        private String sku;

        /**
         * 产品类别 category 非必填
         */
        private String category;

        /**
         * 单价  price 必填
         */
        private BigDecimal price = BigDecimal.ZERO;

        /**
         * 商品数量 quantity 必填
         */
        private Integer quantity;
    }

}
