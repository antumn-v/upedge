package com.upedge.ums.modules.store.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.Store;

/**
 * @author author
 */
public class StoreListRequest extends Page<Store> {

    private String orderBy = "create_time desc";

}
