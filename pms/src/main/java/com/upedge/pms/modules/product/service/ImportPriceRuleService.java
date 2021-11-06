package com.upedge.pms.modules.product.service;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportPriceRule;
import com.upedge.pms.modules.product.response.ImportPriceRuleListResponse;
import com.upedge.pms.modules.product.response.ImportPriceRuleUpdateResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface ImportPriceRuleService{


    ImportPriceRuleListResponse selectPriceRule();

    ImportPriceRuleUpdateResponse updatePriceRule(JSONObject jsonObject);

    ImportPriceRule selectByPrimaryKey(Long id);

    BigDecimal resetPrice(Long customerId, BigDecimal price);

    BigDecimal resetComparePrice(Long customerId, BigDecimal price);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportPriceRule record);

    int updateByPrimaryKeySelective(ImportPriceRule record);

    int insert(ImportPriceRule record);

    int insertSelective(ImportPriceRule record);

    List<ImportPriceRule> select(Page<ImportPriceRule> record);

    long count(Page<ImportPriceRule> record);
}

