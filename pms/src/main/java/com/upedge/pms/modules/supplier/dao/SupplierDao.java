package com.upedge.pms.modules.supplier.dao;

import com.upedge.pms.modules.supplier.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface SupplierDao{

    Supplier selectByLoginId(String supplierLoginId);

    Supplier selectByPrimaryKey(Supplier record);

    int deleteByPrimaryKey(Supplier record);

    int updateByPrimaryKey(Supplier record);

    int updateByPrimaryKeySelective(Supplier record);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    int insertByBatch(List<Supplier> list);

    List<Supplier> select(Page<Supplier> record);

    long count(Page<Supplier> record);

}
