package com.upedge.pms.modules.supplier.service;

import com.upedge.pms.modules.supplier.entity.Supplier;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface SupplierService{

    Supplier selectByLoginId(String supplierLoginId);

    Supplier selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(Supplier record);

    int updateByPrimaryKeySelective(Supplier record);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    List<Supplier> select(Page<Supplier> record);

    long count(Page<Supplier> record);
}

