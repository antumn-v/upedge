package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.request.UploadProductToStoreRequest;
import com.upedge.pms.modules.product.response.UploadProductToStoreResponse;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductAttributeService{

    void updateStateById(Long id, Integer state);

    UploadProductToStoreResponse importProductToStore(UploadProductToStoreRequest request, Session session);

    List<Long> getCustomerAllUnImportIds(Long customerId);

    ImportProductAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportProductAttribute record);

    int updateByPrimaryKeySelective(ImportProductAttribute record);

    int insert(ImportProductAttribute record);

    int insertSelective(ImportProductAttribute record);

    List<ImportProductAttribute> select(Page<ImportProductAttribute> record);

    long count(Page<ImportProductAttribute> record);
}

