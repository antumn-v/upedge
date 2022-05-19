package com.upedge.common.model.store.request;

import com.upedge.common.model.user.vo.Session;
import lombok.Data;

import java.util.List;

@Data
public class CustomStoreSelectRequest {

    Session session;

    List<String> storeNames;
}
