package com.upedge.oms.modules.order.entity;

import com.upedge.common.model.old.oms.AppOrder;
import com.upedge.common.model.store.StoreType;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.utils.DateUtils;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrder;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
@Data
public class StoreOrder {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String platOrderId;
    /**
     *
     */
    private String platOrderName;
    /**
     *
     */
    private Long storeId;

    private Long orgId;

    private String orgPath;

    private String storeName;
    /**
     *
     */
    private Long storeAddressId;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private String currencyCode;
    /**
     *
     */
    private BigDecimal currencyRate;
    /**
     *
     */
    private BigDecimal totalPrice;
    /**
     *
     */
    private BigDecimal totalLineItemsPrice;
    /**
     *
     */
    private BigDecimal freight;
    /**
     *
     */
    private BigDecimal totalWeight;
    /**
     * 0=已支付 1=部分退款 2=全部退款
     */
    private Integer financialStatus;
    /**
     * 0=未发货 1=部分发货 2=全部发货
     */
    private Integer fulfillmentStatus;
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
    private Date importTime;

    private List<StoreOrderItem> items;

    private StoreOrderAddress address;

    public StoreOrder(ShopifyOrder shopifyOrder) {
        this.currencyCode = shopifyOrder.getCurrency();
        this.totalLineItemsPrice = shopifyOrder.getTotal_line_items_price();
        this.totalPrice = shopifyOrder.getTotal_price();
        if (shopifyOrder.getTotal_shipping_price_set() != null) {
            if (shopifyOrder.getTotal_shipping_price_set().getShop_money() != null) {
                this.freight = shopifyOrder.getTotal_shipping_price_set().getShop_money().getAmount();
            }
        }

        this.createTime = DateUtils.parseDate(shopifyOrder.getCreated_at());
        this.updateTime = DateUtils.parseDate(shopifyOrder.getUpdated_at());
        this.platOrderId = shopifyOrder.getId();
        this.platOrderName = shopifyOrder.getName();
        this.totalWeight = shopifyOrder.getTotal_weight();
        switch (shopifyOrder.getFinancial_status()) {
            case "paid":
                this.financialStatus = 0;
                break;
            case "partially_refunded":
                this.financialStatus = 1;
                break;
            case "refunded":
                this.financialStatus = 2;
                break;
            default:
                this.financialStatus = 0;
                break;
        }
        switch (shopifyOrder.getFinancial_status()) {
            case "fulfilled":
                this.fulfillmentStatus = 2;
                break;
            case "partial":
                this.fulfillmentStatus = 1;
                break;
            default:
                this.fulfillmentStatus = 0;
                break;
        }
    }

    public StoreOrder(ShoplazzaOrder shoplazzaOrder) {
        this.currencyCode = shoplazzaOrder.getCurrency();
        this.totalPrice = shoplazzaOrder.getTotal_price();
        this.freight = shoplazzaOrder.getTotal_shipping();
        this.createTime = DateUtils.parseDate(shoplazzaOrder.getCreated_at());
        this.updateTime = DateUtils.parseDate(shoplazzaOrder.getUpdated_at());
        this.platOrderId = shoplazzaOrder.getId();
        this.platOrderName = shoplazzaOrder.getNumber();
        this.totalWeight = BigDecimal.ZERO;
        switch (shoplazzaOrder.getFinancial_status()) {
            case "paid":
                this.financialStatus = 0;
                break;
            case "partially_refunded":
                this.financialStatus = 1;
                break;
            case "refunded":
                this.financialStatus = 2;
                break;
            default:
                this.financialStatus = 0;
                break;
        }
        switch (shoplazzaOrder.getFulfillment_status()) {
            case "fulfilled":
                this.fulfillmentStatus = 2;
                break;
            case "partial":
                this.fulfillmentStatus = 1;
                break;
            default:
                this.fulfillmentStatus = 0;
                break;
        }
    }

    public StoreOrder(WoocommerceOrder woocommerceOrder) {
        this.currencyCode = woocommerceOrder.getCurrency();
        this.totalPrice = woocommerceOrder.getTotal();
        this.freight = woocommerceOrder.getShipping_total();
        this.createTime = woocommerceOrder.getDate_created();
        this.updateTime = woocommerceOrder.getDate_modified();
        this.platOrderId = woocommerceOrder.getId();
        this.platOrderName = "#" + woocommerceOrder.getId();
        switch (woocommerceOrder.getStatus()) {
            case "processing":
                this.financialStatus = 0;
                this.fulfillmentStatus = 0;
                break;
            case "refunded":
                this.financialStatus = 2;
                break;
            case "completed":
                this.fulfillmentStatus = 1;
                break;
            default:
                this.fulfillmentStatus = 0;
                break;
        }
        if (null == this.financialStatus) {
            this.financialStatus = 0;
        }
        if (null == this.fulfillmentStatus) {
            this.fulfillmentStatus = 0;
        }
    }

    public StoreOrder(AppOrder appOrder, StoreVo storeVo) {
        this.id = appOrder.getId();
        this.currencyCode = appOrder.getCurrency();
        this.totalLineItemsPrice = appOrder.getTotalLineItemsPrice();
        if (appOrder.getTotalPrice() != null) {
            this.totalPrice = new BigDecimal(appOrder.getTotalPrice());
        }
        if (appOrder.getFreight() != null) {
            this.freight = new BigDecimal(appOrder.getFreight());
        }
        this.createTime = appOrder.getCreatedAt();
        this.updateTime = appOrder.getUpdatedAt();
        this.platOrderName = appOrder.getName();
        this.totalWeight = appOrder.getTotalWeight();
        this.customerId = appOrder.getUserId();
        this.currencyRate = appOrder.getCurrencyRate();
		if (storeVo != null){
		    this.orgId = storeVo.getOrgId();
		    this.orgPath = storeVo.getOrgPath();
		    this.storeId = storeVo.getId();
		    this.storeName = storeVo.getStoreName();
            if (storeVo.getStoreType() == StoreType.WOOCOMMERCE){
                this.platOrderId = appOrder.getName().replace("#","");
            }else {
                this.platOrderId = appOrder.getId().toString();
            }
        }

		if(null != appOrder.getShopifyStatus()) {
            switch (appOrder.getShopifyStatus()){
                case "paid":
                    this.financialStatus = 0;
                    break;
                case "partially_refunded":
                    this.financialStatus = 1;
                    break;
                case "refunded":
                    this.financialStatus = 2;
                    break;
                case "processing":
                    this.financialStatus = 0;
                    break;
                case "completed":
                    this.fulfillmentStatus = 1;
                    break;
                default:
                    this.financialStatus = 0;
                    break;
            }
        }
		if (null != appOrder.getFulfillmentStatus()) {
            switch (appOrder.getFulfillmentStatus()){
                case "fulfilled":
                    this.fulfillmentStatus = 2;
                    break;
                case "partial":
                    this.fulfillmentStatus = 1;
                    break;
                case "completed":
                    this.fulfillmentStatus = 1;
                    break;
                default:
                    this.fulfillmentStatus = 0;
                    break;
            }
        }
		if (null == this.financialStatus) {
			this.financialStatus = 0;
		}
		if (null == this.fulfillmentStatus) {
			this.fulfillmentStatus = 0;
		}
		this.importTime = appOrder.getCreatedAt();

    }

    public StoreOrder() {
    }
}
