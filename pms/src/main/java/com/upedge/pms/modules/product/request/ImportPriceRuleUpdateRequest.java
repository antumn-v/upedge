package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportPriceRule;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ImportPriceRuleUpdateRequest{

    /**
     * 
     */
    private Long userId;
    /**
     * 
     */
    private BigDecimal rangeStart;
    /**
     * 
     */
    private BigDecimal rangeEnd;
    /**
     * 
     */
    private Integer markupType;
    /**
     * 
     */
    private BigDecimal markupVal;
    /**
     * 
     */
    private Integer compareMarkupType;
    /**
     * 
     */
    private BigDecimal compareMarkupVal;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public ImportPriceRule toImportPriceRule(Long id){
        ImportPriceRule importPriceRule=new ImportPriceRule();
        importPriceRule.setId(id);
        importPriceRule.setUserId(userId);
        importPriceRule.setRangeStart(rangeStart);
        importPriceRule.setRangeEnd(rangeEnd);
        importPriceRule.setMarkupType(markupType);
        importPriceRule.setMarkupVal(markupVal);
        importPriceRule.setCompareMarkupType(compareMarkupType);
        importPriceRule.setCompareMarkupVal(compareMarkupVal);
        importPriceRule.setCustomerId(customerId);
        importPriceRule.setCreateTime(createTime);
        importPriceRule.setUpdateTime(updateTime);
        return importPriceRule;
    }

}
