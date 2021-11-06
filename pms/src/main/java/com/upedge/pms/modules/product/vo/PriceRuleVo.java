package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ImportPriceCents;
import com.upedge.pms.modules.product.entity.ImportPriceRule;
import lombok.Data;

import java.util.List;

/**
 * @author 海桐
 */
@Data
public class PriceRuleVo {

    private ImportPriceCents cents;

    private List<ImportPriceRule> rules;
}
