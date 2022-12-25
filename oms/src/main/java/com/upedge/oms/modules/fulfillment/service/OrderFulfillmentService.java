package com.upedge.oms.modules.fulfillment.service;

import com.upedge.oms.modules.pack.entity.OrderPackage;

public interface OrderFulfillmentService {

    void reFulfillment(Long id);

    void  reUploadStore();

    void orderFulfillment(Long id);

    boolean orderFulfillment(OrderPackage orderPackage,boolean isPreUpload);
}
