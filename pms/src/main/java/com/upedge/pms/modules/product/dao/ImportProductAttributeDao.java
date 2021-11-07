package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.vo.AppProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductAttributeDao{

    List<String> selectImportedSourceProductIds(@Param("customerId") Long customerId,
                                                @Param("products") List<AppProductVo> products);

    int updateStateByIds(@Param("ids") List<Long> ids, @Param("state") Integer state);

    int updateStateByProductId(@Param("productId") Long productId,
                               @Param("customerId") Long customerId,
                               @Param("state") Integer state);

    ImportProductAttribute selectByPlatId(@Param("storeId") Long storeId,
                                          @Param("platProductId") String platProductId);

    ImportProductAttribute selectByPrimaryKey(Long id);

    int deleteProductById(Long id);

    int deleteByPrimaryKey(ImportProductAttribute record);

    int updateByPrimaryKey(ImportProductAttribute record);

    int updateByPrimaryKeySelective(ImportProductAttribute record);

    int insert(ImportProductAttribute record);

    int insertSelective(ImportProductAttribute record);

    int insertByBatch(List<ImportProductAttribute> list);

    List<ImportProductAttribute> select(Page<ImportProductAttribute> record);

    long count(Page<ImportProductAttribute> record);

}