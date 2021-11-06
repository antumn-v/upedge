package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockAdviceSetting;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockAdviceSettingUpdateRequest{

    /**
     * 5
     */
    private BigDecimal factorA;
    /**
     * 10
     */
    private BigDecimal factorB;
    /**
     * 15
     */
    private BigDecimal factorC;
    /**
     * 
     */
    private Integer stockDays;
    /**
     * 
     */
    private Integer inventoryNotice;

    public StockAdviceSetting toStockAdviceSetting(Long customerId){
        StockAdviceSetting stockAdviceSetting=new StockAdviceSetting();
        stockAdviceSetting.setCustomerId(customerId);
        stockAdviceSetting.setFactorA(factorA);
        stockAdviceSetting.setFactorB(factorB);
        stockAdviceSetting.setFactorC(factorC);
        stockAdviceSetting.setStockDays(stockDays);
        stockAdviceSetting.setInventoryNotice(inventoryNotice);
        return stockAdviceSetting;
    }

}
