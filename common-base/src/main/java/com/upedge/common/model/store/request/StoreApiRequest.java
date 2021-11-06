package com.upedge.common.model.store.request;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.model.store.StoreVo;
import lombok.Data;

@Data
public class StoreApiRequest {


    private StoreVo storeVo;

    private JSONObject jsonObject;

    private String id;
}
