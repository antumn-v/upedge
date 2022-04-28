package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2020/2/21.
 */
public class PurchaseInDetailList {

    List<ApiInPurchaseDetail> ProductInfoList=new ArrayList<>();

    @XmlElement(name="ApiInPurchaseDetail")
    public List<ApiInPurchaseDetail> getProductInfoList() {
        return ProductInfoList;
    }

    public void setProductInfoList(List<ApiInPurchaseDetail> productInfoList) {
        ProductInfoList = productInfoList;
    }
}
