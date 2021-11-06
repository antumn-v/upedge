package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;

/**
 * @author author
 */
public class ImportProductAttributeListRequest extends Page<ImportProductAttribute> {

    private String orderBy = "create_time desc";
}
