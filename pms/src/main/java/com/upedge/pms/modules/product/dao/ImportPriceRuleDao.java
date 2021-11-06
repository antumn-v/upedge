package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportPriceRule;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface ImportPriceRuleDao{

    ImportPriceRule selectRestPriceRule(@Param("customerId") Long customerId,
                                        @Param("price") BigDecimal price);

    List<ImportPriceRule> selectByCustomerId(Long customerId);

    int deleteByCustomerId(Long customerId);

    ImportPriceRule selectByPrimaryKey(ImportPriceRule record);

    int deleteByPrimaryKey(ImportPriceRule record);

    int updateByPrimaryKey(ImportPriceRule record);

    int updateByPrimaryKeySelective(ImportPriceRule record);

    int insert(ImportPriceRule record);

    int insertSelective(ImportPriceRule record);

    int insertByBatch(List<ImportPriceRule> list);

    List<ImportPriceRule> select(Page<ImportPriceRule> record);

    long count(Page<ImportPriceRule> record);

}
