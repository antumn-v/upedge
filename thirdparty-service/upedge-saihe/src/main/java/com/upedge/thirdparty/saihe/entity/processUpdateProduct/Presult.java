package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/4.
 */
public class Presult {

    ApiUploadResult apiUploadResult;

    @XmlElement(name="ApiUploadResult")
    public ApiUploadResult getApiUploadResult() {
        return apiUploadResult;
    }

    public void setApiUploadResult(ApiUploadResult apiUploadResult) {
        this.apiUploadResult = apiUploadResult;
    }
}
